package jun.projavawebapp.site.entities;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.io.Serializable;
import java.util.Calendar;

@Table(name = "Publishers",
        indexes = {
                @Index(name = "Publishers_Names", columnList = "PublisherName")
        })
@Entity(name = "PublisherEntity")
public class Publisher implements Serializable {

    private long id;
    private String name;
    private String address;
    private Calendar dateFounded;

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE,
            generator = "PublisherGenerator")
    @TableGenerator(name = "PublisherGenerator", table = "SurrogateKeys",
            pkColumnName = "TableName", pkColumnValue = "Publishers",
            valueColumnName = "KeyValue", initialValue = 1, allocationSize = 1)
    @Column(name = "PublisherId")
    public long getId() {
        return this.id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Basic
    @Column(name = "PublisherName", nullable = false)
    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Basic
    @Column(nullable = false)
    public String getAddress() {
        return this.address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Temporal(TemporalType.DATE)
    public Calendar getDateFounded() {
        return dateFounded;
    }

    public void setDateFounded(Calendar dateFounded) {
        this.dateFounded = dateFounded;
    }
}
