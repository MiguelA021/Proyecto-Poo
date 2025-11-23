package upm.etsisi.poo.es.Commands;

import upm.etsisi.poo.es.Store;
import upm.etsisi.poo.es.Ticket;

public interface Command {

    String INCORRECT    = "Incorrect Format, please try again.";
    String NOTEXIST     = "Product doesn't exist.";
    String EMPTY_TICKET = "Empty ticket, try adding some products.";
    String ID_REPEAT    = "This ID is used, try to use another.";

    /** Nombre del comando (primer token). Ej: "echo", "help", "prod", "ticket" */
    String getName();

    /** Descripción para el help. */
    String getDescription();

    /**
     * Ejecuta el comando.
     * @param fullLine línea completa que ha escrito el usuario
     * @param args     tokens separados por espacios
     * @param store    modelo Store
     * @param ticket   modelo Ticket
     * @return true si el comando quiere terminar la aplicación (exit), false en otro caso
     */
    boolean execute(String fullLine, String[] args, Store store, Ticket ticket);
}
