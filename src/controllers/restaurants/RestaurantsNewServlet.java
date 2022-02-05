package controllers.restaurants;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import models.Restaurant;

/**
 * Servlet implementation class ReportsNewServlet
 */
@WebServlet("/restaurants/new")
public class RestaurantsNewServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public RestaurantsNewServlet() {
        super();
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("_token", request.getSession().getId());

        Restaurant r = new Restaurant();
        request.setAttribute("restaurant", r);

        RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/restaurants/new.jsp");
        rd.forward(request, response);
    }

}