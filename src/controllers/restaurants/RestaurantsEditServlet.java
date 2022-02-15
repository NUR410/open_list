package controllers.restaurants;

import java.io.IOException;
import java.util.ArrayList;
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
 * Servlet implementation class ReportsEditServlet
 */
@WebServlet("/restaurants/edit")
public class RestaurantsEditServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public RestaurantsEditServlet() {
        super();
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        EntityManager em = DBUtil.createEntityManager();

        Restaurant r = em.find(Restaurant.class, Integer.parseInt(request.getParameter("id")));

        em.close();

        User login_user = (User)request.getSession().getAttribute("login_user");
        if(r != null && login_user.getId() == r.getUser().getId()) {
            request.setAttribute("restaurant", r);
            request.setAttribute("_token", request.getSession().getId());
            request.getSession().setAttribute("restaurant_id", r.getId());

            String[] day_week = {"月","火","水","木","金","土","日","祝"};
            List<Boolean> cd = new ArrayList<>();
            for(String dw : day_week){
                cd.add(r.getClosed_day().contains(dw));
            }
            request.setAttribute("cd", cd);
        }

        RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/restaurants/edit.jsp");
        rd.forward(request, response);
    }

}