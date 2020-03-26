package jun.projavawebapp.site.services;

import jun.projavawebapp.site.entities.NewsArticle;

import java.util.List;

public interface NewsArticleService {

    List<NewsArticle> getAllNewsArticles();

    NewsArticle getNewsArticle(long id);

    void deleteNewArticle(long id);

    void saveNewsArticle(NewsArticle article);
}
