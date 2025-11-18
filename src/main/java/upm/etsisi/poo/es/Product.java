package upm.etsisi.poo.es;
import java.time.LocalDateTime; //Importing for the methods of getStatusFood and getStatusMeeting

enum type {
    MERCH, STATIONERY, CLOTHES, BOOK, ELECTRONICS
}

public class Product {
    private int id;
    private String name;
    private double price;
    private int maxPers;
    private type category;
    private final static String NAME_LENGTH_ERROR="The name should be less than 100 characters";
    private final static String NAME_NULL_ERROR="The name shouldn't be empty";
    private final static String PRICE_POSITIVE_ERROR="WARNING: The price should be a positive number";

    public Product(int id, String name, type category, double price) {
        this.name = name.trim();
        this.price = price;
        this.id = id;
        this.category = category;
    }
    public Product(int id, String name, type category, double price, int maxPers) {
        this.name = name.trim();
        this.price = price;
        this.id = id;
        this.category = category;
        this.maxPers = maxPers;
    }

    //Create a new constructor for doing the static class FOOD and MEETING for being called by other classes
    public Product(int id, String name, double price){

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

    //It's the operation of the price for customizable products
    public double pricePerCustomizedText(){
        return 0.1*price;
    }

    //The three methods under that sentence are using to referencing the Food and Meeting methods
    public boolean getStateFood(int numberParticipant){
        return true;
    }
    public boolean getStateMeeting(int numberParticipant){
        return true;
    }
    public String toStringFoodOrMeeting(){
        return "";
    }

    //This Food class can have its abstracted methods
    public static class Food extends Product {
        private final int advanceMinHours = 72;//It's in hours
        private static final int MAXPARTICIPANTS=100;
        private final String expiryDate;

        public Food (int id, String name, double price, String expiryDate) {
            super(id, name, price);
            this.expiryDate = expiryDate;
        }

        public int getAdvanceMinHours() { //Se encarga del minimo timepo para que se pueda planificar tanto FOOD como MEETING
            return advanceMinHours;
        }
        public int getMaxParticipants(){
            return MAXPARTICIPANTS;
        }

        public String getExpiryDate(){
            return expiryDate; //yyyy-MM-dd
        }

        //This method is necessary to create a product and closing the ticket
        public boolean getStateFood(int numberParticipant){
            int numMaxParticipant = getMaxParticipants();
            String[] time = expiryDate.split("-"); //[yyyy, MM, dd]
            int yearOfExpire = Integer.parseInt(time[0]);
            int monthOfExpire = Integer.parseInt(time[1]);
            int daysOfExpire = Integer.parseInt(time[2]);
            LocalDateTime rightNow = LocalDateTime.now();
            int yearOfNow = rightNow.getYear();       // 2025
            int monthOfNow = rightNow.getMonthValue(); // 11 (Noviembre)
            int dayOfNow = rightNow.getDayOfMonth();
            double plannedHours = ((yearOfExpire-yearOfNow)*12*31*24) + ((monthOfExpire-monthOfNow)*31*24) + ((daysOfExpire-dayOfNow)*24);
            return advanceMinHours>=plannedHours && numberParticipant<=numMaxParticipant;
        }
        public String toString() {
            return super.toStringFoodOrMeeting() + "{class:Product, id: " + getId() + ", name: '" + getName() + ", price: " + getPrice() + ", expiry date: "+ expiryDate+" }";
        }
    }

    //This Meeting class can have its abstracted methods
    public static class Meeting extends Product {
        private final int advanceMinHours = 12;//It's in hours
        private static final int MAXPARTICIPANTS=100;
        private final String expiryDate;

        public Meeting (int id, String name, double price, String expiryDate) {
            super(id, name, price);
            this.expiryDate = expiryDate;
        }

        public int getAdvanceMinHours() { //Se encarga del minimo timepo para que se pueda planificar tanto FOOD como MEETING
            return advanceMinHours;
        }
        public int getMaxParticipants(){
            return MAXPARTICIPANTS;
        }
        public String getExpiryDate(){
            return expiryDate;
        }

        //This method is necessary to create a product and closing the ticket
        public boolean getStateMeeting(int numberParticipant){
            int numMaxParticipant = getMaxParticipants();
            String[] time = expiryDate.split("-");
            int yearOfExpire = Integer.parseInt(time[0]);
            int monthOfExpire = Integer.parseInt(time[1]);
            int daysOfExpire = Integer.parseInt(time[2]);
            LocalDateTime rightNow = LocalDateTime.now();
            int yearOfNow = rightNow.getYear();
            int monthOfNow = rightNow.getMonthValue();
            int dayOfNow = rightNow.getDayOfMonth();
            double plannedHours = ((yearOfExpire-yearOfNow)*12*31*24) + ((monthOfExpire-monthOfNow)*31*24) + ((daysOfExpire-dayOfNow)*24);
            return advanceMinHours>=plannedHours && numberParticipant<=numMaxParticipant;
        }
        public String toString() {
            return super.toStringFoodOrMeeting() + "{class:Product, id: " + getId() + ", name: '" + getName() + ", price: " + getPrice() + ", expiry date: "+ expiryDate+" }";
        }
    }


    @Override
    /**
     * the method turns the object into a String with the format required
     */
    public String toString() {
        return "{class:Product, id: " + this.id + ", name: '" + this.name + "', category: " + this.category + ", price: " + this.price + "}";

    }
}
