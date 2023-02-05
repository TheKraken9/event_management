<%@ page import="java.util.Vector" %>
<%@ page import="events.Events" %>
<%@ page import="java.sql.Connection" %>
<%@ page import="connecting.Connecting" %><%--
  Created by IntelliJ IDEA.
  User: thekraken
  Date: 2/3/23
  Time: 8:22 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    Connection conn = new Connecting().connection();
    int idEvent = (int) request.getAttribute("idEvent");
    int nbZone = (int) request.getAttribute("nbZone");
    int temps = (int) request.getAttribute("temps");
    Vector<Events> events = new Events().getSpecifiedEventsByEvent(conn, idEvent);
%>
<html>
<head>
    <title>Time Configuration</title>
    <meta charset="UTF-8">
    <link rel="stylesheet" type="text/css" href="assets/css/bootstrap.css">
</head>
<body>
<div class="container w-50 mt-5">
    <div class="col-12 shadow border p-3 m-1 bg-white rounded">
        <h2 class="text-center" style="color: #198754">'<%=events.get(0).getNameEvent()%>' at <%=events.get(0).getPlace()%> on <%=events.get(0).getTimestamp()%></h2>
        <form method="get" action="ServletConfigureTime">
            <input type="number" name="idEvent" value="<%=idEvent%>" hidden="hidden">
            <input type="number" name="nbZone" value="<%=nbZone%>" hidden="hidden">
            <input type="number" name="temps" value="<%=temps%>" hidden="hidden">
            <% for (int i = 1; i <= nbZone; i++) { %>
            <div class="row">
                <h5><%= "Zone " + (i)%></h5>
                <% for (int j = 0; j < temps; j++) { %>
                <div class="col-md-12">
                    <div class="row">
                        <div class="col-md-6 mt-2">
                            <input style="width: 250px" class="form-control" type="number" name="timer<%=i%><%=j+1%>" placeholder="000 milliseconds">
                        </div>
                        <div class="col-md-6 mt-2">
                            <input style="width: 250px" class="form-control" type="datetime-local" name="time<%=i%><%=j+1%>">
                        </div>
                    </div>
                </div>
                <%}%>
                <div class="md-col-12 mt-1">
                    <div class="row">
                        <div class="col-md-6">
                            <input style="width: 250px" class="form-control" type="number" name="timer<%=i%><%=temps+1%>" placeholder="000 milliseconds">
                        </div>
                        <div class="col-md-6">
                             <input style="width: 250px" class="form-control" type="datetime-local" name="time<%=i%><%=temps+1%>" value="<%=events.get(0).getTimestamp()%>"><small class="form-text text-muted" style="font-size: xx-small"> DEADLINE</small>
                        </div>
                    </div>
                </div>
            </div>
            <%}%>
            <input class="btn btn-primary" type="submit" value="Finish configuration"> <br><br>
            <small class="text-muted">1 second = 1000 milliseconds</small>
        </form>
    </div>
</div>

</body>
</html>
