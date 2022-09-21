package ashmar32.venusrestblog.controller;

import ashmar32.venusrestblog.data.Post;
import ashmar32.venusrestblog.data.User;
import ashmar32.venusrestblog.data.UserRole;
import ashmar32.venusrestblog.misc.FieldHelper;
import ashmar32.venusrestblog.repository.CategoriesRepository;
import ashmar32.venusrestblog.repository.PostsRepository;
import ashmar32.venusrestblog.repository.UsersRepository;
import ashmar32.venusrestblog.services.EmailService;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@RestController
@RequestMapping(value = "/api/posts", produces = "application/json")
public class PostsController {
    private EmailService emailService;
    private PostsRepository postsRepository;
    private UsersRepository usersRepository;
    private CategoriesRepository categoriesRepository;

    @GetMapping("")
//    @RequestMapping(value = "/", method = RequestMethod.GET)
    public List<Post> fetchPosts() {
        return postsRepository.findAll();
    }

    @GetMapping("/{id}")
    public Optional<Post> fetchPostById(@PathVariable long id) {
        return postsRepository.findById(id);
    }

    @PostMapping("")
    @PreAuthorize("hasAuthority('USER') || hasAuthority('ADMIN')")
    public void createPost(@RequestBody Post newPost, OAuth2Authentication auth) {
        if(newPost.getTitle() == null || newPost.getTitle().length() < 1) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Title cannot be blank!");
        }
        if(newPost.getContent() == null || newPost.getContent().length() < 1) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Content cannot be blank!");
        }

        String userName = auth.getName();
        User author = usersRepository.findByUsername(userName);
        newPost.setAuthor(author);

        postsRepository.save(newPost);

        emailService.prepareAndSend(newPost, "Hey man you made a post", "See subject");
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('USER') || hasAuthority('ADMIN')")
    public void deletePostById(@PathVariable long id, OAuth2Authentication auth) {
        String userName = auth.getName();
        User loggedInUser = usersRepository.findByUsername(userName);

        Optional<Post> optionalPost = postsRepository.findById(id);
        if(optionalPost.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Post id " + id + " not found");
        }
        // grab the original post from the optional and check the logged in user
        Post originalPost = optionalPost.get();

        // admin can delete anyone's post. author of the post can delete only their posts
        if(loggedInUser.getRole() != UserRole.ADMIN && originalPost.getAuthor().getId() != loggedInUser.getId()) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Not yo post!");
        }

        postsRepository.deleteById(id);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('USER') || hasAuthority('ADMIN')")
    public void updatePost(@RequestBody Post updatedPost, @PathVariable long id, OAuth2Authentication auth) {
        Optional<Post> optionalPost = postsRepository.findById(id);
        if(optionalPost.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Post " + id + " not found");
        }
        Post originalPost = optionalPost.get();

        String userName = auth.getName();
        User loggedInUser = usersRepository.findByUsername(userName);
        // admin can update anyone's post. author of the post can update only their posts
        if(loggedInUser.getRole() != UserRole.ADMIN && originalPost.getAuthor().getId() != loggedInUser.getId()) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Not yo post!");
        }

        // in case id is not in the request body (i.e., updatedPost), set it
        // with the path variable id
        updatedPost.setId(id);

        // copy any new field values FROM updatedPost TO originalPost
        BeanUtils.copyProperties(updatedPost, originalPost, FieldHelper.getNullPropertyNames(updatedPost));

        postsRepository.save(originalPost);
    }
}