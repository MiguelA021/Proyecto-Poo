package upm.etsisi.poo.es.Product;

import upm.etsisi.poo.es.type;

public class PersonalizadProduct extends BasicProduct{
    private int maxPers;

    public PersonalizadProduct(int id, String name,  type type,double price, int maxPers){
        super(id, name, type, price);
        this.maxPers = maxPers;
    }



}
