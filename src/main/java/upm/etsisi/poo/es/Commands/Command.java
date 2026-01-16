package upm.etsisi.poo.es.Commands;

public interface Command {

    String INCORRECT = "Incorrect Format, please try again.";
    String NOTEXIST = "Product doesn't exist.";
    String EMPTY_TICKET = "Empty ticket, try adding some products.";
    String ID_REPEAT = "This ID is used, try to use another.";

    /**
     * command's forename (first token). Ej: "echo", "help", "prod", "ticket"
     */
    String getName();

    /**
     * Executes the command
     * @param fullLine the whole sentence written by the user
     * @param args     tokens detached by blanks
     * @return returns true if the command executed is exit, else returns false
     */
    boolean execute(String fullLine, String[] args);
}
