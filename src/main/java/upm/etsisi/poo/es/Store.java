package upm.etsisi.poo.es;

import upm.etsisi.poo.es.Product.*;
import upm.etsisi.poo.es.User.Cashier;
import upm.etsisi.poo.es.User.Customer;

import java.util.*;

public class Store {
  int MAX_PRODUCT = 200;
  Product[] productList;
  TreeMap<Integer, Cashier> cashers;
  TreeMap<Integer, Customer> customers;
  public static final String CASHIER_NOT_FOUND = "The cashier given was not found";
  public static final String CUSTOMER_NOT_FOUND = "The customer given was not found";
  public static final String ID_ERROR = "The id given has already been  used";
  private int prodAmount;

  public Store() {
    this.productList = new Product[MAX_PRODUCT];
    this.cashers = new TreeMap<Integer, Cashier>();
    this.customers = new TreeMap<Integer, Customer>();
    this.prodAmount = 0;
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
  public void addCustomer(String name, String dni, String email, int cashId) {
    if (cashers.containsKey(cashId)) {
      int id = dniToId(dni);
      Customer customer = new Customer(email, name, dni, cashId, !Character.isDigit(dni.toCharArray()[dni.length()-1]));
      customers.put(id, customer);
      System.out.println(customer.toString());
      System.out.println("client add: ok");
    } else {
      System.out.println(CASHIER_NOT_FOUND);
    }
  }

  /**
   * The method searches by id the cashier
   * 
   * @param id the ID of the cashier
   * @return the method returns the cashier if it has been found
   */
  public Cashier searchCasherById(int id) {
    return cashers.get(id);
  }

  /**
   * The method turns the ID card number into the id without letters
   * 
   * @param dni the ID cad number given
   * @return the method returns the id without letters
   */
  public int dniToId(String dni) {
    int id = 0;
    for (char c : dni.toCharArray()) {
      if (Character.isDigit(c)) {
        id = id * 10;
        id += c;
      }
    }
    return id;
  }

  /**
   * The method removes the customer
   * 
   * @param dni the ID of the customer given
   * @return the methods returns true if the object was found and removed
   */
  public boolean removeCustomer(String dni) {
    int id = dniToId(dni);
    Customer customer = customers.remove(id);
    return customer != null;
  }

  /**
   * The method list the clients alphabetically, those who had been added on the
   * Store
   */
  public void listCustomers() {
    ArrayList<Customer> listSort = customersToList();
    if (!listSort.isEmpty()) {
      System.out.println("Client:");
      for (Customer customer : listSort) {
        System.out.println("  " + customer.toString());
      }
      System.out.println("client list: ok");
    } else {
      System.out.println("No customers in the store");
    }
  }

  private ArrayList<Customer> customersToList() {
    ArrayList<Customer> resul = new ArrayList<>(customers.values());
    resul.sort(Comparator.comparing(Customer::getName));
    return resul;
  }

  /**
   * The method adds the ticket on the cashier
   * 
   * @param idTicket  the id of the ticket (if it's null, it generates it
   *                  automatically)
   * @param idCashier the id of the cashier
   */
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

  /**
   * The method adds the cashier if the id (if not given, the method itself
   * generates a random id) given hasn't been already used
   * 
   * @param id    the id (if not given it generates automatically)
   * @param name  the name of the cashier
   * @param email the email of the cashier
   * @return it returns true if the cashier has been successfully added
   */
  public boolean addCasher(Integer id, String name, String email) {
    boolean resul = true;
    if (id == null) {
      do {
        id = (int) (Math.random() * 10000000);
      } while (cashers.containsKey(id));
    }
    if (!cashers.containsKey(id)) {
      Cashier cashier = new Cashier(email, name, id.toString());
      cashers.put(id, cashier);
      System.out.println(cashier.toString());
      System.out.println("cash add: ok");
    } else {
      System.out.println(ID_ERROR);
      resul = false;
    }
    return resul;
  }

  /**
   * The method removes the cashier given by its id
   * 
   * @param id the id given
   * @return it returns true if the cashier has been removed successfully
   */
  public boolean removeCasher(int id) {
    return (cashers.remove(id) != null);
  }

  /**
   * The method list the cashiers alphabetically, those who had been added on the
   * Store
   */
  public void listCashers() {
    ArrayList<Cashier> listSort = cashersToList();
    if (!listSort.isEmpty()) {
      System.out.println("Cash:");
      for (Cashier cashier : listSort) {
        System.out.println("  " + cashier.toString());
      }
      System.out.println("cash list: ok");
    } else {
      System.out.println("no cashiers in store");
    }
  }

  /**
   * The method turns the TreeMap into an ArrayList ordered by the name of the
   * Cashiers
   * 
   * @return It returns the ArrayList already sorted
   */
  private ArrayList<Cashier> cashersToList() {
    ArrayList<Cashier> resul = new ArrayList<>(cashers.values());
    resul.sort(Comparator.comparing(Cashier::getName));
    return resul;
  }

  /**
   * The method list the tickets of the casher given by the id
   *
   * @param id the id of the casher
   */
  public void listTicketsOnCasher(int id) {
    if (cashers.containsKey(id)) {
      System.out.print(cashers.get(id).listTickets());
    } else {
      System.out.println(ID_ERROR);
    }
  }

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
        System.out.print("  " + p.toString());
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
  public void ticketList() {
    System.out.println("Ticket list: ");
    for (Map.Entry<Integer, Cashier> entry : cashers.entrySet()) {
      Cashier casher = entry.getValue();
      System.out.print(casher.listTickets());
    }
    System.out.println("ticket list: ok");
  }

  public Cashier getCasher(int cashId) {
    return cashers.get(cashId);
  }

}

// ticketList??
