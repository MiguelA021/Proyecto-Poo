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
o prod remove <id
 */


public class Store {
    int MAX_PRODUCT = 200;
    Product[] productList;

    public Store() {
        this.productList = new Product[MAX_PRODUCT];
    }

    public Product[] getProducts() {
        return productList;
    }

    /**
     * The method adds a product tho the productList if there is below 100 products
     *
     * @param product the product which we want to add it
     * @return returns true if the method added the product if not, it returns false
     */
    public boolean prodAdd(Product product) {
        boolean done = false;
        boolean found = false;
        for (int i = 0; i < MAX_PRODUCT && !done && !found; i++) {
            if (productList[i] != null && productList[i].getId() == product.getId()) {
                found = true;
            } else {
                if (productList[i] == null) {
                    productList[i] = product;
                    done = true;
                }
            }
        }
        return done;
    }

    /**
     * The method removes the product with the id given
     *
     * @param id the id of the product we want to remove
     * @return it returns true if the product with the id given was removed, if not
     * it returns false
     */
    public boolean prodRemove(int id) {// Se puede mejorar la eficiencia con un while
        boolean found = false;
        Product product = null;
        for (int i = 0; i < MAX_PRODUCT && !found; i++) {
            if (productList[i].getId() == id) {
                found = true;
                product = productList[i];
                productList[i] = null;
            }
        }
        if (found) {
            System.out.println(product.toString());
            System.out.println("prod remove: ok");

        }
        return found;
    }

    /**
     * The method lists the products in the prodList
     */
    public void prodList() {
        System.out.println("Catalog:");
        for (Product p : productList) {
            if (p != null) {
                System.out.println(p.toString());
            }
        }
        System.out.println("prod list: ok");
    }

    /**
     * The method changes the category of the product with the id given
     *
     * @param id       the id of the product
     * @param category the new category we want to save
     * @return if the product has been updated the method returns true, if not it
     * returns false
     */
    public Product updateType(int id, type category) {
        boolean done = false;
        Product resul = null;
        for (int i = 0; i < MAX_PRODUCT && !done; i++) {
            if (productList[i].getId() == id) {
                productList[i].SetCategory(category);
                resul = productList[i];
                done = true;
            }
        }
        return resul;
    }

    /**
     * The method changes the name of the product with the id given
     *
     * @param id   the id of the product
     * @param name the new name we want to save
     * @return if the product has been updated the method returns true, if not it
     * returns false
     */
    public Product updateName(int id, String name) {
        boolean done = false;
        Product resul = null;
        for (int i = 0; i < MAX_PRODUCT && !done; i++) {
            if (this.productList[i].getId() == id) {
                this.productList[i].setName(name);
                resul = productList[i];
                done = true;
            }
        }
        return resul;
    }

    /**
     * The method changes the price of the product with the id given
     *
     * @param id    the id of the product
     * @param price the new price we want to save
     * @return if the price has been updated the method returns true, if not it
     * returns false
     */
    public Product updatePrice(int id, double price) {
        boolean done = false;
        Product resul = null;
        for (int i = 0; i < MAX_PRODUCT && !done; i++) {
            if (productList[i].getId() == id) {
                productList[i].setPrice(price);
                resul = productList[i];
                done = true;
            }
        }
        return resul;
    }

    public Product getProduct(int prodId) {
        Product result ;
        int i = 0;
        boolean done = false;
        while ( productList[i] != null && productList[i].getId() != prodId){
            i++;
        }

        return null;
    }

}