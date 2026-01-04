package servlets;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class GameLauncherServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {            
            String classPath = getServletContext().getRealPath("/WEB-INF/classes");           
            ProcessBuilder pb = new ProcessBuilder("java", "-cp", classPath, "game.BrickBreakerGame");
            pb.start();
          
            response.sendRedirect("menu.jsp?game=started");
        } catch (IOException e) {
            response.sendRedirect("menu.jsp?game=error");
        }
    }
}