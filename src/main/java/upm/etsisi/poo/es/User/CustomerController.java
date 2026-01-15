package upm.etsisi.poo.es.User;

import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.CSVRecord;
import upm.etsisi.poo.es.Tickets.Ticket;

import java.io.IOException;
import java.util.*;

public class CustomerController {

    public static final String CASHIER_NOT_FOUND = "The cashier given was not found";
    public static CustomerController instance;
    TreeMap<Integer, Customer> customers;

    private CustomerController() {
        this.customers = new TreeMap<Integer, Customer>();
    }

    public static CustomerController getInstance() {
        if (instance == null) {
            instance = new CustomerController();
        }
        return instance;
    }

    public Customer addCustomer(String name, String dni, String email, int cashId) {
        Customer customer = null;
        int id = dniToId(dni);
        if(!customers.containsKey(id)) {
            if (dni.charAt(0) > 64 && dni.charAt(0) < 91) {
                customer = new CustomerEnterprise(email, name, dni, cashId);
            } else {
                customer = new Customer(email, name, dni, cashId);
            }
            customers.put(id, customer);

        }
        return  customer;
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

    public Customer getCustomer(int userId) {
        return customers.get(userId);
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
        if(where[2]){
            if(!csvRecord.get(0).equals("EnterpriseCustomer") && !csvRecord.get(0).equals("Customer")){
               this.addTicket(Integer.parseInt(csvRecord.get(1)), dniToId(csvRecord.get(0)));
            }else{
                if(csvRecord.get(0).equals("EnterpriseCustomer")){
                    this.addCustomer(csvRecord.get(2), csvRecord.get(4),csvRecord.get(1),Integer.parseInt(csvRecord.get(3)));
                } else if (csvRecord.get(0).equals("Customer")){
                    this.addCustomer(csvRecord.get(2), csvRecord.get(4),csvRecord.get(1),Integer.parseInt(csvRecord.get(3)));
                }
            }
        }
    }
}
