# Proyecto-Poo
## Usage
    As it is a java proyect, we execute the maven compiled version in the terminal. 
    It can be initialized with a txt for automatic execution of commands or without for the user to manualy insert the commands:
    java -jar Poo-1.0-SNAPSHOT.jar "texto.txt"
    or
    java -jar Poo-1.0-SNAPSHOT.jar

## Use of Jline
### We have used Jline as a termilan completion manager, which also saves a history of last used commands in an execution of the app, similar to terminal emulators like kitty or ghostty, we also implemented keybindings for a faster usage of the app:
        start of command <tab> --> autocompletion.
            if multiple options can be autocompleted <tab> to select which one.
        with previous commands written, <Up arrow> --> last command written.
        Keybindings:
            F1: client Alt+a: add Alt+f:addFood
            F2: cash Alt+r: remove Alt+m:addMeeting
            F3: ticket Alt+l: list
            F4: prod Alt+n: new
            F5: help Alt+t: tickets
            F6: echo Alt+u: update
            Also Ctrl+D now closes the App
        
## Commands
### We have a simple sistem of commands to include products, tickets, clients and cashiers, that are saved in structures inside the project initialy and at the moment of shutdown, they are all saved in a CSV file and retrieved from this when initialized again.
      client add "<nombre>" (<DNI>|<NIF>) <email> <cashId>
      client remove <DNI>
      client list
      cash add [<id>] "<nombre>"<email>
      cash remove <id>
      cash list
      cash tickets <id>
      ticket new [<id>] <cashId> <userId> -[c|p|s] (default -p option)
      ticket add <ticketId><cashId> <prodId> <amount> [--p<txt> --p<txt>] 
      ticket remove <ticketId><cashId> <prodId> 
      ticket print <ticketId> <cashId> 
      ticket list
      prod add ([<id>] "<name>" <category> <price> [<maxPers>]) || ("<name>" <category> )
      prod update <id> NAME|CATEGORY|PRICE <value>
      prod addFood [<id>] "<name>" <price> <expiration:yyyy-MM-dd> <max_people>
      prod addMeeting [<id>] "<name>" <price> <expiration:yyyy-MM-dd> <max_people>
      prod list
      prod remove <id>
      help
      echo “<text>” 
      exit
# Requirements
  - Java installed (preferably the last version)
  
