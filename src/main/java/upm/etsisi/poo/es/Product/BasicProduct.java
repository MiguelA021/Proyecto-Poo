package upm.etsisi.poo.es.Product;

import upm.etsisi.poo.es.type;

public class BasicProduct extends Product {

    private type category;


    public BasicProduct(int id, String name, type category, double price) {
        this.name = name.trim();
        this.price = price;
        this.id = id;
        this.category = category;
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


    public void setPrice(double price) {
        if (price > 0) { // Se debería incluir un control de que no este en null
            this.price = price;
        } else {
            this.price = 1;
            System.out.println(PRICE_POSITIVE_ERROR);
        }

    }

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

}
