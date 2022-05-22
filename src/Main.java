import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    //default constructor
    public Main() {
        
    }

    //destructor
    public void finalize() {
        
    }

    public static void main(String[] args) {
        Main main = new Main();
        Allocation allocation;
        Scanner sc= new Scanner(System.in);
        System.out.print("Enter the type of Allocation");
        String type = sc.next();
        switch(type)
        {
            case ("contiguous"):
                String contiguousPath = null;//input
                MyFile.File tobeDeleted = null;//conclude from input
                allocation = new ContiguousAllocation();
                VirtualFileSystem ContiguousSystem = new VirtualFileSystem(allocation,10);
                ArrayList<Integer> ContiguousArrAdd;
                ArrayList<Integer> ContiguousArrDelete;
                Scanner Contig= new Scanner(System.in);
                System.out.print("Enter the command");
                String command = sc.next();
                //parse into operation,path,size
                // switch (command)
                ////////////////////////////////////////////////case create file////////////////////////////////

                 //take path of file and its size
                ContiguousArrAdd=allocation.Allocate(10);
                if (ContiguousArrAdd==null)
                {//cant do operation
                }
                MyFile.File contiguousFile= new MyFile.File(contiguousPath,ContiguousArrAdd);
                for(int i=0; i<ContiguousArrAdd.size(); i++) {
                    ContiguousSystem.Memory.set(ContiguousArrAdd.get(i),1);//fill storage with 1
                }
                ContiguousSystem.createFile(contiguousFile);
                ////////////////////////////////////////////////case delete file////////////////////////////////

                ContiguousArrDelete=allocation.ToDeallocate(tobeDeleted);
                for(int i=0;i<ContiguousArrDelete.size();i++){
                ContiguousSystem.Memory.set(ContiguousArrDelete.get(i), 0);
                }
                tobeDeleted=ContiguousSystem.returnDesiredFile(contiguousPath);
                ContiguousSystem.deleteFile(tobeDeleted);
                break;


            case("linked"):
                 String linkedPath = null;//input
                MyFile.File tobeDeleted2 = null;//conclude from input
                 allocation = new LinkedAllocation();
                 VirtualFileSystem LinkedSystem = new VirtualFileSystem(allocation,10);
                ArrayList<Integer> LinkedArrAdd;
                ArrayList<Integer> LinkedArrDelete;
                Scanner linked= new Scanner(System.in);    //System.in is a standard input stream
                System.out.print("Enter the command");
                String command2 = sc.next();
                //parse into operation,path,size
                // switch (command2)
                ////////////////////////////////////////////////case create file////////////////////////////////

                 //take path of file and its size
                LinkedArrAdd = allocation.Allocate(10);
                if (LinkedArrAdd==null)
                {//cant do operation
                }
                MyFile.File linkedFile= new MyFile.File(linkedPath,LinkedArrAdd);
                for(int i=0; i<LinkedArrAdd.size(); i++) {
                    LinkedSystem.Memory.set(LinkedArrAdd.get(i),1);//fill storage with 1
                }
                LinkedSystem.createFile(linkedFile);
                ////////////////////////////////////////////////case delete file////////////////////////////////

                LinkedArrDelete=allocation.ToDeallocate(tobeDeleted2);
                for(int i=0;i<LinkedArrDelete.size();i++){
                    LinkedSystem.Memory.set(LinkedArrDelete.get(i), 0);
                }
                tobeDeleted2=LinkedSystem.returnDesiredFile(linkedPath);
                LinkedSystem.deleteFile(tobeDeleted2);
                break;


            case("indexed"):
                String indexedPath = null;//input
                MyFile.File tobeDeleted3 = null;//conclude from input
                allocation = new IndexedAllocation();
                VirtualFileSystem IndexedSystem = new VirtualFileSystem(allocation,10);
                ArrayList<Integer> IndexedArrAdd;
                ArrayList<Integer> IndexedArrDelete;
                Scanner indexed= new Scanner(System.in);    //System.in is a standard input stream
                System.out.print("Enter the command");
                String command3 = sc.next();
                //parse into operation,path,size
                // switch (command)

                ////////////////////////////////////////////////case create file////////////////////////////////
                //take path of file and its size
                IndexedArrAdd= allocation.Allocate(10);
                if (IndexedArrAdd==null)
                {//cant do operation
                }
                MyFile.File indexedFile= new MyFile.File(indexedPath,IndexedArrAdd);
                for(int i=0; i<IndexedArrAdd.size(); i++) {
                    IndexedSystem.Memory.set(IndexedArrAdd.get(i),1);//fill storage with 1
                }
                IndexedSystem.createFile(indexedFile);
                ////////////////////////////////////////////////case delete file////////////////////////////////

                IndexedArrDelete=allocation.ToDeallocate(tobeDeleted3);
                for(int i=0;i<IndexedArrDelete.size();i++){
                    IndexedSystem.Memory.set(IndexedArrDelete.get(i), 0);
                }
                tobeDeleted3=IndexedSystem.returnDesiredFile(indexedPath);
                IndexedSystem.deleteFile(tobeDeleted3);

                break;

        }











    }
    
}
