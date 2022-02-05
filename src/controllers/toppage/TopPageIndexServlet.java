package controllers.toppage;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import javax.persistence.EntityManager;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import models.User;
import models.UsersRestaurant;
import utils.DBUtil;
/**
 * Servlet implementation class TopPageIndexServlet
 */
@WebServlet("/index.html")
public class TopPageIndexServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public TopPageIndexServlet() {
        super();
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        EntityManager em = DBUtil.createEntityManager();

        User login_user = (User)request.getSession().getAttribute("login_user");

        int page;
        try{
            page = Integer.parseInt(request.getParameter("page"));
        } catch(Exception e) {
            page = 1;
        }
        List<UsersRestaurant> usersrestaurants = em.createNamedQuery("getMyAllUsersRestaurants", UsersRestaurant.class)
                                  .setParameter("user", login_user)
                                  .setFirstResult(15 * (page - 1))
                                  .setMaxResults(15)
                                  .getResultList();

        long restaurants_count = (long)em.createNamedQuery("getMyUsersRestaurantsCount", Long.class)
                                     .setParameter("user", login_user)
                                     .getSingleResult();

        em.close();

        //現在の 日付・時刻・曜日 を取得
        LocalDateTime current = LocalDateTime.now();
        String today_date = DateTimeFormatter.ofPattern("yyyy/MM/dd").format(current);
        String current_time = DateTimeFormatter.ofPattern("HH:mm").format(current);
        String[] week_name = {"日", "月", "火", "水", "木", "金", "土"};
        String today_week = week_name[current.getDayOfWeek().getValue()];

        request.setAttribute("today_date", today_date);
        request.setAttribute("current_time", current_time);
        request.setAttribute("today_week", today_week);

        request.setAttribute("usersrestaurants", usersrestaurants);
        request.setAttribute("restaurants_count", restaurants_count);
        request.setAttribute("page", page);

        if(request.getSession().getAttribute("flush") != null) {
            request.setAttribute("flush", request.getSession().getAttribute("flush"));
            request.getSession().removeAttribute("flush");
        }

        RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/topPage/index.jsp");
        rd.forward(request, response);
    }

}
