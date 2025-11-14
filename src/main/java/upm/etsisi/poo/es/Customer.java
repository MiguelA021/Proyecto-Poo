package upm.etsisi.poo.es;

public class Customer extends User {
    private Casher casher;

    public Customer (String email, String nombre, Casher casher){
        this.email=email;
        this.nombre=nombre;
        this.casher = casher;
    }
    public String toString(){
        return "Nombre del cliente: "+nombre+". Email: "+email+" Dado de alta en el cajero: {"+ casher.toString()+"}\n";
    }



}
