package upm.etsisi.poo.es.User;
import upm.etsisi.poo.es.Ticket;

public interface ICustomer extends IUser{
    /**
     * The method adds the ticket into the tickets associated with the customer
     * @param id the id of the ticket
     * @param ticket the ticket given
     */
    void addTicket(Integer id, Ticket ticket);
}
