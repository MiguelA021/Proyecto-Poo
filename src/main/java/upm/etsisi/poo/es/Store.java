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

    public boolean updateAmount(int id, int amount) {
        boolean done = false;
        int contador = 0;
        Product product = null;
        for (int i = 0; i < MAX_PRODUCT; i++) {
            if (productList[i].getId() == id) {
                product = productList[i];
                contador++;
            }
        }
        if (contador < amount) {
            boolean equals = false; // boolean that sees if we reached the needed cuantity of profuct
            for (int i = contador; i < amount && !equals; i++) {
                boolean added = false;
                for (int j = 0; j < MAX_PRODUCT && !added; j++) {
                    if (productList[j] == null) {
                        productList[j] = product;
                    }
                }
                if (i == amount) {
                    equals = true;
                }
                done = equals;
            }
        } else {
            for (int i = contador; i > MAX_PRODUCT; i++) {
                boolean found = false;
                for (int j = MAX_PRODUCT; j > 0 && !found; j++) {
                    if (productList[j] == product) {
                        found = true;
                        productList[j] = null;
                    }
                }
            }
            done = true;
        }
        return done;
    }

}

