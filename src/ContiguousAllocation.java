import java.util.ArrayList;

public class ContiguousAllocation implements Allocation{
    VirtualFileSystem System;
    ArrayList<Integer> arrOfLinkedBlocks;

    public ContiguousAllocation() {
        ArrayList<Integer> arrOfContiguousBlocks= new ArrayList<Integer>() ;
    }

    public ArrayList<Integer> Allocate(int fileSize)
    {
            int index = System.findContiguousBlocks(fileSize);
            arrOfLinkedBlocks.set(0,index);

        return arrOfLinkedBlocks;
    }
}
