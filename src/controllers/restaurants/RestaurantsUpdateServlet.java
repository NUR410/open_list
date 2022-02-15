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

            int open_time = 0;
            if(!request.getParameter("open_time").equals("")){
                String ot = request.getParameter("open_time").replace(":", "");
                open_time = Integer.parseInt(ot);
            }
            int close_time = 0;
            if(!request.getParameter("close_time").equals("")){
                String ct = request.getParameter("close_time").replace(":", "");
                close_time = Integer.parseInt(ct);
            }
            r.setClose_time(close_time);
            r.setOpen_time(open_time);

            if(request.getParameterValues("closed_day") != null){
                String closed_day = String.join(",", request.getParameterValues("closed_day"));
                r.setClosed_day(closed_day);
            }else{
                String closed_day = "無休";
                r.setClosed_day(closed_day);
            }

            r.setOpen(0);
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

                response.sendRedirect(request.getContextPath() + "/restaurants/show?id=" +r.getId());
            }
        }
    }

}