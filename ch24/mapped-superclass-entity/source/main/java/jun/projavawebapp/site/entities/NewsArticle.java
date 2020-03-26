package jun.projavawebapp.site.entities;

import javax.persistence.*;

@Entity
@Table(name = "news_articles")
@AttributeOverrides(value = {
        @AttributeOverride(name = "id", column = @Column(name = "article_id")),
        @AttributeOverride(name = "dateCreated", column = @Column(name = "date_created")),
        @AttributeOverride(name = "dateModified", column = @Column(name = "date_modified"))
})
public class NewsArticle extends AuditedEntity {

    private String title;
    private String content;

    @Basic
    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Basic
    public String getContent() {
        return this.content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
