<%@ page import="java.util.Vector" %>
<%@ page import="place.Place" %>
<%@ page import="connecting.Connecting" %>
<%@ page import="java.sql.Connection" %>
<%@ page import="events.Events" %>
<%@ page import="reservation.Reservation" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%
    Connection conn = new Connecting().connection();
    Vector<Place> places = new Place().getAllPlace(conn);
    Vector<Events> events = new Events().getAllEvents(conn);
    Reservation reservation = new Reservation();
    try {
        reservation.checkRefresh(conn);
        Events event = new Events();
        event.refreshEvent(conn);
    }catch (Exception error) {
        error.printStackTrace();
    }
%>
<!DOCTYPE html>
<html>
<head>
    <title>Ticket Place</title>
    <meta charset="UTF-8">
    <link rel="stylesheet" type="text/css" href="assets/css/bootstrap.css">
</head>
<body>
<h1 class="text-center my-3 mb-2" style="color: #198754">Ticket Place</h1>
<div class="container-fluid">
    <div class="row">
        <div class="col-md-4 shadow border p-5 m-2 bg-white rounded ms-4">
            <form method="get" action="ServletAllPlaces">
                <h4 class="mb-3" style="color: #198754">Reserve some places</h4>
                <label for="ListEvent">Choose your event : </label>
                <select name="event" id="ListEvent" style="width: 300px" class="form-control" >
                    <% for (int i = 0; i < events.size(); i++) { %>
                        <option value="<%= events.get(i).getId()%>">
                            <%=events.get(i).getNameEvent()%> | <%=events.get(i).getTimestamp()%>
                        </option>
                    <% } %>
                </select>
                <input type="submit" value="Choose" class="btn btn-success mt-3 d-flex justify-content-end">
                <a class="btn btn-secondary mt-3" href="http://localhost:8080/ticketPlace_war_exploded/checkUser.jsp">Validate my reservation</a>
            </form>
        </div>
        <div class="col-md-3 shadow border p-5 m-2 bg-white rounded">
            <form method="GET" action="ServletNewEvent">
                <h4 class="mb-3" style="color: #198754">Create an event</h4>
                <label for="event">Name : </label><input style="width: 250px" class="form-control" type="text" id="event" name="event" required>
                <label for="date">Date : </label><input style="width: 250px" class="form-control" type="datetime-local" name="date" id="date">
                <label for="place">Place : </label>
                <select style="width: 250px" class="form-control" name="place" id="place">
                    <% for (int i = 0; i < places.size(); i++) { %>
                        <option value="<%=places.get(i).getId()%>">
                            <%=places.get(i).getNamePlace()%>
                        </option>
                    <% } %>
                </select> <br>
                <small style="color: red">More settings ></small>
                <label for="zone">Number of zones : </label><input style="width: 250px" class="form-control" type="number" name="zone" id="zone">
                <label for="temps">Number of time intervals : </label><input style="width: 250px" class="form-control" type="number" name="temps" id="temps">
                <input type="submit" value="Next >" class="btn btn-success mt-3">
            </form>
        </div>
        <div class="col-md-4 shadow border p-5 m-2 bg-white rounded">
            <form method="get" action="ServletSellingFree">
                <h4 class="mb-3" style="color: #198754">Buy free ticket : </h4>
                <label for="ListEvents">Choose your event : </label>
                <select name="event" id="ListEvents" style="width: 300px" class="form-control" >
                    <% for (int i = 0; i < events.size(); i++) { %>
                        <option value="<%= events.get(i).getId()%>">
                            <%=events.get(i).getNameEvent()%> | <%=events.get(i).getTimestamp()%>
                        </option>
                    <% } %>
                </select><br>
                <label for="nombre">Number of tickets you want to purchase : </label>
                <input style="width: 300px" class="form-control" type="number" name="nombre" id="nombre">
                <input type="submit" value="Buy" class="btn btn-success mt-3">
            </form>
        </div>
    </div>
</div>
</body>
</html>