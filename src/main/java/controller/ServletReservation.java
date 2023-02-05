package controller;

import configuration.Configuration;
import connecting.Connecting;
import events.Events;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import reservation.Reservation;
import zone.Zone;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.Vector;

@WebServlet(name = "ServletReservation", value = "/ServletReservation")
public class ServletReservation extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Connection conn = new Connecting().connection();
        try {conn.setAutoCommit(false);
            int id = Integer.parseInt(request.getParameter("event"));
            String name = request.getParameter("name");
            //System.out.println(name);
            String[] checkboxes = request.getParameterValues("nbplace");
            Zone zone = new Zone();
            Vector<Reservation> reservations = new Vector<>();
            Vector<Zone> zones = new Vector<>();
            zones = zone.getSubZone(conn, id);
            if(checkboxes == null) {
                throw new Exception("No place checked, please choose one or more");
            } else if (checkboxes != null) {
                for (String checkbox : checkboxes) {
                    int state = 10;
                    Reservation reservation = new Reservation();
                    Reservation reserv = new Reservation();
                    reserv.setIdEvent(id);
                    reserv.setNameClient(name);
                    reserv.setNbOfPlace(Integer.parseInt(checkbox));
                    reserv.setStateOfReservation(state);
                    reservations = reservation.getListOfReservationsCanceled(conn, id, Integer.parseInt(checkbox));
                    if (reservations.size() != 0) {
                        if (reservations.get(0).getStateOfReservation() == 1) {
                            reserv.updateContentReservation(conn, reserv);
                        } else if (reservations.get(0).getStateOfReservation() == 10) {
                            throw new Exception("place still reserved");
                        } else if (reservations.get(0).getStateOfReservation() == 20) {
                            throw new Exception("place waiting to be confirmed");
                        } else if (reservations.get(0).getStateOfReservation() == 30) {
                            throw new Exception("place already sold out");
                        }
                    } else {
                        reserv.insertContentReservation(conn, reserv);
                    }
                }

                Zone zone1 = new Zone();
                Vector<Zone> zones1 = zone1.getAllZone(conn, id);
                for (int i = 0; i < zones1.size(); i++) {
                    boolean result = false;
                    int nbzone = zones1.get(i).getNbOfZone();
                    Reservation reservation1 = new Reservation();
                    Vector<Reservation> reservations1 = new Vector<>();
                    reservations1 = reservation1.getListOfReservations(conn, id, name);
                    for (int j = 0; j < reservations1.size(); j++) {
                        if (zones1.get(i).getMinSize() <= reservations1.get(j).getNbOfPlace() && zones1.get(i).getMaxSize() >= reservations1.get(j).getNbOfPlace()) {
                            result = true;
                        }
                    }
                    if (result) {
                        Reservation reservation = new Reservation();
                        Configuration configuration = new Configuration();
                        Vector<Configuration> configurations = configuration.getAllConfigurations(conn, id, nbzone); //getAllConfiguration by an event and one zone
                        for (int j = 0; j < configurations.size(); j++) {
                            if (new Timestamp(System.currentTimeMillis()).after(configurations.get(j).getMinBasedTime()) && new Timestamp(System.currentTimeMillis()).before(configurations.get(j).getMaxBasedTime())) {
                                reservation.setIdEvent(id);
                                reservation.setNameClient(name);
                                reservation.setBeginDate(new Timestamp(System.currentTimeMillis()));
                                reservation.setLastDate(new Timestamp(System.currentTimeMillis() + configurations.get(j).getTimer()));
                                reservation.setNbOfZone(nbzone);
                                reservation.insertReservation(conn, reservation);
                            }
                        }
                    }
                }
            }
            conn.commit();
            request.getRequestDispatcher("index.jsp").forward(request, response);
        } catch (Exception e) {
            try {
                conn.rollback();
                e.printStackTrace(response.getWriter());
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
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
