package upm.etsisi.poo.es;

public class Store {
  int MAX_PRODUCT = 100;
  Product[] productList;

  public Store() {
    this.productList = new Product[MAX_PRODUCT];
  }

  public Product[] getProducts() {
    return productList;
  }

  /**
   * The method adds a product tho the productList if there is below 100 products
   * 
   * @param product the product which we want to add it
   * @return returns true if the method added the product if not, it returns false
   */
  public boolean prodAdd(Product product) {
    boolean done = true;
    for (int i = 0; i < MAX_PRODUCT && !done; i++) {
      if (productList[i] == null) {
        productList[i] = product;
        done = true;
      }
    }
    return done;
  }

  /**
   * The method removes the product with the id given
   * 
   * @param id the id of the product we want to remove
   * @return it returns true if the product with the id given was removed, if not
   *         it returns false
   */
  public boolean prodRemove(int id) {// Se puede mejorar la eficiencia con un while
    boolean found = false;
    Product product = null;
    for (int i = 0; i < MAX_PRODUCT; i++) {
      if (productList[i].getId() == id) {
        found = true;
        product = productList[1];
        productList[i] = null;
      }
    }
    if (found) {
      System.out.println(product.toString());
    }
    return found;
  }

  /**
   * The method lists the products in the prodList
   */
  public void prodList() {
    System.out.println("Catalog:");
    for (Product p : productList) {
      if (p != null) {
        System.out.println("{class:Product, id:" + p.getId() + ", name:'" + p.getName() + "', category:"
            + p.getCategory() + ", price:" + p.getPrice() + "}");
      }
    }
    System.out.println("prod list: ok");
  }

  /**
   * The method changes the category of the product with the id given
   * 
   * @param id       the id of the product
   * @param category the new category we want to save
   * @return if the product has been updated the method returns true, if not it
   *         returns false
   */
  public boolean updateType(int id, type category) {
    boolean done = false;
    for (int i = 0; i < MAX_PRODUCT; i++) {
      if (productList[i].getId() == id) {
        productList[i].SetCategory(category);
        done = true;
      }
    }
    return done;
  }

  /**
   * The method changes the name of the product with the id given
   * 
   * @param id   the id of the product
   * @param name the new name we want to save
   * @return if the product has been updated the method returns true, if not it
   *         returns false
   */
  public boolean updateName(int id, String name) {
    boolean done = false;
    for (int i = 0; i < MAX_PRODUCT; i++) {
      if (productList[i].getId() == id) {
        productList[i].setName(name);
        done = true;
      }
    }
    return done;
  }

  /**
   * The method changes the price of the product with the id given
   * 
   * @param id    the id of the product
   * @param price the new price we want to save
   * @return if the price has been updated the method returns true, if not it
   *         returns false
   */
  public boolean updatePrice(int id, double price) {
    boolean done = false;
    for (int i = 0; i < MAX_PRODUCT; i++) {
      if (productList[i].getId() == id) {
        productList[i].setPrice(price);
        done = true;
      }
    }
    return done;
  }
}
