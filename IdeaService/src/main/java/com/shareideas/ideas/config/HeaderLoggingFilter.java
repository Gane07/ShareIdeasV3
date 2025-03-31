package com.shareideas.ideas.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;

@Component
public class HeaderLoggingFilter extends OncePerRequestFilter {
//    @Override
//    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
//            throws ServletException, IOException {
//        System.out.println("Request URI: " + request.getRequestURI());
//        Collections.list(request.getHeaderNames()).forEach(name ->
//                System.out.println("Request Header: " + name + ": " + request.getHeader(name)));
//        // Only set UTF-8 for JSON responses
//        if (request.getRequestURI().endsWith("/exceptuser/" + request.getPathInfo())) {
//            response.setCharacterEncoding("UTF-8");
//            response.setHeader("Content-Type", "application/json; charset=UTF-8");
//        }
//        filterChain.doFilter(request, response);
//        response.getHeaderNames().forEach(name ->
//                System.out.println("Response Header: " + name + ": " + response.getHeader(name)));
//    }
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
//        System.out.println("Request URI: " + request.getRequestURI());
//        Collections.list(request.getHeaderNames()).forEach(name ->
//                System.out.println("Request Header: " + name + ": " + request.getHeader(name)));
        filterChain.doFilter(request, response);
//        response.getHeaderNames().forEach(name ->
//                System.out.println("Response Header: " + name + ": " + response.getHeader(name)));
    }
	
}