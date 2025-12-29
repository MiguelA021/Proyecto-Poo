package upm.etsisi.poo.es.Commands;

import upm.etsisi.poo.es.Store;

public interface Command {

    String INCORRECT = "Incorrect Format, please try again.";
    String NOTEXIST = "Product doesn't exist.";
    String EMPTY_TICKET = "Empty ticket, try adding some products.";
    String ID_REPEAT = "This ID is used, try to use another.";

    /**
     * Nombre del comando (primer token). Ej: "echo", "help", "prod", "ticket"
     */
    String getName();

    /**
     * Ejecuta el comando.
     *
     * @param fullLine línea completa que ha escrito el usuario
     * @param args     tokens separados por espacios
     * @return true si el comando quiere terminar la aplicación (exit), false en
     * otro caso
     */
    boolean execute(String fullLine, String[] args);
}
