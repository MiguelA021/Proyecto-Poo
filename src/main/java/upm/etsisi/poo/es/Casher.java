package upm.etsisi.poo.es;
import java.util.Map;
import java.util.TreeMap;

public class Casher extends User {
    private TreeMap<Integer, Ticket> tickets;

    public Casher(String email, String name){
        this.email=email;
        this.name =name;
        tickets=new TreeMap<>();
    }

    /**
     * The method runs through the tree and gives back the pair [K,V] ordered by the key
     * @return The string returned is the list of tickets that belongs to the atm ordered by their id
     */
    public String listTickets(){
        StringBuilder str= new StringBuilder();
        str.append(toString());
        for(Map.Entry<Integer, Ticket> it : tickets.entrySet()){//saca para cada nodo del arbol (K,V) ordenado por la clave
            Integer id = it.getKey();
            Ticket ticket = it.getValue();
            str.append("Id del ticket: "+id+". Estado del ticket: "+ticket.getStatus()+"\n");
        }
        return str.toString();
    }
    public String toString(){
        return "Nombre del Cajero: "+ name +". Email: "+email+"\n";
    }
    /**
     * The method removes the ticket given by the id
     * @param id the id of the ticket
     * @return returns true if the ticket has been removed
     */
    public boolean removeTicket(int id){
        boolean resul=false;
        if(tickets.containsKey(id)){
            resul=true;
            tickets.remove(id);
        }
        return resul;
    }
}
