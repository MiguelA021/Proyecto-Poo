package upm.etsisi.poo.es;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class App {// REVISAR: Se deberia hacer una parte static para inicializar los mensajes de errores
    final static String FILE_ERROR = "Error while reading the file, please try again";
    final static String FORMAT_ERROR = "Error with the format, please try again";
    final static String DATA_ERROR  = "Error with the data, please introduce the data which is within restrictions";

    public static void main(String[] args) {
        App app = new App();
        app.init();
        app.start(args);
        app.end();
    }

    private void end() {
    }

    private void start(String[] args) {
        if (args.length==0) {
            readCommand();
        } else {
            readFile(args);
        }
    }

    /**
     * The method reads the commands through a file, given by the args.
     * @param args the array holds the path of the file
     */
    private void readFile(String[] args){
        String[] command;
        Ticket ticket = new Ticket();
        Store store = new Store();
        try {
            BufferedReader reader = new BufferedReader(new FileReader(args[0]));
            boolean end=false;
            while(!end){
                String line= reader.readLine();
                if (line!=null){
                    command=line.split(" ");
                    end=readCommandAux(command, store, ticket, line);
                } else {
                    end=true;
                    readCommand();
                }
            }
            reader.close();
        } catch (IOException e){
            System.out.println(FILE_ERROR);
        }
    }

    /**
     * The method reads the commands given by the terminal
     */
    private void readCommand(){
        boolean end = false;
        Scanner scan = new Scanner(System.in);
        Ticket ticket = new Ticket();
        Store store = new Store();
        while(!end){
            String command = scan.nextLine();
            String [] commandArray = command.split(" ");
            end=readCommandAux(commandArray,store,ticket,command);
        }
    }

    /**
     * The method executes the methods of each command, given by command and commandArray
     * @param commandArray is the command sliced
     * @param store the array of products
     * @param ticket
     * @param command the command given by the user
     * @return
     */
    private boolean readCommandAux(String[] commandArray, Store store, Ticket ticket, String command){//REVISAR: Se deberían considerar casos como null o comandos mal escritos
        boolean end=false;
        switch (commandArray[0]){
            case "prod":
                commandProd(commandArray,store,ticket);
                break;
            case "ticket":
                switch (commandArray[1]){
                    case "new":
                        ticket= new Ticket();
                }
                break;
            case "help":
                printHelp();
                break;
            case "echo":
                try {
                    String[] commandMarks = command.split("\"");
                    System.out.println(commandMarks[1]);
                }catch(ArrayIndexOutOfBoundsException e ){
                    System.out.println(FORMAT_ERROR);
                }
                break;
            case "exit":
                end = true;
                break;

        }
        return end;
    }

    /**
     * The method manages when the command given is related with prod
     * @param commandArray is the command sliced by blanks
     * @param store the array of products
     * @param ticket
     */
    private void commandProd(String[] commandArray,Store store, Ticket ticket) {
        switch (commandArray[1]) {
            case "add":
                double price = -1;
                boolean correct = true;
                try {
                    price = Double.parseDouble(commandArray[5]);
                    type type = upm.etsisi.poo.es.type.valueOf(commandArray[4]);
                } catch (NumberFormatException e) {
                    System.out.println(DATA_ERROR);
                    correct = false;
                }
                if (correct) {
//                            Product product = new Product(commandArray[2],Double.parseDouble(commandArray[5]),commandArray[2],type.valueOf(commandArray[4]),);   falta revisar el constructor.
                    Product product = new Product(Integer.getInteger(commandArray[2]),commandArray[3],type.valueOf(commandArray[4]),price);
                    boolean add = store.prodAdd(product);
                    if (add) {
                        System.out.println(product.toString());
                        System.out.println("prod add: ok");
                    }
                }
                break;
            case "list":
                System.out.println(ticket.prodList());
                break;
            case "update":
                switch (commandArray[3]) {
                    case "NOMBRE":
                        store.updateName(Integer.getInteger(commandArray[4]), commandArray[5]);
                        break;
                    case "CATEGORIA":
                        store.updateType(Integer.getInteger(commandArray[4]), type.valueOf(commandArray[5]));
                        break;
                    case "PRECIO":
                        store.updatePrice(Integer.getInteger(commandArray[4]),Double.parseDouble(commandArray[5]));
                        break;
                }
            case "remove":
                break;
        }
    }

    /**
     * The method prints all the commands allowed and their format
     */
    private void printHelp() {
        System.out.println("Commands:");
        System.out.println("pro add <id> \"<name>\"<category><price>");
        System.out.println("prod list");
        System.out.println("prod update <id>NAME|CATEGORY|PRICE<value>");
        System.out.println("prod remove<id>");
        System.out.println("ticket new");
        System.out.println("ticket add<prodid>>quantity>");
        System.out.println("ticket remove<prodid>");
        System.out.println("ticket print");
        System.out.println("echo\"<texto>\"");
        System.out.println("help");
        System.out.println("exit");
    }

    /**
     * It initializes the App
     */
    private void init() {
        System.out.println("Welcome to the ticket module APP.");
        System.out.println("Ticket module. Type 'help' to see commands.");

    }

}
