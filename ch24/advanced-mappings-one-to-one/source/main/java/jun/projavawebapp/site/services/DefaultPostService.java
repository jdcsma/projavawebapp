package jun.projavawebapp.site.services;

import jun.projavawebapp.site.entities.Post;
import jun.projavawebapp.site.repositories.PostRepository;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
public class DefaultPostService implements PostService {

    private PostRepository postRepository;

    @Transactional
    @Override
    public List<Post> getAllPosts() {
        List<Post> posts = new ArrayList<>();
        this.postRepository.findAll().forEach(posts::add);
        return posts;
    }

    @Transactional
    @Override
    public Post getPost(long id) {
        return this.postRepository.findById(id).orElse(null);
    }

    @Transactional
    @Override
    public void deletePost(long id) {
        this.postRepository.deleteById(id);
    }

    @Transactional
    @Override
    public void savePost(Post post) {
        this.postRepository.save(post);
    }

    @Inject
    public void setPostRepository(PostRepository postRepository) {
        this.postRepository = postRepository;
    }
}
