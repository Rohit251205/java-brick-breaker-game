package servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet("/ScoreServlet")
public class ScoreServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("username") == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        String username = (String) session.getAttribute("username");

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            try (
                Connection con = DriverManager.getConnection(
                        "jdbc:mysql://localhost:3306/brickbreaker?useSSL=false&serverTimezone=UTC", "root", "");
                PreparedStatement ps = con.prepareStatement("SELECT highscore FROM users WHERE username = ?");
            ) {
                ps.setString(1, username);
                ResultSet rs = ps.executeQuery();

                out.println("<html><head><title>Leaderboard</title></head><body style='background:#1e1e1e; color:white; font-family:sans-serif; text-align:center;'>");

                if (rs.next()) {
                    int score = rs.getInt("highscore");
                    out.println("<h1>Your High Score: " + score + "</h1>");
                } else {
                    out.println("<h1>No score found</h1>");
                }

                out.println("<br><a href='menu.jsp' style='color:lightblue;'>Back to Menu</a>");
                out.println("</body></html>");
            }
        } catch (ClassNotFoundException | SQLException e) {
            out.println("<h2 style='color:red;'>Error fetching score!</h2>");
        }
    }
}