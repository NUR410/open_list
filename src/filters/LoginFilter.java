package filters;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import models.User;

/**
 * Servlet Filter implementation class LoginFilter
 */
@WebFilter("/*")
public class LoginFilter implements Filter {

    /**
     * Default constructor.
     */
    public LoginFilter() {
    }

    /**
     * @see Filter#destroy()
     */
    public void destroy() {
    }

    /**
     * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
     */
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        String context_path = ((HttpServletRequest)request).getContextPath();
        String servlet_path = ((HttpServletRequest)request).getServletPath();

        if(!servlet_path.matches("/css.*")) {       // CSSフォルダ内は認証処理から除外する
            HttpSession session = ((HttpServletRequest)request).getSession();

            // セッションスコープに保存されたログインユーザ情報を取得
            User u = (User)session.getAttribute("login_user");
            // ログアウトしている状態であれば
            if(u == null) {
                // ログイン画面,ユーザー作成,,トップページ
                //店舗一覧,店舗詳細,ユーザー一覧,ユーザー詳細以外について
                // ログイン画面にリダイレクト
                if(!servlet_path.equals("/login") && !servlet_path.equals("/users/create") && !servlet_path.equals("/index.html") && !servlet_path.equals("/restaurants/index")
                && !servlet_path.equals("/restaurants/show")&& !servlet_path.equals("/users/index") && !servlet_path.equals("/users/show")) {
                    ((HttpServletResponse)response).sendRedirect(context_path + "/login");
                    return;
                }

             // ログインしている状態であれば
            } else {
                // ログインしているのにログイン画面を表示させようとした場合は
                // システムのトップページにリダイレクト
                if(u != null && servlet_path.equals("/login")) {
                    ((HttpServletResponse)response).sendRedirect(context_path + "/");
                    return;
                }
            }
        }

        chain.doFilter(request, response);
    }

    /**
     * @see Filter#init(FilterConfig)
     */
    public void init(FilterConfig fConfig) throws ServletException {
    }

}