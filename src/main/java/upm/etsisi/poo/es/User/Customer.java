package upm.etsisi.poo.es.User;
import upm.etsisi.poo.es.Ticket;

import java.util.TreeMap;

public class Customer extends User {
    private Cashier cashier;

    public Customer (String email, String name, int id, Cashier cashier){
        this.email=email;
        this.name =name;
        this.cashier = cashier;
        this.id=id;
        tickets=new TreeMap<>();
    }
    public String toString(){
        return "Name of the customer: "+ name +". Email: "+email+"Id: "+id+" Registered by the casher: {"+ cashier.toString()+"}";
    }

    public void addTicket(Integer id, Ticket ticket) {
        tickets.put(id,ticket);
    }
}
