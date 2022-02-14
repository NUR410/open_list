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
    ),
    @NamedQuery(
            name = "getOpenRestaurants",
            query = "SELECT r FROM Restaurant AS r WHERE"
                    //開店時間＜閉店時間の場合
                    + "(((r.open_time < r.close_time) "
                    //今日が営業日で
                    + "AND (r.closed_day NOT LIKE :today) "
                    //開店時間＜現時刻 かつ 現時刻＜閉店時間 のとき
                    + "AND (r.open_time < :ct) AND (:ct < r.close_time))"
                    // また、開店時間＞閉店時間の場合
                    + "OR ((r.open_time > r.close_time) "
                    //今日が営業日で、開店時間＜現時刻
                    + "AND (((r.closed_day NOT LIKE :today) AND (r.open_time < :ct))"
                    // また、昨日が営業日の場合で 現時刻＜閉店時間 のとき
                    + "OR ((r.closed_day NOT LIKE :day_before) AND (:ct < r.close_time))))) "
                    + "ORDER BY r.close_time ASC"
    ),
    @NamedQuery(
            name = "getOpenRestaurantsCount",
            query = "SELECT COUNT(r) FROM Restaurant AS r WHERE"
                    + "(((r.open_time < r.close_time) "
                    + "AND (r.closed_day NOT LIKE :today) "
                    + "AND (r.open_time < :ct) AND (:ct < r.close_time))"
                    + "OR ((r.open_time > r.close_time) "
                    + "AND (((r.closed_day NOT LIKE :today) AND (r.open_time < :ct))"
                    + "OR ((r.closed_day NOT LIKE :day_before) AND (:ct < r.close_time))))) "
                    + "ORDER BY r.close_time ASC"
    ),
    @NamedQuery(
            name = "getCloseRestaurants",
            query = "SELECT r FROM Restaurant AS r WHERE"
                    //開店時間＜閉店時間の場合は
                    + "(((r.open_time < r.close_time) "
                    //現時刻＜開店時間、閉店時間＜現時刻、今日が休業日
                    + "AND ((:ct < r.open_time) OR (r.close_time < :ct) OR (r.closed_day LIKE :today)))"
                    //開店時間＞閉店時間の場合は
                    + "OR ((r.open_time > r.close_time) "
                    //開店時間＜現時刻、かつ現時刻＜閉店時間の時、または
                    + "AND (((r.close_time < :ct) AND (:ct < r.open_time)) "
                    //今日が休業日で閉店時間＜現時刻
                    + "OR ((r.closed_day LIKE :today) AND (r.close_time < :ct)) "
                    //前日が休業日で現時刻＜開店時間
                    + "OR ((r.closed_day LIKE :day_before) AND (:ct < r.open_time))))) "
                    + "ORDER BY r.close_time ASC"
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
    private int open_time;

    @Column(name = "close_time")
    private int close_time;

    @Column(name = "closed_day")
    private String closed_day;

    @Column(name = "created_at", nullable = false)
    private Timestamp created_at;

    @Column(name = "updated_at", nullable = false)
    private Timestamp updated_at;

    @Column(name = "open")
    private int open;

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

    public int getOpen_time() {
        return open_time;
    }

    public void setOpen_time(int open_time) {
        this.open_time = open_time;
    }

    public int getClose_time() {
        return close_time;
    }

    public void setClose_time(int close_time) {
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

    public int getOpen() {
        return open;
    }

    public void setOpen(int open) {
        this.open = open;
    }
}