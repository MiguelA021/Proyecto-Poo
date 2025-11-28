package upm.etsisi.poo.es.Product;

import upm.etsisi.poo.es.Commands.ProductCommand;

public class Event extends Product {

    protected int maxPersonas;
    protected  int personasActuales;
    protected  String expiracyDate;

    public Event(int id, String name, double price, String expiracyDate, int maxPersonas) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.expiracyDate = expiracyDate;
        this.maxPersonas = maxPersonas;
        this.personasActuales = 0;
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

    public String toString(){
        return "Class:Food, id:" + this.id + ", name:" + this.name + ", price:" + this.price + ", date of Event:" + this.expiracyDate +
                ", max people allowed:" + this.maxPersonas;
    }
}
