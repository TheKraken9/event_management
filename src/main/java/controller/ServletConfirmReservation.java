package controller;

import connecting.Connecting;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import reservation.Reservation;
import zone.Zone;

import java.io.IOException;
import java.sql.Connection;
import java.util.Vector;

@WebServlet(name = "ServletConfirmReservation", value = "/ServletConfirmReservation")
public class ServletConfirmReservation extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Connecting co = new Connecting();
        Connection conn = co.connection();
        Vector<Zone> zones = new Vector<>();
        Vector<Reservation> reservations = new Vector<>();
        Zone zone = new Zone();
        Reservation reservation = new Reservation();
        int id = Integer.parseInt(request.getParameter("event"));
        String name = request.getParameter("name");
        double price = Double.parseDouble(request.getParameter("price"));
        double newPrice = price;
        double total = 0;
        try {
            conn.setAutoCommit(false);
            zones = zone.getAllZone(conn, id);
            reservations = reservation.getListOfReservations(conn, id, name);
            for (int i = 0; i < reservations.size(); i++) {
                for (int j = 0; j < zones.size(); j++) {
                    if(zones.get(j).getMinSize() <= reservations.get(i).getNbOfPlace() && zones.get(j).getMaxSize() >= reservations.get(i).getNbOfPlace()) {
                        int nbOfPlace = reservations.get(i).getNbOfPlace();
                        int newState = 30;
                        if(newPrice < zones.get(j).getPrice()) {
                            newState = 20; // change it if we want to free place after no confirmation
                            System.out.println(id + " - " + nbOfPlace + " - " + newState);
                            reservation.changeState(conn, id, nbOfPlace, newState);
                        } else {
                            System.out.println(id + " - " + nbOfPlace + " - " + newState);
                            reservation.changeState(conn, id, nbOfPlace, newState);
                            newPrice -= zones.get(j).getPrice();
                            total += zones.get(j).getPrice();
                        }
                    }
                }
            }
            if(price != total && newPrice < 0) {
                conn.commit();
                throw new Exception("Less than the amount due, you are missing "+ newPrice + "ariary pour les autres places. À payer avant le deadline");
            } else if(price != total && newPrice > 0) {
                conn.rollback();
                throw new Exception("Amount different from the amount due, please check the amount to deposit");
            } else {
                conn.commit();
                System.out.println("success reservation");
            }
            request.getRequestDispatcher("index.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace(response.getWriter());
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
