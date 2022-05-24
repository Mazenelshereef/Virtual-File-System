import java.util.ArrayList;

public class IndexedAllocator implements Allocator {

    public IndexedAllocator() {
    }


    @Override
    public ArrayList<Integer> allocate(int fileSize) throws Exception {
        ArrayList<Integer> allocatedBlocks = new ArrayList<Integer>();
        ArrayList<Integer> freeBlocks = SpaceManager.getInstance().getFreeBlocks();
        //an extra block is allocated for the index block
        if (freeBlocks.size() < fileSize + 1) {
            throw new Exception("ERROR: Not enough free blocks!");
        }
        for(int i = 0; i < fileSize + 1; i++){
            allocatedBlocks.add(freeBlocks.get(i));
        }
        return allocatedBlocks;
    }
}
