package servlets;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet("/SignupServlet")
public class SignupServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String username = request.getParameter("username");
        String password = request.getParameter("password");

        if (username == null || password == null || username.trim().isEmpty() || password.trim().isEmpty()) {
            response.sendRedirect("signup.jsp?signup=error");
            return;
        }

        try {
            Class.forName("com.mysql.jdbc.Driver");
            try (Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/brickbreaker", "root", "");
                 PreparedStatement stmt = con.prepareStatement(
                         "INSERT INTO users (username, password) VALUES (?, ?)")) {

                stmt.setString(1, username);
                stmt.setString(2, password);

                if (stmt.executeUpdate() > 0) {
                    response.sendRedirect("login.jsp?signup=success");
                } else {
                    response.sendRedirect("signup.jsp?signup=error");
                }
            }
        } catch (IOException | ClassNotFoundException | SQLException e) {
            response.sendRedirect("signup.jsp?signup=exception");
        }
    }
}