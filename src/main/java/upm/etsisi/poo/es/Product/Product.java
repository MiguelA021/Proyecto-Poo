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
/*
    public Product(int id, String name, type category, double price, int maxPer) {// maxpers son las maximas
        // personalizaciones
        this.name = name.trim();
        this.price = price;
        this.id = id;
        this.category = category;
        this.maxPer = maxPer;
        this.personalizaciones = new ArrayList<String>(maxPer);
    }

    public Product(int id, String name, type category, double price) {
        this.name = name.trim();
        this.price = price;
        this.id = id;
        this.category = category;
        this.maxPer = 0;
        this.personalizaciones = null;
    }

    // Create a new constructor for doing the static class FOOD and MEETING for
    // being called by other classes

    public Product(int id, String name, double price) {
        this.id = id;
        this.name = name.trim();
        this.price = price;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public type getCategory() {
        return this.category;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void SetCategory(type category) {
        this.category = category;
    }

    /**
     * @param name should be of less than 100 characters and not empty
     *
    public void setName(String name) {
        if (name.length() < 100 && !name.isEmpty()) {
            this.name = name.trim();
        } else {
            if (name.length() >= 100) {
                System.out.println(NAME_LENGTH_ERROR);
            }
            if (name.isEmpty()) {
                System.out.println(NAME_NULL_ERROR);
            }
        }
    }

    /**
     * @param price must be always higher than zero
     *
    public void setPrice(double price) {
        if (price > 0) { // Se debería incluir un control de que no este en null
            this.price = price;
        } else {
            this.price = 1;
            System.out.println(PRICE_POSITIVE_ERROR);
        }

    }

    /**
     * @return the discount that is allowed with the amount of the product
     *
    public double getDiscountedPrice() {
        double discountRate;
        switch (this.category) {
            case MERCH:
                discountRate = 0.0;
                break;
            case STATIONERY:
                discountRate = 0.05;
                break;
            case CLOTHES:
                discountRate = 0.07;
                break;
            case BOOK:
                discountRate = 0.10;
                break;
            case ELECTRONICS:
                discountRate = 0.03;
                break;
            default:
                discountRate = 0.0;
        }

        return price * (1 - discountRate);
    }

    // It's the operation of the price for customizable products
    public double pricePerCustomizedText() {
        return 0.1 * price;
    }

    @Override
    /**
     * the method turns the object into a String with the format required
     *
    public String toString() {
        StringBuilder sb = new StringBuilder();
        if (personalizaciones == null) {
            sb.append("{class:Product, id: " + this.id + ", name: '" + this.name + "', category: " + this.category
                    + ", price: " + this.price + "}");
            return sb.toString();
        } else {
            sb.append("{class:Product, id: " + this.id + ", name: '" + this.name + "', category: " + this.category
                    + ", price: " + this.price + "Personalizaciones: ");
            for (int i = 0; i < personalizaciones.size(); i++) {
                sb.append(personalizaciones.get(i).toString());
            }
            sb.append("}");
            return sb.toString();
        }

    }
    */
}



