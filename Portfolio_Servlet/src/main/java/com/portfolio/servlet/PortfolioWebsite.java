package com.portfolio.servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

//@WebServlet("/anything")
public class PortfolioWebsite extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String name = req.getParameter("Name");
        String email = req.getParameter("Email");

        System.out.println("Name: " + name);
        System.out.println("Email: " + email);

        if (name == null || name.isEmpty() || email == null || email.isEmpty()) {
            resp.getWriter().write("Name and Email are required fields.");
            return;
        }

        Connection conn = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/portfolio_clients_db?user=root&password=Sanjay@123");
            PreparedStatement pst = conn.prepareStatement("INSERT INTO portfolio (Name, Email) VALUES (?, ?)");
            pst.setString(1, name);
            pst.setString(2, email);
            pst.executeUpdate();
            resp.sendRedirect("index.html");
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            resp.getWriter().write("Error: " + e.getMessage());
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
