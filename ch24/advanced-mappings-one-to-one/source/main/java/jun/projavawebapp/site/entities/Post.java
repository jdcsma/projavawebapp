package jun.projavawebapp.site.entities;

import javax.persistence.*;

@Entity
@Table(name = "posts")
public class Post {

    private long id;
    private String name;
    private PostDetail detail;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Basic
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @OneToOne(mappedBy = "post", fetch = FetchType.EAGER,
            cascade = CascadeType.ALL, orphanRemoval = true)
    public PostDetail getDetail() {
        return detail;
    }

    public void setDetail(PostDetail detail) {
        this.detail = detail;
    }

    public void addDetail(PostDetail detail) {
        this.detail = detail;
        detail.setPost(this);
    }

    public void removeDetail(PostDetail detail) {
        if (detail != null) {
            detail.setPost(null);
        }
        this.detail = null;
    }
}
