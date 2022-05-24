import java.util.ArrayList;

public class ContiguousAllocator implements Allocator{

    public ContiguousAllocator() {
    }

    public ArrayList<Integer> allocate(int fileSize) throws Exception {
        ArrayList<Integer> allocatedBlocks = new ArrayList<Integer>();
        ArrayList<SpaceManager.ContiguousFreeBlocks> contiguousFreeBlocks = SpaceManager.getInstance().getContiguousFreeBlocks();
        if (contiguousFreeBlocks.size() == 0) {
            throw new Exception("ERROR: No free blocks!");
        }
        int bestFitSize = Integer.MAX_VALUE, bestFitIndex = -1;
        for(int i=0; i<contiguousFreeBlocks.size(); i++)
        {
            if(contiguousFreeBlocks.get(i).length >= fileSize)
            {
                if(contiguousFreeBlocks.get(i).length < bestFitSize)
                {
                    bestFitSize = contiguousFreeBlocks.get(i).length;
                    bestFitIndex = i;
                }
            }
        }
        if(bestFitIndex == -1)
        {
            throw new Exception("ERROR: Not enough free blocks!");
        }
        allocatedBlocks.add(contiguousFreeBlocks.get(bestFitIndex).start);
        allocatedBlocks.add(fileSize);
        return allocatedBlocks;
    }
}
