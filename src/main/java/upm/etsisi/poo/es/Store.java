package upm.etsisi.poo.es;
import java.util.LinkedList;
import java.util.List;
import java.util.TreeMap;

public class Store {
    int MAX_PRODUCT = 200;
    Product[] productList;
    TreeMap<Integer, Casher> cashers;
    TreeMap<Integer, Customer> customers;
    public static  String CASHER_NOT_FOUND = "The casher given was not found";

    public Store() {
        this.productList = new Product[MAX_PRODUCT];
    }

    public Product[] getProducts() {
        return productList;
    }

    /**
     * The method adds the client if the casher given was found
     * @param name the name of the new client
     * @param dni the ID number of the new client
     * @param email the eamail of the new client
     * @param cashId the id of the casher given
     */
    public void addClient(String name, String dni, String email, int cashId){
        Casher casher= searchCasherById(cashId);
        if (casher!=null){
            int id=dniToId(dni);
            customers.put(id,new Customer(email,name, casher));
        } else{
            System.out.println(CASHER_NOT_FOUND);
        }
    }

    /**
     * The method searches by id the casher
     * @param id the ID of the casher
     * @return the method returns the casher if it has been  found
     */
    private Casher searchCasherById(int id){
        return  cashers.get(id);
    }

    /**
     * The method turns the ID card number into the id without letters
     * @param dni the ID cad number given
     * @return the method returns the id without letters
     */
    private int dniToId (String dni){
        int id=0;
        String[] dniToArray= dni.split("");
        for(int i=0; i< dniToArray.length-1; i++){
            id=id*10;
            id+=Integer.parseInt(dniToArray[i]);
        }
        return id;
    }

    /**
     * The method removes the customer
     * @param dni the ID of the customer given
     * @return the methods returns true if the object was found and removed
     */
    public  boolean removeCustomer(String dni){
        int id = dniToId(dni);
        Customer customer = customers.remove(id);
        return customer != null;
    }
    /**
     * The method list the clients added on the Store
     */
    public void listCustmoers(){
        customers.forEach((id,customer)->{
            System.out.println("Id del cliente: "+id+" "+customer.toString());
        });
    }

    /**
     * The method adds a product tho the productList if there is below 100 products
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