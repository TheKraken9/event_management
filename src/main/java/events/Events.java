package events;

import java.sql.*;
import java.util.Vector;

public class Events {
    private int id;
    private String nameEvent;
    private int idPlace;
    private Date date;
    private Time time;
    private Timestamp timestamp;
    private int nbZone;
    private int maxfreeplace;
    private int maxvipplace;
    private String place;
    private int state;


    public Events() {
    }

    public Events(int id, String nameEvent, int idPlace, Date date, Time time, Timestamp timestamp, int nbZone, int maxfreeplace, int maxvipplace, String place, int state) {
        this.id = id;
        this.nameEvent = nameEvent;
        this.idPlace = idPlace;
        this.date = date;
        this.time = time;
        this.timestamp = timestamp;
        this.nbZone = nbZone;
        this.maxfreeplace = maxfreeplace;
        this.maxvipplace = maxvipplace;
        this.place = place;
        this.state = state;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) throws Exception {
        if(id <= 0) {
            throw new Exception("Invalid id");
        }
        this.id = id;
    }

    public String getNameEvent() {
        return nameEvent;
    }

    public void setNameEvent(String nameEvent) throws Exception {
        if(nameEvent == "") {
            throw new Exception("Invalid name");
        }
        this.nameEvent = nameEvent;
    }

    public int getIdPlace() {
        return idPlace;
    }

    public void setIdPlace(int idPlace) throws Exception {
        if(idPlace <= 0) {
            throw new Exception("Invalid place id");
        }
        this.idPlace = idPlace;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Time getTime() {
        return time;
    }

    public void setTime(Time time) {
        this.time = time;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) throws Exception{
        if(timestamp == null) {
            timestamp = new Timestamp(System.currentTimeMillis());
        }
        this.timestamp = timestamp;
    }

    public int getNbZone() {
        return nbZone;
    }

    public void setNbZone(int nbZone) throws Exception {
        if(nbZone <= 0) {
            throw new Exception("Invalid number of zone");
        }
        this.nbZone = nbZone;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public int getMaxfreeplace() {
        return maxfreeplace;
    }

    public void setMaxfreeplace(int maxfreeplace) {
        this.maxfreeplace = maxfreeplace;
    }

    public int getMaxvipplace() {
        return maxvipplace;
    }

    public void setMaxvipplace(int maxvipplace) {
        this.maxvipplace = maxvipplace;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public static void insertEvent(Connection co, Events event) throws Exception {
        String sql = "INSERT INTO EVENT(nameEvent, idPlace, dateOf, nbZone, state) VALUES ('"+ event.getNameEvent() + "'," + event.getIdPlace() + ",'" + event.getTimestamp().toString() + "'," + event.getNbZone() + ", default)";
        //System.out.println(sql);
        int result = co.createStatement().executeUpdate(sql);
        if(result != 0) {
            System.out.println("okay be, tafiditra");
        }
    }

    public Vector<Events> allEvents(Connection co) throws Exception {
        Vector<Events> events = new Vector<>();
        String sql = "select * from event";
        ResultSet resultSet = co.createStatement().executeQuery(sql);
        while (resultSet.next()) {
            Events event = new Events();
            event.setId(resultSet.getInt("id"));
            event.setNameEvent(resultSet.getString("nameEvent"));
            event.setIdPlace(resultSet.getInt("idPlace"));
            event.setTimestamp(resultSet.getTimestamp("dateOf"));
            event.setNbZone(resultSet.getInt("nbZone"));
            event.setState(resultSet.getInt("state"));
            events.add(event);
        }
        return events;
    }

    public void updateEventFinished(Connection co, int idEvent) throws Exception {
        String sql = "update event set state = 0 where id = " + idEvent;
        int result = co.createStatement().executeUpdate(sql);
        if(result != 0)
            System.out.println("success update for event finished");
    }

    public Vector<Events> getLastEvent(Connection co) throws Exception {
        Vector<Events> events = new Vector<>();
        String sql = "SELECT * FROM LASTEVENT";
        ResultSet resultSet = co.createStatement().executeQuery(sql);
        while (resultSet.next()) {
            Events event = new Events();
            event.setId(resultSet.getInt("id"));
            event.setNameEvent(resultSet.getString("nameevent"));
            event.setIdPlace(resultSet.getInt("idPlace"));
            event.setTimestamp(resultSet.getTimestamp("dateof"));
            event.setNbZone(resultSet.getInt("nbzone"));
            events.add(event);
        }
        return events;
    }

    public Vector<Events> getAllEvents(Connection co) throws Exception {
        Vector<Events> events = new Vector<>();
        String sql = "SELECT * FROM INFOEVENT";
        ResultSet resultSet = co.createStatement().executeQuery(sql);
        while (resultSet.next()) {
            Events event = new Events();
            event.setId(resultSet.getInt("idevent"));
            event.setPlace(resultSet.getString("place"));
            event.setIdPlace(resultSet.getInt("idPlace"));
            event.setTimestamp(resultSet.getTimestamp("dateof"));
            event.setNameEvent(resultSet.getString("nameevent"));
            event.setNbZone(resultSet.getInt("nbZone"));
            events.add(event);
        }
        return events;
    }

    public Vector<Events> getSpecifiedEventsByPlace(Connection co, int idPlace) throws Exception {
        Vector<Events> events = new Vector<>();
        String sql = "SELECT * FROM INFOEVENT where idPlace = " + idPlace;
        ResultSet resultSet = co.createStatement().executeQuery(sql);
        //System.out.println(sql);
        while (resultSet.next()) {
            Events event = new Events();
            event.setId(resultSet.getInt("idevent"));
            event.setPlace(resultSet.getString("place"));
            event.setId(resultSet.getInt("idPlace"));
            event.setTimestamp(resultSet.getTimestamp("dateof"));
            event.setNameEvent(resultSet.getString("nameevent"));
            event.setNbZone(resultSet.getInt("nbZone"));
            events.add(event);
        }
        return events;
    }

    public Vector<Events> getSpecifiedEventsByEvent(Connection co, int idEvent) throws Exception {
        Vector<Events> events = new Vector<>();
        String sql = "SELECT * FROM INFOEVENT where idEvent = " + idEvent;
        ResultSet resultSet = co.createStatement().executeQuery(sql);
        System.out.println(sql);
        while (resultSet.next()) {
            Events event = new Events();
            event.setId(resultSet.getInt("idevent"));
            event.setPlace(resultSet.getString("place"));
            event.setId(resultSet.getInt("idPlace"));
            event.setTimestamp(resultSet.getTimestamp("dateof"));
            event.setNameEvent(resultSet.getString("nameevent"));
            event.setNbZone(resultSet.getInt("nbZone"));
            event.setMaxfreeplace(resultSet.getInt("maxFreePlace"));
            event.setMaxvipplace(resultSet.getInt("maxVipPlace"));
            events.add(event);
        }
        return events;
    }

    public void refreshEvent(Connection co) throws Exception {
        Vector<Events> events = new Vector<>();
        events = this.allEvents(co);
        for (int i = 0; i < events.size(); i++) {
            if(events.get(i).getTimestamp().before(new Timestamp(System.currentTimeMillis()))){
                this.updateEventFinished(co, events.get(i).getId());
            }
        }
    }
}
