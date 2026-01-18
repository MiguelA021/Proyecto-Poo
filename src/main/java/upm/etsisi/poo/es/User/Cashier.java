package upm.etsisi.poo.es.User;

import org.apache.commons.csv.CSVPrinter;
import upm.etsisi.poo.es.Tickets.TicketData;

import java.io.IOException;
import java.util.ArrayList;

public class Cashier extends User {
    private static final String UPM_WORKER = "UW";
    private static final String ID_ERROR = "The id given has been already used";
    private final ArrayList<String> customers;

    public Cashier(String email, String name, String id) {
        this.tickets = new ArrayList<Integer>();
        this.customers = new ArrayList<String>();
        this.email = email;
        this.name = name;
        this.id = id;
    }

    /**
     * The method adds the ticket given by id into the cashers tree
     *
     * @param id the id given by parameter (if it is not given, it generates one
     *           automatically)
     */
    public int addTicket(Integer id) {
        if (tickets.contains(id)) {
            System.out.println(ID_ERROR);
        } else {
            tickets.add(id);
        }
        return id;
    }

    public ArrayList<String> getCustomers() {
        return this.customers;
    }

    public void addCustomer(String id) {
        customers.add(id);
    }

    /**
     * .
     * The method returns the ticket given by id
     *
     * @param id the id of the ticket
     * @return the ticket (if it has been found)
     */
    public boolean getTicketById(int id) {
        return tickets.contains(id);

    }

    public String toString() {
        return "Cash{identifier='" + UPM_WORKER + id + "', name='" + name.replace('"', ' ').trim() + "', email='" + email + "'}";
    }


    public void printCsv(CSVPrinter csvPrinter) throws IOException {
        csvPrinter.printRecord("Cashier", email, name.replace('"', ' ').trim(), id);
        for (Integer tickid : tickets) {
            csvPrinter.printRecord(id, tickid);
        }
    }

    public String listTickets() {
        StringBuilder sb = new StringBuilder();
        if (!tickets.isEmpty()) {
            for (Integer id : tickets) {
                sb.append(TicketData.getInstance().getTicket(id).formatList()).append("\n");
            }
        }
        return sb.toString();
    }

    public String cashTickets() {
        StringBuilder sb = new StringBuilder();
        if (!tickets.isEmpty()) {
            for (Integer id : tickets) {
                sb.append(TicketData.getInstance().getTicket(id).formatListCashList()).append("\n");
            }
        }
        return sb.toString();
    }
}
