package upm.etsisi.poo.es;

public class Customer extends User {
    private Casher casher;

    public Customer (String email, String name, Casher casher){
        this.email=email;
        this.name =name;
        this.casher = casher;
    }
    public String toString(){
        return "Nombre del cliente: "+ name +". Email: "+email+" Dado de alta en el cajero: {"+ casher.toString()+"}\n";
    }



}
