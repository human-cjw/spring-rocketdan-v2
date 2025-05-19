package com.metacoding.springrocketdanv2._core.interceptor;

import com.metacoding.springrocketdanv2._core.error.ex.ExceptionApi401;
import com.metacoding.springrocketdanv2.user.User;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.servlet.HandlerInterceptor;

@Deprecated
public class CompanyInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String uri = request.getRequestURI();
        System.out.println("uri: " + uri);

        HttpSession session = request.getSession();
        User sessionUser = (User) session.getAttribute("sessionUser");

        Integer companyId = null;
        if (sessionUser != null) {
            companyId = sessionUser.getCompanyId();
        }

        if (companyId == null) {
            if (uri.contains("/api")) {
                throw new ExceptionApi401("기업 인증이 필요합니다");
            } else {
                throw new ExceptionApi401("기업 인증이 필요합니다");
            }
        }

        return true;
    }
}