package upm.etsisi.poo.es.User;

import java.util.ArrayList;

public class CustomerEnterprise extends Customer {
    private ArrayList<Integer> tickets;
    private final int cashierId;

    public CustomerEnterprise(String email, String name, String id, int cashierId) {
        super(email, name, id, cashierId);
        this.email = email;
        this.name = name;
        this.cashierId = cashierId;
        this.id = id;
        this.tickets = new ArrayList<Integer>();
    }

    @Override
    public String toString() {
        return "COMPANY{identifier='" + id + "', name='" + name + "', email='" + email + "', cash=UW" + cashierId + "}";
    }
    @Override
    public void addTicket(Integer id) {this.tickets.add(id);}

}
