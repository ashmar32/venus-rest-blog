package ashmar32.venusrestblog.controller;

import ashmar32.venusrestblog.data.Category;
import ashmar32.venusrestblog.data.User;
import ashmar32.venusrestblog.misc.FieldHelper;
import ashmar32.venusrestblog.repository.CategoriesRepository;
import ashmar32.venusrestblog.repository.PostsRepository;
import ashmar32.venusrestblog.data.Post;
import ashmar32.venusrestblog.repository.UsersRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@RestController
@RequestMapping(value = "/api/posts", produces = "application/json")
public class PostsController {
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
    public void createPost(@RequestBody Post newPost) {
        // use a fake author for the post
        User author = usersRepository.findById(8L).get();
//        System.out.println(usersRepository.findById(1L).get());
        newPost.setAuthor(author);

        Category cat1 = categoriesRepository.findById(1L).get();
        Category cat2 = categoriesRepository.findById(2L).get();
        newPost.setCategories(new ArrayList<>());
        newPost.getCategories().add(cat1);
        newPost.getCategories().add(cat2);
        postsRepository.save(newPost);
    }

    @DeleteMapping("/{id}")
    public void deletePostById(@PathVariable long id) {
        postsRepository.deleteById(id);
        }

    @PutMapping("/{id}")
    public void updatePost(@RequestBody Post updatedPost, @PathVariable long id) {
//        In case id is not in the request body that is updated post set it with the path                 variable id
        updatedPost.setId(id);

        Optional<Post> originalPost = postsRepository.findById(id);
        if (originalPost.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Post " + id + " not found");
        }
//        Copy any new field values FROM updatedPost TO originalPost
        BeanUtils.copyProperties(updatedPost, originalPost.get(), FieldHelper.getNullPropertyNames(updatedPost));
        postsRepository.save(updatedPost);

//         find the post to update in the posts list
//        Post post = findPostById(id);
//        if(post == null) {
//            System.out.println("Post not found");
//        } else {
//            if(updatedPost.getTitle() != null) {
//                post.setTitle(updatedPost.getTitle());
//            }
//            if(updatedPost.getContent() != null) {
//                post.setContent(updatedPost.getContent());
//            }
//            return;
//        }
//        throw new RuntimeException("Post not found");
    }
}