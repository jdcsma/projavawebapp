package jun.projavawebapp.site.controllers;

import jun.projavawebapp.site.entities.NewsArticle;
import jun.projavawebapp.site.services.NewsArticleService;
import org.apache.commons.lang3.StringUtils;
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
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
public class MainController {

    private static final Logger log = LogManager.getLogger();

    private NewsArticleService newsArticleService;

    @RequestMapping(value = "/newsArticles")
    public String listPosts(Map<String, Object> model) {
        List<NewsArticle> articles = this.newsArticleService.getAllNewsArticles();
        model.put("articles", articles);
        return "list-news-articles";
    }

    @RequestMapping(value = "/newsArticle")
    public String news_article(
            Map<String, Object> model,
            @RequestParam("id") long articleId) {
        NewsArticle article = this.newsArticleService.getNewsArticle(articleId);
        List<NewsArticle> articles = new ArrayList<>();
        if (article != null) {
            articles.add(article);
        }
        model.put("articles", articles);

        return "list-news-articles";
    }

    @RequestMapping(value = "/saveNewsArticle")
    public View saveNewsArticle(
            @NotNull @RequestParam("title") String title,
            @NotNull @RequestParam("content") String content) {

        NewsArticle article = new NewsArticle();
        article.setTitle(title);
        article.setContent(content);
        article.setDateCreated(Instant.now());

        this.newsArticleService.saveNewsArticle(article);

        return new RedirectView("/newsArticles", true, false);
    }

    @RequestMapping(value = "/modifyNewsArticle")
    public View modifyNewsArticle(
            @NotNull @RequestParam("id") long articleId,
            @NotNull @RequestParam("title") String title,
            @NotNull @RequestParam("content") String content) {

        NewsArticle article = this.newsArticleService.getNewsArticle(articleId);
        article.setTitle(title);
        article.setContent(content);

        this.newsArticleService.saveNewsArticle(article);

        return new RedirectView("/newsArticles", true, false);
    }

    @RequestMapping(value = "/deleteNewsArticle")
    public View deleteNewsArticle(@RequestParam("id") long id)
            throws ServletException, IOException {

        this.newsArticleService.deleteNewArticle(id);

        return new RedirectView("/newsArticles", true, false);
    }

    @Inject
    public void setNewsArticleService(NewsArticleService newsArticleService) {
        this.newsArticleService = newsArticleService;
    }
}
