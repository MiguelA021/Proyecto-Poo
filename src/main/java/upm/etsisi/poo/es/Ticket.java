package upm.etsisi.poo.es;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.Comparator;

enum Status {
  EMPTY, ACTIVE, CLOSED;
}

public class Ticket {
    final static int MAX_PRODUCT = 100;
    public static final String ERROR_FULL = "ERROR: Full Ticket (100 products max)";
    Product[] productList;
  int amount;
  private StringBuilder id;
  Comparator<Product> nameComp = Comparator.comparing(Product::getName);
  private Status status;

  public Ticket(int id) { // Aaron lo ha implementado con un int id en vez de una Store, ver cual es mejor
    this.productList = new Product[MAX_PRODUCT];
    LocalTime now = LocalTime.now();
    this.id = new StringBuilder(now.toString()).append(String.format("%05d", id));
    this.amount = 0;
    this.status = Status.EMPTY;
  }

  public boolean ticketAdd(int proId, Store store, int amount) {
    boolean resul = false;

    if (this.status != Status.CLOSED) {
      Product productoEncontrado = store.getProduct(proId);
      int before = this.amount;
      if (productoEncontrado == null) {
        System.out.println("ERROR: Product ID not found " + proId);

      }else if (productoEncontrado.foodOrMetting()){
          if(amount >1 || inTicket(productoEncontrado) ){
              System.out.println("ERROR: food or metting can't be duplicated.");
          }else {
              if (this.amount == 0) {
                  this.status = Status.ACTIVE;
              }
              if (this.amount < MAX_PRODUCT) {
                  productList[this.amount] = productoEncontrado;
                  this.amount++;
                  System.out.println(ticketPrint(false));
                  resul = true;
              } else {
                  System.out.println(ERROR_FULL);
              }

          }


      }
      else {
        if (this.amount == 0) {
          this.status = Status.ACTIVE;
        }
        int i = 0;
        while (i < amount && this.amount < MAX_PRODUCT) {
          productList[this.amount] = productoEncontrado;
          this.amount++;
          i++;
        }
        System.out.println(ticketPrint(false));
        if ((this.amount - before) == amount) {
          resul = true;
          System.out.println("ticket add: ok");
        } else {
          System.out.println(ERROR_FULL);
        }
      }
    } else {
      System.out.println("ERROR: the ticket is closed. It can't be modified");
    }
    return resul;
  }

    private boolean inTicket(Product productoEncontrado) {
      boolean found = false;
      int i =0;
      while (i <amount && !found){
          found = productList[i].getId() == productoEncontrado.getId();
      }
      return found;
    }

    public boolean ticketAddP(int proId, Store store, int amount, String [] customs) {
        boolean resul = false;

        if (this.status != Status.CLOSED) {
            Product productoEncontrado = store.getProduct(proId);
            int before = this.amount;
            if (productoEncontrado == null) {
                System.out.println("ERROR: Product ID not found " + proId);

            } else if (productoEncontrado.foodOrMetting()) {
                System.out.println("ERROR: Only products is customizable");
            }
            else {
                if(addCustom(customs,productoEncontrado)){
                    if (this.amount == 0) {
                        this.status = Status.ACTIVE;
                    }
                    int i = 0;
                    while (i < amount && this.amount < MAX_PRODUCT) {
                        productList[this.amount] = productoEncontrado;
                        this.amount++;
                        i++;
                    }

                    System.out.println(ticketPrint(false));
                    if ((this.amount - before) == amount) {
                        resul = true;
                        System.out.println("ticket add: ok");
                    } else {
                        System.out.println("ERROR: Full Ticket (100 products max)");
                    }
                }
                else{
                    System.out.println("ERROR: this product can't have this number of personalizations.");
                }
            }
        } else {
            System.out.println("ERROR: the ticket is closed. It can't be modified");
        }
        return resul;

  }
  private boolean addCustom(String [] personalizations, Product product){
      int i = 0;
      boolean maxPers = false;
      while ( i < personalizations.length && !maxPers){
          String custom = personalizations[i].substring(3);
          maxPers = product.addCustom(custom);
          i++;
      }
      return maxPers;
  }

  /**
   * @param prodId This is Id from the product that sending us to remove
   *               This method remove all occurrences of the product
   * @return it's a boolean that checks if the product is removed
   */
  public Product ticketRemove(int prodId) {
    Product product = null;
    int iterations = this.amount;
    if (this.status != Status.CLOSED) {
      boolean removed = false;
      if (this.amount == 0) {
        System.out.println("ERROR: No products in the ticket");

      } else {
        for (int i = 0; i < iterations; i++) {
          if (productList[i] != null && productList[i].getId() == prodId) {
            if (this.amount == 1) {
              productList[0] = null;
              this.amount--;
            } else {
              product = productList[i];
              productList[i] = productList[amount - 1];
              productList[amount - 1] = null;
              this.amount--;
              i--;
            }
          }
        }

        boolean comprobation = true;
        int i = 0;
        while (comprobation && i < this.amount) {
          if (productList[i] != null) {
            if (productList[i].getId() == prodId) {
              comprobation = false;
            }
          }
        }
        removed = comprobation;
        sort();
        if (iterations == this.amount) {
          System.out.println("ERROR: this product does not exist.");
        }
      }
    } else {
      System.out.println("ERROR: the ticket is closed. It can't be modified");
    }
    return product;

  }

  /**
   * @return the ticket printed
   */
  public String ticketPrint(boolean close) {
    StringBuilder sc = new StringBuilder();
    if (close) {
      LocalDateTime now = LocalDateTime.now();
      id.append(String.format(now.toString()));
      this.status = Status.CLOSED;
    }
    System.out.println(id.toString());
    if (this.amount > 0 && this.productList[0] != null) {
      sort();
      int n = this.amount;
      int[] categoryCount = new int[type.values().length];
      for (int i = 0; i < n; i++) {
        Product p = productList[i];
        if (p != null) {
          categoryCount[p.getCategory().ordinal()]++;
        }
      }

      double totalPrice = 0.0;
      double totalDiscount = 0.0;

      for (int i = 0; i < n; i++) {
        Product p = productList[i];

        if (p != null) {
          double price = p.getPrice();
          double discountValue = 0.0;

          if (categoryCount[p.getCategory().ordinal()] >= 2) {
            discountValue = price - p.getDiscountedPrice();
          }

          totalPrice += price;
          totalDiscount += discountValue;

          if (discountValue > 0.0) {
            sc.append(String.format(
                "{class:Product, id: %d, name: '%s', category: %s, price: %.2f} **discount -%.2f", p.getId(),
                p.getName(), p.getCategory(), price, discountValue));
          } else {
            sc.append(String.format(
                "{class:Product, id: %d, name: '%s', category: %s, price: %.2f}", p.getId(), p.getName(),
                p.getCategory(), price));
          }
          sc.append("\n");
        }
      }

      double finalPrice = totalPrice - totalDiscount;
      sc.append("Total price: ").append(String.format("%.2f", totalPrice));
      sc.append("\nTotal discount: ").append(String.format("%.2f", totalDiscount));
      sc.append("\nFinal price: ").append(String.format("%.2f", finalPrice));
    }

    return sc.toString();
  }

  public Status setStatus(String status) {
    return this.status = Status.valueOf(status);
  }

  /**
   * The method sorts the names alphabetically
   */
  public void sort() {
    Arrays.sort(productList, 0, amount, nameComp);
  }

  public String getStatus() {
    String str;
    switch (this.status) {
      case EMPTY:
        str = "Empty";
        break;
      case ACTIVE:
        str = "Active";
      case CLOSED:
        str = "Closed";
        break;
      default:
        str = "Error, status is undefined";
        break;
    }
    return str;
  }

}
