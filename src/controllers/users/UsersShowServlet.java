package controllers.users;

import java.io.IOException;
import java.util.List;

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
 * Servlet implementation class EmployeesShowServlet
 */
@WebServlet("/users/show")
public class UsersShowServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public UsersShowServlet() {
        super();
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        EntityManager em = DBUtil.createEntityManager();

        User u = em.find(User.class, Integer.parseInt(request.getParameter("id")));

        int page;
        try{
            page = Integer.parseInt(request.getParameter("page"));
        } catch(Exception e) {
            page = 1;
        }
        List<Restaurant> restaurants = em.createNamedQuery("getMyAllRestaurants", Restaurant.class)
                .setParameter("user", u)
                .setFirstResult(15 * (page - 1))
                .setMaxResults(15)
                .getResultList();

        long restaurants_count = (long)em.createNamedQuery("getMyRestaurantsCount", Long.class)
                .setParameter("user", u)
                .getSingleResult();

        em.close();

        request.setAttribute("user", u);
        request.setAttribute("restaurants", restaurants);
        request.setAttribute("size", restaurants.size());
        request.setAttribute("restaurants_count", restaurants_count);
        request.setAttribute("page", page);

        RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/users/show.jsp");
        rd.forward(request, response);
    }

}