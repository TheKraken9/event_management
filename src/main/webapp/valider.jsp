<%@ page import="connecting.Connecting" %>
<%@ page import="java.sql.Connection" %>
<%@ page import="events.Events" %>
<%@ page import="java.util.Vector" %>
<%@ page import="reservation.Facture" %><%--
  Created by IntelliJ IDEA.
  User: thekraken
  Date: 2/2/23
  Time: 9:19 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    Connection conn = new Connecting().connection();
    Vector<Events> events = new Vector<>();
    int idEvent = (int) request.getAttribute("idEvent");
    String name = (String) request.getAttribute("name");
    Vector<Facture> factures = new Vector<>();
    double price = 0;
    try {
        events = new Events().getAllEvents(conn);
        factures = (Vector<Facture>) request.getAttribute("allPrices");
        price = (double) request.getAttribute("total");
    } catch (Exception e) {
        e.printStackTrace(response.getWriter());
    }

%>
<html>
<head>
    <title>Validate my reservation</title>
    <meta charset="UTF-8">
    <link rel="stylesheet" type="text/css" href="assets/css/bootstrap.css">
</head>
<body>
<div class="container mt-5">
    <div class="col-md-4 shadow border p-3 m-2 bg-white rounded ms-4">
        <% if(price == 0) { %>
            <h2 class="text-center text-danger fs-6"><%=name%>, You have not any reservation in progress</h2>
        <%} else {%>
            <h2 class="text-center" style="color: #198754"><%=name%>, Validate your reservation</h2><br>
                <table class="table table-striped table-bordered">
                    <thead>
                        <tr>
                            <th>No Place</th>
                            <th>Price</th>
                        </tr>
                    </thead>
                    <tbody>
                    <% for (int i = 0; i < factures.size(); i++) { %>
                        <tr>
                            <td><%=factures.get(i).getNbOfPlace()%></td>
                            <td><%=factures.get(i).getPrice()%></td>
                        </tr>
                    <%}%>
                    </tbody>
                </table>
                <h4 style="color: red">Total : <%=price%></h4> <br>
            <form method="get" action="ServletConfirmReservation">
                <input style="width: 300px" class="form-control" type="number" value="<%=idEvent%>" name="event" hidden="hidden">
                <input type="text" value="<%=name%>" name="name" hidden="hidden">
                <label for="price">Your money : </label>
                <input style="width: 300px" class="form-control" type="number" name="price" id="price"><br><br>
                <input class="btn btn-primary" type="submit" value="Validate">
            </form>
    </div>
</div>

<%}%>
</body>
</html>
