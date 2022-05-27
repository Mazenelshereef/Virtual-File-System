import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    public static void setupSystem() {
        try {
            if (!FileParser.getInstance().parseFile())
            {
                System.out.println("Enter the size of the system in KB: ");
                Scanner scanner = new Scanner(System.in);
                int systemSizeInKB = scanner.nextInt();
                VirtualFileSystem.getInstance().setSystemSizeInKB(systemSizeInKB);
            }
        } catch (Exception e) {
            System.out.println(e.toString());;
        }
    }

    public static void closeSystem(){
        try {
            FileParser.getInstance().updateFile();
        } catch (Exception e) {
            System.out.println(e.toString());
        }
    }

    public static void main(String[] args) {
        //read the file and set up the system first
        setupSystem();
        VirtualFileSystem vfs = VirtualFileSystem.getInstance();
        Scanner sc = new Scanner(System.in);
        System.out.print("Choose type of allocation: \n"
                            + "1- Contiguous allocation\n"
                            + "2- Indexed allocation\n"
                            + "3- Linked allocation\n");
        int allocationType = sc.nextInt();
        if (allocationType == 1) {
            ContiguousAllocator ca = new ContiguousAllocator();
            vfs.setAllocation(ca);
        } else if (allocationType == 2) {
            IndexedAllocator ia = new IndexedAllocator();
            vfs.setAllocation(ia);
        } else if (allocationType == 3) {
            LinkedAllocator la = new LinkedAllocator();
            vfs.setAllocation(la);
        } else {
            System.out.println("Invalid allocation type");
            sc.close();
            return;
        }
        Scanner sc2 = new Scanner(System.in);
        while (true) {
            System.out.print("Enter command: \n");
            String input = sc2.nextLine();
            ArrayList<String> command;
            try {
                command = CommandParser.getInstance().parseCommand(input);
            } catch (Exception e) {
                System.out.println(e.toString());
                continue;
            }
            if (command.get(0).equals("CreateFile")) {
                try {
                    vfs.createFile(command.get(1), Integer.parseInt(command.get(2)));
                    System.out.println("File created successfully");
                } catch (Exception e) {
                    System.out.println(e.toString());
                }
            } else if (command.get(0).equals("DeleteFile")) {
                try {
                    vfs.deleteFile(command.get(1));
                    System.out.println("File deleted successfully");
                } catch (Exception e) {
                    System.out.println(e.toString());
                }
            } else if (command.get(0).equals("CreateFolder")) {
                try {
                    vfs.createFolder(command.get(1));
                    System.out.println("Folder created successfully");
                } catch (Exception e) {
                    System.out.println(e.toString());
                }
            } else if (command.get(0).equals("DeleteFolder")) {
                try {
                    vfs.deleteFolder(command.get(1));
                    System.out.println("Folder deleted successfully");
                } catch (Exception e) {
                    System.out.println(e.toString());
                }
                
            } else if (command.get(0).equals("CUser")) {
                try {
                    vfs.addUser(command.get(1), command.get(2));
                    System.out.println("User created successfully");
                } catch (Exception e) {
                    System.out.println(e.toString());
                } 
            } else if (command.get(0).equals("Grant")) {
                try {
                    vfs.grantAccess(command.get(1), command.get(2), command.get(3));
                    System.out.println(command.get(1) + " capabilities is granted  successfully");
                } catch (Exception e) {
                    System.out.println(e.toString());
                } 
            } else if (command.get(0).equals("Login")) {
                try {
                    vfs.loginUser(command.get(1), command.get(2));
                    System.out.println("Logged in successfully");
                } catch (Exception e) {
                    System.out.println(e.toString());
                } 
            } else if (command.get(0).equals("DisplayDiskStatus")) {
                vfs.displayDiskStatus();
            } else if (command.get(0).equals("DisplayDiskStructure")) {
                vfs.displayDiskStructure();
            }else if (command.get(0).equals("TellUser")) {
                System.out.println("The current Logged in user is: " + vfs.getCurrentUser().getUserName());
            } else if (command.get(0).equals("Exit")) {
                System.out.println("Exiting...");
                break;
            }
        }
        sc.close();
        sc2.close();
        closeSystem();
    }

}
