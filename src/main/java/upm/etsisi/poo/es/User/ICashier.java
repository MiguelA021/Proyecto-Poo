package upm.etsisi.poo.es.User;

import upm.etsisi.poo.es.Ticket;

public interface ICashier extends IUser{
    String UPM_WORKER = "UW";
    String ID_ERROR = "The id given has been already used";
    String ID_NOT_FOUND = "The id given, was not found ";

    /**
     * The method adds the ticket to the cashier as a pair [K, V] where the id is the key
     * @param id the id given,if null the method generates it automatically
     * @return it returns the id given (or generated randomly)
     */
    int addTicket(Integer id);
    /**
     * The method runs through the tree and gives back the pair [K,V] ordered by the
     * key
     *
     * @return The string returned is the list of tickets that belongs to the atm
     *         ordered by their id
     */
    String listTickets();
    /**.
     * The method returns the ticket given by id
     *
     * @param id the id of the ticket
     * @return the ticket (if it has been found)
     */
    Ticket getTicketById(int id);
    /**
     * The method removes the ticket given by the id
     *
     * @param id the id of the ticket
     * @return returns true if the ticket has been removed
     */
    boolean removeTicket(int id);
}
