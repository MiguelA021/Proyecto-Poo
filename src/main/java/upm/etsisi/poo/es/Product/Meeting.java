package upm.etsisi.poo.es.Product;

public class Meeting extends Event {
    public Meeting(int id, String name, double price, String expiracyDate) {
        super(id, name, price, expiracyDate);
    }

    public String toString() {
        return "Class:Meeting" + ", id:" + this.id + ", name:" + this.name + ", price:" + this.price + ", date of Event:" + this.expiracyDate +
                ", max people allowed:" + this.maxPersonas;
    }
}
