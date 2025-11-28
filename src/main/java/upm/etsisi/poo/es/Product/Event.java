package upm.etsisi.poo.es.Product;

import jdk.vm.ci.meta.Local;
import upm.etsisi.poo.es.Commands.ProductCommand;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;

public class Event extends Product {

    private int personasActuales;
    private LocalDateTime expiracyDate;
    protected int maxPersonas = 100;

    public Event(int id, String name, double price, String expiryDate, int maxPersonas) {
        try {
            this.id = id;
            this.name = name;
            this.price = price;
            LocalDate date = LocalDate.parse(expiryDate);   // Conversión a DataLocalTime debido al formato
            this.expiracyDate = date.atStartOfDay();        //que nos llega por el comando
            this.maxPersonas = maxPersonas;
            this.personasActuales = 0;
        }catch(DateTimeParseException e) {
            System.out.println("ERROR: DATE FORMAT NOT VALID");
        }
    }

    public boolean fechaValida(LocalDateTime time) {
        return false;
    }
    public int getMaxPersonas() {
        return maxPersonas;
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

    public String toString(){
        return "Class:Food, id:" + this.id + ", name:" + this.name + ", price:" + this.price + ", date of Event:" + this.expiracyDate +
                ", max people allowed:" + this.maxPersonas;
    }
}
