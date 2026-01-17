package upm.etsisi.poo.es.User;

import org.apache.commons.csv.CSVPrinter;

import java.io.IOException;
import java.util.ArrayList;

public class Customer extends User {
  protected final int cashierId;

  public Customer(String email, String name, String id, int cashierId) {

    this.email = email;
    this.name = name;
    this.cashierId = cashierId;
    this.id = id;
    tickets = new ArrayList<Integer>();
  }

  public String toString() {
    return "USER{identifier='" + id + "', name='" + name + "', email='" + email + "', cash=UW" + cashierId + "}";
  }

  /**
   * The method adds the ticket into the tickets associated with the customer
   * 
   * @param id     the id of the ticket
   */
  public void addTicket(Integer id) {
    tickets.add(id);
  }

    public void printCsv(CSVPrinter csvPrinter) throws IOException {
       csvPrinter.printRecord("Customer", email, name, cashierId, id);
       for(Integer tickid:tickets){
           csvPrinter.printRecord(id, tickid);
       }
    }
}
