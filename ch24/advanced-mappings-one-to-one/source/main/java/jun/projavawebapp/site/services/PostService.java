package jun.projavawebapp.site.services;

import jun.projavawebapp.site.entities.Post;

import java.util.List;

public interface PostService {

    List<Post> getAllPosts();

    Post getPost(long id);

    void deletePost(long id);

    void savePost(Post post);
}
