package controller;

import connecting.Connecting;
import events.Events;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import selling.FreeSelling;

import java.io.IOException;
import java.io.Writer;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Vector;

@WebServlet(name = "ServletSellingFree", value = "/ServletSellingFree")
public class ServletSellingFree extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Connecting co = new Connecting();
        Connection conn = co.connection();
        int event = Integer.parseInt(request.getParameter("event"));
        int nb = Integer.parseInt(request.getParameter("nombre"));
        FreeSelling freeSelling = new FreeSelling();
        try {
            Vector<Events> events = new Events().getSpecifiedEventsByEvent(conn, event);
            if(events.get(0).getMaxfreeplace() < nb) {
                throw new Exception("Places you entered are too strong");
            } else {
                freeSelling.freeSell(conn, event, nb);
            }
            request.getRequestDispatcher("index.jsp").forward(request, response);
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
