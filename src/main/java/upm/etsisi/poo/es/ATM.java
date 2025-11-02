package upm.etsisi.poo.es;
import java.util.HashMap;

public class ATM extends User {
    private HashMap<Integer, Ticket> tickets;//Ver si cambiar a arboles B

    public ATM (String email, String nombre){
        this.email=email;
        this.nombre=nombre;
    }
    public void listTickets(int id){

    }
    public String toString(){
        return "Nombre del Cajero: "+nombre+". Email: "+email+"\n";
    }
    public void createNewTicket(){

    }


}
