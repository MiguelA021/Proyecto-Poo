package upm.etsisi.poo.es;

import java.util.*;

import static upm.etsisi.poo.es.type.MERCH;

enum type {
  MERCH, PAPELERIA, ROPA, LIBRO, ELECTRONICA
}

public class Product {
  private int id;
  private String name;
  private int price;
  private int cuantity;
  private type category;


  public Product(String name, int price, int id, int cuantity) {
    this.name = name;
    this.price = price;
    this.id = id;
    this.cuantity = cuantity;
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

  public int getPrice() {
    return price;
  }

  public void setCuantity(int cuantity) {
    this.cuantity = cuantity;
  }

  public void setId(int id) {
    this.id = id;
  }

  // Name should be of less than 100 characters and nos empty
  public void setName(String name) {
    if (name.length() < 100 && !name.isEmpty()) {
      this.name = name;
    } else {
      if (name.length() > 100) {
        System.out.println("The name should be less than 100 characters");
      }
      if (name.isEmpty()) {
        System.out.println("The name shouldn't be empty");
      }
    }
  }

  // Price is always positive
  public void setPrice(int price) {
    if (price > 0) {
      this.price = price;
    } else {
      this.price = 1;
      System.out.println("WARNING: The price should be a positive number");
    }

  }

  public double getDiscountedPrice(int quantityOfProduct) {
    if (quantityOfProduct <= 1) {
      return price; //No discount
    }

    double discountRate;
    switch (category) {
      case MERCH:
        discountRate = 0.0;
        break;
      case PAPELERIA:
        discountRate = 0.05;
        break;
      case ROPA:
        discountRate = 0.07;
        break;
      case LIBRO:
        discountRate = 0.10;
        break;
      case ELECTRONICA:
        discountRate = 0.03;
        break;
      default:
        discountRate = 0.0;
    }

    return price * (1 - discountRate);
  }

}
