package selling;

import place.Place;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Vector;

public class FreeSelling {
    private int idEvent;
    private int maxFreePlace;
    private int maxVipPlace;
    private int sumOfSell;

    public FreeSelling() {
    }

    public FreeSelling(int idEvent, int maxFreePlace, int maxVipPlace, int sumOfSell) {
        this.idEvent = idEvent;
        this.maxFreePlace = maxFreePlace;
        this.maxVipPlace = maxVipPlace;
        this.sumOfSell = sumOfSell;
    }

    public int getIdEvent() {
        return idEvent;
    }

    public void setIdEvent(int idEvent) {
        this.idEvent = idEvent;
    }

    public int getMaxFreePlace() {
        return maxFreePlace;
    }

    public void setMaxFreePlace(int maxFreePlace) {
        this.maxFreePlace = maxFreePlace;
    }

    public int getMaxVipPlace() {
        return maxVipPlace;
    }

    public void setMaxVipPlace(int maxVipPlace) {
        this.maxVipPlace = maxVipPlace;
    }

    public int getSumOfSell() {
        return sumOfSell;
    }

    public void setSumOfSell(int sumOfSell) {
        this.sumOfSell = sumOfSell;
    }

    public void freeSell(Connection co, int idEvent, int nbOfPlace) throws Exception {
        if(this.isFreeSell(co, idEvent, nbOfPlace)) {
            String sql = "INSERT INTO sellingfree(idEvent, nbPlaces) VALUES(" + idEvent + "," + nbOfPlace + ")";
            Statement statement = co.createStatement();
            int result = statement.executeUpdate(sql);
            if(result != 0) {
                System.out.println("achat en success");
            }
        } else {
            throw new Exception("Places you entered are too strong");
        }
    }

    public boolean isFreeSell(Connection co, int idEvent, int nbOfPlace) throws Exception {
        Vector<FreeSelling> freeSellings = new Vector<FreeSelling>();
        Vector<Place> places = new Vector<Place>();
        freeSellings = this.getPlace(co, idEvent);
        if(freeSellings.size() == 0) {
            return true;
        } else if(freeSellings.size() != 0) {
            if (freeSellings.get(0).getMaxFreePlace() < (nbOfPlace+freeSellings.get(0).getSumOfSell())) {
                return false;
            }
        } else {
            return true;
        }
        return true;
    }

    public Vector<FreeSelling> getPlace(Connection co, int idEvent) throws Exception {
        Vector<FreeSelling> freeSellings = new Vector<FreeSelling>();
        String sql = "select * from checkFreeSell where idEvent = " + idEvent;
        Statement statement = co.createStatement();
        ResultSet resultSet = statement.executeQuery(sql);
        while (resultSet.next()) {
            FreeSelling freeSelling = new FreeSelling();
            freeSelling.setIdEvent(resultSet.getInt("idEvent"));
            freeSelling.setMaxFreePlace(resultSet.getInt("maxFreePlace"));
            freeSelling.setMaxVipPlace(resultSet.getInt("maxVipPlace"));
            freeSelling.setSumOfSell(resultSet.getInt("somme"));
            freeSellings.add(freeSelling);
        }
        return freeSellings;
    }

     public Vector<Place> getAllPlace(Connection co) throws Exception {
        Vector<Place> places = new Vector<Place>();
        String sql = "SELECT * FROM PLACE";
        Statement statement = co.createStatement();
        ResultSet resultSet = statement.executeQuery(sql);
        while (resultSet.next()) {
            Place place = new Place();
            place.setNamePlace(resultSet.getString("namePlace"));
            place.setMaxFreePlace(resultSet.getInt("maxFreePlace"));
            place.setMaxVipPlace(resultSet.getInt("maxVipPlace"));
            places.add(place);
        }
        return places;
    }
}
