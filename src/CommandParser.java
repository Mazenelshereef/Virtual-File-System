import java.util.ArrayList;

public class CommandParser {
    private static CommandParser instance;
    private String[] allCmds = { "CreatFile", "CreateFolder", "DeleteFile", "DeleteFolder", "DispalyDiskStatus",
            "DisplayDiskStructure", "Exit" };

    private CommandParser() {
    }

    public static CommandParser getInstance() {
        if (instance == null) {
            instance = new CommandParser();
        }
        return instance;
    }

    public ArrayList<String> parseCommand(String command) {
        ArrayList<String> arguments = new ArrayList<>();
        int numberOfArguments = 1;
        for (int i = 0; i < command.length(); i++) {
            if (command.charAt(i) == (' ')) {
                numberOfArguments++;
            }

        }
        int indexOfFisrstSpace = command.indexOf(" ");
        if (indexOfFisrstSpace == -1) {
            if (command.equals("DispalyDiskStatus")) {
                if (numberOfArguments != 1) {
                    throw new ArithmeticException("Wrong Number Of Argument!!");
                } else {
                    arguments.add(command);

                    // call the fucnction that print Disk status
                }
            } else if (command.equals("DisplayDiskStructure")) {
                if (numberOfArguments != 1) {
                    throw new ArithmeticException("Wrong Number Of Argument!!");
                } else {
                    arguments.add(command);

                    // call function print DisplayDiskStructure
                }

            } else if (command.equals("Exit")) {
                if (numberOfArguments != 1) {
                    throw new ArithmeticException("Wrong Number Of Argument!!");
                } else {
                    arguments.add(command);

                    // call function print DisplayDiskStructure
                }

            } else if (command.equals("CreateFile") ||
                    command.equals("DeleteFolder") ||
                    command.equals("DeleteFile") ||
                    command.equals("CreatFolder")) {
                throw new ArithmeticException("Wrong Number Of Argument!!");
            } else {
                throw new ArithmeticException("command not available!");
            }

        } else {
            String cmdName = command.substring(0, indexOfFisrstSpace);
            if (cmdName.equals("CreateFile")) {
                if (numberOfArguments != 3) {
                    throw new ArithmeticException("Wrong Number Of Argument!!");
                } else {
                    String pathSize = command.substring(indexOfFisrstSpace + 1);
                    int indexOfSecSpace = pathSize.indexOf(" ");
                    String path = pathSize.substring(0, indexOfSecSpace);
                    String Size = pathSize.substring(indexOfSecSpace + 1);
                    try {
                        int size = Integer.parseInt(Size);
                        // call Create file function
                        arguments.add(cmdName);
                        arguments.add(path);
                        arguments.add(Size);
                    } catch (NumberFormatException ex) {
                        throw new ArithmeticException("3rd Argument have to be integers");
                    }

                    // call Create file function

                }
            } else if (cmdName.equals("DeleteFile")) {
                if (numberOfArguments != 2) {
                    throw new ArithmeticException("Wrong Number Of Argument!!");
                } else {
                    String path = command.substring(indexOfFisrstSpace + 1);
                    // callFunctionThatDeleteTheFile

                    arguments.add(cmdName);
                    arguments.add(path);
                }
            } else if (cmdName.equals("DeleteFolder")) {
                if (numberOfArguments != 2) {
                    throw new ArithmeticException("Wrong Number Of Argument!!");
                } else {
                    String path = command.substring(indexOfFisrstSpace + 1);
                    // callFunctionThatDeleteTheFolder
                    arguments.add(cmdName);
                    arguments.add(path);
                }

            } else if (cmdName.equals("CreateFolder")) {
                if (numberOfArguments != 2) {
                    throw new ArithmeticException("Wrong Number Of Argument!!");
                } else {
                    String path = command.substring(indexOfFisrstSpace + 1);
                    // callFunctionThatCreateTheFolder
                    arguments.add(cmdName);
                    arguments.add(path);
                }

            } else {
                throw new ArithmeticException("command not valid!");

            }

        }

        return arguments;
    }

    public static void main(String[] args) {
        String command = "DeleteFolder C:\\Users\\user\\Desktop\\test.txt";
        ArrayList<String> arguments = CommandParser.getInstance().parseCommand(command);
        for (String arg : arguments) {
            System.out.println(arg);
        }
    }
}
