package upm.etsisi.poo.es;

import upm.etsisi.poo.es.User.Casher;
import upm.etsisi.poo.es.User.Customer;
import java.util.ArrayList;
import java.util.TreeMap;
import java.util.Comparator;


public class Store {
  int MAX_PRODUCT = 200;
  Product[] productList;
  TreeMap<Integer, Casher> cashers;
  TreeMap<Integer, Customer> customers;
  public static final String CASHER_NOT_FOUND = "The casher given was not found";
  public static final String CUSTOMER_NOT_FOUND = "The customer given was not found";
  public static final String ID_ERROR = "The id given has already been  used";

  public Store() {
    this.productList = new Product[MAX_PRODUCT];
  }

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
  public boolean addFood(int id, String name, int price, String expiryDate, int maxPeople) {
    boolean done = false;
    boolean found = false;
    Product food = new Product.Food(id, name, price, expiryDate);
    if (food.getStateFood(maxPeople)) {
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
    }
    return done;
  }

  public boolean addMeeting(int id, String name, int price, String expiryDate, int maxPeople) {
    boolean done = false;
    boolean found = false;
    Product meeting = new Product.Meeting(id, name, price, expiryDate);
    if (meeting.getStateMeeting(maxPeople)) {
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
    Casher casher = searchCasherById(cashId);
    if (casher != null) {
      int id = dniToId(dni);
      customers.put(id, new Customer(email, name, id, casher));
    } else {
      System.out.println(CASHER_NOT_FOUND);
    }
  }

  /**
   * The method searches by id the casher
   * 
   * @param id the ID of the casher
   * @return the method returns the casher if it has been found
   */
  public Casher searchCasherById(int id) {
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
    String[] dniToArray = dni.split("");
    for (int i = 0; i < dniToArray.length - 1; i++) {
      id = id * 10;
      id += Integer.parseInt(dniToArray[i]);
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
    if (listSort != null) {
      for (Customer customer : listSort) {
        System.out.println(customer.toString());
      }
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
   * The method adds the ticket on the casher
   * 
   * @param idTicket the id of the ticket (if it's null, it generates it
   *                 automatically)
   * @param idCasher the id of the casher
   */
  public void addTicketOnCasher(Integer idTicket, int idCasher, int idCustomer) {
    if (cashers.containsKey(idCasher) && customers.containsKey(idCustomer)) {
      cashers.get(idCasher).addTicket(idTicket);
      customers.get(idCustomer).addTicket(idTicket, cashers.get(idCasher).getTicketById(idTicket));
    } else {
      if (cashers.containsKey(idCasher)) {
        System.out.println(CASHER_NOT_FOUND);
      }
      if (customers.containsKey(idCustomer)) {
        System.out.println(CUSTOMER_NOT_FOUND);
      }
    }
  }

  /**
   * The method adds the casher if the id (if not given, the method itself
   * generates a random id) given hasn't been already used
   * 
   * @param id    the id (if not given it generates automatically)
   * @param name  the name of the casher
   * @param email the email of the casher
   * @return it returns true if the casher has been successfully added
   */
  public boolean addCasher(Integer id, String name, String email) {
    boolean resul = true;
    if (id == null) {
      do {
        id = (int) (Math.random() * 10000000);
      } while (cashers.containsKey(id));
    }
    if (!cashers.containsKey(id)) {
      cashers.put(id, new Casher(email, name, id));
    } else {
      System.out.println(ID_ERROR);
      resul = false;
    }
    return resul;
  }

  /**
   * The method removes the casher given by its id
   * 
   * @param id the id given
   * @return it returns true if the casher has been removed successfully
   */
  public boolean removeCasher(int id) {
    return (cashers.remove(id) != null);
  }

  /**
   * The method list the cashers alphabetically, those who had been added on the
   * Store
   */
  public void listCashers() {
    ArrayList<Casher> listSort = cashersToList();
    for (Casher casher : listSort) {
      System.out.println(casher.toString());
    }
  }

  private ArrayList<Casher> cashersToList() {
    ArrayList<Casher> resul = new ArrayList<>(cashers.values());
    resul.sort(Comparator.comparing(Casher::getName));
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
      System.out.println(product.toString());
      System.out.println("prod remove: ok");

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
        System.out.println(p.toString());
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
        productList[i].SetCategory(category);
        resul = productList[i];
        done = true;
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
        this.productList[i].setName(name);
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

}
