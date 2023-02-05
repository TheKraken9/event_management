package place;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Vector;

public class Place {
    //namePlace, maxFreePlace, maxVipPlace
    private int id;
    private String namePlace;
    private double maxFreePlace;
    private double maxVipPlace;

    public Place() {
    }

    public Place(int id, String namePlace, double maxFreePlace, double maxVipPlace) {
        this.id = id;
        this.namePlace = namePlace;
        this.maxFreePlace = maxFreePlace;
        this.maxVipPlace = maxVipPlace;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNamePlace() {
        return namePlace;
    }

    public void setNamePlace(String namePlace) {
        this.namePlace = namePlace;
    }

    public double getMaxFreePlace() {
        return maxFreePlace;
    }

    public void setMaxFreePlace(double maxFreePlace) {
        this.maxFreePlace = maxFreePlace;
    }

    public double getMaxVipPlace() {
        return maxVipPlace;
    }

    public void setMaxVipPlace(double maxVipPlace) {
        this.maxVipPlace = maxVipPlace;
    }

    public Vector<Place> getAllPlace(Connection co) throws Exception {
        String sql = "SELECT * FROM PLACE";
        Statement statement = co.createStatement();
        ResultSet resultSet = statement.executeQuery(sql);
        Vector<Place> places = new Vector<>();
        while (resultSet.next()) {
            Place place = new Place();
            place.setId(resultSet.getInt("id"));
            place.setNamePlace(resultSet.getString("namePlace"));
            place.setMaxFreePlace(resultSet.getInt("maxFreePlace"));
            place.setMaxVipPlace(resultSet.getInt("maxVipPlace"));
            places.add(place);
        }
        return places;
    }

     public Vector<Place> getSpecifiedPlace(Connection co, int id) throws Exception {
        String sql = "SELECT * FROM PLACE where id = " + id;
        Statement statement = co.createStatement();
        ResultSet resultSet = statement.executeQuery(sql);
        Vector<Place> places = new Vector<>();
        while (resultSet.next()) {
            Place place = new Place();
            place.setId(resultSet.getInt("id"));
            place.setNamePlace(resultSet.getString("namePlace"));
            place.setMaxFreePlace(resultSet.getInt("maxFreePlace"));
            place.setMaxVipPlace(resultSet.getInt("maxVipPlace"));
            places.add(place);
        }
        return places;
    }
}
