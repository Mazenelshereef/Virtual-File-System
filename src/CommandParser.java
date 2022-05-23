public class CommandParser {
    private String[] allCmds = { "CreatFile", "CreateFolder", "DeleteFile", "DeleteFolder", "DispalyDiskStatus",
            "DisplayDiskStructure" };

    public CommandParser() {
    }

    void parse(String command) {
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
                    System.out.println("Wrong Number Of Argument!!");
                } else {

                    System.out.println("hehe");
                    // call the fucnction that print Disk status
                }
            } else if (command.equals("DisplayDiskStructure")) {
                if (numberOfArguments != 1) {
                    System.out.println("Wrong Number Of Argument!!");
                } else {
                    System.out.println("keek");

                    // call function print DisplayDiskStructure
                }
            } else {
                System.out.println("command not available!");
            }

        } else {
            String cmdName = command.substring(0, indexOfFisrstSpace);
            if (cmdName.equals("CreateFile")) {
                if (numberOfArguments != 3) {
                    System.out.println("Wrong Number Of Argument!!");
                } else {
                    String pathSize = command.substring(indexOfFisrstSpace + 1);
                    int indexOfSecSpace = pathSize.indexOf(" ");
                    String path = pathSize.substring(0, indexOfSecSpace);
                    String Size = pathSize.substring(indexOfSecSpace + 1);
                    int sizeInt = 0;
                    try {
                        int size = Integer.parseInt(Size);
                        sizeInt = size;
                        // call Create file function
                        System.out.println(cmdName + '\n' + path + '\n' + sizeInt + '\n');

                    } catch (NumberFormatException ex) {
                        System.out.println("3rd Argument have to be integers");
                    }

                    // call Create file function

                }
            } else if (cmdName.equals("DeleteFile")) {
                if (numberOfArguments != 2) {
                    System.out.println("Wrong Number Of Argument!!");
                } else {
                    String path = command.substring(indexOfFisrstSpace + 1);
                    // callFunctionThatDeleteTheFile

                    System.out.println(cmdName + " " + path + '\n');
                }
            } else if (cmdName.equals("DeleteFolder")) {
                if (numberOfArguments != 2) {
                    System.out.println("Wrong Number Of Argument!!");
                } else {
                    String path = command.substring(indexOfFisrstSpace + 1);
                    // callFunctionThatDeleteTheFolder
                    System.out.println(cmdName + " " + path + '\n');
                }

            } else if (cmdName.equals("CreateFolder")) {
                if (numberOfArguments != 2) {
                    System.out.println("Wrong Number Of Argument!!");
                } else {
                    String path = command.substring(indexOfFisrstSpace + 1);
                    // callFunctionThatCreateTheFolder
                    System.out.println(cmdName + " " + path + '\n');
                }

            } else {
                System.out.println("command not available!");

            }

        }

    }

    public static void main(String[] args) {
        CommandParser cmd = new CommandParser();
        cmd.parse("CreateFile");
    }
}
