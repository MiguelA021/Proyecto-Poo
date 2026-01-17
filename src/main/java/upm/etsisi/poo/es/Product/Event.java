package upm.etsisi.poo.es.Product;

import org.apache.commons.csv.CSVPrinter;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class Event extends Product {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
  protected LocalDateTime expiracyDate;
  protected int maxPersonas = 100;
  protected double pricePerPerson;

  public Event(int id, String name, double price, String expiryDate) {
    try {
      this.id = id;
      this.name = name;

      this.pricePerPerson = price;

      this.price = price
      ;

      LocalDate date = LocalDate.parse(expiryDate);
      this.expiracyDate = date.atStartOfDay();
    } catch (DateTimeParseException e) {
      System.out.println("ERROR: DATE FORMAT NOT VALID");
    }
  }

  /**
   * The method checks if the event is on a permitted date (must be today or later)
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
    @Override
    public void printCsv(CSVPrinter csvPrinter) throws IOException {
        csvPrinter.printRecord(id, name, pricePerPerson, expiracyDate);
    }
   public Product copy(){
      return new Event(this.id,this.name,this.price,this.expiracyDate.format(formatter));
  }
}
