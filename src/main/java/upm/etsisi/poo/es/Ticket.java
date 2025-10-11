package upm.etsisi.poo.es;

import java.util.Arrays;
import java.util.Comparator;

public class Ticket {
    final static int MAX_PRODUCT = 100;
    Store store;
    Product[] productList;
    Product[] storeProducts;
    int amount;
    Comparator<Product> nameComp = Comparator.comparing(Product::getName);

    public Ticket(Store store) {
        this.productList = new Product[MAX_PRODUCT];
        this.store = store;
        this.storeProducts = this.store.getProducts();
        this.amount = 0;
    }

    /**
     * @return a new ticket, which has been reset
     */
    public Product[] ticketNew() {
        productList = new Product[MAX_PRODUCT];
        amount = 0;
        return productList;
    }

    /**
     * @param prodId is the iD from the product that we want to add to the ticket.
     * @param amount is the product amount
     *               This method adds the product amount to the ticket
     * @return a boolean if the product was found,and in the case 'true', the method
     * set the
     * ticket amount to new amount.
     */
    public boolean ticketAdd(int prodId, int amount) {
        Product productoEncontrado = null;
        boolean found = false;
        for (int i = 0; i < storeProducts.length && !found; i++) {
            if (storeProducts[i] != null && storeProducts[i].getId() == prodId) {
                productoEncontrado = storeProducts[i];
                found = true;
            }
        }

        if (productoEncontrado == null) {
            System.out.println("ERROR: Product ID not found " + prodId);
            return false;
        }
        boolean done = false;
        boolean complete = this.amount == MAX_PRODUCT;
        for (int i = 0; i < amount && !done && !complete; i++) {
            if (this.amount < MAX_PRODUCT) {
                productList[this.amount] = productoEncontrado;
                this.amount++;
                //   System.out.println(productoEncontrado.toString());
                if (this.amount == amount) {
                    done = true;
                }
            } else {
                System.out.println("ERROR: Full Ticket (100 products max)");
            }
        }
        if (complete) {
            System.out.println("ERROR: Full Ticket (100 products max)");
        }
        System.out.println(ticketPrint());
        System.out.println("ticket add: ok");
        return done;
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