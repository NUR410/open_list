package controllers.restaurants;

import java.io.IOException;
import java.util.List;

import javax.persistence.EntityManager;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import models.Restaurant;
import models.UsersRestaurant;
import utils.DBUtil;

/**
 * Servlet implementation class RestaurantsDestroyServlet
 */
@WebServlet("/restaurants/destroy")
public class RestaurantsDestroyServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public RestaurantsDestroyServlet() {
        super();
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String _token = (String)request.getParameter("_token");
        if(_token != null && _token.equals(request.getSession().getId())) {
            EntityManager em = DBUtil.createEntityManager();

            // リクエストスコープから店舗のIDを取得して
            // 該当のIDの店舗１件のみをデータベースから取得
            Restaurant r = em.find(Restaurant.class, Integer.parseInt(request.getParameter("restaurant_id")));
            List<UsersRestaurant> ur_list = em.createNamedQuery("getUsersRestaurants", UsersRestaurant.class)
                    .setParameter("rest", r)
                    .getResultList();

            em.getTransaction().begin();
            for(UsersRestaurant ur :ur_list){
                em.remove(ur);       // お気に入り店舗削除
            }
            em.remove(r);       // 店舗削除
            em.getTransaction().commit();
            em.close();

            response.sendRedirect(request.getContextPath() + "/");
        }
    }
}
