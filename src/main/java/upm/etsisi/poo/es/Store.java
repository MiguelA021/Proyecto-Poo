package upm.etsisi.poo.es;

public class Store {
    int MAX_PRODUCT = 100;
    Product[] productList;

    public Store() {
        this.productList = new Product[MAX_PRODUCT];
    }

    public Product[] getProducts() {
        return productList;
    }

    public boolean prodAdd(Product product) { // Metodo
        boolean done = true;
        for (int i = 0; i < MAX_PRODUCT && !done; i++) {
            if (productList[i] == null) {
                productList[i] = product;
                done = true;
            }
        }
        return done;
    }

    public boolean prodRemove(int id) {
        boolean found = false;
        for (int i = 0; i < MAX_PRODUCT; i++) {
            if (productList[i].getId() == id) {
                found = true;
                productList[i] = null;
            }
        }
        return found;
    }

    public void prodList() {
        // TODO
    }

    public boolean updateType(int id, type category) {
        boolean done = false;
        for (int i = 0; i < MAX_PRODUCT; i++) {
            if (productList[i].getId() == id) {
                productList[i].SetCategory(category);
                done = true;
            }
        }
        return done;
    }

    public boolean updateName(int id, String name) {
        boolean done = false;
        for (int i = 0; i < MAX_PRODUCT; i++) {
            if (productList[i].getId() == id) {
                productList[i].setName(name);
                done = true;
            }
        }
        return done;
    }

    public boolean updatePrice(int id, double price) {
        boolean done = false;
        for (int i = 0; i < MAX_PRODUCT; i++) {
            if (productList[i].getId() == id) {
                productList[i].setPrice(price);
                done = true;
            }
        }
        return done;
    }
}
