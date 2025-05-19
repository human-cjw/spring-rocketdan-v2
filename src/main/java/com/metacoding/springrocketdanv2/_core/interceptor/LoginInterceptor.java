package com.metacoding.springrocketdanv2._core.interceptor;

import org.springframework.web.servlet.HandlerInterceptor;

@Deprecated
public class LoginInterceptor implements HandlerInterceptor {
//    @Override
//    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
//        String uri = request.getRequestURI();
//        System.out.println("uri: " + uri);
//
//        HttpSession session = request.getSession();
//        UserResponse.SessionUserDTO sessionUser = (UserResponse.SessionUserDTO) session.getAttribute("sessionUser");
//
//        if (sessionUser == null) {
//            if (uri.contains("/api")) {
//                throw new ExceptionApi401("인증이 필요합니다");
//            } else {
//                throw new ExceptionApi401("인증이 필요합니다");
//            }
//        }
//
//        return true;
//    }
}