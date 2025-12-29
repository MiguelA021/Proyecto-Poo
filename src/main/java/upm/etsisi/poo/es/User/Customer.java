package upm.etsisi.poo.es.User;
import upm.etsisi.poo.es.CustomerTicket;

import java.util.TreeMap;

public class Customer extends User {
    private final int cashierId;

    public Customer (String email, String name, String id, int cashierId){
        this.email=email;
        this.name =name;
        this.cashierId = cashierId;
        this.id=id;
        tickets=new TreeMap<>();
    }
    public String toString(){
        return "Client{identifier='"+id+"', name='"+name+"', email='"+email+"', cash=UW"+cashierId+"}";
    }

    /**
     * The method adds the ticket into the tickets associated with the customer
     * @param id the id of the ticket
     * @param customerTicket the ticket given
     */
    public void addTicket(Integer id, CustomerTicket customerTicket) {
        tickets.put(id, customerTicket);
    }

}
