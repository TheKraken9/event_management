package controller;

import connecting.Connecting;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import zone.Zone;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

@WebServlet(name = "ServletConfigure", value = "/ServletConfigure")
public class ServletConfigure extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Connecting co = new Connecting();
        Connection conn = co.connection();
        try {
            conn.setAutoCommit(false);
            int idEvent = Integer.parseInt(request.getParameter("idEvent"));
            int idPlace = Integer.parseInt(request.getParameter("idPlace"));
            int nbZone = Integer.parseInt(request.getParameter("nbZone"));
            int temps = Integer.parseInt(request.getParameter("temps"));
            double pricefree = Integer.parseInt(request.getParameter("libre"));
            for (int i = 1; i <= nbZone; i++) {
                Zone zone = new Zone();
                int min = Integer.parseInt(request.getParameter("min"+i));
                int max  = Integer.parseInt(request.getParameter("max"+i));
                double price = Double.parseDouble(request.getParameter("price"+i));
                zone.setIdEvent(idEvent);
                zone.setIdPlace(idPlace);
                zone.setNbOfZone(i);
                zone.setMinSize(min);
                zone.setMaxSize(max);
                zone.setPrice(price);
                zone.insertZoneProgramming(conn, zone);
                zone.insertToSubZone(conn, zone);
            }
            Zone zone = new Zone();
            zone.insertFreePrice(conn, idEvent, idPlace, pricefree);
            conn.commit();
            request.setAttribute("idEvent", idEvent);
            request.setAttribute("nbZone", nbZone);
            request.setAttribute("temps", temps);
            request.getRequestDispatcher("configTime.jsp").forward(request, response);
        } catch (Exception error) {
            try {
                conn.rollback();
                error.printStackTrace(response.getWriter());
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
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
