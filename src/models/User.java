package models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Table(name = "users")
@NamedQueries({
    @NamedQuery(
        name = "getAllUsers",
        query = "SELECT u FROM User AS u WHERE u.delete_flag = 0 ORDER BY u.id DESC"
    ),
    @NamedQuery(
        name = "getUsersCount",
        query = "SELECT COUNT(u) FROM User AS u WHERE u.delete_flag = 0"
    ),
    @NamedQuery(
        name = "checkRegisteredName",
        query = "SELECT COUNT(u) FROM User AS u WHERE u.name = :name"
    ),
    @NamedQuery(
            name = "checkLoginNameAndPassword",
            query = "SELECT u FROM User AS u WHERE u.delete_flag = 0 AND u.name = :name AND u.password = :pass"
        )
})
@Entity
public class User {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @Column(name = "password", length = 64, nullable = false)
    private String password;

    @Column(name= "delete_flag", nullable = false)
    private Integer delete_flag;

    public Integer getId(){
        return id;
    }

    public void setId(Integer id){
        this.id = id;
    }

    public String getName(){
        return name;
    }

    public void setName(String name){
        this.name = name;
    }

    public String getPassword(){
        return password;
    }

    public void setPassword(String password){
        this.password = password;
    }

    public Integer getDelete_flag() {
        return delete_flag;
    }

    public void setDelete_flag(Integer delete_flag) {
        this.delete_flag = delete_flag;
    }
 }
