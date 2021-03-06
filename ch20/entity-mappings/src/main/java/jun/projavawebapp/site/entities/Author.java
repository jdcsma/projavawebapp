package jun.projavawebapp.site.entities;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "Authors", indexes = {
        @Index(name = "Authors_Names", columnList = "AuthorName")
})
public class Author implements Serializable {

    private long id;
    private String name;
    private String emailAddress;

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE,
            generator = "AuthorGenerator")
    @TableGenerator(name = "AuthorGenerator", table = "SurrogateKeys",
            pkColumnName = "TableName", pkColumnValue = "Authors",
            valueColumnName = "KeyValue", initialValue = 1, allocationSize = 1)
    @Column(name = "AuthorId")
    public long getId() {
        return this.id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Basic
    @Column(name = "AuthorName")
    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Basic
    public String getEmailAddress() {
        return this.emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }
}
