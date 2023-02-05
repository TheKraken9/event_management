package zone;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Vector;

public class Zone {
    private int id;
    private int idEvent;
    private int idPlace;
    private int nbOfZone;
    private int minSize;
    private int maxSize;
    private double price;
    private int placenb;

    public Zone() {
    }

    public Zone(int id, int idEvent, int idPlace, int nbOfZone, int minSize, int maxSize, double price, int placenb) {
        this.id = id;
        this.idEvent = idEvent;
        this.idPlace = idPlace;
        this.nbOfZone = nbOfZone;
        this.minSize = minSize;
        this.maxSize = maxSize;
        this.price = price;
        this.placenb = placenb;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) throws Exception{
        if(id <= 0)
            throw new Exception("Invalid id");
        this.id = id;
    }

    public int getIdEvent() {
        return idEvent;
    }

    public void setIdEvent(int idEvent) throws Exception{
        if(idEvent <= 0)
            throw new Exception("Invalid Id event");
        this.idEvent = idEvent;
    }

    public int getIdPlace() {
        return idPlace;
    }

    public void setIdPlace(int idPlace) throws Exception{
        if(idPlace <= 0)
            throw new Exception("Invalid Id Place");
        this.idPlace = idPlace;
    }

    public int getNbOfZone() {
        return nbOfZone;
    }

    public void setNbOfZone(int nbOfZone) throws Exception{
        if(nbOfZone <= 0)
            throw new Exception("Invalid number of Zone");
        this.nbOfZone = nbOfZone;
    }

    public int getMinSize() {
        return minSize;
    }

    public void setMinSize(int minSize) throws Exception{
        if(minSize < 0)
            throw new Exception("Invalid Minimum of size of Zone");
        this.minSize = minSize;
    }

    public int getMaxSize() {
        return maxSize;
    }

    public void setMaxSize(int maxSize) throws Exception{
        if(maxSize < 0)
            throw new Exception("Invalid Maximum of size of Zone");
        this.maxSize = maxSize;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getPlacenb() {
        return placenb;
    }

    public void setPlacenb(int placenb) throws Exception{
        if(placenb < 0)
            throw new Exception("Invalid number of place");
        this.placenb = placenb;
    }

    public void insertZoneProgramming(Connection co, Zone zone) throws Exception {
        String sql = "insert into zoneprogramming(idevent, idPlace, nbOfZone, minSize, maxSize, price) values(?, ?, ?, ?, ?, ?)";
        System.out.println(sql);
        PreparedStatement statement = co.prepareStatement(sql);
        statement.setInt(1, zone.getIdEvent());
        statement.setInt(2, zone.getIdPlace());
        statement.setInt(3, zone.getNbOfZone());
        statement.setInt(4, zone.getMinSize());
        statement.setInt(5, zone.getMaxSize());
        statement.setDouble(6, zone.getPrice());
        int insert = statement.executeUpdate();
        if(insert != 0) {
            System.out.println("success insert zone programming");
        }
    }

    public void insertToSubZone(Connection co, Zone zone) throws Exception {
        String sql = "insert into subZone(idEvent, zonenb, placenb) values(?, ?, ?)";
        //System.out.println(sql);
        PreparedStatement statement = co.prepareStatement(sql);
        for (int i = zone.getMinSize(); i <= zone.getMaxSize(); i++) {
            statement.setInt(1, zone.getIdEvent());
            statement.setInt(2, zone.getNbOfZone());
            statement.setInt(3, i);
            int insert = statement.executeUpdate();
            if(insert != 0) {
                System.out.println("success insert sub_zone programming");
            }
        }
    }

    public Vector<Zone> getSubZone(Connection co, int idEvent) throws Exception {
        Vector<Zone> zones = new Vector<>();
        String sql = "select * from subzone where idEvent = " + idEvent + " order by zonenb";
        ResultSet resultSet = co.createStatement().executeQuery(sql);
        //System.out.println(sql);
        while(resultSet.next()) {
            Zone zone = new Zone();
            zone.setIdEvent(resultSet.getInt("idEvent"));
            zone.setNbOfZone(resultSet.getInt("zonenb"));
            zone.setPlacenb(resultSet.getInt("placenb"));
            zones.add(zone);
        }
        return zones;
    }

    public void insertFreePrice(Connection co, int IdEvent, int idPlace, double price) throws Exception {
        String sql = "insert into freeplace(idEvent, idPlace, price) values(" + IdEvent + "," + idPlace + "," + price +")";
        int result = co.createStatement().executeUpdate(sql);
        //System.out.println(sql);
        if(result != 0) {
            System.out.println("free price successfully");
        }
    }

    public Vector<Zone> getAllZone(Connection co, int idEvent) throws Exception {
        Vector<Zone> zones = new Vector<>();
        String sql = "select * from zoneprogramming where idEvent = " + idEvent;
        ResultSet resultSet = co.createStatement().executeQuery(sql);
        while (resultSet.next()) {
            Zone zone = new Zone();
            zone.setIdEvent(resultSet.getInt("idEvent"));
            zone.setIdPlace(resultSet.getInt("idPlace"));
            zone.setNbOfZone(resultSet.getInt("nbOfZone"));
            zone.setMinSize(resultSet.getInt("minsize"));
            zone.setMaxSize(resultSet.getInt("maxsize"));
            zone.setPrice(resultSet.getDouble("price"));
            zones.add(zone);
        }
        return zones;
    }

    public Vector<Zone> getAllZoneByZone(Connection co, int idEvent, int nbOfzone) throws Exception {
        Vector<Zone> zones = new Vector<>();
        String sql = "select * from zoneprogramming where idEvent = " + idEvent + " and nbofzone = " + nbOfzone;
        ResultSet resultSet = co.createStatement().executeQuery(sql);
        while (resultSet.next()) {
            Zone zone = new Zone();
            zone.setIdEvent(resultSet.getInt("idEvent"));
            zone.setIdPlace(resultSet.getInt("idPlace"));
            zone.setNbOfZone(resultSet.getInt("nbOfZone"));
            zone.setMinSize(resultSet.getInt("minsize"));
            zone.setMaxSize(resultSet.getInt("maxsize"));
            zone.setPrice(resultSet.getDouble("price"));
            zones.add(zone);
        }
        return zones;
    }
}
