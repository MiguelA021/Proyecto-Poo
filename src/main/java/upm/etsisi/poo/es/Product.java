package upm.etsisi.poo.es;

//BORRAR ESTE COMENTARIO

enum type {
    MERCH, STATIONERY, CLOTHES, BOOK, ELECTRONICS
}

public class Product {
    private int id;
    private String name;
    private double price;
    private type category;
    private final static String NAME_LENGTH_ERROR="The name should be less than 100 characters";
    private final static String NAME_NULL_ERROR="The name shouldn't be empty";
    private final static String PRICE_POSITIVE_ERROR="WARNING: The price should be a positive number";

    public Product(int id, String name, type category, double price) {
        this.name = name;
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

    /**
     * @param name should be of less than 100 characters and not empty
     */
    public void setName(String name) {
        if (name.length() < 100 && !name.isEmpty()) {
            this.name = name;
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
     */
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
     */
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

    @Override
    public String toString() {
        return "{class:Product, id: " + this.id + ", name: '" + this.name + "', category: " + this.category + ", price: " + this.price + "}";

    }
    public String toStringTicket(){
        double discountValue = this.price - this.getDiscountedPrice();
        return String.format("{class:Product, id: %d, name: '%s', category: %s, price: %.2f} **discount -%.2f", this.id, this.name, this.category, this.price, discountValue);
    }
}
