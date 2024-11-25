package org.example.demo1;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet(name = "CalculatorServlet", urlPatterns = "/Calculator")
public class CalculatorServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String query = req.getParameter("expression");

        if (query == null) {
            resp.getWriter().println("No expression provided.");
            return;
        }

        query = query.replace(" ", "+");

        query = query.replace("=", "");

        String[] parts = query.split("(?<=\\d)(?=[+\\-*/])|(?<=[+\\-*/])(?=-?\\d)");

        if (parts.length != 3) {
            resp.getWriter().println("Invalid expression format. Use the format: 1+2= or -1*2=");
            return;
        }

        try {
            double num1 = Double.parseDouble(parts[0]);
            String operation = parts[1];
            double num2 = Double.parseDouble(parts[2]);
            double result = calculate(num1, num2, operation);
            resp.getWriter().println(query + "=" + result);
        } catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
            resp.getWriter().println("Error parsing numbers or invalid format.");
        } catch (IllegalArgumentException e) {
            resp.getWriter().println(e.getMessage());
        }
    }

    private double calculate(double num1, double num2, String operation) {
        return switch (operation) {
            case "+" -> num1 + num2;
            case "-" -> num1 - num2;
            case "*" -> num1 * num2;
            case "/" -> {
                if (num2 == 0) throw new IllegalArgumentException("Division by zero!");
                yield num1 / num2;
            }
            default -> throw new IllegalArgumentException("Invalid operation! Use +, -, *, or /");
        };
    }
}
