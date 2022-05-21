import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Allocation allocation;
        Scanner sc= new Scanner(System.in);
        System.out.print("Enter the type of Allocation");
        String type = sc.next();
        switch(type)
        {
            case ("contiguous"):
                String contiguousPath = null;//input
                allocation = new ContiguousAllocation();
                VirtualFileSystem ContiguousSystem = new VirtualFileSystem(allocation,10);
                ArrayList<Integer> ContiguousArr;
                Scanner Contig= new Scanner(System.in);
                System.out.print("Enter the command");
                String command = sc.next();
                //parse into operation,path,size
                // switch (command)
                ////////////////////////////////////////////////case create file////////////////////////////////
                 //take path of file and its size
                ContiguousArr=allocation.Allocate(10);
                DirectoryFileStructures.File contiguousFile= new DirectoryFileStructures.File(contiguousPath,ContiguousArr);
                for(int i=0; i<ContiguousArr.size(); i++) {
                    ContiguousSystem.Storage.set(ContiguousArr.get(i),1);//fill storage with 1
                }
                ContiguousSystem.createFile(contiguousFile);


                break;
            case("linked"):
                 String linkedPath = null;//input
                 allocation = new LinkedAllocation();
                 VirtualFileSystem LinkedSystem = new VirtualFileSystem(allocation,10);
                 ArrayList<Integer> LinkedArr;
                Scanner linked= new Scanner(System.in);    //System.in is a standard input stream
                System.out.print("Enter the command");
                String command2 = sc.next();
                //parse into operation,path,size
                // switch (command2)
                ////////////////////////////////////////////////case create file////////////////////////////////
                 //take path of file and its size
                LinkedArr = allocation.Allocate(10);
                DirectoryFileStructures.File linkedFile= new DirectoryFileStructures.File(linkedPath,LinkedArr);
                for(int i=0; i<LinkedArr.size(); i++) {
                    LinkedSystem.Storage.set(LinkedArr.get(i),1);//fill storage with 1
                }
                LinkedSystem.createFile(linkedFile);


                break;
            case("indexed"):
                String indexedPath = null;//input
                allocation = new IndexedAllocation();
                VirtualFileSystem IndexedSystem = new VirtualFileSystem(allocation,10);
                ArrayList<Integer> IndexedArr;
                Scanner indexed= new Scanner(System.in);    //System.in is a standard input stream
                System.out.print("Enter the command");
                String command3 = sc.next();
                //parse into operation,path,size
                // switch (command)
                ////////////////////////////////////////////////case create file////////////////////////////////
                //take path of file and its size
                IndexedArr=allocation.Allocate(10);
                DirectoryFileStructures.File indexedFile= new DirectoryFileStructures.File(indexedPath,IndexedArr);

                                                                   //fill storage with 1
                IndexedSystem.createFile(indexedFile);
                break;

        }











    }
    
}
