package upm.etsisi.poo.es.Tickets;

import java.util.HashMap;

public class TicketData {
    private static final String ID_ERROR = "The id given has been already used";
    HashMap<Integer, Ticket> tickets;
    private static TicketData instance;
    TicketDAO ticketDAO;

    private TicketData() {

        this.ticketDAO = TicketDAO.getInstance();
        this.tickets = ticketDAO.loadTickets();

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

    public int addTicket(String tycketType, int cashId, int userId) {
        int id;
        do {
            id = (int) (Math.random() * 100000);
        } while (tickets.containsKey(id));
        switch (tycketType) {
            case "products":
                CustomerTicket customerTicket = new CustomerTicket(id);
                tickets.put(id, customerTicket);
                ticketDAO.addTicket(customerTicket,tycketType,cashId,userId);
                System.out.println(customerTicket.toStringNew());
                break;
            case "services":
                EnterpriseServiceTicket enterpriseServiceTicket = new EnterpriseServiceTicket(id);
                tickets.put(id,enterpriseServiceTicket);
                ticketDAO.addTicket(enterpriseServiceTicket,tycketType,cashId,userId);
                System.out.println(enterpriseServiceTicket.toStringId());
                break;
            case "combined":
                EnterpriseMixedTicket enterpriseMixedTicket = new EnterpriseMixedTicket(id);
                tickets.put(id,enterpriseMixedTicket);
                ticketDAO.addTicket(enterpriseMixedTicket,tycketType,cashId,userId);
                System.out.println(enterpriseMixedTicket.toStringId());
                break;
            default:
                System.out.println(ID_ERROR);
                break;
        }

        return id;
    }

    public boolean addTicket(int idTicket, String ticketType, int cashId, int userId) {
        boolean resul = false;
        if (tickets.containsKey(idTicket))
            System.out.println(ID_ERROR);
        else {
            switch (ticketType) {
                case "products":
                    CustomerTicket customerTicket = new CustomerTicket(idTicket);
                    tickets.put(idTicket, customerTicket);
                    ticketDAO.addTicket(customerTicket,ticketType,cashId,userId);
                    resul = true;
                    System.out.println(customerTicket.toStringNew());
                    break;
                case "services":
                    EnterpriseServiceTicket enterpriseServiceTicket = new EnterpriseServiceTicket(idTicket);
                    tickets.put(idTicket,enterpriseServiceTicket);
                    ticketDAO.addTicket(enterpriseServiceTicket,ticketType,cashId,userId);
                    resul = true;
                    System.out.println(enterpriseServiceTicket.toStringNew());
                    break;
                case "combined":
                    EnterpriseMixedTicket enterpriseMixedTicket = new EnterpriseMixedTicket(idTicket);
                    tickets.put(idTicket,enterpriseMixedTicket);
                    ticketDAO.addTicket(enterpriseMixedTicket,ticketType,cashId,userId);
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

    public void setTickets(HashMap<Integer, Ticket> tickets) {
        this.tickets = tickets;
    }
}
