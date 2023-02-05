<%@ page import="events.Events" %>
<%@ page import="java.util.Vector" %>
<%@ page import="java.sql.Connection" %>
<%@ page import="connecting.Connecting" %><%--
  Created by IntelliJ IDEA.
  User: thekraken
  Date: 1/27/23
  Time: 12:21 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
  Connection conn = new Connecting().connection();
  Vector<Events> event = new Events().getLastEvent(conn);
  int nb = (int) request.getAttribute("zone");
  int temps = (int) request.getAttribute("temps");
%>
<html>
<head>
    <title>Form Zone</title>
    <meta charset="UTF-8">
    <link rel="stylesheet" type="text/css" href="assets/css/bootstrap.css">
</head>
<body>
<div class="container mt-5">
  <div class="col-12 shadow border p-3 m-2 bg-white rounded ms-4">
    <form method="get" action="ServletConfigure">
      <h2 class="text-center" style="color: #198754">All Zone</h2>
      <h4 style="color: #198754">VIP Place Configuration</h4>
      <input type="number" name="idEvent" value="<%= event.get(0).getId() %>" hidden="hidden">
      <input type="number" name="idPlace" value="<%= event.get(0).getIdPlace() %>" hidden="hidden">
      <input type="number" name="temps" value="<%= temps %>" hidden="hidden">
      <input type="number" name="nbZone" value="<%= event.get(0).getNbZone() %>" hidden="hidden">
      <% for (int i = 1; i <= nb; i++) { %>
      <div class="row">
        <h5>Zone <%=i%></h5>
        <div class="col-md-3">
          <span> Start of Zone : </span>
          <input style="width: 250px" class="form-control" type="number" id="zone<%=i%>" name="min<%=i%>">
        </div>
        <div class="col-md-3">
          <span> End of Zone : </span>
          <input style="width: 250px" class="form-control" type="number" id="zone<%=i%>" name="max<%=i%>">
        </div>
        <div class="col-md-3">
          <span> Price : </span>
          <input style="width: 250px" class="form-control" type="number" id="zone<%=i%>" name="price<%=i%>" placeholder=".00 Ariary">
        </div>
      </div><br>
      <% } %>
      <h4 style="color: #198754">Free Place Configuration</h4>
      <label for="libre">Price : </label>
      <input style="width: 300px" class="form-control" type="number" name="libre" id="libre" placeholder=".00 Ariary">
      <input class="btn btn-primary mt-3" type="submit" value="Next >">
    </form>
  </div>
</div>

</body>
</html>
