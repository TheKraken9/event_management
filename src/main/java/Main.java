import connecting.Connecting;
import events.Events;
import selling.FreeSelling;
import zone.Zone;

import java.sql.Connection;
import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.Vector;

public class Main {
    public static void main(String args[]) throws Exception{
        Connecting co = new Connecting();
        Connection conn = co.connection();
        /*Events events = new Events();
        events.setNameEvent("concert");
        events.setTimestamp(Timestamp.valueOf("2023-01-31 14:00"));
        //events.setTime(Time.valueOf("14:00:00"));
        events.setIdPlace(1);
        events.setNbZone(12);
        events.insertEvent(conn, events);*/

        /*FreeSelling freeSelling = new FreeSelling();
        freeSelling.freeSell(conn, 10, 90);*/

        //Timestamp timestamp = (java.sql.Timestamp.valueOf());
        //System.out.println(timestamp);
        Timestamp timestamp = new Timestamp(System.currentTimeMillis() + 10);
        System.out.println(timestamp.toString());

        Zone zone = new Zone();
        Vector<Zone> zones = new Vector<>();
        zones = zone.getSubZone(conn, 13);
        System.out.println(zones.size());
    }
}
