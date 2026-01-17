package upm.etsisi.poo.es.Tickets;

import upm.etsisi.poo.es.Product.BasicProduct;
import upm.etsisi.poo.es.Product.Event;
import upm.etsisi.poo.es.Product.PersonalizedProduct;
import upm.etsisi.poo.es.Product.Product;
import upm.etsisi.poo.es.type;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Locale;

public class CustomerTicket extends Ticket {
    private Status status;

    public CustomerTicket(Integer id) {
        super(id);
    }


    @Override
    public boolean canBeClosed() {
        return false;
    }

    /**
     * The method adds the product given, and it also prints it. The ticket status
     * must be
     * OPEN or EMPTY. The ticket must have less than 100 products, if not, the
     * product
     * won't be added.
     *
     * @param product The product given, it cannot be null (if it is, the method
     *                won't add it).
     *                Also, if the product given is a Meeting or a Food, then it
     *                cannot be an
     *                invalid date.
     * @param amount  The amount when the product is a Food or Meeting, it shows the
     *                amount of
     *                people that are expected on that Food/Meeting. If not, it
     *                shows how much times
     *                are we going to add the product into the ticket.
     * @return It returns true if the product has been added successfully.
     */
    public boolean ticketAdd(Product product, int amount) {
        boolean resul = true;
        if (this.status != Status.CLOSED) {
            int before = this.amount;
            if (product == null) {
                resul = false;
                System.out.println(ERROR_PRODUCT_ID_NOT_FOUND);

            } else {
                if (this.amount == 0) {
                    this.status = Status.OPEN;
                    TicketDAO.getInstance().setStatus(this.id_bd, Status.OPEN);
                }

                if (product instanceof Event) {
                    Event event = (Event) product;
                    if (event.fechaValida(LocalDateTime.now())) {
                        if (amount <= event.getMaxPersonas()) {

                            double price = event.getPricePerPerson() * amount;
                            event.setPrice(price);

                            productList[this.amount] = event;
                            this.amount++;

                            System.out.println(ticketPrint(false));
                            System.out.println(ADD_OK);

                        } else {
                            System.out.println(MANY_PEOPLE);
                            resul = false;
                        }
                    } else {
                        System.out.println(PERIOD_NOT_VALID);
                    }

                } else {
                    int i = 0;
                    while (this.amount < MAX_PRODUCT && i < amount) {
                        productList[this.amount] = product;
                        this.amount++;
                        i++;
                    }
                    System.out.println(ticketPrint(false));
                    if ((this.amount - before) == amount) {
                        resul = true;
                        System.out.println(ADD_OK);
                    } else {
                        resul = false;
                        System.out.println(ERROR_FULL);
                    }

                }

            }
        } else {
            resul = false;
            System.out.println("ERROR: the ticket is closed. It can't be modified");
        }
        return resul;
    }

    /**
     * @param prodId This is Id from the product that sending us to remove
     *               This method remove all occurrences of the product
     * @return it's a boolean that checks if the product is removed
     */
    public Product ticketRemove(int prodId) {
        Product product = null;
        int iterations = this.amount;
        if (this.status != Status.CLOSED) {
            if (this.amount == 0) {
                System.out.println(NO_PRODUCTS_IN_THE_TICKET);

            } else {
                for (int i = 0; i < iterations; i++) {
                    if (productList[i] != null && productList[i].getId() == prodId) {
                        if (this.amount == 1) {
                            productList[0] = null;
                            this.amount--;
                        } else {
                            product = productList[i];
                            productList[i] = productList[amount - 1];
                            productList[amount - 1] = null;
                            this.amount--;
                            i--;
                        }
                    }
                }

                boolean comprobation = true;
                int i = 0;
                while (comprobation && i < this.amount) {
                    if (productList[i] != null) {
                        if (productList[i].getId() == prodId) {
                            comprobation = false;
                        }
                    }
                    i++;
                }
                if (iterations == this.amount) {
                    System.out.println(PRODUCT_DOES_NOT_EXIST);
                }
            }
        } else {
            System.out.println(ERROR_TICKET_CLOSE);
        }
        return product;

    }

    /**
     * @return the ticket printed
     */
    private boolean comprobarFechasTodosEventos(LocalDateTime now) {
        int i = 0;
        boolean valido = true;

        while (valido && i < this.amount) {
            if ((productList[i] != null) && (productList[i] instanceof Event)
                    && !(((Event) productList[i]).fechaValida(now))) {
                valido = false;
            }
            i++;
        }
        return valido;
    }

    /**
     * The method closes the ticket (if the Events are on date) and turns it into a
     * String.
     *
     * @param close it shows if the ticket has been already closed
     * @return the String of the ticket
     */
    public String ticketPrint(boolean close) {
        StringBuilder sc = new StringBuilder();

        if (close) {
            LocalDateTime now = LocalDateTime.now();
            setDate(now);
            boolean validClose = comprobarFechasTodosEventos(now);
            if (validClose) {
                this.status = Status.CLOSED;
                TicketDAO.getInstance().setStatus(this.id_bd, Status.CLOSED);

            } else
                System.out.println(DONT_CLOSE_NOT_VALID_TIME);
        }
        sc.append(TICKET + " ").append(toStringId()).append("\n");
        if (this.amount > 0 && this.productList[0] != null) {
            sort();
            int n = this.amount;
            int[] categoryCount = new int[type.values().length];
            for (int i = 0; i < n; i++) {
                Product p = productList[i];
                if (p != null) {
                    if (p instanceof BasicProduct) {
                        BasicProduct pr = (BasicProduct) p;
                        categoryCount[pr.getCategory().ordinal()]++;
                    }
                }
            }

            double totalPrice = 0.0;
            double totalDiscount = 0.0;

            for (int i = 0; i < n; i++) {
                Product p = productList[i];
                if (p != null) {
                    double price = p.getPrice();
                    sc.append("  ");
                    if (p instanceof PersonalizedProduct) {
                        PersonalizedProduct product = (PersonalizedProduct) p;
                        double discountValue = 0.0;
                        if (categoryCount[product.getCategory().ordinal()] >= 2) {
                            discountValue = price - product.getDiscountedPrice();
                        }

                        totalPrice += price;
                        totalDiscount += discountValue;

                        if (discountValue > 0.0) {
                            sc.append(product.toStringDiscount(discountValue));
                        } else {
                            sc.append(product);
                        }

                    } else if (p instanceof BasicProduct) {
                        BasicProduct product = (BasicProduct) p;

                        double discountValue = 0.0;
                        if (categoryCount[product.getCategory().ordinal()] >= 2) {
                            discountValue = price - product.getDiscountedPrice();
                        }

                        totalPrice += price;
                        totalDiscount += discountValue;

                        if (discountValue > 0.0) {
                            sc.append(product.toStringDiscount(discountValue));
                        } else {
                            sc.append(product.toString());
                        }

                    } else if (p instanceof Event) {
                        // Meeting / Food (u otros eventos): sin descuento por categoría
                        Event event = (Event) p;

                        totalPrice += price;
                        // totalDiscount NO cambia (no hay descuento por categoría)

                        sc.append(event.toString());
                    }
                }
            }

            double finalPrice = totalPrice - totalDiscount;
            sc.append("  " + TOTAL_PRICE + " ").append(String.format(Locale.US, "%.3f", totalPrice));
            sc.append("\n  " + TOTAL_DISCOUNT + " ").append(String.format(Locale.US, "%.3f", totalDiscount));
            sc.append("\n  " + FINAL_PRICE + " ").append(String.format(Locale.US, "%.3f", finalPrice));
        }

        return sc.toString();
    }
    public void setAmount (int amount) {
        this.amount = amount;
    }
    public void setProductList ( Product[] productList) {
        this.productList = productList;
    }
    public String getType(){
        return TicketController.PRODUCTS1;
    }
    /**
     * The method sorts the names alphabetically
     */
    public void sort() {
        Arrays.sort(this.productList, 0, this.amount, nameComp);
    }


}
