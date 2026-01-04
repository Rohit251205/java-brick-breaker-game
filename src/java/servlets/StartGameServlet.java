package servlets;

import java.io.IOException;
import javax.servlet.*;
import javax.servlet.http.*;

public class StartGameServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        if (session != null && session.getAttribute("username") != null) {
        } else {
            response.sendRedirect("login.jsp");
            return;
        }

        response.sendRedirect("game.html"); // Make sure game.html is inside your web folder
    }
}