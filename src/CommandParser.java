import java.util.ArrayList;

public class CommandParser {
    private static CommandParser instance;

    private CommandParser() {
    }

    public static CommandParser getInstance() {
        if (instance == null) {
            instance = new CommandParser();
        }
        return instance;
    }

    public ArrayList<String> parseCommand(String command) throws Exception {
        ArrayList<String> commandList = new ArrayList<String>();
        String[] commandArray = command.split(" ");
        for (int i = 0; i < commandArray.length; i++) {
            commandList.add(commandArray[i]);
        }
        return commandList;
    }
}
