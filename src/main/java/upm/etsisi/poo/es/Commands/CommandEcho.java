package upm.etsisi.poo.es.Commands;

public class CommandEcho extends Command {
    public CommandEcho(String command) {
        super(command);
    }

    private void commandEcho(String command) {
        try {
            String[] parts = command.split("\"");
            System.out.println(parts[0].trim() + " \"" + parts[1].trim() + "\"");
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println(INCORRECT);
        }
    }
}
