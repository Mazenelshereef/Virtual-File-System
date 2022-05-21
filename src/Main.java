import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {

        String type = null;
        Allocation allocation;
        switch(type)
        {
            case ("contiguous"):
                 String contiguousPath = null;//input
                 allocation = new ContiguousAllocation();
                 VirtualFileSystem ContiguousSystem = new VirtualFileSystem(allocation,10);
                 ArrayList<Integer> ContiguousArr;
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
