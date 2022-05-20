import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        Allocation contiguousAll = new ContiguousAllocation();
        Allocation linkedAll = new LinkedAllocation();
        Allocation indexedAll = new IndexedAllocation();
        VirtualFileSystem ContiguousSystem = new VirtualFileSystem(contiguousAll,10);
        VirtualFileSystem LinkedSystem = new VirtualFileSystem(linkedAll,10);
        VirtualFileSystem IndexedSystem = new VirtualFileSystem(indexedAll,10);
        ArrayList<Integer> ContiguousArr;
        ArrayList<Integer> LinkedArr;
        ArrayList<Integer> IndexedArr;

        ContiguousArr=contiguousAll.Allocate(10);
        for(int x= ContiguousArr.get(0); x<10; x++) {
            ContiguousSystem.Storage.set(x, 1);
        }

        LinkedArr = linkedAll.Allocate(10);
        for(int i=0; i<LinkedArr.size(); i++) {
            LinkedSystem.Storage.set(LinkedArr.get(i),1);
        }

        IndexedArr=indexedAll.Allocate(10);
    }
    
}
