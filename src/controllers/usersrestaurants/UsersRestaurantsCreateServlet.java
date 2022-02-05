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
 * Servlet implementation class UsersRestaurantCreateServlet
 */
@WebServlet("/usersrestaurants/create")
public class UsersRestaurantsCreateServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public UsersRestaurantsCreateServlet() {
        super();
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
            EntityManager em = DBUtil.createEntityManager();

            Restaurant r = em.find(Restaurant.class, Integer.parseInt(request.getParameter("id")));
            UsersRestaurant ur = new UsersRestaurant();
            ur.setUser((User)request.getSession().getAttribute("login_user"));
            ur.setRestaurant(r);


            em.getTransaction().begin();
            em.persist(ur);
            em.getTransaction().commit();
            em.close();

            request.getSession().setAttribute("flush", "お気に入りに登録しました。");
            request.setAttribute("restaurant", r);
            request.setAttribute("_token", request.getSession().getId());
            request.setAttribute("favorite","true");

            RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/restaurants/show.jsp");
            rd.forward(request, response);
            }
}
