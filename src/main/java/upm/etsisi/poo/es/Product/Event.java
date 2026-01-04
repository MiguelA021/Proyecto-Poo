package upm.etsisi.poo.es.Product;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public class Event extends Product {

    @Column(name = "expiracyDate")
    protected LocalDateTime expiracyDate;
    @Column(name = "maxPersonas")
    protected int maxPersonas = 100;
    @Column(name = "pricePerPerson")
    protected double pricePerPerson;

    public Event(int id, String name, double price, String expiryDate) {
        try {
            this.id = id;
            this.name = name;

            this.pricePerPerson = price;

            this.price = 0.0;

            LocalDate date = LocalDate.parse(expiryDate);
            this.expiracyDate = date.atStartOfDay();
        } catch (DateTimeParseException e) {
            System.out.println("ERROR: DATE FORMAT NOT VALID");
        }
    }

    /**
     * The method checks if the event is on a permitted date (must be today or later)
     *
     * @param time the time given by parameter (usually the actual time)
     * @return it returns true if the date is valid
     */
    public boolean fechaValida(LocalDateTime time) {
        if (expiracyDate == null) {
            return false;
        }
        LocalDate today = time.toLocalDate();
        LocalDate eventDay = expiracyDate.toLocalDate();
        return !eventDay.isBefore(today);
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

    public void setMaxPersonas(int maxPersonas) {
        this.maxPersonas = maxPersonas;
    }

    public double getPricePerPerson() {
        return pricePerPerson;
    }
}
