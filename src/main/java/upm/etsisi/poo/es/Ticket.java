package upm.etsisi.poo.es;

import java.util.*;

public class Ticket {
  int amount;
  ArrayList<Product> productList;
  private final int MAX_PRODUCT = 100;

  public Ticket() {
    productList = new ArrayList<>();
    amount = 0;
  }

  /**
   * @param product
   *                Method that add the product to the list just when the amount
   *                is below 100.
   * @return boolean
   */
  public boolean prodAdd(int id, String name, type category, int price) { // Metodo
    boolean done = true;
    if (amount < MAX_PRODUCT) {
      Product product = new Product(name, price, id, 1, category);
      if (this.productList.contains(product)) {
        int productPosition = productList.indexOf(product);
        int amount = productList.get(productPosition).getCuantity();
        if (amount < 200) {
          productList.get(productPosition).setCuantity(amount + 1);
        } else {
          done = false;
        }

      } else {
        productList.add(product);
        amount++;
      }
      System.out.println(product.toString());
    }
    return done;
  }

  public String prodList() {
    String list = "";
    Iterator<Product> it = productList.iterator();
    while (it.hasNext()) {
      Product product = it.next();
      for (int i = 0; i < product.getCuantity(); i++) {
        list += product.toString() + "\n";
      }
    }
    return list;
  }

  public boolean prodRemove(int id) {
    Iterator<Product> it = productList.iterator();
    boolean removed = false;
    boolean found = false;
    while (it.hasNext() && !found) {
      Product product = it.next();
      if (product.getId() == id) {
        found = true;
      }
      int amount = product.getCuantity();
      product.setCuantity(amount - 1);
      if (amount - 1 == 0) {
        productList.remove(id);
      } else
        productList.set(id, product);
      removed = true;
    }
    return removed;
  }

  public boolean updateCategoria(int id, type category) {
    boolean done = false;
    Iterator<Product> it = productList.iterator();
    while (it.hasNext() && !done) {
      Product product = it.next();
      if (product.getId() == id) {
        product.SetCategory(category);
        productList.set(id, product);
        done = true;
      }
    }
    return done;
  }

  public boolean updateName(int id, String name) {
    boolean done = false;
    Iterator<Product> it = productList.iterator();
    while (it.hasNext() && !done) {
      Product product = it.next();
      if (product.getId() == id) {
        product.setName(name);
        productList.set(id, product);
        done = true;
      }
    }
    return done;
  }

  public boolean updateAmount(int id, int amount) {
    boolean done = false;
    Iterator<Product> it = productList.iterator();
    while (it.hasNext() && !done) {
      Product product = it.next();
      if (product.getId() == id) {
        product.setCuantity(amount);
        productList.set(id, product);
        done = true;
      }
    }
    return done;
  }
}
