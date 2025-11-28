package upm.etsisi.poo.es.Product;


public class Event extends Product {

    protected int maxPersonas = 100;
    protected  String expiracyDate;

    public Event(int id, String name, double price, String expiracyDate) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.expiracyDate = expiracyDate;
    }

    public  int getMaxPersonas(){
        return maxPersonas;
    }

    public String getName() {
        return this.name;
    }

    public int getId() {
        return this.id;
    }

    public double getPrice() {
        return this.price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setName(String name) {
        this.name = name;
    }


}
