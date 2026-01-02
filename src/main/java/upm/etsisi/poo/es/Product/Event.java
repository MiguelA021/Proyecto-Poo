package upm.etsisi.poo.es.Product;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;

public abstract class Event extends Product {

  protected LocalDateTime expiracyDate;
  protected int maxPeopleGeneral=100;
  protected  int maxPeopleLocal; //Este es el MAX PEOPLE QUE SE PASA POR ARGUMENTOS
  protected int actualPeople;

  public Event(int id, String name, double price, String expiryDate, int maxPeople) {
    try {
      this.id = id;
      this.name = name;
      this.price = price;
      if(maxPeople<=maxPeopleGeneral){
        setMaxPeopleGeneral(maxPeople);
        this.maxPeopleLocal = maxPeople;
      }
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

  public boolean actualPeopleCorrect(int actualPeople){
    return actualPeople<=maxPeopleLocal;
  }

  public int getMaxPeopleGeneral() {
    return maxPeopleGeneral;
  }

  public int getMaxPeopleLocal(){return maxPeopleLocal;}

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

  public void setMaxPeopleGeneral(int maxPeopleGeneral) {
    this.maxPeopleGeneral = maxPeopleGeneral;
  }

  public void setActualPeople(int actualPeople){
    this.actualPeople = actualPeople;
  }

  public abstract String toStringTicketAdd();
}
