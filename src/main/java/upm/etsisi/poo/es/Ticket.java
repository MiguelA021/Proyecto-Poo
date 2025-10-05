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
     * Method that add the product to the list just when the amount
     * is below 100.
     *
     * @return boolean
     */
    public boolean prodAdd(int id, String name, type category, double price) { // Metodo
        boolean done = true;
        Product product = new Product(id, name, category, price);
        for (int i = 0; i < MAX_PRODUCT && !done; i++) {
            if (productList[i] == null) {
                productList[i] = product;
                done = true;
            }
        }
        return done;
    }

    public String prodList() {
        StringBuilder list = new StringBuilder();
        for (int i = 0; i < MAX_PRODUCT; i++) {
            list.append(productList[i].toString()).append("\n");
        }
        return list.toString();
    }

    public boolean prodRemove(int id) {
        boolean found = false;
        for (int i = 0; i < MAX_PRODUCT; i++) {
            if (productList[i].getId() == id) {
                found = true;
                productList[i] = null;
            }
        }
        return found;
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
    public boolean ticketAdd(int prodId, int amount) { // Agrega al ticket la cantidad del producto
        boolean found = false;
        for (int i = 0; i < MAX_PRODUCT; i++) {
            if (productList[i].getId() == prodId) {
                found = true;
            }
            Product product = productList[i];
            boolean added = true;
            for (int j = 0; j < amount && added; j++) {
            }
        }
        if (found) {
            this.amount += amount;
        }

        return found;
    }

    /**
     * @param prodId This is Id from the product that sending us to remove
     *               This method remove all occurrences of the product
     * @return it's a boolean that checks if the product is removed
     */
    public Product ticketRemove(int prodId) { // (elimina todas las apariciones del producto, revisa si existe el id )
        boolean encontrado = false;
        Product resul = null;
        int i = 0;
        while (productList[i].getId() != prodId) { // encuentra la primera aparición del obejeto que buscamos
            i++;
            encontrado= true;
        }
        if (encontrado){
            resul = productList[i];
            int first = i;
            while (productList[first].getId() == prodId) {
                productList[first] = null;
                first++;
            }
            for (int a = i ; i<productList.length;a++){
                if (first<productList.length && productList[first] != null){
                    productList[a]= productList[first];
                    first++;
                }
            }
        }
        sort();
        return resul;
    }

    public boolean updateAmount(int id, int amount) {
        boolean done = false;
        int contador = 0;
        Product product = null;
        for (int i = 0; i < MAX_PRODUCT; i++) {
            if (productList[i].getId() == id) {
                product = productList[i];
                contador++;
            }
        }
        if (contador < amount) {
            boolean equals = false; // boolean that sees if we reached the needed cuantity of profuct
            for (int i = contador; i < amount && !equals; i++) {
                boolean added = false;
                for (int j = 0; j < MAX_PRODUCT && !added; j++) {
                    if (productList[j] == null) {
                        productList[j] = product;
                    }
                }
                if (i == amount) {
                    equals = true;
                }
                done = equals;
            }
        } else {
            for (int i = contador; i > MAX_PRODUCT; i++) {
                boolean found = false;
                for (int j = MAX_PRODUCT; j > 0 && !found; j++) {
                    if (productList[j] == product) {
                        found = true;
                        productList[j] = null;
                    }
                }
            }
            done = true;
        }
        return done;
    }

    /**
     * @return the ticket printed
     */
    public String ticketPrint() {
        StringBuilder sc = new StringBuilder();
        int i = 0;
        int amount = 0;
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

    public void sort() {
        Arrays.sort(productList, nameComp);
    }

}
