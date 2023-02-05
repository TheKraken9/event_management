package controller;

import configuration.Configuration;
import connecting.Connecting;
import events.Events;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;
import java.sql.Connection;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Vector;

@WebServlet(name = "ServletConfigureTime", value = "/ServletConfigureTime")
public class ServletConfigureTime extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Connecting co = new Connecting();
        Connection conn = co.connection();
        int idEvent = Integer.parseInt(request.getParameter("idEvent"));
        int nbZone = Integer.parseInt(request.getParameter("nbZone"));
        int temps = Integer.parseInt(request.getParameter("temps"));
        Events event = new Events();
        Vector<Events> events = new Vector<>();
        try {
            events = event.getSpecifiedEventsByEvent(conn, idEvent);
            for (int i = 1; i <= nbZone; i++) {
                for (int j = 0; j <= temps; j++) {
                    Configuration configuration = new Configuration();
                    configuration.setIdEvent(idEvent);
                    configuration.setIdZone(i);
                    String time = "time"+(i)+(j+1);
                    String timer = "timer"+(i)+(j+1);
                    if(j == 0) {
                        configuration.setMinBasedTime(new Timestamp(System.currentTimeMillis()));
                        String datetime = request.getParameter(time);
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
                        Date date = sdf.parse(datetime);
                        Timestamp timestamp = new Timestamp(date.getTime());
                        configuration.setMaxBasedTime((Timestamp) timestamp);
                        System.out.println(timestamp);
                        configuration.setTimer(Long.parseLong(request.getParameter(timer)));
                        configuration.insertConfiguration(conn , configuration);
                    } else {
                        String time1 = "time"+(i)+(j);
                        String datetime1 = request.getParameter(time1);
                        String datetime2 = request.getParameter(time);
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
                        Date date1 = sdf.parse(datetime1);
                        Date date2 = sdf.parse(datetime2);
                        Timestamp timestamp1 = new Timestamp(date1.getTime());
                        Timestamp timestamp2 = new Timestamp(date2.getTime());
                        configuration.setMinBasedTime((Timestamp) timestamp1);
                        configuration.setMaxBasedTime((Timestamp) timestamp2);
                        configuration.setTimer(Long.parseLong(request.getParameter(timer)));
                        configuration.insertConfiguration(conn , configuration);
                    }
                }
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
