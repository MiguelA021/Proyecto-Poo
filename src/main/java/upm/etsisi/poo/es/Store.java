package upm.etsisi.poo.es;

import upm.etsisi.poo.es.Product.*;
import upm.etsisi.poo.es.User.Cashier;
import upm.etsisi.poo.es.User.CashierController;


import java.util.*;

public class Store {
  int MAX_PRODUCT = 200;
  Product[] productList;

  public static final String CASHIER_NOT_FOUND = "The cashier given was not found";
  public static final String CUSTOMER_NOT_FOUND = "The customer given was not found";
  public static final String ID_ERROR = "The id given has already been  used";
  private int prodAmount;
  private static Store instance;

  private Store() {
    this.productList = new Product[MAX_PRODUCT];
    this.prodAmount = 0;
  }
    public static Store getInstance(){
        if(instance == null){
            instance = new Store();
        }
        return instance;
    }

  /**
   * The method returns the product if it has been found
   * 
   * @param prodId the id of the product given
   * @return it returns the product if it was found. If not, it returns null
   */
  public Product getProduct(int prodId) {
    Product result = null;
    boolean found = false;
    int i = 0;
    while (i < MAX_PRODUCT && !found) {
      Product p = productList[i];
      if (p != null && p.getId() == prodId) {
        result = p;
        found = true;
      } else {
        i++;
      }
    }
    return result;
  }

  // We're going to do ckecking of maxPeople using the right now date and the
  // expiry date for knowing
  // if we can create the Food or Meeting object
  public boolean addFood(int id, String name, int price, String expiryDate, int assistants) {
    boolean done = false;
    boolean found = false;
    Event food = new Food(id, name, price, expiryDate);
    for (int i = 0; i < MAX_PRODUCT && !done && !found; i++) {
      if (productList[i] != null && productList[i].getId() == food.getId()) {
        found = true;
      } else {
        if (productList[i] == null) {
          productList[i] = food;
          done = true;
        }
      }
    }
    return done;
  }

  public boolean addMeeting(int id, String name, double price, String expiryDate, int assistants) {
    boolean done = false;
    boolean found = false;
    Product meeting = new Meeting(id, name, price, expiryDate);
    for (int i = 0; i < MAX_PRODUCT && !done && !found; i++) {
      if (productList[i] != null && productList[i].getId() == meeting.getId()) {
        found = true;
      } else {
        if (productList[i] == null) {
          productList[i] = meeting;
          done = true;
        }
      }
    }
    return done;
  }

  /**
   * The method adds the client if the casher given was found
   * 
   * @param name   the name of the new client
   * @param dni    the ID number of the new client
   * @param email  the eamail of the new client
   * @param cashId the id of the casher given
   */
  /*public void addCustomer(String name, String dni, String email, int cashId) {
    Customer customer;
    if (cashers.containsKey(cashId)) {
      int id = dniToId(dni);
      if (!isEnterprise(dni)) {
        customer = new Customer(email, name, dni, cashId);
      }else {
        customer = new CustomerEnterprise(email, name, dni, cashId);
      }
      customers.put(id, customer);
      System.out.println(customer.toString());
      System.out.println("client add: ok");
    } else {
      System.out.println(CASHIER_NOT_FOUND);
    }
  }

   */

  /**
   * The method checks if the id given is from a DNI/NIE or a NIF
   * @param id the id given by parameter
   * @return it returns true if the id is a NIF, else returns false
   */
  private boolean isEnterprise(String id){
    boolean resul=true;
    if (Character.isDigit(id.charAt(id.length()-1))){
      resul=false;
    }
    return resul;
  }

  /*
  public void addTicketOnCashier(Integer idTicket, int idCashier, int idCustomer) {
    if (cashers.containsKey(idCashier) && customers.containsKey(idCustomer)) {
      Cashier c = cashers.get(idCashier);
      idTicket = c.addTicket(idTicket);
      Ticket resul = cashers.get(idCashier).getTicketById(idTicket);
      System.out.println(resul.toStringNew());
      customers.get(idCustomer).addTicket(idTicket, resul);
    } else {
      if (cashers.containsKey(idCashier)) {
        System.out.println(CASHIER_NOT_FOUND);
      }
      if (customers.containsKey(idCustomer)) {
        System.out.println(CUSTOMER_NOT_FOUND);
      }
    }
  }
*/

  /**
   * The method adds a product tho the productList if there is below 100 products
   * 
   * @param product the product which we want to add it
   * @return returns true if the method added the product if not, it returns false
   */
  public boolean prodAdd(Product product) {
    boolean done = false;
    boolean found = false;
    for (int i = 0; i < MAX_PRODUCT && !done && !found; i++) {
      if (productList[i] != null && productList[i].getId() == product.getId()) {
        found = true;
      } else {
        if (productList[i] == null) {
          productList[i] = product;
          done = true;
          this.prodAmount++;
        }
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
    for (int i = 0; i < MAX_PRODUCT && !found; i++) {
      if (productList[i].getId() == id) {
        found = true;
        product = productList[i];
        productList[i] = null;
      }
    }
    if (found) {
      this.prodAmount--;
      System.out.println(product.toString());
      System.out.println("prod remove: ok");

    }
    return found;
  }

  /**
   * The method lists the products in the prodList
   */
  public void prodList() {
    Arrays.sort(productList, 0, this.prodAmount, Comparator.comparing(Product::getName));
    System.out.println("Catalog:");
    for (Product p : productList) {
      if (p != null) {
        System.out.println("  " + p.toString());
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
  public Product updateType(int id, type category) {
    boolean done = false;
    Product resul = null;
    for (int i = 0; i < MAX_PRODUCT && !done; i++) {
      if (productList[i].getId() == id) {
        if (productList[i] instanceof BasicProduct) {
          BasicProduct basic = (BasicProduct) productList[i];
          basic.SetCategory(category);
          resul = basic;
          done = true;
        }
      }
    }
    return resul;
  }

  /**
   * The method changes the name of the product with the id given
   * 
   * @param id   the id of the product
   * @param name the new name we want to save
   * @return if the product has been updated the method returns true, if not it
   *         returns false
   */
  public Product updateName(int id, String name) {
    boolean done = false;
    Product resul = null;
    for (int i = 0; i < MAX_PRODUCT && !done; i++) {
      if (this.productList[i].getId() == id) {
        this.productList[i].setName(name.replaceAll("\"", ""));
        resul = productList[i];
        done = true;
      }
    }
    return resul;
  }

  /**
   * The method changes the price of the product with the id given
   * 
   * @param id    the id of the product
   * @param price the new price we want to save
   * @return if the price has been updated the method returns true, if not it
   *         returns false
   */
  public Product updatePrice(int id, double price) {
    boolean done = false;
    Product resul = null;
    for (int i = 0; i < MAX_PRODUCT && !done; i++) {
      if (productList[i].getId() == id) {
        productList[i].setPrice(price);
        resul = productList[i];
        done = true;
      }
    }
    return resul;
  }

  /**
   * The method runs through the Map, getting all cashiers and showing their
   * tickets
   */
  /*
  public void ticketList() {
    System.out.println("Ticket list: ");
    for (Map.Entry<Integer, Cashier> entry : cashers.entrySet()) {
      Cashier casher = entry.getValue();
      System.out.print(casher.listTickets());
    }
    System.out.println("ticket list: ok");
  }
*/
}

// ticketList??
