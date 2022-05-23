import java.util.ArrayList;

public class LinkedAllocator implements Allocator{

    public LinkedAllocator() {
    }

    public ArrayList<Integer> allocate(int fileSize) throws Exception{
        ArrayList<Integer> allocatedBlocks = new ArrayList<>();
        ArrayList<Integer> freeBlocks = SpaceManager.getInstance().getFreeBlocks();
        if(freeBlocks.size() < fileSize){
            throw new Exception("ERROR: Not enough free blocks!");
        }
        for(int i = 0; i < fileSize; i++){
            allocatedBlocks.add(freeBlocks.get(i));
        }
        return allocatedBlocks;
    }

}
