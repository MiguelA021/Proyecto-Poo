package upm.etsisi.poo.es;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

import javax.sql.CommonDataSource;

public class App {
  private final static String INCORRECT = "Incorrect Format, please try again.";
  private final static String NOTEXIST = "Product doesn't exist.";
  private final static String WELCOME_MESSAGE = "Welcome to the ticket module App.";
  private final static String HELP_MESSAGE = "Ticket module. Type 'help' to see commands.";
  private final static String FILE_ERROR = "Error while reading the file, please try again.";
  private final static String EMPTY_TICKET = "Empty ticket, try adding some products.";
  private final static String COMMAND_ERROR = "command not found, please try again.";
  public static final String UPM = "tUPM>";
  public static final String ID_REPEAT = "This ID is used, try to use another.";

  public static void main(String[] args) {
    App app = new App();
    app.init();
    app.start(args);
    app.end();

  }

  private void end() {
    System.out.println("Closing application");
    System.out.println("Goodbye");
  }

  public void start(String[] args) {
    if (args.length == 0) {
      userCommand();
    } else {
      readFile(args);
    }
  }

  /**
   * the method reads the commands given by the user
   */
  public void userCommand() {
    boolean end = false;
    Scanner scan = new Scanner(System.in);
    Store store = new Store();
    Ticket ticket = new Ticket(store);
    while (!end) {
      System.out.print(UPM);
      Command command = new Command(scan.nextLine());
      end = command.readCommand(store, ticket);
      System.out.println();
    }
    scan.close();
  }

  /**
   * the method reads the file which path is given by the args
   * 
   * @param args contains the path of the file we want to read
   */
  private void readFile(String[] args) {
    String command;
    Store store = new Store();
    Ticket ticket = new Ticket(store);
    try {
      BufferedReader reader = new BufferedReader(new FileReader(args[0]));
      boolean end = false;
      while (!end) {
        System.out.print(UPM);
        command = reader.readLine();
        if (command != null) {
          System.out.println(command);
          Command command2 = new Command(command);
          end = command2.readCommand(store, ticket);
        } else {// el fichero no tiene el comando exit, por tanto no termina la ejecucion del
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
