package reservation;

import selling.VipSelling;
import zone.Zone;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.Vector;

public class Reservation {
    private int id;
    private int idEvent;
    private String nameClient;
    private Timestamp beginDate;
    private Timestamp lastDate;
    private int nbOfPlace;
    private int stateOfReservation;
    private int nbOfZone;

    public Reservation() {
    }

    public Reservation(int id, int idEvent, String nameClient, Timestamp beginDate, Timestamp lastDate, int nbOfPlace, int stateOfReservation, int nbOfZone) {
        this.id = id;
        this.idEvent = idEvent;
        this.nameClient = nameClient;
        this.beginDate = beginDate;
        this.lastDate = lastDate;
        this.nbOfPlace = nbOfPlace;
        this.stateOfReservation = stateOfReservation;
        this.nbOfZone = nbOfZone;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) throws Exception{
        if(id < 0) {
            throw new Exception("Invalid parameter");
        }
        this.id = id;
    }

    public int getIdEvent() {
        return idEvent;
    }

    public void setIdEvent(int idEvent) throws Exception{
        if(id < 0) {
            throw new Exception("Invalid parameter");
        }
        this.idEvent = idEvent;
    }

    public String getNameClient() {
        return nameClient;
    }

    public void setNameClient(String nameClient) throws Exception{
        if(nameClient == "") {
            throw new Exception("Please enter name");
        }
        this.nameClient = nameClient;
    }

    public Timestamp getBeginDate() {
        return beginDate;
    }

    public void setBeginDate(Timestamp beginDate) {
        if(beginDate == null) {
            this.beginDate = new Timestamp(System.currentTimeMillis());
        } else {
            this.beginDate = beginDate;
        }
    }

    public Timestamp getLastDate() {
        return lastDate;
    }

    public void setLastDate(Timestamp lastDate) {
        if(lastDate == null) {
            this.lastDate = new Timestamp(System.currentTimeMillis());
        } else {
            this.lastDate = lastDate;
        }

    }

    public int getNbOfPlace() {
        return nbOfPlace;
    }

    public void setNbOfPlace(int nbOfPlace) throws Exception{
        if(nbOfPlace <= 0) {
            throw new Exception("Invalid number oo place");
        }
        this.nbOfPlace = nbOfPlace;
    }

    public int getStateOfReservation() {
        return stateOfReservation;
    }

    public void setStateOfReservation(int stateOfReservation) throws Exception{
        if(stateOfReservation <= 0) {
            throw new Exception("Invalid state of reservation");
        }
        this.stateOfReservation = stateOfReservation;
    }

    public int getNbOfZone() {
        return nbOfZone;
    }

    public void setNbOfZone(int nbOfZone) {
        this.nbOfZone = nbOfZone;
    }

    public void insertReservation(Connection connection, Reservation reservation) throws Exception {
        String sql = "insert into sellingvip(idEvent, nameclient, beginDate, lastDate, nbOfZone) values(?, ?, ?, ?, ?)";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1, reservation.getIdEvent());
        statement.setString(2, reservation.getNameClient());
        statement.setTimestamp(3, Timestamp.valueOf(reservation.getBeginDate().toString()));
        statement.setTimestamp(4, Timestamp.valueOf(reservation.getLastDate().toString()));
        statement.setInt(5, reservation.getNbOfZone());
        int result = statement.executeUpdate();
        if(result != 0) {
            System.out.println("success reservation");
        }
    }

    public void insertContentReservation(Connection connection, Reservation reservation) throws Exception {
        String sql = "insert into listOfReservation(idevent, nameclient, nbofplace, stateofreservation) values(?, ?, ?, ?)";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1, reservation.getIdEvent());
        statement.setString(2, reservation.getNameClient());
        statement.setInt(3, reservation.getNbOfPlace());
        statement.setInt(4, reservation.getStateOfReservation());
        int result = statement.executeUpdate();
        if(result != 0) {
            System.out.println("content of reservation successfully");
        }
    }

    public void updateContentReservation(Connection connection, Reservation reservation) throws Exception {
        String sql = "update listofreservation set nameclient ='" + reservation.getNameClient() + "', stateofreservation = " + reservation.getStateOfReservation() + " where idevent =" + reservation.getIdEvent() + " and nbofplace = " + reservation.getNbOfPlace();
        int result = connection.createStatement().executeUpdate(sql);
        System.out.println(sql);
        if(result != 0) {
            System.out.println("success be");
        }
    }

    public Vector<Reservation> getListOfReservations(Connection co, int idEvent, String name) throws Exception {
        Vector<Reservation> reservations = new Vector<>();
        String sql = "select * from listOfReservation where idEvent =" + idEvent + " and nameClient ='" + name + "' and stateofreservation != 30";
        ResultSet resultSet = co.createStatement().executeQuery(sql);
        System.out.println(sql);
        while (resultSet.next()) {
            Reservation reservation = new Reservation();
            reservation.setIdEvent(resultSet.getInt("idEvent"));
            reservation.setNameClient(resultSet.getString("nameclient"));
            reservation.setNbOfPlace(resultSet.getInt("nbofplace"));
            reservation.setStateOfReservation(resultSet.getInt("stateofreservation"));
            reservations.add(reservation);
        }
        return reservations;
    }

     public Vector<Reservation> getListOfReservationsRest(Connection co, int idEvent, String name) throws Exception {
        Vector<Reservation> reservations = new Vector<>();
        String sql = "select * from listOfReservation where idEvent =" + idEvent + " and nameClient ='" + name + "' and stateofreservation != 30 and stateofreservation != 1";
        ResultSet resultSet = co.createStatement().executeQuery(sql);
        System.out.println(sql);
        while (resultSet.next()) {
            Reservation reservation = new Reservation();
            reservation.setIdEvent(resultSet.getInt("idEvent"));
            reservation.setNameClient(resultSet.getString("nameclient"));
            reservation.setNbOfPlace(resultSet.getInt("nbofplace"));
            reservation.setStateOfReservation(resultSet.getInt("stateofreservation"));
            reservations.add(reservation);
        }
        return reservations;
    }

    public Vector<Reservation> getAllReservation(Connection co, String name) throws Exception {
        Vector<Reservation> reservations = new Vector<>();
        String sql = "select * from listOfReservation where nameClient ='" + name + "'";
        ResultSet resultSet = co.createStatement().executeQuery(sql);
        System.out.println(sql);
        while (resultSet.next()) {
            Reservation reservation = new Reservation();
            reservation.setIdEvent(resultSet.getInt("idEvent"));
            reservation.setNameClient(resultSet.getString("nameclient"));
            reservation.setNbOfPlace(resultSet.getInt("nbofplace"));
            reservation.setStateOfReservation(resultSet.getInt("stateofreservation"));
            reservations.add(reservation);
        }
        return reservations;
    }

    public Vector<Reservation> getListOfReservationsCanceled(Connection co, int idEvent, int nbOfPlace) throws Exception {
        Vector<Reservation> reservations = new Vector<>();
        String sql = "select * from listOfReservation where idEvent =" + idEvent + " and nbofplace =" + nbOfPlace;
        ResultSet resultSet = co.createStatement().executeQuery(sql);
        while (resultSet.next()) {
            Reservation reservation = new Reservation();
            reservation.setIdEvent(resultSet.getInt("idEvent"));
            reservation.setNameClient(resultSet.getString("nameclient"));
            reservation.setNbOfPlace(resultSet.getInt("nbofplace"));
            reservation.setStateOfReservation(resultSet.getInt("stateofreservation"));
            reservations.add(reservation);
        }
        return reservations;
    }

    public void changeState(Connection co, int idEvent, int nbOfPlace, int newState) throws Exception {
        String sql = "Update listofreservation set stateofreservation = " + newState + " where idEvent = " + idEvent + " and nbOfPlace =" + nbOfPlace;
        int result = co.createStatement().executeUpdate(sql);
        System.out.println(sql);
        if(result != 0) {
            System.out.println("Success modification");
        }
    }

    public void checkRefresh(Connection connection) throws Exception {
        Vector<VipSelling> vipSellings = new Vector<>();
        Vector<Reservation> reservations = new Vector<>();
        VipSelling vipSelling = new VipSelling();
        Reservation reservation = new Reservation();
        vipSellings = vipSelling.getAllVipSelling(connection);

        for (int i = 0; i < vipSellings.size(); i++) {
            if(vipSellings.get(i).getEndDate().before(new java.sql.Timestamp(System.currentTimeMillis()))) {
                int nbzone = vipSellings.get(i).getNbOfZone();
                Zone zone = new Zone();
                Vector<Zone> zones = zone.getAllZoneByZone(connection, vipSellings.get(i).getIdEvent(), nbzone);
                reservations = reservation.getListOfReservations(connection, vipSellings.get(i).getIdEvent(), vipSellings.get(i).getName());
                for (int k = 0; k < zones.size(); k++) {
                    for (int j = 0; j < reservations.size(); j++) {
                        if(zones.get(k).getMinSize() <= reservations.get(j).getNbOfPlace() && zones.get(k).getMaxSize() >= reservations.get(j).getNbOfPlace()) {
                            if(reservations.get(j).getStateOfReservation() != 30) {
                                this.changeState(connection, reservations.get(j).getIdEvent(), reservations.get(j).getNbOfPlace(), 1);
                            }
                        }
                    }
                }
            }
        }
    }
}
