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
import models.User;
import models.UsersRestaurant;
import models.validators.RestaurantValidator;
import utils.DBUtil;

/**
 * Servlet implementation class ReportsCreateServlet
 */
@WebServlet("/restaurants/create")
public class RestaurantsCreateServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public RestaurantsCreateServlet() {
        super();
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String _token = (String)request.getParameter("_token");
        if(_token != null && _token.equals(request.getSession().getId())) {
            EntityManager em = DBUtil.createEntityManager();

            Restaurant r = new Restaurant();

            r.setUser((User)request.getSession().getAttribute("login_user"));
            r.setName(request.getParameter("name"));

            String ot = request.getParameter("open_time").replace(":", "");
            String ct = request.getParameter("close_time").replace(":", "");
            int open_time = Integer.parseInt(ot);
            int close_time = Integer.parseInt(ct);
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
            Timestamp currentTime = new Timestamp(System.currentTimeMillis());
            r.setCreated_at(currentTime);
            r.setUpdated_at(currentTime);

            List<String> errors = RestaurantValidator.validate(r);
            if(errors.size() > 0) {
                em.close();

                request.setAttribute("_token", request.getSession().getId());
                request.setAttribute("restaurant", r);
                request.setAttribute("errors", errors);

                RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/restaurants/new.jsp");
                rd.forward(request, response);
            } else {
                em.getTransaction().begin();
                em.persist(r);

                UsersRestaurant ur = new UsersRestaurant();
                ur.setUser((User)request.getSession().getAttribute("login_user"));
                ur.setRestaurant(r);
                em.persist(ur);

                em.getTransaction().commit();
                em.close();
                request.getSession().setAttribute("flush", "登録が完了しました。");

                response.sendRedirect(request.getContextPath() + "/restaurants/index");
            }
        }
    }

}