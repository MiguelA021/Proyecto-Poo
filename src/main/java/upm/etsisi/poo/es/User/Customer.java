package upm.etsisi.poo.es.User;
import upm.etsisi.poo.es.Ticket;

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
        return "Client{identifier='"+id+"', name='"+name+"', email='"+email+"', cash=UW"+cashierId+"}\n";
    }

    public void addTicket(Integer id, Ticket ticket) {
        tickets.put(id,ticket);
    }
}
