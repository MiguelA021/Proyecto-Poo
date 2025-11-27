package upm.etsisi.poo.es.Product;

import upm.etsisi.poo.es.type;

import java.util.ArrayList;

public class PersonalizadProduct extends BasicProduct{
    private int maxPers;
    private ArrayList<String > personalizaciones;

    public PersonalizadProduct(int id, String name,  type type,double price, int maxPers){
        super(id, name, type, price);
        this.maxPers = maxPers;
        this.personalizaciones = new ArrayList<String>(maxPers);
    }

}
