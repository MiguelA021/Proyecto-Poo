package upm.etsisi.poo.es;



enum type {
    MERCH, STATIONERY, CLOTHES, BOOK, ELECTRONICS
}

//#Se hace la comprobacion desde prodAddFood o Meeting desde el getStateFood(...), pero como llevarlo a store¿?
/*
ATRIBUTOS/METODOS
- fecha de caducidad, sin categoría y con número máximo de participantes (máximo 100) y cuyo precio es
calculado por persona. Enumerado independiente, que no forme parte de type¿?

 */

public class Product {
    private int id;
    private String name;
    private double price;
    private type category;
    private final static String NAME_LENGTH_ERROR="The name should be less than 100 characters";
    private final static String NAME_NULL_ERROR="The name shouldn't be empty";
    private final static String PRICE_POSITIVE_ERROR="WARNING: The price should be a positive number";

    public enum AdvanceProducts{
        FOOD(72),
        MEETING(12);

        private final int advanceMinHours;
        private final int MAXPARTICIPANTES=100;
        private String fechaCaducidad;
        //precio de FOOD y MEETING es calculado por persona, metodo que calcula el precio/persona

        AdvanceProducts(int advanceMinHours){
            this.advanceMinHours = advanceMinHours;
        }

        public int getAdvanceMinHours() { //Se encarga del minimo timepo para que se pueda planificar tanto FOOD como MEETING
            return advanceMinHours;
        }
        public int getMaxParticipantes(){
            return MAXPARTICIPANTES;
        }
        public String getFechaCaducidad(){
            int dia = (int)(Math.random()*(31-1+1))+1;
            int mes = (int)(Math.random()*(12-1+1))+1;
            int anho = (int)(Math.random()*(2030-2025+1))+2025;
            this.fechaCaducidad = String.format("%02d/%02d/%d",dia, mes, anho);
            return fechaCaducidad;
        }
        //public int getPrecio(){}

        //Debe ser un metodo necesario a la hora de crear el producto y cerrar el ticket
        public boolean getStateFood(int plannedHours){ //Devuelve un booleano para que se vea que este o no disponible para usarlo
            return advanceMinHours>=plannedHours;
        }
    }

    public Product(int id, String name, type category, double price) {
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

    @Override
    /**
     * the method turns the object into a String with the format required
     */
    public String toString() {
        return "{class:Product, id: " + this.id + ", name: '" + this.name + "', category: " + this.category + ", price: " + this.price + "}";

    }

}