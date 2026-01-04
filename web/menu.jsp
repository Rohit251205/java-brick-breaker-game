<%@ page import="java.util.*" %>
<%@ page session="true" %>
<%
    String username = (String) session.getAttribute("username");
    if (username == null) {
        response.sendRedirect("login.jsp?session=expired");
        return;
    }
%>
<!DOCTYPE html>
<html>
    <head>
        <title>Start Menu</title>
        <link rel="stylesheet" href="css/style.css">
    </head>
    <body>
        <div class="card">
            <h1>Welcome, <%= username %>!</h1>
            <form action="GameLauncherServlet" method="post">
                <button type="submit">Start Game</button>
            </form>
            <form action="LeaderboardServlet">
                <button type="submit">View Score</button>
            </form>
            <form action="LoginServlet" method="post">
                <button type="submit">Logout</button>
            </form>
        </div>
    </body>
</html>