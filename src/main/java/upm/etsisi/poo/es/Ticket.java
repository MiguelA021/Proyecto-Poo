package upm.etsisi.poo.es;
//BORRAR ESTE COMENTARIO
import java.util.*;

public class Ticket {
  int amount;
  ArrayList<Product> productList;
  private final int MAX_PRODUCT = 100;

  public Ticket() {
    productList = new ArrayList<>();
    amount = 0;
  }
  public int getAmount(){
    return amount;
  }
  public void setAmount(int amount){
    this.amount = amount;
  }

  /**
   * @param id
   *                Method that add the product to the list just when the amount
   *                is below 100.
   * @return boolean
   */
  public boolean prodAdd(int id, String name, type category, double price) { // Metodo
    boolean done = true;
    if (amount < MAX_PRODUCT) {
      Product product = new Product(name, price, id, 1, category);
      if (this.productList.contains(product)) {
        int productPosition = productList.indexOf(product);
        int amount = productList.get(productPosition).getCuantity();
        if (amount < 200) {
          productList.get(productPosition).setCuantity(amount + 1);
        } else {
          done = false;
        }

      } else {
        productList.add(product);
        amount++;
      }
      System.out.println(product.toString());
    }
    return done;
  }

  public String prodList() {
    String list = "";
    Iterator<Product> it = productList.iterator();
    while (it.hasNext()) {
      Product product = it.next();
      for (int i = 0; i < product.getCuantity(); i++) {
        list += product.toString() + "\n";
      }
    }
    return list;
  }

  public boolean prodRemove(int id) {
    Iterator<Product> it = productList.iterator();
    boolean removed = false;
    boolean found = false;
    while (it.hasNext() && !found) {
      Product product = it.next();
      if (product.getId() == id) {
        found = true;
      }
      int amount = product.getCuantity();
      product.setCuantity(amount - 1);
      if (amount - 1 == 0) {
        productList.remove(id);
      } else
        productList.set(id, product);
      removed = true;
    }
    return removed;
  }

  /**
   * @return a new ticket, which has been reset
   */
  public ArrayList<Product> ticketNew(){
     productList = new ArrayList<>();
     return productList;
  }

  /**
   * @param prodId is the iD from the product that we want to add to the ticket.
   * @param amount is the product amount
   * This method adds the product amount to the ticket
   * @return a boolean if the product was found,and in the case 'true', the method set the
   * ticket amount to new amount.
   */
  public boolean ticketAdd(int prodId, int amount){ //Agrega al ticket la cantidad del producto
    Iterator<Product> iterator = productList.iterator();
    boolean found = false;
    while(iterator.hasNext() && !found){
      if(iterator.next().getId() == prodId){
        found=true;
      }
    }
    if(found){
      int amountTicket = getAmount();
      setAmount(amount+amountTicket);
    }
    return found;
  }

  /**
   * @param prodId This is Id from the product that sending us to remove
   *               This method remove all occurrences of the product
   * @return it's a boolean that checks if the product is removed
   */
  public boolean ticketRemove(int prodId){ //(elimina todas las apariciones del producto, revisa si existe el id )
    boolean removed = false;
    Iterator<Product> iterator = productList.iterator();
    while(iterator.hasNext() && !removed){
      Product product = iterator.next();
      if(product.getId() == prodId){
        productList.remove(product);
        removed=true;
      }
    }
    return removed;
  }

  /**
   * @return the ticket printed
   */
  public String ticketPrint(){
      StringBuilder sc = new StringBuilder();
      sc.append("\nUPM STORE\n");
      sc.append(String.format("%-10s %-50s%n", "Amount", "Product"));
      Iterator<Product> iterator = productList.iterator();
      while(iterator.hasNext()){
        Product product = iterator.next();
        sc.append(String.format("%-10d %-50s%n", product.getCuantity(), product.toString()));
      }
      return sc.toString();
  }

  public boolean updateCategoria(int id, type category) {
    boolean done = false;
    Iterator<Product> it = productList.iterator();
    while (it.hasNext() && !done) {
      Product product = it.next();
      if (product.getId() == id) {
        product.SetCategory(category);
        productList.set(id, product);
        done = true;
      }
    }
    return done;
  }

  public boolean updateName(int id, String name) {
    boolean done = false;
    Iterator<Product> it = productList.iterator();
    while (it.hasNext() && !done) {
      Product product = it.next();
      if (product.getId() == id) {
        product.setName(name);
        productList.set(id, product);
        done = true;
      }
    }
    return done;
  }

  public boolean updateAmount(int id, int amount) {
    boolean done = false;
    Iterator<Product> it = productList.iterator();
    while (it.hasNext() && !done) {
      Product product = it.next();
      if (product.getId() == id) {
        product.setCuantity(amount);
        productList.set(id, product);
        done = true;
      }
    }
    return done;
  }
}
