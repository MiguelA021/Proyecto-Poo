package upm.etsisi.poo.es;

public class Customer extends User {
    private Casher casher;

    public Customer (String email, String name, int id, Casher casher){
        this.email=email;
        this.name =name;
        this.casher = casher;
        this.id=id;
    }
    public String toString(){
        return "Name of the customer: "+ name +". Email: "+email+"Id: "+id+" Registered by the casher: {"+ casher.toString()+"}";
    }



}
