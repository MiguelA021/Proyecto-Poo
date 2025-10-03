package upm.etsisi.poo.es;


public class Ticket {
  final static int MAX_PRODUCT = 100;
  Store store;
  Product[] productList;
  Product[] storeProducts;
  int amount;

  public Ticket() {
    this.productList = new Product[MAX_PRODUCT];
    this.storeProducts = store.getProducts();
    this.amount = 0;
  }

  /**
   * Method that add the product to the list just when the amount
   * is below 100.
   * @return boolean
   */
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

  public String prodList() {
    String list = "";
    for (int i = 0; i < MAX_PRODUCT; i++) {
      list += productList[i].toString() + "\n";
    }
    return list;
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

  /**
   * @return a new ticket, which has been reset
   */
  public Product[] ticketNew() {
    productList = new Product[MAX_PRODUCT];
    return productList;
  }

  /**
   * @param prodId is the iD from the product that we want to add to the ticket.
   * @param amount is the product amount
   *               This method adds the product amount to the ticket
   * @return a boolean if the product was found,and in the case 'true', the method
   *         set the
   *         ticket amount to new amount.
   */
  public boolean ticketAdd(int prodId, int amount) { // Agrega al ticket la cantidad del producto
    boolean found = false;
    for (int i = 0; i < MAX_PRODUCT; i++) {
      if (productList[i].getId() == prodId) {
        found = true;
      }
      Product product = productList[i];
      boolean added = true;
      for (int j = 0; j < amount && added; j++) {
      }
    }
    if(found){
      this.amount+= amount;
    }

    return found;
  }

  /**
   * @param prodId This is Id from the product that sending us to remove
   *               This method remove all occurrences of the product
   * @return it's a boolean that checks if the product is removed
   */
  public boolean ticketRemove(int prodId) { // (elimina todas las apariciones del producto, revisa si existe el id )
      boolean resul = false;
      int count = 0;
      boolean encontrado = false;
      int i = 0;
      while (productList[i].getId() != prodId){ // encuentra la primera aparición del obejeto que buscamos
          i++;
      }
      int first = i;
      while (productList[first].getId()== prodId){
          productList[first]= null;
          first++;
      }
    //  for ( int a = )
      return resul;
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
  /**
   * @return the ticket printed
   */
  public String ticketPrint() {
    StringBuilder sc = new StringBuilder();
    int I=0;
    int posBehindOfI=I-1;
    int amount=0;
    double totalPrice=0, totalDiscount=0;
    double finalPrice=0;
    while(productList[I]!=null || productList[posBehindOfI]!=null){
        if(productList[I]!=null){
            sc.append(productList[I].toString());
            totalPrice += productList[I].getPrice();
        }
        if(posBehindOfI>=0){
            if(productList[I]==productList[posBehindOfI]){
                amount++;
            }else{
                int amountReal=amount+1;
                totalDiscount += productList[posBehindOfI].getDiscounted(amountReal); //It's not necessary to check if amountReal<1 because the method can difference it
                finalPrice += productList[posBehindOfI].getDiscountedPrice(amountReal); //This method also can difference it.
                amount=0;
            }
        }
        I++;
        posBehindOfI++;
        sc.append("\n");
    }
    sc.append("Total price: ");
    sc.append(totalPrice);
    sc.append("\nTotal discount: ");
    sc.append(totalDiscount);
    sc.append("\nFinal price: ");
    sc.append(finalPrice);
    sc.append("\nticket print: ok");
    return sc.toString();
  }


}
