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