package upm.etsisi.poo.es.Tickets;

import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.CSVRecord;
import upm.etsisi.poo.es.Product.*;
import upm.etsisi.poo.es.type;

import javax.print.Doc;
import java.io.IOException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

public class TicketData {
    private static final String ID_ERROR = "The id given has been already used";
    HashMap<Integer, Ticket> tickets;
    private static TicketData instance;

    private TicketData() {
        this.tickets = new HashMap<>();
    }

    /**
     * The method returns the unique instance of the class
     * @return the instance
     */
    public static TicketData getInstance() {
        if (instance == null) {
            instance = new TicketData();
        }
        return instance;
    }

    /**
     * The method returns the ticket wit the id given by parameter
     * @param ticketId the id of the ticket
     * @return the ticket if it has been found, if not returns null
     */
    public Ticket getTicket(int ticketId) {
        Ticket resul = tickets.get(ticketId);
        return resul;
    }

    /**
     * The method adds the ticket into the HashMap
     * @param tycketType gives the type of ticket
     * @return it returns the id of the ticket
     */
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

    /**
     * The method adds the ticket into the HashMap
     * @param idTicket the id of the ticket we are going to add
     * @param ticketType gives the type of ticket
     * @return returns true if the ticket has been added successfully, else returns false
     */
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
        for (Map.Entry<Integer, Ticket> entry : tickets.entrySet()) {
            entry.getValue().printCsv(csvPrinter);
        }
    }

    public void csvTickets(CSVRecord record, int[] ticketActual) {
        if (!record.get(0).equals("EnterpriseMixedTicket") && !record.get(0).equals("CustomerTicket") && !record.get(0).equals("EnterpriseServiceTicket")) {
            Ticket ticket = this.getTicket(Integer.parseInt(record.get(0)));

            if (ticket != null) {
                if (ticketActual[1] == 2) {
                    if (ticket instanceof CustomerTicket) {
                        switch (record.get(1)) {
                            case "PersonalizedProduct":
                                PersonalizedProduct p = new PersonalizedProduct(Integer.parseInt(record.get(2)), record.get(3),
                                        upm.etsisi.poo.es.type.valueOf(record.get(4)), Double.parseDouble(record.get(5)), Integer.parseInt(record.get(6)));
                                if (record.getRecordNumber() > 6) {
                                    String[] pers = record.get(7).split(",");
                                    for (int i = 0; i < pers.length; i++) {
                                        p.addPersonalized(pers[i]);
                                    }
                                }
                                ((CustomerTicket) ticket).ticketAddNoString(p, 1);
                                break;
                            case "BasicProduct":
                                BasicProduct b = new BasicProduct(Integer.parseInt(record.get(2)), record.get(3), upm.etsisi.poo.es.type.valueOf(record.get(4)),
                                        Double.parseDouble(record.get(5)));
                                ((CustomerTicket) ticket).ticketAddNoString(b, 1);
                                break;
                            case "Meeting":
                                Meeting m = new Meeting(Integer.parseInt(record.get(2)), record.get(3), Double.parseDouble(record.get(4)), record.get(5));
                                ((CustomerTicket) ticket).ticketAddNoString(m, 1);
                                break;
                            case "Food":
                                Food f = new Food(Integer.parseInt(record.get(2)), record.get(3), Double.parseDouble(record.get(4)), record.get(5));
                                ((CustomerTicket) ticket).ticketAddNoString(f, 1);
                                break;
                            case "Service":
                                System.out.println("Can not add Service to this product");
                                break;
                            default:
                                System.out.println("could not find product type");
                        }
                    } else if (ticket instanceof EnterpriseMixedTicket) {
                        switch (record.get(1)) {
                            case "PersonalizedProduct":
                                PersonalizedProduct p = new PersonalizedProduct(Integer.parseInt(record.get(2)), record.get(3),
                                        upm.etsisi.poo.es.type.valueOf(record.get(4)), Double.parseDouble(record.get(5)), Integer.parseInt(record.get(6)));
                                if (record.getRecordNumber() > 6) {
                                    String[] pers = record.get(7).split(",");
                                    for (int i = 0; i < pers.length; i++) {
                                        p.addPersonalized(pers[i]);
                                    }
                                }
                                ((EnterpriseMixedTicket) ticket).addProductNoString(p, 1);
                                break;
                            case "BasicProduct":
                                BasicProduct b = new BasicProduct(Integer.parseInt(record.get(2)), record.get(3), upm.etsisi.poo.es.type.valueOf(record.get(4)),
                                        Double.parseDouble(record.get(5)));
                                ((EnterpriseMixedTicket) ticket).addProductNoString(b, 1);
                                break;
                            case "Meeting":
                                Meeting m = new Meeting(Integer.parseInt(record.get(2)), record.get(3), Double.parseDouble(record.get(4)), record.get(5));
                                ((EnterpriseMixedTicket) ticket).addProductNoString(m, 1);
                                break;
                            case "Food":
                                Food f = new Food(Integer.parseInt(record.get(2)), record.get(3), Double.parseDouble(record.get(4)), record.get(5));
                                ((EnterpriseMixedTicket) ticket).addProductNoString(f, 1);
                                break;
                            case "Service":
                                Service s = new Service(LocalDate.parse(record.get(2)), record.get(3), Integer.parseInt(record.get(4)));
                                ((EnterpriseMixedTicket) ticket).addService(s);
                                break;
                            default:
                                System.out.println("could not find product type");
                        }
                    } else {
                        switch (record.get(1)) {
                            case "PersonalizedProduct":
                                System.out.println("can not add product to ticket");
                                break;
                            case "BasicProduct":
                                System.out.println("can not add product to ticket");
                                break;
                            case "Meeting":
                                System.out.println("can not add product to ticket");
                                break;
                            case "Food":
                                System.out.println("can not add product to ticket");
                                break;
                            case "Service":
                                Service s = new Service(LocalDate.parse(record.get(2)), record.get(3), Integer.parseInt(record.get(4)));
                                ((EnterpriseServiceTicket) ticket).addService(s);
                                break;
                            default:
                                System.out.println("could not find product type");
                        }
                    }
                } else {
                    if (ticket instanceof CustomerTicket) {
                        switch (record.get(1)) {
                            case "PersonalizedProduct":
                                PersonalizedProduct p = new PersonalizedProduct(Integer.parseInt(record.get(2)), record.get(3),
                                        upm.etsisi.poo.es.type.valueOf(record.get(4)), Double.parseDouble(record.get(5)), Integer.parseInt(record.get(6)));
                                if (record.getRecordNumber() > 6) {
                                    String[] pers = record.get(7).split(",");
                                    for (int i = 0; i < pers.length; i++) {
                                        p.addPersonalized(pers[i]);
                                    }
                                }
                                ((CustomerTicket) ticket).ticketAddNoString(p, 1);
                                break;
                            case "BasicProduct":
                                Product b = ProductController.getInstance().getProduct(Integer.parseInt(record.get(2)));
                                if (b != null) {
                                    if (b instanceof BasicProduct) {
                                        ((CustomerTicket) ticket).ticketAddNoString(b, 1);
                                    } else {
                                        System.out.println("wrong type of product");
                                    }
                                } else {
                                    System.out.println("Product not found");
                                }
                                break;
                            case "Meeting":
                                Product m = ProductController.getInstance().getProduct(Integer.parseInt(record.get(2)));
                                if (m != null) {
                                    if (m instanceof Meeting) {
                                        ((CustomerTicket) ticket).ticketAddNoString(m, 1);
                                    } else {
                                        System.out.println("wrong type of product");
                                    }
                                } else {
                                    System.out.println("Product not found");
                                }

                                break;
                            case "Food":
                                Product f = ProductController.getInstance().getProduct(Integer.parseInt(record.get(2)));
                                if (f != null) {
                                    if (f instanceof Food) {
                                        ((CustomerTicket) ticket).ticketAddNoString(f, 1);
                                    } else {
                                        System.out.println("wrong type of product");
                                    }
                                } else {
                                    System.out.println("Product not found");
                                }
                                break;
                            case "Service":
                                System.out.println("Can not add Service to this ticket");
                            default:
                                System.out.println("could not find product type");
                        }

                    } else if (ticket instanceof EnterpriseMixedTicket) {
                        switch (record.get(1)) {
                            case "PersonalizedProduct":
                                PersonalizedProduct p = new PersonalizedProduct(Integer.parseInt(record.get(2)), record.get(3),
                                        upm.etsisi.poo.es.type.valueOf(record.get(4)), Double.parseDouble(record.get(5)), Integer.parseInt(record.get(6)));
                                if (record.getRecordNumber() > 6) {
                                    String[] pers = record.get(7).split(",");
                                    for (int i = 0; i < pers.length; i++) {
                                        p.addPersonalized(pers[i]);
                                    }
                                }
                                ((EnterpriseMixedTicket) ticket).addProductNoString(p, 1);
                                break;
                            case "BasicProduct":
                                Product b = ProductController.getInstance().getProduct(Integer.parseInt(record.get(2)));
                                if (b != null) {
                                    if (b instanceof BasicProduct) {
                                        ((EnterpriseMixedTicket) ticket).addProductNoString(b, 1);
                                    } else {
                                        System.out.println("wrong type of product");
                                    }
                                } else {
                                    System.out.println("Product not found");
                                }
                                break;
                            case "Meeting":
                                Product m = ProductController.getInstance().getProduct(Integer.parseInt(record.get(2)));
                                if (m != null) {
                                    if (m instanceof Meeting) {
                                        ((EnterpriseMixedTicket) ticket).addProductNoString(m, 1);
                                    } else {
                                        System.out.println("wrong type of product");
                                    }
                                } else {
                                    System.out.println("Product not found");
                                }

                                break;
                            case "Food":
                                Product f = ProductController.getInstance().getProduct(Integer.parseInt(record.get(2)));
                                if (f != null) {
                                    if (f instanceof Food) {
                                        ((EnterpriseMixedTicket) ticket).addProductNoString(f, 1);
                                    } else {
                                        System.out.println("wrong type of product");
                                    }
                                } else {
                                    System.out.println("Product not found");
                                }
                                break;
                            case "Service":
                                Product s = ProductController.getInstance().getProduct(Integer.parseInt(record.get(2)));
                                if (s != null) {
                                    if (s instanceof Service) {
                                        ((EnterpriseMixedTicket) ticket).addService((Service) s);
                                    } else {
                                        System.out.println("wrong type of product");
                                    }
                                } else {
                                    System.out.println("Product not found");
                                }
                                break;
                            default:
                                System.out.println("could not find product type");
                        }
                    } else {
                        switch (record.get(1)) {
                            case "PersonalizedProduct":
                                System.out.println("can not add product to ticket");
                                break;
                            case "BasicProduct":
                                System.out.println("can not add product to ticket");
                                break;
                            case "Meeting":
                                System.out.println("can not add product to ticket");
                                break;
                            case "Food":
                                System.out.println("can not add product to ticket");
                                break;
                            case "Service":
                                Product s = ProductController.getInstance().getProduct(Integer.parseInt(record.get(2)));
                                if (s != null) {
                                    if (s instanceof Service) {
                                        ((EnterpriseServiceTicket) ticket).addService((Service) s);
                                    } else {
                                        System.out.println("wrong type of product");
                                    }
                                } else {
                                    System.out.println("Product not found");
                                }
                                break;
                            default:
                                System.out.println("could not find product type");
                        }
                    }
                }
            } else {
                System.out.println("could not find ticket");
                return;
            }
        } else {
            Ticket ti = TicketData.getInstance().getTicket(ticketActual[0]);
            if( ti!= null){
                if(ticketActual[1] == 2){
                    ti.close();
                }
            }

            switch (record.get(0)) {
                case "CustomerTicket":
                    CustomerTicket c = new CustomerTicket(Integer.parseInt(record.get(1)));
                    this.tickets.put(c.getId(), c);
                    ticketActual[0] = c.getId();
                    ticketActual[1]= Status.valueOf(record.get(2)).ordinal();
                    break;
                case "EnterpriseMixedTicket":
                    EnterpriseMixedTicket m = new EnterpriseMixedTicket(Integer.parseInt(record.get(1)));
                    this.tickets.put(m.getId(), m);
                    ticketActual[0] = m.getId();
                    ticketActual[1]= Status.valueOf(record.get(2)).ordinal();
                    break;
                case "EnterpriseServiceTicket":
                    EnterpriseServiceTicket s = new EnterpriseServiceTicket(Integer.parseInt(record.get(1)));
                    this.tickets.put(s.getId(), s);
                    ticketActual[0] = s.getId();
                    ticketActual[1]= Status.valueOf(record.get(2)).ordinal();
                    break;
                default:
                    System.out.println("Not a type of ticket");
                    break;
            }
        }

    }

    public String listTickets() {
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<Integer, Ticket> entry : tickets.entrySet()) {
            sb.append(entry.getValue().formatList() + "\n");
        }
        return sb.toString();
    }
}

