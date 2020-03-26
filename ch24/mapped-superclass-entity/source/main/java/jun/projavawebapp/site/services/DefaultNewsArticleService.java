package jun.projavawebapp.site.services;

import jun.projavawebapp.site.entities.BaseEntity;
import jun.projavawebapp.site.entities.NewsArticle;
import jun.projavawebapp.site.repositories.NewsArticleRepository;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import javax.transaction.Transactional;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Service
public class DefaultNewsArticleService implements NewsArticleService {

    private NewsArticleRepository newsArticleRepository;

    @Transactional
    @Override
    public List<NewsArticle> getAllNewsArticles() {
        List<NewsArticle> articles = new ArrayList<>();
        this.newsArticleRepository.findAll().forEach(articles::add);
        return articles;
    }

    @Transactional
    @Override
    public NewsArticle getNewsArticle(long id) {
        return this.newsArticleRepository.findById(id).orElse(null);
    }

    @Transactional
    @Override
    public void deleteNewArticle(long id) {
        this.newsArticleRepository.deleteById(id);
    }

    @Transactional
    @Override
    public void saveNewsArticle(NewsArticle article) {
        if (article.getId() < 1) {
            article.setDateCreated(Instant.now());
        } else {
            article.setDateModified(Instant.now());
        }
        this.newsArticleRepository.save(article);
    }

    @Inject
    public void setNewsArticleRepository(NewsArticleRepository newsArticleRepository) {
        this.newsArticleRepository = newsArticleRepository;
    }
}
