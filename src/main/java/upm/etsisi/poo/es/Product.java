package upm.etsisi.poo.es;

/*
#Cada uno es un enum
· Nuevos productos:         Comidas en campus; Reuniones

#Metodos/atributos del enum
· Poseen:                   fecha de caducidad, sin categoría y con número máximo de participantes (máximo 100) y cuyo precio es calculado por persona.
                            Enumerado independiente, que no forme parte de type¿?

#Metodos del enum: minimaPlanificacion
· Creacion de los productos: Para las reuniones se requiere un tiempo mínimo de planificación de 12 horas
                            mientras que para las comidas es necesario 3 días, por lo que, estos tiempos, deben respetarse
                            a la hora de crear el producto y cerrar el ticket.

#Metodos que pueden ser de implementacion, es decir, interfaz llamada: int numMaxTextosPersonalizados(Producto producto),
#en donde, dependiendo del type of product, tendremos un numero maximo determinado. La interfaz vendra del que se encarga
#de crear al producto.
#Para la personalizacion, si maxPers es distinto a Integer.MaxValue es porque es personalizable.

También se extienden los productos con una versión personalizable. Estos productos
personalizados tienen una lista máxima de textos permitidos para el producto y el precio de
estos es calculado agregándole al precio del producto sin personalizar un recargo del 10% por
cada texto personalizado agregado. De esto productos se conoce el número máximo de textos
personalizables por producto. No todos los productos básicos serán personalizables. Un
producto básico no puede pasar a personalizable en el futuro.

#Podemos hacer un metodo booleano que nos indica si el enumerado de FOOD o MEETING es usable o no, de modo que
#en los metodos prod addFood y prodAddMeeting comprobemos esto para que este o no. En ese booleano tenemos el try-catch

o prod add [<id>] "<name>" <category> <price> [<maxPers>]
     si tiene <maxPers> se considerara que el producto es personalizable)
o prod update <id> NAME|CATEGORY|PRICE <value>
o prod addFood [<id>] "< name>" <price> <expiration: yyyy-MM-dd> <max_people>
     El precio es por persona apuntada
o prod addMeeting [<id>] "<name>" <price> < expiration: yyyy-MM-dd> <max_people >
     El precio es por persona apuntada
o prod list
o prod remove <id>
 */

enum type {
    MERCH, STATIONERY, CLOTHES, BOOK, ELECTRONICS
}

//#No necesitamos un metodo booleano extra, se hace la comprobacion desde prodAddFood o Meeting
enum AdvanceProducts {
    FOOD(72),
    MEETING(12);

    private final int advanceMinHours;

    AdvanceProducts(int advanceMinHours){
        this.advanceMinHours = advanceMinHours;
    }

    public int getAdvanceMinHours() {
        return advanceMinHours;
    }

    public boolean getStateFood(int plannedHours){
        return advanceMinHours>=plannedHours;
    }
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