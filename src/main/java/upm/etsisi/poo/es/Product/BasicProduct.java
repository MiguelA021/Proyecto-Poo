package upm.etsisi.poo.es.Product;

import org.apache.commons.csv.CSVPrinter;
import upm.etsisi.poo.es.type;

import java.io.IOException;

public class BasicProduct extends Product {

  protected type category;

  public BasicProduct(int id, String name, type category, double price) {
    this.name = name.trim();
    this.price = price;
    this.id = id;
    this.category = category;
  }

  public int getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public double getPrice() {
    return price;
  }

  public type getCategory() {
    return this.category;
  }

  public void setId(int id) {
    this.id = id;
  }

  public void SetCategory(type category) {
    this.category = category;
  }

  public void setName(String name) {
    if (name.length() < 100 && !name.isEmpty()) {
      this.name = name.trim();
    } else {
      if (name.length() >= 100) {
        System.out.println(NAME_LENGTH_ERROR);
      }
      if (name.isEmpty()) {
        System.out.println(NAME_NULL_ERROR);
      }
    }
  }

  public void setPrice(double price) {
    if (price > 0) { // Se debería incluir un control de que no este en null
      this.price = price;
    } else {
      this.price = 1;
      System.out.println(PRICE_POSITIVE_ERROR);
    }

  }

  /**
   * The method returns the price already with the discount applied
   * @return it returns the price discounted
   */
  public double getDiscountedPrice() {
    double discountRate;
    switch (this.category) {// no hace falta MERCH/ si el descuento es 0.0
      case STATIONERY:
        discountRate = 0.05;
        break;
      case CLOTHES:
        discountRate = 0.07;
        break;
      case BOOK:
        discountRate = 0.10;
        break;
      case ELECTRONICS:
        discountRate = 0.03;
        break;
      default:
        discountRate = 0.0;
    }

    return price * (1 - discountRate);
  }

  public String toString() {
    return "{class:Product, id:" + this.id + ", name:'" + this.name + "', category:" + this.category + ", price:"
        + this.price + "}";
  }

  /**
   * The method returns the String according to the format and with the discounted price
   * @param discountValue
   * @return
   */
  public String toStringDiscount(double discountValue) {
    StringBuilder sc = new StringBuilder();
    sc.append(String.format("{class:Product, id: %d, name: '%s', category: %s, price: %.2f} **discount -%.2f%n",
        this.id, this.name, this.category, this.price, discountValue));
    return sc.toString();
  }
    @Override
    public void printCsv(CSVPrinter csvPrinter) throws IOException {
        csvPrinter.printRecord("BasicProduct", id, name,category.name(), price);
    }


}
