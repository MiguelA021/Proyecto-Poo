package upm.etsisi.poo.es;

import upm.etsisi.poo.es.Product.BasicProduct;
import upm.etsisi.poo.es.Product.Event;
import upm.etsisi.poo.es.Product.PersonalizedProduct;
import upm.etsisi.poo.es.Product.Product;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;

enum Status {
    EMPTY, ACTIVE, CLOSED
}

public class Ticket {
    final static int MAX_PRODUCT = 100;
    public static final String ERROR_FULL = "ERROR: Full Ticket (100 products max)";
    Product[] productList;
    int amount;
    private ArrayList<LocalDateTime> dates;
    private int tickId;
    Comparator<Product> nameComp = Comparator.comparing(Product::getName);
    private Status status;
    private static DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("yy-MM-dd-HH:mm");

    public Ticket(Integer id) {
        this.productList = new Product[MAX_PRODUCT];
        LocalDateTime now = LocalDateTime.now();
        this.dates = new ArrayList<LocalDateTime>();
        dates.add(now);
        if (id != null) {
            this.tickId = id;
        }
        this.amount = 0;
        this.status = Status.EMPTY;
    }

    public int getId() {
        return this.tickId;
    }

    public boolean ticketAdd(Product product, Store store, int amount) {
        boolean resul = true;
        if (this.status != Status.CLOSED) {
            int before = this.amount;
            if (product == null) {
                resul = false;
                System.out.println("ERROR: Product ID not found ");

            } else {
                if (this.amount == 0) {
                    this.status = Status.ACTIVE;
                }

                if (product instanceof Event) {
                    Event event = (Event) product;
                    if (event.fechaValida(LocalDateTime.now())) {
                        if (amount < event.getMaxPersonas()) {
                            productList[this.amount] = event;
                            this.amount++;
                        } else {
                            System.out.println("Too many people");
                            resul = false;
                        }
                    } else {
                        System.out.println("The period of time is not valid");
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
                        System.out.println("ticket add: ok");
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
                System.out.println("ERROR: No products in the ticket");

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
                sort();
                if (iterations == this.amount) {
                    System.out.println("ERROR: this product does not exist.");
                }
            }
        } else {
            System.out.println("ERROR: the ticket is closed. It can't be modified");
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
            if ((productList[i] != null) && (productList[i] instanceof Event) && !(((Event) productList[i]).fechaValida(now))) {
                valido = false;
            }
            i++;
        }
        return valido;
    }
    public String ticketPrint(boolean close) {
        StringBuilder sc = new StringBuilder();

        sc.append(dates.get(0)).append("-").append(tickId + "\n");
        if (close) {
            LocalDateTime now = LocalDateTime.now();
            dates.add(now);
            boolean validClose = comprobarFechasTodosEventos(now);
            if (validClose) {
                sc.append(tickId).append("-").append(now + "\n");
                this.status = Status.CLOSED;
            } else System.out.println("The ticket can`t be closed because some event's period of time is invalid. \n");
        }
        System.out.println(sc.toString());
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
                    if (p instanceof BasicProduct) {
                        double discountValue = 0.0;
                        BasicProduct product = (BasicProduct) p;
                        if (categoryCount[product.getCategory().ordinal()] >= 2) {
                            discountValue = price - product.getDiscountedPrice();
                        }

                        totalPrice += price;
                        totalDiscount += discountValue;

                        if (discountValue > 0.0) {
                            sc.append(String.format(
                                    "{class:Product, id: %d, name: '%s', category: %s, price: %.2f} **discount -%.2f", p.getId(),
                                    p.getName(), product.getCategory(), price, discountValue));
                        } else {
                            sc.append(String.format(
                                    "{class:Product, id: %d, name: '%s', category: %s, price: %.2f}", p.getId(), p.getName(),
                                    product.getCategory(), price));
                        }
                        sc.append("\n");
                    } else {
                        System.out.println(p);
                    }
                }
            }

            double finalPrice = totalPrice - totalDiscount;
            sc.append("Total price: ").append(String.format("%.2f", totalPrice));
            sc.append("\nTotal discount: ").append(String.format("%.2f", totalDiscount));
            sc.append("\nFinal price: ").append(String.format("%.2f", finalPrice));
        }

        return sc.toString();
    }


    /**
     * The method sorts the names alphabetically
     */
    public void sort() {
        Arrays.sort(productList, 0, amount, nameComp);
    }

    public String getStatus() {
        String str;
        switch (this.status) {
            case EMPTY:
                str = "Empty";
                break;
            case ACTIVE:
                str = "Active";
            case CLOSED:
                str = "Closed";
                break;
            default:
                str = "Error, status is undefined";
                break;
        }
        return str;
    }

    public String formatList() {// si esta abierto mostramos solo id. Si esta vacio mostramos fecha de creacion. Si esta cerrado fecha de cierre
        StringBuilder resul = new StringBuilder();
        String status = this.status.toString().toUpperCase();
        switch (status) {
            case "EMPTY":
                String inicio = dates.get(0).format(DATE_FORMAT);
                resul.append(inicio).append("-").append(tickId);
                break;
            case "ACTIVE":
                resul.append(tickId);
                break;
            case"CLOSED":
                String fin = dates.get(1).format(DATE_FORMAT);
                resul.append(tickId).append(fin);
                break;
            default:
                resul.append("ERROR, status is undefined");
                break;
        }
        resul.append(" - ").append(this.status.toString().toUpperCase());
        return resul.toString();
    }

    public String toStringNew() {
        StringBuilder sc = new StringBuilder(); // Soy Aaron, lo de format() esta puesto para que siga el formato que buscamos de fecha.
        //te lo pongo para que asi no te comas la cabeza con eso. Por lo demás ya te dejo que sigas con ello
        sc.append("Ticket : " + dates.get(0).format(DATE_FORMAT) + "-" + tickId + "\n");
        sc.append("\t Total price: 0.0 \n");
        sc.append("\t Total discount: 0.0 \n");
        sc.append("\t Final price: 0.0 \n");
        sc.append("ticket new: ok");
        return sc.toString();
    }

}
