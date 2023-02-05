package selling;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.Vector;

public class VipSelling {
    private int idEvent;
    private String name;
    private Timestamp beginDate;
    private Timestamp endDate;
    private int nbOfZone;

    public VipSelling() {
    }

    public VipSelling(int idEvent, String name, Timestamp beginDate, Timestamp endDate, int nbOfZone) {
        this.idEvent = idEvent;
        this.name = name;
        this.beginDate = beginDate;
        this.endDate = endDate;
        this.nbOfZone = nbOfZone;
    }

    public int getIdEvent() {
        return idEvent;
    }

    public void setIdEvent(int idEvent) throws Exception {
        if(idEvent <= 0)
            throw new Exception("Invalid Id Event");
        this.idEvent = idEvent;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) throws Exception{
        if(name == "")
            throw new Exception("Invalid Name, name is Empty");
        this.name = name;
    }

    public Timestamp getBeginDate() {
        return beginDate;
    }

    public void setBeginDate(Timestamp beginDate) throws Exception{
        if(beginDate == null)
            throw new Exception("The start date is empty");
        this.beginDate = beginDate;
    }

    public Timestamp getEndDate() {
        return endDate;
    }

    public void setEndDate(Timestamp endDate) throws Exception {
        if(endDate == null)
            throw new Exception("The end date is empty");
        this.endDate = endDate;
    }

    public int getNbOfZone() {
        return nbOfZone;
    }

    public void setNbOfZone(int nbOfZone) throws Exception{
        if(nbOfZone <= 0)
            throw new Exception("The Number of Zone is invalid");
        this.nbOfZone = nbOfZone;
    }

    public Vector<VipSelling> getAllVipSelling(Connection co) throws Exception {
         Vector<VipSelling> vipSellings = new Vector<>();
        String sql = "select * from sellingvip";
        ResultSet resultSet = co.createStatement().executeQuery(sql);
        while (resultSet.next()) {
            VipSelling vipSelling = new VipSelling();
            vipSelling.setIdEvent(resultSet.getInt("idEvent"));
            vipSelling.setName(resultSet.getString("nameclient"));
            vipSelling.setBeginDate(resultSet.getTimestamp("begindate"));
            vipSelling.setEndDate(resultSet.getTimestamp("lastdate"));
            vipSelling.setNbOfZone(resultSet.getInt("nbofzone"));
            vipSellings.add(vipSelling);
        }
        return vipSellings;
    }
}
