package upm.etsisi.poo.es.User;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.TreeMap;

public class CustomerController {

    public static final String CASHIER_NOT_FOUND = "The cashier given was not found";
    public static CustomerController instance;
    TreeMap<Integer, Customer> customers;
    HashMap<Integer, Cashier> tickets;

    private CustomerController() {
        this.customers = new TreeMap<Integer, Customer>();
        this.tickets = new HashMap<Integer, Cashier>();
    }

    public static CustomerController getInstance() {
        if (instance == null) {
            instance = new CustomerController();
        }
        return instance;
    }

    public void addCustomer(String name, String dni, String email, int cashId) {
        Customer customer;
        int id = dniToId(dni);
        customer = new Customer(email, name, dni, cashId);
        customers.put(id, customer);
        System.out.println(customer.toString());
        System.out.println("client add: ok");
    }

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

    public boolean removeCustomer(String dni) {
        int id = dniToId(dni);
        Customer customer = customers.remove(id);
        return customer != null;
    }


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

    public void addTicket(Integer ticketId, int cashId) {
        CashierController cashierController = CashierController.getInstance();
        try{
            Cashier cashier =cashierController.getCasher(cashId);
            tickets.put(ticketId, cashier);
        }catch (NullPointerException e){
            System.out.println("no");
        }
    }


}
