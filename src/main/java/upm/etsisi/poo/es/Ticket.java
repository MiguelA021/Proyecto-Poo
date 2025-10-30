package upm.etsisi.poo.es;

import java.time.LocalTime;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Locale;

enum status {
    EMPTY, ACTIVE, CLOSED
}

public class Ticket {
    final static int MAX_PRODUCT = 100;
    Product[] productList;
    int amount;
    private StringBuilder id;
    Comparator<Product> nameComp = Comparator.comparing(Product::getName);
    private status status;

    public Ticket(Store store) { //Aaron lo ha implementado con un int id en vez de una Store, ver cual es mejor
        this.productList = new Product[MAX_PRODUCT];
        LocalTime now = LocalTime.now();
        this.id = new StringBuilder(now.toString()).append(String.format("%05d", id));
        this.amount = 0;
        this.status = upm.etsisi.poo.es.status.EMPTY;
    }


    public boolean ticketAdd(int proId, Store store, int amount) {
        boolean resul;

        if (this.status != upm.etsisi.poo.es.status.CLOSED) {
            Product productoEncontrado = store.getProduct(proId);
            int before = this.amount;
            if (productoEncontrado == null) {
                resul = false;
                System.out.println("ERROR: Product ID not found " + proId);

            } else {
                if (this.amount == 0) {
                    this.status = upm.etsisi.poo.es.status.ACTIVE;
                }
                int i = 0;
                while (i < amount && this.amount < MAX_PRODUCT) {
                    productList[this.amount] = productoEncontrado;
                    this.amount++;
                    i++;
                }
                System.out.println(ticketPrint());
                if ((this.amount - before) == amount) {
                    resul = true;
                    System.out.println("ticket add: ok");
                } else {
                    resul = false;
                    System.out.println("ERROR: Full Ticket (100 products max)");
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
        boolean removed = false;
        int iterations = this.amount;
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
            for (int i = 0; i < this.amount; i++) {
                if (productList[i] != null) {
                    if (productList[i].getId() == prodId) {
                        comprobation = false;
                    }
                }
            }
            removed = comprobation;

            sort();
        }
        return product;

    }

    /**
     * @return the ticket printed
     */
    public String ticketPrint() {
        StringBuilder sc = new StringBuilder();
        LocalTime now = LocalTime.now();
        this.id.append(now.toString());
        this.status = upm.etsisi.poo.es.status.CLOSED;

        if (this.amount > 0 && this.productList[0] != null) {
            sort();
            int n = this.amount;

            int[] categoryCount = new int[type.values().length];
            for (int i = 0; i < n; i++) {
                Product p = productList[i];
                if (p != null) {
                    categoryCount[p.getCategory().ordinal()]++;
                }
            }

            double totalPrice = 0.0;
            double totalDiscount = 0.0;

            for (int i = 0; i < n; i++) {
                Product p = productList[i];

                if (p != null) {
                    double price = p.getPrice();
                    double discountValue = 0.0;

                    if (categoryCount[p.getCategory().ordinal()] >= 2) {
                        discountValue = price - p.getDiscountedPrice();
                    }

                    totalPrice += price;
                    totalDiscount += discountValue;

                    if (discountValue > 0.0) {
                        sc.append(String.format(
                                "{class:Product, id: %d, name: '%s', category: %s, price: %.2f} **discount -%.2f", p.getId(), p.getName(), p.getCategory(), price, discountValue));
                    } else {
                        sc.append(String.format(
                                "{class:Product, id: %d, name: '%s', category: %s, price: %.2f}", p.getId(), p.getName(), p.getCategory(), price));
                    }
                    sc.append("\n");
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

}