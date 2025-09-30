package upm.etsisi.poo.es;

import static upm.etsisi.poo.es.type.MERCH;

enum type {
  MERCH, STATIONERY, CLOTHES, BOOK, ELECTRONICS
}

public class Product {
  private int id;
  private String name;
  private double price;
  private int cuantity;
  private type category;

  public Product(String name, double price, int id, int cuantity, type category) {
    this.name = name;
    this.price = price;
    this.id = id;
    this.cuantity = cuantity;
    this.category = category;
  }

  public int getCuantity() {
    return cuantity;
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

  public void setCuantity(int cuantity) {
    this.cuantity = cuantity;
  }

  public void setId(int id) {
    this.id = id;
  }

  /**
   * @param name should be of less than 100 characters and not empty
   */
  public void setName(String name) {
    if (name.length() < 100 && !name.isEmpty()) {
      this.name = name;
    } else {
      if (name.length() >= 100) {
        System.out.println("The name should be less than 100 characters");
      }
      if (name.isEmpty()) {
        System.out.println("The name shouldn't be empty");
      }
    }
  }

  /**
   * @param price must be always higher than zero
   */
  public void setPrice(int price) {
    if (price > 0) { // Se debería incluir un control de que no este en null
      this.price = price;
    } else {
      this.price = 1;
      System.out.println("WARNING: The price should be a positive number");
    }

  }

  /**
   * @param quantityOfProduct the amount of products of the Product (Object)
   * @return the discount that is allowed with the amount of the product
   */
  public double getDiscountedPrice(double quantityOfProduct) {
    if (quantityOfProduct <= 1) {
      return price; // No discount
    }

    double discountRate;
    switch (this.category) {
      case MERCH:
        discountRate = 0.0;
        break;
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

  @Override
  public String toString() {
    return "{class:Product, Id: " + this.id + ", name: " + this.name + ", category: " + this.category + ", price: "
        + this.price + " €" + "}";
  }

}
