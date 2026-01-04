package servlets;

import java.io.IOException;
import java.sql.*;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String username = request.getParameter("username");
        String password = request.getParameter("password");

        if (username == null || password == null || username.trim().isEmpty() || password.trim().isEmpty()) {
            response.sendRedirect("login.jsp?login=empty");
            return;
        }

        try {
            Class.forName("com.mysql.jdbc.Driver");

            try (Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/brickbreaker", "root", "");
                 PreparedStatement stmt = con.prepareStatement("SELECT password FROM users WHERE username = ?")) {

                stmt.setString(1, username);
                ResultSet rs = stmt.executeQuery();

                if (rs.next()) {
                    String dbPassword = rs.getString("password");

                    if (dbPassword.equals(password)) {
                        HttpSession session = request.getSession();
                        session.setAttribute("username", username);
                        response.sendRedirect("menu.jsp");
                    } else {
                        response.sendRedirect("login.jsp?login=invalid");
                    }
                } else {
                    response.sendRedirect("login.jsp?login=notfound");
                }
            }
        } catch (ClassNotFoundException | SQLException e) {           
            System.out.println("Login Error: " + e.getMessage());
            response.sendRedirect("login.jsp?login=exception");
        }
    }
}