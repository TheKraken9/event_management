package reservation;

public class Facture {
    private int nbOfPlace;
    private double price;

    public Facture() {
    }

    public Facture(int nbOfPlace, double price) {
        this.nbOfPlace = nbOfPlace;
        this.price = price;
    }

    public int getNbOfPlace() {
        return nbOfPlace;
    }

    public void setNbOfPlace(int nbOfPlace) throws Exception{
        if(nbOfPlace <= 0)
            throw new Exception("Invalid number of place");
        this.nbOfPlace = nbOfPlace;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) throws Exception{
        if(price < 0)
            throw new Exception("Invalid Price");
        this.price = price;
    }
}
