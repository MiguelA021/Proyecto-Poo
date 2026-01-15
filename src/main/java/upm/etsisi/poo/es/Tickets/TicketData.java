package upm.etsisi.poo.es.Tickets;

import org.apache.commons.csv.CSVPrinter;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class TicketData {
    private static final String ID_ERROR = "The id given has been already used";
    HashMap<Integer, Ticket> tickets;
    private static TicketData instance;

    private TicketData() {
        this.tickets = new HashMap<>();
    }

    public static TicketData getInstance() {
        if (instance == null) {
            instance = new TicketData();
        }
        return instance;
    }

    public Ticket getTicket(int ticketId) {
        Ticket resul = tickets.get(ticketId);
        if (resul == null) {
            System.out.println(ID_ERROR);
        }
        return resul;
    }

    public int addTicket(String tycketType) {
        int id;
        do {
            id = (int) (Math.random() * 100000);
        } while (tickets.containsKey(id));
        switch (tycketType) {
            case "products":
                CustomerTicket customerTicket = new CustomerTicket(id);
                tickets.put(customerTicket.getId(), customerTicket);
                System.out.println(customerTicket.toStringNew());
                break;
            case "services":
                EnterpriseServiceTicket enterpriseServiceTicket = new EnterpriseServiceTicket(id);
                tickets.put(enterpriseServiceTicket.getId(), enterpriseServiceTicket);
                System.out.println(enterpriseServiceTicket.toStringId());
                break;
            case "combined":
                EnterpriseMixedTicket enterpriseMixedTicket = new EnterpriseMixedTicket(id);
                tickets.put(enterpriseMixedTicket.getId(), enterpriseMixedTicket);
                System.out.println(enterpriseMixedTicket.toStringId());
                break;
            default:
                System.out.println(ID_ERROR);
                break;
        }

        return id;
    }

    public boolean addTicket(int idTicket, String ticketType) {
        boolean resul = false;
        if (tickets.containsKey(idTicket))
            System.out.println(ID_ERROR);
        else {
            switch (ticketType) {
                case "products":
                    CustomerTicket customerTicket = new CustomerTicket(idTicket);
                    tickets.put(idTicket, customerTicket);
                    resul = true;
                    System.out.println(customerTicket.toStringNew());
                    break;
                case "services":
                    EnterpriseServiceTicket enterpriseServiceTicket = new EnterpriseServiceTicket(idTicket);
                    tickets.put(idTicket, enterpriseServiceTicket);
                    resul = true;
                    System.out.println(enterpriseServiceTicket.toStringNew());
                    break;
                case "combined":
                    EnterpriseMixedTicket enterpriseMixedTicket = new EnterpriseMixedTicket(idTicket);
                    tickets.put(idTicket, enterpriseMixedTicket);
                    resul = true;
                    System.out.println(enterpriseMixedTicket.toStringNew());
                    break;
                default:
                    System.out.println(ID_ERROR);
                    resul = false;
                    break;
            }

        }
        return resul;
    }

    public void saveTickets(CSVPrinter csvPrinter) throws Exception {
        for(Map.Entry<Integer,Ticket> entry: tickets.entrySet()){
            entry.getValue().printCsv(csvPrinter);
        }
    }
}
