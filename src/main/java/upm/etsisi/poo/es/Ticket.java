package upm.etsisi.poo.es;

import java.util.ArrayList;

public class Ticket {
    int amount;
    ArrayList<Product> productList;
    private final int MAX_PRODUCT=100;

    public Ticket(){
        productList = new ArrayList<>();
        amount= 0;
    }

    /**
     * @param product
     * Method that add the product to the list just when the amount is below 100.
     * @return boolean
     */
    public boolean add(Product product){  //Metodo
        boolean done = true;
        if(amount<= 100){
            productList.add(product.getId(),product);
            amount++;
        }
        else done = false;
        return done;
    }
    public boolean remove(Product product){
        return false;
    }

}
