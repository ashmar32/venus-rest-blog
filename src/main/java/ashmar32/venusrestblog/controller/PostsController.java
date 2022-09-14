package ashmar32.venusrestblog.controller;

import ashmar32.venusrestblog.repository.PostsRepository;
import ashmar32.venusrestblog.data.Post;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/api/posts", produces = "application/json")
public class PostsController {
    private PostsRepository postsRepository;

    public PostsController(PostsRepository postsRepository) {
        this.postsRepository = postsRepository;
    }

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
        postsRepository.save(newPost);
    }

    @DeleteMapping("/{id}")
    public void deletePostById(@PathVariable long id) {
        postsRepository.deleteById(id);
        }

    @PutMapping("/{id}")
    public void updatePost(@RequestBody Post updatedPost, @PathVariable long id) {
//        In case id is not in the request body that is updated post set it with the path variable id
        updatedPost.setId(id);
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