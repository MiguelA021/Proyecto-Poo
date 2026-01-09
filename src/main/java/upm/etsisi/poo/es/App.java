package upm.etsisi.poo.es;
import org.jline.builtins.Completers.TreeCompleter;
import org.jline.reader.*;
import org.jline.utils.AttributedString;
import org.jline.utils.AttributedStringBuilder;
import org.jline.utils.AttributedStyle;
import upm.etsisi.poo.es.Commands.CommandController;
import org.jline.terminal.Terminal;
import org.jline.terminal.TerminalBuilder;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.*;
import java.util.function.BiConsumer;
import java.util.regex.Pattern;
import org.jline.keymap.KeyMap;

import static org.jline.builtins.Completers.TreeCompleter.node;

public class App {
  private final static String WELCOME_MESSAGE = "Welcome to the ticket module App.";
  private final static String HELP_MESSAGE = "Ticket module. Type 'help' to see commands.";
  private final static String FILE_ERROR = "Error while reading the file, please try again.";
  private final static String TERMINAL_ERROR= "Error while using the terminal, please try again.";
  public static final String UPM = "tUPM> ";
  private static final Map<String, List<String>> COMMANDS = new HashMap<>();
  static {
    COMMANDS.put("client", Arrays.asList("add", "list", "remove"));
    COMMANDS.put("cash",   Arrays.asList("add", "remove", "list", "tickets"));
    COMMANDS.put("ticket", Arrays.asList("new", "add", "remove", "print", "list"));
    COMMANDS.put("prod",   Arrays.asList("add", "update", "addFood", "addMeeting", "list", "remove"));
    COMMANDS.put("help",   Collections.emptyList());
    COMMANDS.put("echo",   Collections.emptyList());
    COMMANDS.put("exit",   Collections.emptyList());
  }

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
    List<TreeCompleter.Node> nodes = new ArrayList<>();
    for (Map.Entry<String, List<String>> entry : COMMANDS.entrySet()) {
      String command = entry.getKey();
      List<String> subs = entry.getValue();
      Object[] subNodes = subs.stream().map(TreeCompleter::node).toArray();
      List<Object> nodeParts = new ArrayList<>();
      nodeParts.add(command);
      for (String sub : subs) {
        nodeParts.add(node(sub));
      }
      nodes.add(node(nodeParts.toArray()));
    }
    Completer completer = new TreeCompleter(nodes);
    if (args.length == 0) {
      userCommand(completer);
    } else {
      readFile(args, completer);
    }
  }

  /**
   * Modo interactivo por consola
   */
  public void userCommand(Completer completer) {
    boolean end = false;
    Scanner scan = new Scanner(System.in);
    CommandController controller = new CommandController();
    Highlighter myHighlighter = new Highlighter() {
      @Override
      public void setErrorIndex(int errorIndex) {}
      @Override
      public void setErrorPattern(Pattern errorPattern) {}
      @Override
      public AttributedString highlight(LineReader reader, String buffer) {
        AttributedStringBuilder sb = new AttributedStringBuilder();
        String[] parts = buffer.split("\\s+");
        for (int i = 0; i < parts.length; i++) {
          String token = parts[i];
          String rootCommand = parts[0];
          if (i == 0) {
            if (COMMANDS.containsKey(token)) {
              sb.style(AttributedStyle.DEFAULT.foreground(AttributedStyle.GREEN));
            } else {
              sb.style(AttributedStyle.DEFAULT.foreground(AttributedStyle.RED));
            }
          }
          else if (i == 1) {
            if (rootCommand.equals("echo")) {
              sb.style(AttributedStyle.DEFAULT.foreground(AttributedStyle.YELLOW));
            }
            else if (rootCommand.equals("help") || rootCommand.equals("exit")) {
              sb.style(AttributedStyle.DEFAULT.foreground(AttributedStyle.RED));
            }
            else if (COMMANDS.containsKey(rootCommand) && COMMANDS.get(rootCommand).contains(token)){
              sb.style(AttributedStyle.DEFAULT.foreground(AttributedStyle.BLUE));
            } else {
              sb.style(AttributedStyle.DEFAULT.foreground(AttributedStyle.RED));
            }
          }
          else {
            if (rootCommand.equals("client")||rootCommand.equals("cash")||rootCommand.equals(("ticket"))||(rootCommand.equals("prod"))) {
              sb.style(AttributedStyle.DEFAULT.foreground(AttributedStyle.YELLOW));
            } else{
              sb.style(AttributedStyle.DEFAULT.foreground(AttributedStyle.RED));
            }
          }
          sb.append(token);
          if (i < parts.length - 1 || buffer.endsWith(" ")) {
            sb.style(AttributedStyle.DEFAULT).append(" ");
          }
        }
        return sb.toAttributedString();
      }
    };
    try{
      Terminal terminal= TerminalBuilder.builder().system(true).build();
      LineReader reader = LineReaderBuilder.builder().terminal(terminal).completer(completer).highlighter(myHighlighter).variable(LineReader.HISTORY_FILE, Paths.get("record_poo.txt")).variable(LineReader.HISTORY_SIZE, 50).build();
      BiConsumer<String, String> registerKey = (seq, text) -> {
        reader.getKeyMaps().get(LineReader.MAIN).bind(
                new Widget() {
                  @Override
                  public boolean apply() {
                    reader.getBuffer().write(text + " ");
                    reader.callWidget(LineReader.REDRAW_LINE);
                    reader.callWidget(LineReader.REDISPLAY);
                    return true;
                  }
                },
                seq
        );
      };
      registerKey.accept("\u001BOP", "client");
      registerKey.accept("\u001B[11~", "client");
      registerKey.accept("\u001BOQ", "cash");
      registerKey.accept("\u001B[12~", "cash");
      registerKey.accept("\u001BOR", "ticket");
      registerKey.accept("\u001B[13~", "ticket");
      registerKey.accept("\u001BOS", "prod");
      registerKey.accept("\u001B[14~", "prod");
      registerKey.accept("\u001B[15~", "help");
      registerKey.accept("\u001B[17~", "echo");
      registerKey.accept("\u001Ba", "add");
      registerKey.accept("\u001Br", "remove");
      registerKey.accept("\u001Bl", "list");
      registerKey.accept("\u001Bn", "new");
      registerKey.accept("\u001Bt", "ticket");
      registerKey.accept("\u001Bu", "update");
      registerKey.accept("\u001Bf", "addFood");
      registerKey.accept("\u001Bm", "addMeeting");
      while (!end) {
        String line = reader.readLine(UPM);
        end = controller.handle(line);
      }
    } catch (IOException e) {
      System.out.println(TERMINAL_ERROR);
    }catch (EndOfFileException e) {
      System.out.println("Ctrl+D detected, closing App");
    } finally {
      scan.close();
    }
  }

  /**
   * Modo lectura de fichero
   */
  private void readFile(String[] args, Completer completer) {
    String line;
    CommandController controller = CommandController.getInstance();

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
          end = true;
          userCommand(completer);
        }
        if (!end) {
          System.out.println();
        }
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
