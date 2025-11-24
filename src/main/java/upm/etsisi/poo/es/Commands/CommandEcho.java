package upm.etsisi.poo.es.Commands;

import upm.etsisi.poo.es.Store;
import upm.etsisi.poo.es.Ticket;

public class CommandEcho implements Command {

    @Override
    public String getName() {
        return "echo";
    }

    @Override
    public String getDescription() {
        return "echo \"<text>\"  - prints the text";
    }

    @Override
    public boolean execute(String fullLine, String[] args, Store store) {
        try {
            String[] parts = fullLine.split("\"");
            System.out.println(parts[0].trim() + " \"" + parts[1].trim() + "\"");
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println(INCORRECT);
        }
        return false; // no termina la app
    }
}
