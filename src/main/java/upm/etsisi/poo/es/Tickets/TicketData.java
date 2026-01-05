package upm.etsisi.poo.es.Tickets;

import java.util.HashMap;

import static upm.etsisi.poo.es.App.session;

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
                CustomerTicket customerTicketNoId = new CustomerTicket(id);
                tickets.put(customerTicketNoId.getId(), customerTicketNoId);
                session.beginTransaction();
                session.saveOrUpdate(customerTicketNoId);
                session.getTransaction().commit();
                System.out.println(customerTicketNoId.toStringNew());
                break;
            case "services":
                EnterpriseServiceTicket enterpriseServiceTicketNoId = new EnterpriseServiceTicket(id);
                tickets.put(enterpriseServiceTicketNoId.getId(), enterpriseServiceTicketNoId);
                session.beginTransaction();
                session.saveOrUpdate(enterpriseServiceTicketNoId);
                session.getTransaction().commit();
                System.out.println(enterpriseServiceTicketNoId.toStringId());
                break;
            case "combined":
                EnterpriseMixedTicket enterpriseMixedTicketNoId = new EnterpriseMixedTicket(id);
                tickets.put(enterpriseMixedTicketNoId.getId(), enterpriseMixedTicketNoId);
                session.beginTransaction();
                session.saveOrUpdate(enterpriseMixedTicketNoId);
                session.getTransaction().commit();
                System.out.println(enterpriseMixedTicketNoId.toStringId());
                break;
            default:
                System.out.println(ID_ERROR);
                break;
        }

        return id;
    }

    public boolean addTicketId(int idTicket, String ticketType) {
        boolean resul = false;
        if (tickets.containsKey(idTicket))
            System.out.println(ID_ERROR);
        else {
            switch (ticketType) {
                case "products":
                    CustomerTicket customerTicket = new CustomerTicket(idTicket);
                    tickets.put(idTicket, customerTicket);
                    resul = true;
                    session.beginTransaction();
                    session.saveOrUpdate(customerTicket);
                    session.getTransaction().commit();
                    System.out.println(customerTicket.toStringNew());
                    break;
                case "services":
                    EnterpriseServiceTicket enterpriseServiceTicket = new EnterpriseServiceTicket(idTicket);
                    tickets.put(idTicket, enterpriseServiceTicket);
                    session.beginTransaction();
                    session.saveOrUpdate(enterpriseServiceTicket);
                    session.getTransaction().commit();
                    resul = true;
                    System.out.println(enterpriseServiceTicket.toStringNew());
                    break;
                case "combined":
                    EnterpriseMixedTicket enterpriseMixedTicket = new EnterpriseMixedTicket(idTicket);
                    tickets.put(idTicket, enterpriseMixedTicket);
                    session.beginTransaction();
                    session.saveOrUpdate(enterpriseMixedTicket);
                    session.getTransaction().commit();
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
}
