import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CommandParser {
    private static CommandParser instance;
    private String[] allCmds = { "CreateFile", "CreateFolder", "DeleteFile", "DeleteFolder", "DisplayDiskStatus", "DisplayDiskStructure", "Exit" };
    private int[] Size = {3,2,2,2,1,1,1};
    private CommandParser() {
    }

    public static CommandParser getInstance() {
        if (instance == null) {
            instance = new CommandParser();
        }
        return instance;
    }

    public ArrayList<String> parseCommand(String command) throws Exception {
        String[] substrings = command.split("\s+");
        boolean test = Arrays.asList(allCmds).contains(substrings[0]);
        if(test==false){
            throw new Exception("Wrong Command Name!!") ;
        }
        List<String> Commands = Arrays.asList(allCmds);
        int indx = Commands.indexOf(substrings[0]);
        if(Size[indx]!=substrings.length) {
            throw new Exception("Wrong Number Of Arguments!!");
        }
        if(substrings[0].equals("CreateFile")){
            try{
                Integer.parseInt(substrings[2]);
            } catch (NumberFormatException e) {
                throw new Exception("3rd Argument must be Integer!!");
            }
        }
        ArrayList<String> Arguments=new ArrayList<>();
        for(int i=0;i<substrings.length;i++)Arguments.add(substrings[i]);
        return Arguments;
    }


}