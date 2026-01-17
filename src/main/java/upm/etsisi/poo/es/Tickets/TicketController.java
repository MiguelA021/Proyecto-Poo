package upm.etsisi.poo.es.Tickets;

import upm.etsisi.poo.es.Product.PersonalizedProduct;
import upm.etsisi.poo.es.Product.Product;
import upm.etsisi.poo.es.Product.ProductController;
import upm.etsisi.poo.es.Product.Service;
import upm.etsisi.poo.es.User.CashierController;
import upm.etsisi.poo.es.User.Customer;
import upm.etsisi.poo.es.User.CustomerController;
import upm.etsisi.poo.es.User.CustomerEnterprise;

import javax.swing.*;
import java.time.LocalDate;

import static upm.etsisi.poo.es.Commands.Command.*;

public class TicketController {
    public static final String COMBINED = "combined";
    public static final String SERVICE = "services";
    public static final String PRODUCTS1 = "products";

    private TicketData ticketData;
    private TicketDAO ticketDAO;
    public static TicketController instance;

    private TicketController() {
        this.ticketData = TicketData.getInstance();
        this.ticketDAO = TicketDAO.getInstance();
        ticketData.setTickets(ticketDAO.loadTickets());
    }

    public static TicketController getInstance() {
        if (instance == null) {
            instance = new TicketController();
        }
        return instance;
    }


    public void prodAdd(String[] args) {
        try {
            int ticketId = Integer.parseInt(args[2]);

            Ticket ticket = TicketData.getInstance().getTicket(ticketId);


            // -------------------------
            // CASE 1: ADD SERVICE
            // ticket add <ticketId> <cashId> <serviceId>
            // -------------------------

            if (args.length == 5) {
                int idProducto = Integer.parseInt(args[4].replace("S", "")) * -1;
                Service s = (Service) ProductController.getInstance().getProduct(idProducto);

                boolean ok = false;
                if (ticket instanceof EnterpriseServiceTicket) {
                    ok = ((EnterpriseServiceTicket) ticket).addService(s);
                    if (ok) {
                        ticketDAO.addProduct(ticket.getId(), idProducto, 1, null);
                    }
                    EnterpriseServiceTicket enterpriseServiceTicket = (EnterpriseServiceTicket) ticket;
                    System.out.println(enterpriseServiceTicket.print(false));

                } else if (ticket instanceof EnterpriseMixedTicket) {
                    ok = ((EnterpriseMixedTicket) ticket).addService(s);
                    if (ok) {
                        ticketDAO.addProduct(ticket.getId(), idProducto, 1, null);
                    }
                    EnterpriseMixedTicket enterpriseMixedTicket = (EnterpriseMixedTicket) ticket;
                    System.out.println(enterpriseMixedTicket.print(false));
                } else {
                    System.out.println(INCORRECT);
                    return;
                }

                if (!ok) {
                    System.out.println(INCORRECT);
                    return;
                }

                System.out.println("ticket add: ok");
                return;
            }

            // -------------------------
            // CASE 2: ADD PRODUCT
            // ticket add <ticketId> <cashId> <prodId> <amount> [--p...]
            // -------------------------
            if (args.length < 6) {
                System.out.println(INCORRECT);
                return;
            }

            int prodId = Integer.parseInt(args[4]);
            int amount = Integer.parseInt(args[5]);

            Product product = ProductController.getInstance().getProduct(prodId);
            if (product == null) {
                System.out.println(NOTEXIST);
                return;
            }
            // CustomerTicket: comportamiento legacy (incluye personalizados)
            if (ticket instanceof CustomerTicket) {
                CustomerTicket customerTicket = (CustomerTicket) ticket;

                if (product instanceof PersonalizedProduct) {
                    int maxPers = ((PersonalizedProduct) product).getMaxPers();
                    PersonalizedProduct local = new PersonalizedProduct(
                            prodId,
                            product.getName(),
                            ((PersonalizedProduct) product).getCategory(),
                            product.getPrice(),
                            maxPers
                    );
                    StringBuilder customs = new StringBuilder();

                    for (int i = 6; i < args.length; i++) {
                        String personalization = args[i].replaceAll("--p", "");
                        local.addPersonalized(personalization);
                        if (i == 6) customs.append(personalization);
                        else customs.append(personalization).append(",");
                    }
                    local.newPrice();

                    boolean ok = customerTicket.ticketAdd(local, amount);
                    if (ok) ticketDAO.addProduct(customerTicket.getId(), prodId, amount, customs.toString());
                } else {
                    boolean ok = customerTicket.ticketAdd(product, amount);
                    if (ok) ticketDAO.addProduct(customerTicket.getId(), prodId, amount, null);
                }

                return;
            }
            // EnterpriseMixedTicket: añade productos (amount veces)
            if (ticket instanceof EnterpriseMixedTicket) {
                EnterpriseMixedTicket mt = (EnterpriseMixedTicket) ticket;

                if (product instanceof PersonalizedProduct) {
                    int maxPers = ((PersonalizedProduct) product).getMaxPers();
                    PersonalizedProduct local = new PersonalizedProduct(
                            prodId,
                            product.getName(),
                            ((PersonalizedProduct) product).getCategory(),
                            product.getPrice(),
                            maxPers
                    );
                    StringBuilder customs = new StringBuilder();

                    for (int i = 6; i < args.length; i++) {
                        String personalization = args[i].replaceAll("--p", "");
                        local.addPersonalized(personalization);
                        if (i == 6) customs.append(personalization);
                        else customs.append(personalization).append(",");
                    }
                    local.newPrice();

                    boolean ok = mt.addProduct(local, amount);
                    if (ok) ticketDAO.addProduct(mt.getId(), prodId, amount, customs.toString());
                } else {
                    boolean ok =
                            mt.addProduct(product, amount);
                    if (ok) ticketDAO.addProduct(mt.getId(), prodId, amount, null);
                }
            }

            // EnterpriseServiceTicket (solo servicios) u otros: no aceptan productos
            System.out.println(INCORRECT);


        } catch (NumberFormatException e) {

            //TODO catch all exceptions


            System.out.println(INCORRECT);
        }
    }

    public void ticketRemove(int ticketId, int prodId) {

        Ticket t = TicketData.getInstance().getTicket(ticketId);
        int idBD = t.getId();

        if (t instanceof CustomerTicket) {
            boolean borrado = false;
            Product customerProduct = ((CustomerTicket) t).ticketRemove(prodId);
            if (customerProduct != null) {
                if (customerProduct instanceof PersonalizedProduct) {
                    PersonalizedProduct local = (PersonalizedProduct) customerProduct;
                    String customs = local.getCustomsBD();
                    borrado = true;
                    if (!ticketDAO.removeProduct(idBD, prodId, customs)) {
                        System.out.println(TicketDAO.ERROR_DB);
                    }
                }
                if (borrado) System.out.println(((CustomerTicket) t).print(false));


            } else System.out.println(NOTEXIST);

        } else if (t instanceof EnterpriseServiceTicket) {
            Product serviceProduct = ((EnterpriseServiceTicket) t).ticketRemove(prodId);
            if (serviceProduct == null) {
                System.out.println(NOTEXIST);
            } else {

                if (!ticketDAO.removeProduct(idBD, prodId, null)) System.out.println(TicketDAO.ERROR_DB);
                ;
                System.out.println(((EnterpriseServiceTicket) t).print(false));

            }
        } else if (t instanceof EnterpriseMixedTicket) {
            Product mixedProduct = ((EnterpriseMixedTicket) t).ticketRemove(prodId);
            if (mixedProduct == null) {
                System.out.println(NOTEXIST);
            } else {
                if (mixedProduct instanceof PersonalizedProduct) {
                    PersonalizedProduct local = (PersonalizedProduct) mixedProduct;
                    String customs = local.getCustomsBD();
                    if(!ticketDAO.removeProduct(idBD, prodId, customs)){System.out.println(TicketDAO.ERROR_DB);}
                    System.out.println(((EnterpriseMixedTicket) t).print(false));
                }
                boolean borrado = ticketDAO.removeProduct(idBD, prodId, null);
                if (borrado) System.out.println(((EnterpriseMixedTicket) t).print(false));
                else System.out.println(TicketDAO.ERROR_DB);
            }
        }
    }

    public void ticketNew(String[] args) {
        if (args.length != 4 && args.length != 5 && args.length != 6) {
            System.out.println(INCORRECT);
            return;
        }

        Integer ticketId;
        int cashId;
        int userId;

        // ticket new [<id>] <cashId> <userId> -[c|p|s]

        try {
            userId = CustomerController.getInstance().dniToId(args[3]);
            Customer customer = CustomerController.getInstance().getCustomer(userId);
            if (customer == null) {
                userId = CustomerController.getInstance().dniToId(args[4]);
                customer = CustomerController.getInstance().getCustomer(userId);
                if (customer == null) {
                    System.out.println("User not found");
                    return;
                }
            }
            if (customer instanceof CustomerEnterprise) {
                if (args.length == 5) {
                    // ticket new [<id>] <cashId> <userId> -[c|p|s]
                    cashId = Integer.parseInt(args[3].replaceAll("UW", ""));
                    switch (args[4]) {
                        case "-c":
                            ticketId = TicketData.getInstance().addTicket(COMBINED, cashId, userId);
                            CashierController.getInstance().addTicket(ticketId, cashId);
                            CustomerController.getInstance().addTicket(ticketId, userId);
                            break;
                        case "-s":
                            ticketId = TicketData.getInstance().addTicket(SERVICE, cashId, userId);
                            CashierController.getInstance().addTicket(ticketId, cashId);
                            CustomerController.getInstance().addTicket(ticketId, userId);
                            break;
                        default:
                            System.out.println(INCORRECT);
                            break;
                    }

                } else if (args.length == 6) {
                    // ticket new [<id>] <cashId> <userId> -[c|p|s]
                    ticketId = Integer.valueOf(args[2]);
                    cashId = Integer.parseInt(args[3].replaceAll("UW", ""));
                    switch (args[5]) {
                        case "-s":
                            if (!TicketData.getInstance().addTicket(ticketId, SERVICE, cashId, userId)) {
                                System.out.println(ID_REPEAT);
                            } else {
                                CashierController.getInstance().addTicket(ticketId, cashId);
                                CustomerController.getInstance().addTicket(ticketId, userId);
                            }
                            break;
                        case "-c":
                            if (!TicketData.getInstance().addTicket(ticketId, COMBINED, cashId, userId)) {
                                System.out.println(ID_REPEAT);
                            } else {
                                CashierController.getInstance().addTicket(ticketId, cashId);
                                CustomerController.getInstance().addTicket(ticketId, userId);
                            }
                            break;
                        default:
                            System.out.println(INCORRECT);
                            break;
                    }
                } else {
                    System.out.println(INCORRECT);

                }
            } else {
                if (args.length == 4) {
                    // ticket new <cashId> <userId>
                    String cashierId = args[2].replaceAll("UW", "");
                    cashId = Integer.parseInt(cashierId);
                    ticketId = TicketData.getInstance().addTicket(PRODUCTS1, cashId, userId);
                    CashierController.getInstance().addTicket(ticketId, cashId);
                    CustomerController.getInstance().addTicket(ticketId, userId);
                } else {
                    // ticket new <id> <cashId> <userId>
                    String cashierId = args[3].replaceAll("UW", "");
                    cashId = Integer.parseInt(cashierId);
                    ticketId = Integer.valueOf(args[2]);
                    if (!TicketData.getInstance().addTicket(ticketId, PRODUCTS1, cashId, userId))
                        System.out.println(ID_REPEAT);
                    else {
                        CashierController.getInstance().addTicket(ticketId, cashId);
                        CustomerController.getInstance().addTicket(ticketId, userId);
                    }
                }
            }

        } catch (NumberFormatException e) {
            System.out.println(INCORRECT);
        }

    }


    public void ticketPrint(int idTicket) {
        Ticket ticket = ticketData.getTicket(idTicket);
        if (ticket != null) {
            String data = ticket.print(true);
            if (data.isEmpty()) {
                System.out.println(EMPTY_TICKET);
            } else {
                System.out.println(data);
                System.out.println("ticket print: ok");
            }
        }
    }
}
