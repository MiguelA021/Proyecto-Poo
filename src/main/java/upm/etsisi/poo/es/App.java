package upm.etsisi.poo.es;

import upm.etsisi.poo.es.Commands.CommandController;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class App {
    private final static String WELCOME_MESSAGE = "Welcome to the ticket module App.";
    private final static String HELP_MESSAGE = "Ticket module. Type 'help' to see commands.";
    private final static String FILE_ERROR = "Error while reading the file, please try again.";
    public static final String UPM = "tUPM> ";

    public static void main(String[] args) {
        App app = new App();
        app.init();
        app.start(args);
        app.end();
    }

    private void end() {
        System.out.println("Closing application");
        System.out.println("Goodbye!");
    }

    public void start(String[] args) {
        if (args.length == 0) {
            userCommand();
        } else {
            readFile(args);
        }
    }

    /**
     * Modo interactivo por consola
     */
    public void userCommand() {
        boolean end = false;
        Scanner scan = new Scanner(System.in);
        Store store = new Store();
        CommandController controller = new CommandController(store);

        while (!end) {
            System.out.print(UPM);
            String line = scan.nextLine();
            end = controller.handle(line);
            if (!end) {
                System.out.println();
            }
        }
        scan.close();
    }

    /**
     * Modo lectura de fichero
     */
    private void readFile(String[] args) {
        String line;
        Store store = new Store();
        CommandController controller = new CommandController(store);

        try {
            BufferedReader reader = new BufferedReader(new FileReader(args[0]));
            boolean end = false;
            while (!end) {
                System.out.print(UPM);
                line = reader.readLine();
                if (line != null) {
                    System.out.println(line);
                    end = controller.handle(line);
                } else {
                    // el fichero no tiene exit → pasamos a modo interactivo
                    end = true;
                    userCommand();
                }
                System.out.println();
            }
            reader.close();
        } catch (IOException e) {
            System.out.println(FILE_ERROR);
        }
    }

    /**
     * It initializes the App
     */
    private void init() {
        System.out.println(WELCOME_MESSAGE);
        System.out.println(HELP_MESSAGE);
    }
}
