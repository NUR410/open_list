package models;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Table(name = "restaurants")
@NamedQueries({
    @NamedQuery(
        name = "getAllRestaurants",
        query = "SELECT r FROM Restaurant AS r ORDER BY r.id DESC"
    ),
    @NamedQuery(
        name = "getRestaurantsCount",
        query = "SELECT COUNT(r) FROM Restaurant AS r"
    ),
    @NamedQuery(
            name = "getMyAllRestaurants",
            query = "SELECT r FROM Restaurant AS r WHERE r.user = :user ORDER BY r.id DESC"
    ),
    @NamedQuery(
            name = "getMyRestaurantsCount",
            query = "SELECT COUNT(r) FROM Restaurant AS r WHERE r.user = :user"
        )
})
@Entity
public class Restaurant {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "name", length = 255, nullable = false)
    private String name;

    @Column(name = "open_time")
    private String open_time;

    @Column(name = "close_time")
    private String close_time;

    @Column(name = "closed_day")
    private String closed_day;

    @Column(name = "created_at", nullable = false)
    private Timestamp created_at;

    @Column(name = "updated_at", nullable = false)
    private Timestamp updated_at;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOpen_time() {
        return open_time;
    }

    public void setOpen_time(String open_time) {
        this.open_time = open_time;
    }

    public String getClose_time() {
        return close_time;
    }

    public void setClose_time(String close_time) {
        this.close_time = close_time;
    }

    public String getClosed_day() {
        return closed_day;
    }

    public void setClosed_day(String closed_day) {
        this.closed_day = closed_day;
    }

    public Timestamp getCreated_at() {
        return created_at;
    }

    public void setCreated_at(Timestamp created_at) {
        this.created_at = created_at;
    }

    public Timestamp getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(Timestamp updated_at) {
        this.updated_at = updated_at;
    }
}