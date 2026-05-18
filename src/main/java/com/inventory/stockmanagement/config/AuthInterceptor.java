package com.inventory.stockmanagement.config;

import com.inventory.stockmanagement.model.user.UserAccount;
import com.inventory.stockmanagement.model.user.UserRole;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class AuthInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest req, HttpServletResponse res, Object handler) throws Exception {
        String path = req.getRequestURI();
        if (path.equals("/login") || path.equals("/register") || path.startsWith("/css/") || path.startsWith("/js/") || path.startsWith("/images/") || path.equals("/favicon.ico"))
            return true;
        UserAccount user = (UserAccount) req.getSession().getAttribute("loggedUser");
        if (user == null) {
            res.sendRedirect("/login");
            return false;
        }
        if ((path.startsWith("/users") || path.startsWith("/report")) && user.getRole() != UserRole.ADMIN) {
            res.sendRedirect("/dashboard?denied=true");
            return false;
        }
        return true;
    }
}
