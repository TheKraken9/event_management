<%@ page import="connecting.Connecting" %>
<%@ page import="java.sql.Connection" %>
<%@ page import="place.Place" %>
<%@ page import="java.util.Vector" %>
<%@ page import="zone.Zone" %>
<%@ page import="reservation.ColorCode" %><%--
  Created by IntelliJ IDEA.
  User: thekraken
  Date: 1/27/23
  Time: 2:22 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    int id = (int) request.getAttribute("id");
    Connection conn = new Connecting().connection();
    Vector<Zone> zones = new Vector<>();
    Vector<ColorCode> colorCodes = new Vector<>();
    try {
        zones = new Zone().getSubZone(conn, id);
        colorCodes = new ColorCode().getAllColorCode(conn, id);
    } catch (Exception e) {
        throw new RuntimeException(e);
    }
    String color = "gray";
%>

<html>
<head>
    <title>All Places</title>
    <meta charset="UTF-8">
    <link rel="stylesheet" type="text/css" href="assets/css/bootstrap.css">
</head>
<body>
<div class="container" style="position: absolute; top: 5%;">
    <div class="col-md-auto shadow border p-3 m-2 bg-white rounded ms-4">
      <form method="get" action="ServletReservation">
          <h1 style="color: #198754">All Places</h1>
          <label for="name">Your Name : </label>
          <input style="width: 300px" class="form-control mt-3 mb-5" type="text" name="name" id="name">
          <input type="number" value="<%= zones.get(0).getIdEvent() %>" name="event" hidden="hidden">
          <h3>Zone 1</h3>
          <%
              int nb = 1;
              for (int i = 0; i < zones.size(); i++) {
                for (int j = 0; j < colorCodes.size(); j++) {
                    if (colorCodes.get(j).getNbofplace() == zones.get(i).getPlacenb()) {
                        color = colorCodes.get(j).getColor();
                    }
                }
          %>
              <% if(zones.get(i).getNbOfZone() != nb) { nb = zones.get(i).getNbOfZone(); %>
                    <br><br><h4>Zone <%=nb%></h4>
              <% } %>
            <button type="button" style="background-color:<%=color%> ; border-radius: 4px; padding: 5px">
                <input style="background-color:<%=color%> ; border-radius: 4px; padding: 5px" class="form-check-input" type="checkbox" id="<%=zones.get(i).getPlacenb()%>" name="nbplace" value="<%=zones.get(i).getPlacenb()%>">
            </button>
            <label for="<%=zones.get(i).getPlacenb()%>"><%=zones.get(i).getPlacenb()%></label>
          <% color = "gray";} %> <br><br>
          <input class="btn btn-primary mt-5 mb-5" type="submit" value="Reserve">
      </form>
          <small class="form-text" style="color: green">Reserved | GREEN</small>&nbsp;
          <small class="form-text" style="color: red">Pending | RED</small>&nbsp;
          <small class="form-text" style="color: blue">Not available | BLUE</small>&nbsp;
          <small class="form-text" style="color: gray">Available | GRAY</small>
    </div>
</div>

</body>
</html>
