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

        for (int i = 0; i < storeProducts.length; i++) {
            if (storeProducts[i] != null && storeProducts[i].getId() == prodId) {
                productoEncontrado = storeProducts[i];
                break;
            }
        }

        if (productoEncontrado == null) {
            System.out.println("ERROR: Product ID not found " + prodId);
            return false;
        }

        for (int i = 0; i < amount; i++) {
            if (this.amount < MAX_PRODUCT) {
                productList[this.amount] = productoEncontrado;
                this.amount++;
            } else {
                System.out.println("ERROR: Full Ticket (100 products max)");
                break;
            }
        }

        //No se si hace falta aqui un sort

        for (int i = 0; i < this.amount; i++) {
            Product p = productList[i];
            if (p != null) {
                System.out.println(p.toString());
            }
        }

        System.out.println("ticket add: ok");
        return true;
    }

    /**
     * @param prodId This is Id from the product that sending us to remove
     *               This method remove all occurrences of the product
     * @return it's a boolean that checks if the product is removed
     */
    public Product ticketRemove(int prodId) {
        if (this.amount == 0) {
            System.out.println("ERROR: No products in the ticket");
            return null;
        }

        Product removed = null;

        for (int i = 0; i < this.amount; i++) {
            Product p = productList[i];
            if (p != null && p.getId() == prodId) {
                if (removed == null) {
                    removed = p;
                }
                for (int j = i; j < this.amount - 1; j++) {
                    productList[j] = productList[j + 1];
                }
                productList[this.amount - 1] = null;
                this.amount--;
                i--;
            }
        }

        if (removed == null) {
            System.out.println("ERROR: No product with that ID " + prodId + " in the ticket");
            return null;
        }

        sort();
        System.out.println("ticket remove: ok");
        return removed;
    }

    /**
     * @return the ticket printed
     */
    public String ticketPrint() {
        StringBuilder sc = new StringBuilder();
        int i = 0;
        double totalPrice = 0, totalDiscount = 0;
        double finalPrice = 0;
        sort();
        while (productList[i] != null) {
            sc.append(productList[i].toString());
            totalPrice += productList[i].getPrice();
            totalDiscount += productList[i].getDiscountedPrice();
            i++;
            sc.append("\n");
        }
        finalPrice = totalPrice - totalDiscount;
        sc.append("Total price: ");
        sc.append(totalPrice + "\n");
        sc.append("\nTotal discount: ");
        sc.append(totalDiscount + "\n");
        sc.append("\nFinal price: ");
        sc.append(finalPrice + "\n");
        sc.append("\nticket print: ok");
        return sc.toString();
    }

    /**
     * The method sorts the names alphabetically
     */
    public void sort() {
        Arrays.sort(productList, nameComp);
    }

}
