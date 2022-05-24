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
        //add the start
        allocatedBlocks.add(freeBlocks.get(0));
        //add the end
        allocatedBlocks.add(freeBlocks.get(fileSize-1));
        //add the links
        for (int i = 0; i < fileSize - 1; i++) {
            allocatedBlocks.add(freeBlocks.get(i));
            allocatedBlocks.add(freeBlocks.get(i+1));
        }
        //add the last link
        allocatedBlocks.add(freeBlocks.get(fileSize-1));
        allocatedBlocks.add(-1);
        return allocatedBlocks;
    }

}
