package com.metacoding.springrocketdanv2._core.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.metacoding.springrocketdanv2.user.User;

import java.util.Date;

public class JwtUtil {
    public static String createRefresh(User user) {
        String jwt = JWT.create()
                .withSubject("rocketdan")
                .withExpiresAt(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24 * 7))
                .withClaim("id", user.getId())
                .withClaim("username", user.getUsername())
                .withClaim("companyId", user.getCompanyId())
                .sign(Algorithm.HMAC512("metacoding"));
        return jwt;
    }

    public static String create(User user) {
        String jwt = JWT.create()
                .withSubject("rocketdan")
                .withExpiresAt(new Date(System.currentTimeMillis() + 1000 * 60 * 60))
                .withClaim("id", user.getId())
                .withClaim("username", user.getUsername())
                .withClaim("companyId", user.getCompanyId())
                .sign(Algorithm.HMAC512("metacoding"));
        return jwt;
    }

    public static User verify(String jwt) {
        DecodedJWT decodedJWT = JWT.require(Algorithm.HMAC512("metacoding")).build().verify(jwt);
        Integer id = decodedJWT.getClaim("id").asInt();
        String username = decodedJWT.getClaim("username").asString();
        Integer companyId = decodedJWT.getClaim("companyId") == null ? null : decodedJWT.getClaim("companyId").asInt();

        return User.builder()
                .id(id)
                .username(username)
                .companyId(companyId)
                .build();
    }
}
