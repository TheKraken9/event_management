package configuration;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.Vector;

public class Configuration {
    private int id;
    private int idEvent;
    private int idZone;
    private Timestamp minBasedTime;
    private long timer;
    private Timestamp maxBasedTime;

    public Configuration() {
    }

    public Configuration(int id, int idEvent, int idZone, Timestamp minBasedTime, long timer, Timestamp maxBasedTime) {
        this.id = id;
        this.idEvent = idEvent;
        this.idZone = idZone;
        this.minBasedTime = minBasedTime;
        this.timer = timer;
        this.maxBasedTime = maxBasedTime;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdEvent() {
        return idEvent;
    }

    public void setIdEvent(int idEvent) {
        this.idEvent = idEvent;
    }

    public int getIdZone() {
        return idZone;
    }

    public void setIdZone(int idZone) throws Exception{
        if(idZone <= 0) {
            throw new Exception("invalid id Zone");
        }
        this.idZone = idZone;
    }

    public Timestamp getMinBasedTime() {
        return minBasedTime;
    }

    public void setMinBasedTime(Timestamp minBasedTime) throws Exception{
        if(minBasedTime == null) {
            throw new Exception("the min based time is empty");
        }
        this.minBasedTime = minBasedTime;
    }

    public long getTimer() {
        return timer;
    }

    public void setTimer(long timer) throws Exception{
        if(timer <= 0) {
            throw new Exception("Timer invalid");
        }
        this.timer = timer;
    }

    public Timestamp getMaxBasedTime() {
        return maxBasedTime;
    }

    public void setMaxBasedTime(Timestamp maxBasedTime) throws Exception{
        if(maxBasedTime == null) {
            throw new Exception("the max based time is empty");
        }
        this.maxBasedTime = maxBasedTime;
    }

    public void insertConfiguration(Connection co, Configuration configuration) throws Exception {
        String sql = "insert into customtime(idEvent, idZone, minBasedTime, timer, maxBasedTime) values(" + configuration.getIdEvent() + ", " + configuration.getIdZone() + ", '" + configuration.getMinBasedTime() + "', " + configuration.getTimer() + ", '" + configuration.getMaxBasedTime() + "')";
        System.out.println(sql);
        int result = co.createStatement().executeUpdate(sql);
        if(result != 0) {
            System.out.println("success insertion configuration time");
        }
    }

    public Vector<Configuration> getAllConfigurations(Connection co, int idEvent, int nbOfZone) throws Exception {
        Vector<Configuration> configurations = new Vector<>();
        String sql = "select * from customtime where idEvent = " + idEvent + " and idZone = " + nbOfZone;
        ResultSet resultSet = co.createStatement().executeQuery(sql);
        while (resultSet.next()) {
            Configuration configuration = new Configuration();
            configuration.setIdEvent(resultSet.getInt("idEvent"));
            configuration.setIdZone(resultSet.getInt("idZone"));
            configuration.setMinBasedTime(resultSet.getTimestamp("minBasedTime"));
            configuration.setTimer(resultSet.getInt("timer"));
            configuration.setMaxBasedTime(resultSet.getTimestamp("maxBasedTime"));
            configurations.add(configuration);
        }
        return configurations;
    }
}
