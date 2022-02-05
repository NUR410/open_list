package controllers.restaurants;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.List;

import javax.persistence.EntityManager;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import models.Restaurant;
import models.validators.RestaurantValidator;
import utils.DBUtil;

/**
 * Servlet implementation class ReportsUpdateServlet
 */
@WebServlet("/restaurants/update")
public class RestaurantsUpdateServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public RestaurantsUpdateServlet() {
        super();
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String _token = (String)request.getParameter("_token");
        if(_token != null && _token.equals(request.getSession().getId())) {
            EntityManager em = DBUtil.createEntityManager();

            Restaurant r = em.find(Restaurant.class, (Integer)(request.getSession().getAttribute("restaurant_id")));

            r.setName(request.getParameter("name"));
            r.setOpen_time(request.getParameter("open_time"));
            r.setClose_time(request.getParameter("close_time"));

            String cd[] = request.getParameterValues("closed_day");
            String closed_day = String.join(",", cd);
            r.setClosed_day(closed_day);

            r.setUpdated_at(new Timestamp(System.currentTimeMillis()));

            List<String> errors = RestaurantValidator.validate(r);
            if(errors.size() > 0) {
                em.close();

                request.setAttribute("_token", request.getSession().getId());
                request.setAttribute("restaurant", r);
                request.setAttribute("errors", errors);

                RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/restaurants/edit.jsp");
                rd.forward(request, response);
            } else {
                em.getTransaction().begin();
                em.getTransaction().commit();
                em.close();
                request.getSession().setAttribute("flush", "更新が完了しました。");

                request.getSession().removeAttribute("restaurant_id");

                response.sendRedirect(request.getContextPath() + "/restaurants/index");
            }
        }
    }

}