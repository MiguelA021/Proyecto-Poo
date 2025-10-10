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
        this.amount = 0;
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

        if (amount <= 0) {
            System.out.println("ERROR: Amount must be positive");
            return false;
        }

        boolean complete = this.amount == MAX_PRODUCT;
        int agregadas = 0;

        for (int i = 0; i < amount && !complete; i++) {
            if (this.amount < MAX_PRODUCT) {
                productList[this.amount] = productoEncontrado;
                this.amount++;

                double discount = productoEncontrado.getPrice() - productoEncontrado.getDiscountedPrice();
                double shownDiscount = -discount;
                System.out.println(formatoProductoConDescuento(productoEncontrado, shownDiscount));

                agregadas++;
            } else {
                System.out.println("ERROR: Full Ticket (100 products max)");
                complete = true;
            }
        }

        if (complete && agregadas < amount) {
            System.out.println("ERROR: Full Ticket (100 products max)");
        }

        if (agregadas > 0) {
            double totalPrice = 0.0;
            double totalDiscount = 0.0;
            for (int i = 0; i < this.amount; i++) {
                Product p = productList[i];
                if (p == null) break;
                totalPrice += p.getPrice();
                totalDiscount += p.getPrice() - p.getDiscountedPrice();
            }
            double finalPrice = totalPrice - totalDiscount;

            System.out.println("Total price: " + totalPrice);
            System.out.println("Total discount: " + totalDiscount);
            System.out.println("Final price: " + finalPrice);
            System.out.println("ticket print: ok");
        }

        return agregadas > 0;
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
        if (this.productList[0] != null) {
            int i = 0;
            double totalPrice = 0, totalDiscount = 0;
            double finalPrice = 0;
            sort();
            while (i < amount && productList[i] != null) {
                Product p = productList[i];
                double discount = p.getPrice() - p.getDiscountedPrice();
                double shownDiscount = -discount;

                sc.append(formatoProductoConDescuento(p, shownDiscount));
                sc.append("\n");

                totalPrice += p.getPrice();
                totalDiscount += discount;
                i++;
            }
            finalPrice = totalPrice - totalDiscount;
            sc.append("Total price: ");
            sc.append(totalPrice);
            sc.append("\nTotal discount: ");
            sc.append(totalDiscount);
            sc.append("\nFinal price: ");
            sc.append(finalPrice); // ← sin salto de línea al final
        }
        return sc.toString();
    }

    /**
     * The method sorts the names alphabetically
     */
    public void sort() {
        Arrays.sort(productList, 0, amount, nameComp);
    }

    private String formatoProductoConDescuento(Product p, double shownDiscount) {
        return "{class:Product, id:" + p.getId()
                + ", name:'" + p.getName() + "'"
                + ", category:" + p.getCategory()
                + ", price:" + p.getPrice() + "}"
                + " **discount:" + shownDiscount;
    }
}
