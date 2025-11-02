package upm.etsisi.poo.es;

public class Customer extends User {
    private ATM atm;

    public Customer (String email, String nombre,ATM atm){
        this.email=email;
        this.nombre=nombre;
        this.atm=atm;
    }
    public String toString(){
        return "Nombre del cliente"+nombre+". Email: "+email+" Dado de alta en el cajero{"+atm.toString()+"}\n";
    }


}
