package ashmar32.venusrestblog.controller;

import ashmar32.venusrestblog.data.Post;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "/api/posts", produces = "application/json")
public class PostsController {
    @GetMapping("/")

//    @RequestMapping(value = "/", method = RequestMethod.GET) is the same as @GetMapping either can be used
    private List<Post> fetchPosts() {
        List<Post> posts = new ArrayList<>();
        posts.add(new Post(1L, "Post 1", "This is Post 1"));
        posts.add(new Post(2L, "Post 2", "This is Post 2"));

        return posts;
    }
@GetMapping("/{id}")
public Post fetchPostById(@PathVariable long id) {
    // TODO: search through the list of posts
//    and return the post that matches the given id
    for (Post post:
         ) {

    }

    }
}
    @GetMapping("test")
    public String doTest() {
        return "<h1>hello</h1>";
    }

    @PostMapping("/")
    private void createPost(@RequestBody Post newPost) {

    }
}
