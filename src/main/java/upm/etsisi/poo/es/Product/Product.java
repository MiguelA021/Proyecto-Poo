package upm.etsisi.poo.es.Product;

import upm.etsisi.poo.es.type;

import java.time.LocalDateTime; //Importing for the methods of getStatusFood and getStatusMeeting
import java.util.ArrayList;

public abstract class Product {
    protected int id;
    protected String name;
    protected double price;
    protected final static String NAME_LENGTH_ERROR = "The name should be less than 100 characters";
    protected final static String NAME_NULL_ERROR = "The name shouldn't be empty";
    protected final static String PRICE_POSITIVE_ERROR = "WARNING: The price should be a positive number";
    private ArrayList<String> personalizaciones;

    public abstract int getId();
    public abstract String getName();
    public abstract double getPrice();
    public abstract void setPrice(double price);
    public abstract void setName(String name);


}



