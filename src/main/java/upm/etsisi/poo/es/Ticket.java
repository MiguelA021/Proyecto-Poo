package upm.etsisi.poo.es;

import upm.etsisi.poo.es.Product.BasicProduct;
import upm.etsisi.poo.es.Product.Event;
import upm.etsisi.poo.es.Product.PersonalizedProduct;
import upm.etsisi.poo.es.Product.Product;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Locale;

enum Status {
  EMPTY, OPEN, CLOSED
}

public class Ticket {
  final static int MAX_PRODUCT = 100;
  public static final String ERROR_FULL = "ERROR: Full Ticket (100 products max)";
    private static final String ERROR_PRODUCT_ID_NOT_FOUND = "ERROR: Product ID not found";
    private static final String ADD_OK = "ticket add: ok";
    private static final String MANY_PEOPLE = "Too many people";
    private static final String PERIOD_NOT_VALID = "The period of time is not valid";
    private static final String NO_PRODUCTS_IN_THE_TICKET = "ERROR: No products in the ticket";
    private static final String PRODUCT_DOES_NOT_EXIST = "ERROR: this product does not exist.";
    private static final String ERROR_TICKET_CLOSE = "ERROR: the ticket is closed. It can't be modified";
    private static final String ERROR_AMOUNT_PRODUCT = "ERROR: The amount for this product can't be zero";
    private static final String DONT_CLOSE_NOT_VALID_TIME = "The ticket can`t be closed because some event's period of time is invalid. \n";
    private static final String TOTAL_PRICE = "Total price:";
    private static final String TOTAL_DISCOUNT = "Total discount:";
    private static final String FINAL_PRICE = "Final price:";
    private static final String TICKET = "Ticket :";
    private static final String TICKET_NEW_OK = "ticket new: ok";
    Product[] productList;
  int amount;
  private ArrayList<LocalDateTime> dates;
  private int tickId;
  Comparator<Product> nameComp = Comparator.comparing(Product::getName);
  private Status status;
  private static DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("yy-MM-dd-HH:mm");

  public Ticket(Integer id) {
    this.productList = new Product[MAX_PRODUCT];
    LocalDateTime now = LocalDateTime.now();
    this.dates = new ArrayList<LocalDateTime>();
    dates.add(now);
    if (id != null) {
      this.tickId = id;
    }
    this.amount = 0;
    this.status = Status.EMPTY;
  }

  public int getId() {
    return this.tickId;
  }

  public Status getStatus(){
    return status;
  }

  public ArrayList<LocalDateTime> getDates(){
      return dates;
  }

    /**
   * The method adds the product given, and it also prints it. The ticket status
   * must be
   * OPEN or EMPTY. The ticket must have less than 100 products, if not, the
   * product
   * won't be added.
   * 
   * @param product The product given, it cannot be null (if it is, the method
   *                won't add it).
   *                Also, if the product given is a Meeting or a Food, then it
   *                cannot be an
   *                invalid date.
   * @param amount  The amount when the product is a Food or Meeting, it shows the
   *                amount of
   *                people that are expected on that Food/Meeting. If not, it
   *                shows how much times
   *                are we going to add the product into the ticket.
   * @return It returns true if the product has been added successfully.
   */
  public boolean ticketAdd(Product product, int amount) {
    boolean resul = true;
    if (this.status != Status.CLOSED) {
      int before = this.amount;
      if (product == null) {
        resul = false;
        System.out.println(ERROR_PRODUCT_ID_NOT_FOUND);

      }else if(amount==0){
        resul = false;
        System.out.println(ERROR_AMOUNT_PRODUCT);
      }else {
        if (this.amount == 0) {
          this.status = Status.OPEN;
        }

        if (product instanceof Event) {
          Event event = (Event) product;
          if (event.fechaValida(LocalDateTime.now())) {
            if (amount <= event.getMaxPeopleLocal()){
              double price = event.getPrice() * amount;
              event.setPrice(price);
              if(event.actualPeopleCorrect(amount)){
                event.setActualPeople(amount);
              }
              if(this.amount<MAX_PRODUCT){
                productList[this.amount] = event;
                this.amount++;

                System.out.println(ticketPrint(false));
                System.out.println(ADD_OK);
              }else{
                resul = false;
                System.out.println(ERROR_FULL);
              }
            } else {
              System.out.println(MANY_PEOPLE);
              resul = false;
            }
          } else {
            resul = false;
            System.out.println(PERIOD_NOT_VALID);
          }

        } else {
          int i = 0;
          while (this.amount < MAX_PRODUCT && i < amount) {
            productList[this.amount] = product;
            this.amount++;
            i++;
          }
          System.out.println(ticketPrint(false));
          if ((this.amount - before) == amount) {
            resul = true;
            System.out.println(ADD_OK);
          } else {
            resul = false;
            System.out.println(ERROR_FULL);
          }

        }

      }
    } else {
      resul = false;
      System.out.println("ERROR: the ticket is closed. It can't be modified");
    }
    return resul;
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
      if (this.amount == 0) {
        System.out.println(NO_PRODUCTS_IN_THE_TICKET);

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
          i++;
        }
        if (iterations == this.amount) {
          System.out.println(PRODUCT_DOES_NOT_EXIST);
        }
      }
    } else {
      System.out.println(ERROR_TICKET_CLOSE);
    }
    return product;

  }

  /**
   * @return the ticket printed
   */
  private boolean comprobarFechasTodosEventos(LocalDateTime now) {
    int i = 0;
    boolean valido = true;

    while (valido && i < this.amount) {
      if ((productList[i] != null) && (productList[i] instanceof Event)
          && !(((Event) productList[i]).fechaValida(now))) {
        valido = false;
      }
      i++;
    }
    return valido;
  }

  /**
   * The method closes the ticket (if the Events are on date) and turns it into a
   * String.
   * 
   * @param close it shows if the ticket has been already closed
   * @return the String of the ticket
   */
  public String ticketPrint(boolean close) {
    StringBuilder sc = new StringBuilder();

    if (close) {
      LocalDateTime now = LocalDateTime.now();
      dates.add(now);
      boolean validClose = comprobarFechasTodosEventos(now);
      if (validClose) {
        this.status = Status.CLOSED;
      } else
        System.out.println(DONT_CLOSE_NOT_VALID_TIME);
    }
    sc.append(TICKET + " ").append(toStringId()).append("\n");
    if (this.amount > 0 && this.productList[0] != null) {
      sort();
      int n = this.amount;
      int[] categoryCount = new int[type.values().length];
      for (int i = 0; i < n; i++) {
        Product p = productList[i];
        if (p != null) {
          if (p instanceof BasicProduct) {
            BasicProduct pr = (BasicProduct) p;
            categoryCount[pr.getCategory().ordinal()]++;
          }
        }
      }

      double totalPrice = 0.0;
      double totalDiscount = 0.0;

      for (int i = 0; i < n; i++) {
        Product p = productList[i];
        if (p != null) {
          double price = p.getPrice();
          sc.append("  ");
          if (p instanceof PersonalizedProduct) {
            PersonalizedProduct product = (PersonalizedProduct) p;
            double discountValue = 0.0;
            if (categoryCount[product.getCategory().ordinal()] >= 2) {
              discountValue = price - product.getDiscountedPrice();
            }

            totalPrice += price;
            totalDiscount += discountValue;

            if (discountValue > 0.0) {
              sc.append(product.toStringDiscount(discountValue));
            } else {
              sc.append(product);
            }

          } else if (p instanceof BasicProduct) {
            BasicProduct product = (BasicProduct) p;

            double discountValue = 0.0;
            if (categoryCount[product.getCategory().ordinal()] >= 2) {
              discountValue = price - product.getDiscountedPrice();
            }

            totalPrice += price;
            totalDiscount += discountValue;

            if (discountValue > 0.0) {
              sc.append(product.toStringDiscount(discountValue));
            } else {
              sc.append(product.toString());
            }

          } else if (p instanceof Event) {
            // Meeting / Food (u otros eventos): sin descuento por categoría
            Event event = (Event) p;
            //**
            totalPrice += price;
            // totalDiscount NO cambia (no hay descuento por categoría)

            sc.append(event.toStringTicketAdd());
          }
        }
      }

      double finalPrice = totalPrice - totalDiscount;
      sc.append("  " + TOTAL_PRICE + " ").append(String.format(Locale.US, "%.3f", totalPrice));
      sc.append("\n  " + TOTAL_DISCOUNT + " ").append(String.format(Locale.US, "%.3f", totalDiscount));
      sc.append("\n  " + FINAL_PRICE + " ").append(String.format(Locale.US, "%.3f", finalPrice));
    }

    return sc.toString();
  }

  /**
   * The method sorts the names alphabetically
   */
  public void sort() {
    Arrays.sort(this.productList, 0, this.amount, nameComp);
  }

  public String formatList() {// si esta abierto mostramos solo id. Si esta vacio mostramos fecha de creacion.
    // Si esta cerrado fecha de cierre
    StringBuilder resul = new StringBuilder();

    resul.append("  " + toStringId()).append("->").append(this.status.toString().toUpperCase());
    return resul.toString();
  }

  private String toStringId() {
    StringBuilder resul = new StringBuilder();
    String status = this.status.toString().toUpperCase();
    switch (status) {
      case "EMPTY":
        String inicio = dates.get(0).format(DATE_FORMAT);
        resul.append(inicio).append("-").append(tickId);
        break;
      case "OPEN":
        resul.append(tickId);
        break;
      case "CLOSED":
        String fin = dates.get(1).format(DATE_FORMAT);
        resul.append(tickId).append("-").append(fin);
        break;
      default:
        resul.append("ERROR, status is undefined");
        break;
    }
    return resul.toString();
  }

  /**
   * The method turns the ticket into a String with the new format
   * 
   * @return the ticket turned into a String
   */
  public String toStringNew() {
    StringBuilder sc = new StringBuilder(); // Soy Aaron, lo de format() esta puesto para que siga el formato que
    // buscamos de fecha.
    // te lo pongo para que asi no te comas la cabeza con eso. Por lo demás ya te
    // dejo que sigas con ello
    sc.append(TICKET + " " + tickId + "\n");
    sc.append("  " + TOTAL_PRICE + " 0.0 \n");
    sc.append("  " + TOTAL_DISCOUNT + " 0.0 \n");
    sc.append("  " + FINAL_PRICE + " 0.0 \n");
    sc.append(TICKET_NEW_OK);
    return sc.toString();
  }

}
