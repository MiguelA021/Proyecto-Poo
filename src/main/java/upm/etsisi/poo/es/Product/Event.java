package upm.etsisi.poo.es.Product;

import upm.etsisi.poo.es.Commands.ProductCommand;

public class Event extends Product {

    private int maxPersonas;
    private int personasActuales;
    private String expiracyDate;

    public Event(int id, String name, double price, String expiryDate, int maxPersonas) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.expiracyDate = expiryDate;
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
}
