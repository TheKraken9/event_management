package controller;

import connecting.Connecting;
import events.Events;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;

@WebServlet(name = "ServletNewEvent", value = "/ServletNewEvent")
public class ServletNewEvent extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Connecting co = new Connecting();
        Connection conn = co.connection();
        try {
            int temps = Integer.parseInt(request.getParameter("temps"));
            String event = request.getParameter("event");
            String datetime = request.getParameter("date");
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
            java.util.Date date = null;
            Timestamp timestamp = null;
            date = dateFormat.parse(datetime);
            timestamp = new Timestamp(date.getTime());
            int place = Integer.parseInt(request.getParameter("place"));
            int zone = Integer.parseInt(request.getParameter("zone"));
            Events events = new Events();
            events.setNameEvent(event);
            if(timestamp.before(new Timestamp(System.currentTimeMillis()))) {
                throw new Exception("Invalid Date of event");
            }
            events.setTimestamp((Timestamp) timestamp);
            events.setIdPlace(place);
            events.setNbZone(zone);
            request.setAttribute("temps", temps);
            request.setAttribute("zone", zone);
            events.insertEvent(conn ,events);
            request.getRequestDispatcher("formZone.jsp").forward(request, response);
        } catch (Exception error) {
            error.printStackTrace(response.getWriter());
        } finally {
            try {
                conn.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
