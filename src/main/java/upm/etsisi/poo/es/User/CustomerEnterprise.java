package upm.etsisi.poo.es.User;

public class CustomerEnterprise extends Customer{
    public CustomerEnterprise(String email, String name, String id, int cashierId) {
        super(email, name, id, cashierId);
    }

    @Override
    public String toString(){
        return "COMPANY{identifier='" + id + "', name='" + name + "', email='" + email + "', cash=UW" + cashierId + "}";
    }
}
