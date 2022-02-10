package controllers.toppage;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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

        //現在の 日付・時刻・曜日 を取得
        LocalDateTime current = LocalDateTime.now();
        String today_date = DateTimeFormatter.ofPattern("yyyy/MM/dd").format(current);
        String current_time = DateTimeFormatter.ofPattern("HH:mm").format(current);
        int ct = Integer.parseInt(current_time.replace(":", ""));
        String[] week_name = {"日","月", "火", "水", "木", "金", "土", "日"};
        String today = week_name[current.getDayOfWeek().getValue()];
        String day_before = week_name[current.getDayOfWeek().getValue() - 1];


        long users_restaurants_count = (long)em.createNamedQuery("getMyUsersRestaurantsCount", Long.class)
                .setParameter("user", login_user)
                .getSingleResult();

        long open_restaurants_count = (long)em.createNamedQuery("getOpenRestaurantsCount", Long.class)
                .setParameter("user", login_user)
                .setParameter("ct", ct)
                .setParameter("today", "%"+today+"%")
                .setParameter("day_before", "%"+day_before+"%")
                .getSingleResult();

        List<UsersRestaurant> users_restaurants = em.createNamedQuery("getOpenRestaurants", UsersRestaurant.class)
                .setParameter("user", login_user)
                .setParameter("ct", ct)
                .setParameter("today", "%"+today+"%")
                .setParameter("day_before", "%"+day_before+"%")
                .setFirstResult(15 * (page - 1))
                .setMaxResults(15)
                .getResultList();

        int open_page = (int)open_restaurants_count /15;
        int open_fraction = (int)open_restaurants_count %15;
        int size = users_restaurants.size();
        if(size < 15){
            List<UsersRestaurant> close_restaurants = em.createNamedQuery("getCloseRestaurants", UsersRestaurant.class)
                    .setParameter("user", login_user)
                    .setParameter("ct", ct)
                    .setParameter("today", "%"+today+"%")
                    .setParameter("day_before", "%"+day_before+"%")
                    .setFirstResult(15 * (page - open_page -2) + (15 - open_fraction))
                    .setMaxResults(15 - size)
                    .getResultList();

            users_restaurants.addAll(close_restaurants);
        }


        em.close();

        List<Restaurant> open_restaurants = new ArrayList<Restaurant>();
        for(UsersRestaurant ur : users_restaurants){
            Restaurant r = ur.getRestaurant();
            int open_time = r.getOpen_time();
            int close_time = r.getClose_time();
            if(open_time < close_time){
                if(!(r.getClosed_day().contains(today)) && (open_time < ct) && (ct < close_time)){
                    r.setOpen(1);
                }else{
                    r.setOpen(0);
                }
            }else{
                if(!(r.getClosed_day().contains(today)) && open_time < ct){
                    r.setOpen(1);
                }else if(!(r.getClosed_day().contains(day_before)) && (ct < close_time)){
                    r.setOpen(1);
                }else{
                    r.setOpen(0);
                }
            }
            open_restaurants.add(r);
        }

        request.setAttribute("today_date", today_date);
        request.setAttribute("current_time", ct);
        request.setAttribute("today_week", today);
        request.setAttribute("usersrestaurants", open_restaurants);
        request.setAttribute("size", open_restaurants.size());
        request.setAttribute("page", page);
        request.setAttribute("restaurants_count", users_restaurants_count);
        request.setAttribute("open_restaurants_count", open_restaurants_count);
        request.setAttribute("page", page);


        if(request.getSession().getAttribute("flush") != null) {
            request.setAttribute("flush", request.getSession().getAttribute("flush"));
            request.getSession().removeAttribute("flush");
        }

        RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/topPage/index.jsp");
        rd.forward(request, response);
    }

}
