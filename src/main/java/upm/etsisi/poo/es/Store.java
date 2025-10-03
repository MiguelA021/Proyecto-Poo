package upm.etsisi.poo.es;

public class Store {
    int MAX_PRODUCT = 100;
    Product[] productList;


    public Store(Product[] products) {
        this.productList = new Product[MAX_PRODUCT];
    }

    public Product[] getProducts() {
        return productList;
    }

    public boolean prodAdd(String name, double price, int id, type category) { // Metodo
        boolean done = true;
        Product product = new Product(name, price, id, category);
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

    public boolean updateCategoria(int id, type category) {
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


}

