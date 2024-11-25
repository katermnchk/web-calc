package org.example.demo1;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;

import java.io.IOException;

@WebFilter(urlPatterns = "/Calculator")
public class CalcFilter implements Filter {

    @Override
    public void doFilter(ServletRequest req, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) req;
        String query = httpRequest.getParameter("expression");

        if (query == null || query.isEmpty()) {
            response.getWriter().println("Invalid input. Please provide an expression like 1+2=");
            return;
        }

        query = query.replace(" ", "+");

        if (!query.matches("^-?\\d+[+\\-*/]-?\\d+=$")) {
            response.getWriter().println("Invalid expression format. Use the format: 1+2= or -1+2=");
            return;
        }

        chain.doFilter(req, response);
    }
}





