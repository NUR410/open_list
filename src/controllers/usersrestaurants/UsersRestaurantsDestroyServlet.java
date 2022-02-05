package controllers.usersrestaurants;

import java.io.IOException;

import javax.persistence.EntityManager;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import models.Restaurant;
import models.User;
import models.UsersRestaurant;
import utils.DBUtil;

/**
 * Servlet implementation class UsersRestaurantsDestroyServlet
 */
@WebServlet("/usersrestaurants/destroy")
public class UsersRestaurantsDestroyServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public UsersRestaurantsDestroyServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        EntityManager em = DBUtil.createEntityManager();

        Restaurant r = em.find(Restaurant.class, Integer.parseInt(request.getParameter("id")));
        User u = (User)request.getSession().getAttribute("login_user");

        UsersRestaurant ur = em.createNamedQuery("getUsersRestaurants", UsersRestaurant.class)
                .setParameter("user", u)
                .setParameter("rest", r)
                .getSingleResult();

        em.getTransaction().begin();
        em.remove(ur);       // データ削除
        em.getTransaction().commit();
        em.close();


        request.getSession().setAttribute("flush", "お気に入り解除しました。");
        request.setAttribute("restaurant", r);
        request.setAttribute("_token", request.getSession().getId());

        RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/restaurants/show.jsp");
        rd.forward(request, response);
        }
}