package upm.etsisi.poo.es.User;
import upm.etsisi.poo.es.Ticket;

import java.util.TreeMap;

public class Customer extends User {
    private final int cashierId;
    private final boolean isEnterprise;

    public Customer (String email, String name, String id, int cashierId, boolean isEnterprise){
        this.email=email;
        this.name =name;
        this.cashierId = cashierId;
        this.id=id;
        this.isEnterprise=isEnterprise;
        tickets=new TreeMap<>();
    }
    public String toString(){
        return "Client{identifier='"+id+"', name='"+name+"', email='"+email+"', cash=UW"+cashierId+"}";
    }

    /**
     * The method adds the ticket into the tickets associated with the customer
     * @param id the id of the ticket
     * @param ticket the ticket given
     */
    public void addTicket(Integer id, Ticket ticket) {
        tickets.put(id,ticket);
    }

    /**
     * The method checks if the user is an enterprise
     * @return returns true if the user is an enterprise
     */
    public boolean isEnterprise() {
        return isEnterprise;
    }
}
