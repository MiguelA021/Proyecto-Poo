package upm.etsisi.poo.es.Product;

import jdk.vm.ci.meta.Local;
import upm.etsisi.poo.es.Commands.ProductCommand;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;

public class Event extends Product {

    private int maxPersonas;
    private int personasActuales;
    private LocalDateTime expiracyDate;

    public Event(int id, String name, double price, String expiryDate, int maxPersonas) {
        try {
            this.id = id;
            this.name = name;
            this.price = price;
            this.expiracyDate = LocalDateTime.parse(expiryDate);
            this.maxPersonas = maxPersonas;
            this.personasActuales = 0;
        }catch(DateTimeParseException e) {
            System.out.println("ERROR: DATE FORMAT NOT VALID");
        }
    }

    public boolean fechaValida(LocalDateTime time) {
        return false;
    }
    public LocalDateTime getExpiryDate() {
        return expiracyDate;
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
