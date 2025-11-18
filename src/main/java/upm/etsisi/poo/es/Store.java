package upm.etsisi.poo.es;

public class Store {
    int MAX_PRODUCT = 200;
    Product[] productList;

    public Store() {
        this.productList = new Product[MAX_PRODUCT];
    }

    public Product[] getProducts() {
        return productList;
    }

    //We're going to do ckecking of maxPeople using the right now date and the expiry date for knowing
    //if we can create the Food or Meeting object
    public boolean addFood(int id, String name, int price, String expiryDate, int maxPeople){
        boolean done=false;
        boolean found = false;
        Product food = new Product.Food(id, name, price, expiryDate);
        if(food.getStateFood(maxPeople)){
            for (int i = 0; i < MAX_PRODUCT && !done && !found; i++) {
                if (productList[i] != null && productList[i].getId() == food.getId()) {
                    found = true;
                } else {
                    if (productList[i] == null) {
                        productList[i] =food;
                        done = true;
                    }
                }
            }
        }
        return done;
    }

    public boolean addMeeting(int id, String name, int price, String expiryDate, int maxPeople){
        boolean done=false;
        boolean found = false;
        Product meeting = new Product.Meeting(id, name, price, expiryDate);
        if(meeting.getStateMeeting(maxPeople)){
            for (int i = 0; i < MAX_PRODUCT && !done && !found; i++) {
                if (productList[i] != null && productList[i].getId() == meeting.getId()) {
                    found = true;
                } else {
                    if (productList[i] == null) {
                        productList[i] = meeting;
                        done = true;
                    }
                }
            }
        }
        return done;
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