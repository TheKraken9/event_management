package controller;

import connecting.Connecting;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import reservation.Facture;
import reservation.Reservation;
import zone.Zone;

import java.io.IOException;
import java.sql.Connection;
import java.util.Vector;

@WebServlet(name = "ServletCheckUser", value = "/ServletCheckUser")
public class ServletCheckUser extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Connection conn = new Connecting().connection();
        int idEvent = Integer.parseInt(request.getParameter("event"));
        String name = request.getParameter("name");
        Vector<Facture> factures = new Vector<>();
        double price = 0;
        try {
            Vector<Reservation> reservations = new Reservation().getListOfReservationsRest(conn, idEvent, name);
            Vector<Reservation> reservation = new Reservation().getAllReservation(conn, name);
            if(reservation.size() == 0) {
                throw new Exception("This name does not exist, verify your name");
            } else {
                Vector<Zone> zones = new Zone().getAllZone(conn, idEvent);
                for (int i = 0; i < zones.size(); i++) {
                    for (int j = 0; j < reservations.size(); j++) {
                        if(zones.get(i).getMinSize() <= reservations.get(j).getNbOfPlace() && zones.get(i).getMaxSize() >= reservations.get(j).getNbOfPlace()) {
                            Facture facture = new Facture();
                            facture.setNbOfPlace(reservations.get(j).getNbOfPlace());
                            facture.setPrice(zones.get(i).getPrice());
                            factures.add(facture);
                            price += zones.get(i).getPrice();
                        }
                    }
                }
            }
            request.setAttribute("idEvent", idEvent);
            request.setAttribute("name", name);
            request.setAttribute("allPrices", factures);
            request.setAttribute("total", price);
            request.getRequestDispatcher("valider.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace(response.getWriter());
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
