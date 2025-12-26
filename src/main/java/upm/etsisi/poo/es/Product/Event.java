package upm.etsisi.poo.es.Product;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;

public class Event extends Product {

  protected LocalDateTime expiracyDate;
  protected int maxPersonas = 100;

  public Event(int id, String name, double price, String expiryDate) {
    try {
      this.id = id;
      this.name = name;

      this.price = price;

      LocalDate date = LocalDate.parse(expiryDate);
      this.expiracyDate = date.atStartOfDay();
    } catch (DateTimeParseException e) {
      System.out.println("ERROR: DATE FORMAT NOT VALID");
    }
  }

  public boolean fechaValida(LocalDateTime time) {
    if (expiracyDate == null) {
      return false;
    }

    LocalDate today = time.toLocalDate();
    LocalDate eventDay = expiracyDate.toLocalDate();

    // válido si el día del evento es hoy o posterior
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
}
