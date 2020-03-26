package jun.projavawebapp.site.controllers;

import jun.projavawebapp.site.entities.Post;
import jun.projavawebapp.site.entities.PostComment;
import jun.projavawebapp.site.services.PostService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.view.RedirectView;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.validation.constraints.NotNull;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Controller
public class MainController {

    private static final Logger log = LogManager.getLogger();

    private PostService postService;

    @RequestMapping(value = "/posts")
    public String listPosts(Map<String, Object> model) {
        List<Post> posts = this.postService.getAllPosts();
        model.put("posts", posts);
        return "list-posts";
    }

    @RequestMapping(value = "/post")
    public String post(
            Map<String, Object> model,
            @RequestParam("id") long postId) {
        Post post = this.postService.getPost(postId);
        List<Post> posts = new ArrayList<>();
        if (post != null) {
            posts.add(post);
        }
        model.put("posts", posts);

        return "list-posts";
    }

    @RequestMapping(value = "/savePost")
    public View savePost(@RequestParam("name") String name) {

        Post post = new Post();
        post.setName(name);

        this.postService.savePost(post);

        return new RedirectView("/posts", true, false);
    }

    @RequestMapping(value = "/saveComment")
    public View saveComment(
            @RequestParam("id") long id,
            @RequestParam("review") String review) {

        PostComment comment = new PostComment();
        comment.setReview(review);
        comment.setCreatedOn(new Date());

        Post post = this.postService.getPost(id);
        post.addComment(comment);

        this.postService.savePost(post);

        return new RedirectView("/posts", true, false);
    }

    @RequestMapping(value = "/deletePost")
    public View deletePost(@RequestParam("id") long id)
            throws ServletException, IOException {

        this.postService.deletePost(id);

        return new RedirectView("/posts", true, false);
    }

    @Inject
    public void setPostService(PostService postService) {
        this.postService = postService;
    }
}
