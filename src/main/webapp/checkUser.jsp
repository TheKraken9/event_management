<%@ page import="java.sql.Connection" %>
<%@ page import="events.Events" %>
<%@ page import="java.util.Vector" %>
<%@ page import="connecting.Connecting" %><%--
  Created by IntelliJ IDEA.
  User: thekraken
  Date: 2/4/23
  Time: 10:51 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    Connection conn = new Connecting().connection();
    Vector<Events> events = new Events().getAllEvents(conn);
%>
<html>
<head>
    <title>Verify your identity</title>
    <meta charset="UTF-8">
    <link rel="stylesheet" type="text/css" href="assets/css/bootstrap.css">
</head>
<body>
<div class="container" style="position: absolute; top: 20%;left: 10%">
    <div class="col-md-4 shadow border p-3 m-2 bg-white rounded ms-4">
        <form action="ServletCheckUser" method="get">
            <h2 class="text-center" style="color: #198754">Validate my reservation</h2>
            <label for="ListEvent">Event : </label>
            <select name="event" id="ListEvent" style="width: 300px" class="form-control">
                <% for (int i = 0; i < events.size(); i++) { %>
                    <option value="<%= events.get(i).getId()%>">
                        <%=events.get(i).getNameEvent()%> | <%=events.get(i).getTimestamp()%>
                    </option>
                <% } %>
            </select>
            <label for="name">Your Name : </label>
            <input style="width: 250px" class="form-control" type="text" name="name" id="name"><br>
            <input class="btn btn-primary" type="submit" value="Next >">
        </form>
    </div>
</div>

</body>
</html>
