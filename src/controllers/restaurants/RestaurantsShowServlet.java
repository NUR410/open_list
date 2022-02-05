package controllers.restaurants;

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
import utils.DBUtil;

/**
 * Servlet implementation class ReportsShowServlet
 */
@WebServlet("/restaurants/show")
public class RestaurantsShowServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public RestaurantsShowServlet() {
        super();
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        EntityManager em = DBUtil.createEntityManager();

        Restaurant r = em.find(Restaurant.class, Integer.parseInt(request.getParameter("id")));
        User u = (User)request.getSession().getAttribute("login_user");

        long usersrest_count = (long)em.createNamedQuery("getUsersRestaurantsCount", Long.class)
                .setParameter("user", u)
                .setParameter("rest", r)
                .getSingleResult();
        boolean favorite = false;
        if(usersrest_count > 0){
            favorite = true;
        }

        em.close();

        request.setAttribute("favorite", favorite);
        request.setAttribute("restaurant", r);
        request.setAttribute("_token", request.getSession().getId());

        RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/restaurants/show.jsp");
        rd.forward(request, response);
    }

}