package reservation;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Vector;

public class ColorCode {
    private int idEvent;
    private String nameClient;
    private int nbofplace;
    private int stateofreservation;
    private String color;
    private String state;

    public ColorCode() {
    }

    public ColorCode(int idEvent, String nameClient, int nbofplace, int stateofreservation, String color, String state) {
        this.idEvent = idEvent;
        this.nameClient = nameClient;
        this.nbofplace = nbofplace;
        this.stateofreservation = stateofreservation;
        this.color = color;
        this.state = state;
    }

    public int getIdEvent() {
        return idEvent;
    }

    public void setIdEvent(int idEvent) throws Exception{
        if(idEvent <= 0) {
            throw new Exception("Invalid event");
        }
        this.idEvent = idEvent;
    }

    public String getNameClient() {
        return nameClient;
    }

    public void setNameClient(String nameClient) throws Exception{
        if(nameClient == "") {
            throw new Exception("Invalid name");
        }
        this.nameClient = nameClient;
    }

    public int getNbofplace() {
        return nbofplace;
    }

    public void setNbofplace(int nbofplace) throws Exception{
        if(nbofplace <= 0) {
            throw new Exception("Invalid number of place");
        }
        this.nbofplace = nbofplace;
    }

    public int getStateofreservation() {
        return stateofreservation;
    }

    public void setStateofreservation(int stateofreservation) throws Exception{
        if(stateofreservation <= 0) {
            throw new Exception("Invalid state of reservation");
        }
        this.stateofreservation = stateofreservation;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) throws Exception {
        if(color == "") {
            throw new Exception("Empty color");
        }
        this.color = color;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public Vector<ColorCode> getAllColorCode(Connection co, int idEvent) throws Exception {
        Vector<ColorCode> colorCodes = new Vector<>();
        String sql = "select * from color where idEvent =" + idEvent;
        Statement statement = co.createStatement();
        ResultSet resultSet = statement.executeQuery(sql);
        while (resultSet.next()) {
            ColorCode colorCode = new ColorCode();
            colorCode.setIdEvent(resultSet.getInt("idEvent"));
            colorCode.setNameClient(resultSet.getString("nameclient"));
            colorCode.setNbofplace(resultSet.getInt("nbofplace"));
            colorCode.setColor(resultSet.getString("color"));
            colorCode.setState(resultSet.getString("state"));
            colorCode.setStateofreservation(resultSet.getInt("stateofreservation"));
            colorCodes.add(colorCode);
        }
        return colorCodes;
    }
}
