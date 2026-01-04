<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Your High Score</title>
    <link rel="stylesheet" href="css/style.css">
    <style>
        body {
            font-family: 'Segoe UI', sans-serif;
            background: linear-gradient(135deg, #1e3c72, #2a5298);
            color: white;
            margin: 0;
            padding: 0;
        }

        .score-container {
            display: flex;
            justify-content: center;
            align-items: center;
            flex-direction: column;
            height: 100vh;
        }

        .score-table {
            width: 100%;
            border-collapse: collapse;
            background: #1e3c72;
            color: white;
            border-radius: 10px;
            overflow: hidden;
            box-shadow: 0 0 15px black;
        }

        .score-table th, .score-table td {
            padding: 15px;
            border: 1px solid #fff;
            text-align: center;
        }

        .score-table th {
            background-color: #162345;
            font-size: 18px;
        }

        .score-table td {
            font-size: 16px;
        }

        .back-link {
            margin-top: 30px;
        }

        .back-link a {
            color: #ffffff;
            text-decoration: none;
            font-weight: bold;
            font-size: 16px;
            border: 2px solid white;
            padding: 8px 15px;
            border-radius: 8px;
            transition: 0.3s ease;
        }

        .back-link a:hover {
            background: white;
            color: #1e3c72;
        }
    </style>
</head>
<body>
    <div class="score-container">
        <table class="score-table">
            <tr>
                <th>Username</th>            
                <th>Highest Score</th>
            </tr>
            <tr>
                <td><%= request.getAttribute("username") %></td>           
                <td><%= request.getAttribute("high_score") %></td>
            </tr>
        </table>

        <div class="back-link">
            <a href="menu.jsp">← Back to Menu</a>
        </div>
    </div>
</body>
</html>