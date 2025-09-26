package upm.etsisi.poo.es;

enum type {
  MERCH, PAPELERIA, ROPA, LIBRO, ELECTRONICA
}

public class Product {
  private int Id;
  private String name;
  private int price;

    public Product(String name, int price, int id) {
        this.name = name;
        this.price = price;
        Id = id;
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

    public void setId(int id) {
        Id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}
