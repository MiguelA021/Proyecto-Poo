package upm.etsisi.poo.es.User;

import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.CSVRecord;

import java.io.IOException;
import java.util.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.TreeMap;

public class CustomerController {

  public static final String CASHIER_NOT_FOUND = "The cashier given was not found";
  public static CustomerController instance;
  TreeMap<Integer, Customer> customers;

  private CustomerController() {
    this.customers = new TreeMap<Integer, Customer>();
  }

  /**
   * The method returns the unique instance of the class
   *
   * @return the instance of the class
   */
  public static CustomerController getInstance() {
    if (instance == null) {
      instance = new CustomerController();
    }
    return instance;
  }

  /**
   * The method adds the customer into the treemap, it automatically decides if
   * the customer is regular or enterprise
   *
   * @param name   the name given by parameter
   * @param dni    the DNI/NIE/NIF which will give us the key for the treemap
   * @param email  the email given by parameter
   * @param cashId the cashier that will be linked to our customer
   */
  public Customer addCustomer(String name, String dni, String email, int cashId) {

    String dninew = dni.trim().toUpperCase();
    int id = dniToId(dninew);

    if (customers.containsKey(id)) {
      System.out.println("This Id is already used");
      return null;
    }

    Customer customer;

    IdType type = IdType.detect(dninew);

    switch (type) {
      case DNI:
      case NIE:
        customer = new Customer(email, name, dninew, cashId);
        break;

      case COMPANY:
        customer = new CustomerEnterprise(email, name, dninew, cashId);
        break;

      default:
        System.out.println("Incorrect Format, please try again.");
        return null;
    }

    customers.put(id, customer);
    return customer;
  }


  /**
   * The method gives us the numerical part of the NIE/DNI/NIF
   *
   * @param dni the NIE/DNI/NIF given by parameter
   * @return it returns only the numerical part
   */
  public int dniToId(String dni) {
    int id = 0;
    for (char c : dni.toCharArray()) {
      if (Character.isDigit(c)) {
        id = id * 10;
        id += c-'0';
      }
    }
    return id;
  }

  public Customer getCustomer(int userId) {
    return customers.get(userId);
  }

  /**
   * The method removes the customer from the treemap
   *
   * @param dni the NIE/DNI/NIF given by parameter
   * @return the method returns true if the customer has been removed
   *         successfully, else returns false
   */
  public boolean removeCustomer(String dni) {
    int id = dniToId(dni);
    Customer customer = customers.remove(id);
    return customer != null;
  }

  /**
   * The method list the customers ordered by their name
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

  /**
   * The method turns the tree into a List ordered by the name of the customer
   *
   * @return the list ordered by the name of it's customers
   */
  private ArrayList<Customer> customersToList() {
    ArrayList<Customer> resul = new ArrayList<>(customers.values());
    resul.sort(Comparator.comparing(Customer::getName));
    return resul;
  }

  /**
   * The method adds a ticket to their customer
   *
   * @param ticketId the id of the ticket we are going to add
   * @param userId   the id of the user that we are adding their ticket
   */
  public void addTicket(Integer ticketId, int userId) {
    try {
      Customer customer = customers.get(userId);
      customer.addTicket(ticketId);
    } catch (NullPointerException e) {
      System.out.println("no");
    }
  }

  public void saveCustomers(CSVPrinter csvPrinter) throws IOException {
    for (Map.Entry<Integer, Customer> entry : customers.entrySet()) {
      entry.getValue().printCsv(csvPrinter);
    }
  }

  public void csvCustomers(CSVRecord csvRecord, boolean[] where) {
    if (csvRecord.get(0).equals("Tickets")) {
      where[2] = false;
    }
    if (where[2]) {
      if (!csvRecord.get(0).equals("EnterpriseCustomer") && !csvRecord.get(0).equals("Customer")) {
        this.addTicket(Integer.parseInt(csvRecord.get(1)), dniToId(csvRecord.get(0)));
      } else {
        if (csvRecord.get(0).equals("EnterpriseCustomer")) {
          this.addCustomer(csvRecord.get(2), csvRecord.get(4), csvRecord.get(1), Integer.parseInt(csvRecord.get(3)));
        } else if (csvRecord.get(0).equals("Customer")) {
          this.addCustomer(csvRecord.get(2), csvRecord.get(4), csvRecord.get(1), Integer.parseInt(csvRecord.get(3)));
        }
      }
    }
  }
}
