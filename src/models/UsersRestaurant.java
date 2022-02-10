package models;

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

@Table(name = "usersrestaurants")
@NamedQueries({
    @NamedQuery(
            name = "getMyAllUsersRestaurants",
            query = "SELECT ur FROM UsersRestaurant AS ur WHERE ur.user = :user"
    ),
    @NamedQuery(
            name = "getMyUsersRestaurantsCount",
            query = "SELECT COUNT(ur) FROM UsersRestaurant AS ur WHERE ur.user = :user"
    ),
    @NamedQuery(
            name = "getUsersRestaurantsCount",
            query = "SELECT COUNT(ur) FROM UsersRestaurant AS ur WHERE ur.user = :user and ur.restaurant = :rest"
    ),
    @NamedQuery(
            name = "getUsersRestaurants",
            query = "SELECT ur FROM UsersRestaurant AS ur WHERE ur.user = :user and ur.restaurant = :rest"
    ),
    @NamedQuery(
            name = "getOpenRestaurants",
            query = "SELECT ur FROM UsersRestaurant AS ur WHERE (ur.user = :user) "
                    //開店時間＜閉店時間の場合
                    + "AND (((ur.restaurant.open_time < ur.restaurant.close_time) "
                    //今日が営業日で
                    + "AND (ur.restaurant.closed_day NOT LIKE :today) "
                    //開店時間＜現時刻 かつ 現時刻＜閉店時間 のとき
                    + "AND (ur.restaurant.open_time < :ct) AND (:ct < ur.restaurant.close_time))"
                    // また、開店時間＞閉店時間の場合
                    + "OR ((ur.restaurant.open_time > ur.restaurant.close_time) "
                    //今日が営業日で、開店時間＜現時刻
                    + "AND (((ur.restaurant.closed_day NOT LIKE :today) AND (ur.restaurant.open_time < :ct))"
                    // また、昨日が営業日の場合で 現時刻＜閉店時間 のとき
                    + "OR ((ur.restaurant.closed_day NOT LIKE :day_before) AND (:ct < ur.restaurant.close_time))))) "
                    + "ORDER BY ur.restaurant.close_time ASC"
    ),
    @NamedQuery(
            name = "getOpenRestaurantsCount",
            query = "SELECT COUNT(ur) FROM UsersRestaurant AS ur WHERE (ur.user = :user) "
            + "AND (((ur.restaurant.open_time < ur.restaurant.close_time) "
            + "AND (ur.restaurant.closed_day NOT LIKE :today) "
            + "AND (ur.restaurant.open_time < :ct) AND (:ct < ur.restaurant.close_time))"
            + "OR ((ur.restaurant.open_time > ur.restaurant.close_time) "
            + "AND (((ur.restaurant.closed_day NOT LIKE :today) AND (ur.restaurant.open_time < :ct))"
            + "OR ((ur.restaurant.closed_day NOT LIKE :day_before) AND (:ct < ur.restaurant.close_time))))) "
            + "ORDER BY ur.restaurant.close_time ASC"
    ),
    @NamedQuery(
            name = "getCloseRestaurants",
            query = "SELECT ur FROM UsersRestaurant AS ur WHERE (ur.user = :user) "
                    //開店時間＜閉店時間の場合は
                    + "AND (((ur.restaurant.open_time < ur.restaurant.close_time) "
                    //現時刻＜開店時間、閉店時間＜現時刻、今日が休業日
                    + "AND ((:ct < ur.restaurant.open_time) OR (ur.restaurant.close_time < :ct) OR (ur.restaurant.closed_day LIKE :today)))"
                    //開店時間＞閉店時間の場合は
                    + "OR ((ur.restaurant.open_time > ur.restaurant.close_time) "
                    //開店時間＜現時刻、かつ現時刻＜閉店時間の時、または
                    + "AND (((ur.restaurant.close_time < :ct) AND (:ct < ur.restaurant.open_time)) "
                    //今日が休業日で閉店時間＜現時刻
                    + "OR ((ur.restaurant.closed_day LIKE :today) AND (ur.restaurant.close_time < :ct)) "
                    //前日が休業日で現時刻＜開店時間
                    + "OR ((ur.restaurant.closed_day LIKE :day_before) AND (:ct < ur.restaurant.open_time))))) "
                    + "ORDER BY ur.restaurant.close_time ASC"
    )
})
@Entity
public class UsersRestaurant {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "restaurant_id", nullable = false)
    private Restaurant restaurant;

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

    public Restaurant getRestaurant(){
        return restaurant;
    }

    public void setRestaurant(Restaurant restaurant){
        this.restaurant = restaurant;
    }
}