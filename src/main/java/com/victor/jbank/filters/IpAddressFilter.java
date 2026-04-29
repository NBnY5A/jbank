package com.victor.jbank.filters;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class IpAddressFilter extends HttpFilter {
    private final static String USER_IP_ADDRESS = "x-user-ip";

    @Override
    protected void doFilter(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain chain
    ) throws IOException, ServletException {
        var userIpAddress = request.getRemoteAddr();

        request.setAttribute(USER_IP_ADDRESS, userIpAddress);
        response.setHeader(USER_IP_ADDRESS, userIpAddress);

        chain.doFilter(request, response);

    }
}

