package upm.etsisi.poo.es.Tickets;

import org.apache.commons.csv.CSVPrinter;
import upm.etsisi.poo.es.Product.*;
import upm.etsisi.poo.es.type;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Locale;

public class CustomerTicket extends Ticket {

    public CustomerTicket(Integer id) {
        super(id);
    }

    public CustomerTicket(Integer id, Status status) {
        super(id);
        this.status = status;
    }

    @Override
    public String print(boolean close) {
        return ticketPrint(close);
    }

    @Override
    public boolean close() {
        if (status == Status.CLOSE) {
            return true;
        }
            status = Status.CLOSE;
            for (int i = 0; i<this.amount; i++){
                if(productList[i] instanceof BasicProduct){
                    this.productList[i] = copy((BasicProduct) productList[i]);
                }
            }

        return true;
    }


    @Override
    public boolean canBeClosed() {
        LocalDateTime now = LocalDateTime.now();
        return comprobarFechasTodosEventos(now);
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
        if (this.status != Status.CLOSE) {
            int before = this.amount;
            if (product == null) {
                resul = false;
                System.out.println(ERROR_PRODUCT_ID_NOT_FOUND);

            } else {
                if (this.amount == 0) {
                    this.status = Status.OPEN;
                }

                if (product instanceof Event) {
                    Event event = (Event) product;
                    if (event.fechaValida(LocalDateTime.now())) {
                        if (amount <= event.getMaxPersonas()) {

                            double price = event.getPricePerPerson() * amount;
                            if (event instanceof Meeting) {
                                Meeting meeting = new Meeting(event.getId(), event.getName(), event.getPricePerPerson(), event.getExpiryDate().toLocalDate().toString());
                                meeting.setPrice(price);
                                productList[this.amount] = meeting;
                                this.amount++;
                            } else {
                                Food food = new Food(event.getId(), event.getName(), event.getPricePerPerson(), event.getExpiryDate().toLocalDate().toString());
                                food.setPrice(price);
                                productList[this.amount] = food;
                                this.amount++;
                            }


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

    public boolean ticketAddNoString(Product product, int amount) {
        boolean resul = true;
        if (this.status != Status.CLOSE) {
            int before = this.amount;
            if (product == null) {
                resul = false;

            } else {
                if (this.amount == 0) {
                    this.status = Status.OPEN;
                }

                if (product instanceof Event) {
                    Event event = (Event) product;
                    if (event.fechaValida(LocalDateTime.now())) {
                        if (amount <= event.getMaxPersonas()) {
                            double price = event.getPricePerPerson() * amount;
                            if (event instanceof Meeting) {
                                Meeting meeting = new Meeting(event.getId(), event.getName(), event.getPricePerPerson(), event.getExpiryDate().toLocalDate().toString());
                                meeting.setPrice(price);
                                productList[this.amount] = meeting;
                                this.amount++;
                            } else {
                                Food food = new Food(event.getId(), event.getName(), event.getPricePerPerson(), event.getExpiryDate().toLocalDate().toString());
                                food.setPrice(price);
                                productList[this.amount] = food;
                                this.amount++;
                            }
                        } else {
                            resul = false;
                        }
                    } else {
                    }

                } else {
                    int i = 0;
                    while (this.amount < MAX_PRODUCT && i < amount) {
                        productList[this.amount] = product;
                        this.amount++;
                        i++;
                    }
                    if ((this.amount - before) == amount) {
                        resul = true;
                    } else {
                        resul = false;
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
        if (this.status != Status.CLOSE) {
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

    private String formatOutput(double val) {

        double rounded = Math.round(val * 1000.0) / 1000.0;

        return String.valueOf(rounded);
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
            if (!this.close())
                return PERIOD_NOT_VALID;
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
            sc.append("  " + TOTAL_PRICE + " ").append(formatOutput(totalPrice));
            sc.append("\n  " + TOTAL_DISCOUNT + " ").append(formatOutput(totalDiscount));
            sc.append("\n  " + FINAL_PRICE + " ").append(formatOutput(finalPrice));
        }

        return sc.toString();
    }

    /**
     * The method sorts the names alphabetically
     */
    public void sort() {
        Arrays.sort(this.productList, 0, this.amount, nameComp);
    }


    @Override
    public void printCsv(CSVPrinter csvPrinter) throws Exception {
        csvPrinter.printRecord("CustomerTicket", id, status);
        for (int i = 0; i < this.amount; i++) {
            Product p = productList[i];
            if (p instanceof PersonalizedProduct) {
                PersonalizedProduct pp = (PersonalizedProduct) p;
                csvPrinter.printRecord(id, "PersonalizedProduct", pp.getId(), pp.getName(), pp.getCategory().name(), pp.getPrice(), pp.getMaxPers(), pp.getPerstonalizations());
            } else if (p instanceof BasicProduct) {
                BasicProduct pp = (BasicProduct) p;
                csvPrinter.printRecord(id, "BasicProduct", pp.getId(), pp.getName(), pp.getCategory().name(), pp.getPrice());
            } else if (p instanceof Meeting) {
                Meeting m = (Meeting) p;
                csvPrinter.printRecord(id, "Meeting", m.getId(), m.getName(), m.getPricePerPerson(), m.getExpiryDate().toLocalDate());
            } else if (p instanceof Food) {
                Food m = (Food) p;
                csvPrinter.printRecord(id, "Food", m.getId(), m.getName(), m.getPricePerPerson(), m.getExpiryDate().toLocalDate());
            } else {
                Service s = (Service) p;
                csvPrinter.printRecord(id, "Service", s.getMaxUseDate(), s.getName(), s.getId());
            }
        }
    }

}
