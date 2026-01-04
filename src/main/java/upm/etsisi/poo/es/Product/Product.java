package upm.etsisi.poo.es.Product;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "productos")
public abstract class Product {
    @Id
    @Column(name = "Id")
    protected int id;

    @Column(name = "NombreProducto")
    protected String name;

    @Column(name = "price")
    protected double price;

    protected final static String NAME_LENGTH_ERROR = "The name should be less than 100 characters";
    protected final static String NAME_NULL_ERROR = "The name shouldn't be empty";
    protected final static String PRICE_POSITIVE_ERROR = "WARNING: The price should be a positive number";

    public abstract int getId();

    public abstract String getName();

    public abstract double getPrice();

    public abstract void setPrice(double price);

    public abstract void setName(String name);

}
