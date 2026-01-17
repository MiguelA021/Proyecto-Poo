package upm.etsisi.poo.es.Tickets;


import org.apache.commons.csv.CSVPrinter;
import upm.etsisi.poo.es.Product.BasicProduct;
import upm.etsisi.poo.es.Product.Product;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;

public abstract class Ticket {
    protected final Integer id;
    protected Status status;
    final static int MAX_PRODUCT = 100;
    Product[] productList;
    protected ArrayList<LocalDateTime> dates;
    private static DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("yy-MM-dd-HH:mm");
    int amount;

    public static final String ERROR_FULL = "ERROR: Full Ticket (100 products max)";
    protected static final String ERROR_PRODUCT_ID_NOT_FOUND = "ERROR: Product ID not found";
    protected static final String ADD_OK = "ticket add: ok";
    protected static final String MANY_PEOPLE = "Too many people";
    protected static final String PERIOD_NOT_VALID = "The period of time is not valid";
    protected static final String NO_PRODUCTS_IN_THE_TICKET = "ERROR: No products in the ticket";
    protected static final String PRODUCT_DOES_NOT_EXIST = "ERROR: this product does not exist.";
    protected static final String ERROR_TICKET_CLOSE = "ERROR: the ticket is closed. It can't be modified";
    protected static final String DONT_CLOSE_NOT_VALID_TIME = "The ticket can`t be closed because some event's period of time is invalid. \n";
    Comparator<Product> nameComp = Comparator.comparing(Product::getName);
    protected static final String TOTAL_PRICE = "Total price:";
    protected static final String TOTAL_DISCOUNT = "Total discount:";
    protected static final String FINAL_PRICE = "Final price:";
    protected static final String TICKET = "Ticket :";
    protected static final String TICKET_NEW_OK = "ticket new: ok";

    protected Ticket(Integer id) {
        this.id = id;
        this.amount = 0;
        this.status = Status.EMPTY;
        this.productList = new Product[MAX_PRODUCT];
        this.dates = new ArrayList<LocalDateTime>();
        LocalDateTime now = LocalDateTime.now();
        dates.add(now);
    }

    public Integer getId() {
        return id;
    }

    public Status getStatus() {
        return status;
    }

    public abstract boolean canBeClosed();

    public boolean close() {
        if (status == Status.CLOSE) {
            return true;
        }
        if (!canBeClosed()) {
            return false;
        }
        status = Status.CLOSE;
        return true;
    }

    /**
     * The method prints the ticket
     * @param close shows if the ticket is closed or not
     * @return returns the String
     */
    public String print(boolean close) {
        return "";
    }

    /**
     * The method returns a String with a specific format, if it's open it only shows the id.
     * If it's empty it shows only the creation date. If it's closed it shows the closing date
     * @return It returns the String
     */
    public String formatList() {
        StringBuilder resul = new StringBuilder();
        resul.append("  " + toStringId()).append(" - ").append(this.status.toString().toUpperCase());
        return resul.toString();
    }

    public String formatListCashList(){
        StringBuilder resul = new StringBuilder();
        resul.append("  " + toStringId()).append(" -> ").append(this.status.toString().toUpperCase());
        return resul.toString();
    }
    /**
     * The method makes a String with a specific format
     * @return the String with the format
     */
    public String toStringNew(boolean withId) {
        StringBuilder sc = new StringBuilder();
        if (withId) {
            sc.append(TICKET + " " + this.id + "\n");
        } else{
            sc.append(TICKET + " " +this.dates.get(0).format(DATE_FORMAT)+"-"+ this.id + "\n");
        }
        sc.append("  " + TOTAL_PRICE + " 0.0 \n");
        sc.append("  " + TOTAL_DISCOUNT + " 0.0 \n");
        sc.append("  " + FINAL_PRICE + " 0.0 \n");
        sc.append(TICKET_NEW_OK);
        return sc.toString();
    }

    /**
     * The method creates a String with a specific format
     * @return returns the String with the format
     */
    protected String toStringId() {
        StringBuilder resul = new StringBuilder();
        String status = this.status.toString().toUpperCase();
        switch (status) {
            case "EMPTY": {
                String inicio = dates.get(0).format(DATE_FORMAT);
                resul.append(inicio).append("-").append(this.id);
                break;
            }
            case "OPEN":
                resul.append(this.id);
                break;
            case "CLOSE": {
                String fin;

                if (dates.size() > 1) {
                    fin = dates.get(1).format(DATE_FORMAT);
                } else {
                    fin = dates.get(0).format(DATE_FORMAT);
                }

                resul.append(this.id).append("-").append(fin);
                break;
            }
            default:
                resul.append("ERROR, status is undefined");
                break;
        }
        return resul.toString();
    }


    public BasicProduct copy(BasicProduct b){
        return new BasicProduct(b.getId(), b.getName(),b.getCategory() , b.getPrice());
    }


    public void printCsv(CSVPrinter csvPrinter) throws Exception{

    }
}
