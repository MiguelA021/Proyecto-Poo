package upm.etsisi.poo.es;

enum type {
  MERCH, PAPELERIA, ROPA, LIBRO, ELECTRONICA
}

public class Product {
  private int Id;
  private String name;
  private int price;
  private int cuantity;

  public Product(String name, int price, int id, int cuantity) {
    this.name = name;
    this.price = price;
    this.Id = id;
    this.cuantity = cuantity;
  }

  public int getCuantity() {
    return cuantity;
  }

  public int getId() {
    return Id;
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
    this.Id = id;
  }

  // Name should be of less than 100 characters and nos empty
  public void setName(String name) {
    String[] nameComplete = name.split(" ");
    if (name.length() < 100 && !name.isEmpty()) {
      this.name = name;
    } else {
      if (!(name.length() < 100)) {
        System.out.println("The name should have less than 100 characters");
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
}
