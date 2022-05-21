import java.util.ArrayList;

public class ContiguousAllocation implements Allocation{
    VirtualFileSystem System;
    DirectoryFileStructures.Directory rootDirectory;
    ArrayList<Integer> arrOfContiguousBlocks;

    public ContiguousAllocation() {
        ArrayList<Integer> arrOfContiguousBlocks= new ArrayList<Integer>() ;
    }

    public ArrayList<Integer> Allocate(int fileSize)
    {
        for(int i=0; i<fileSize; i++)
        {
            int index = System.findContiguousBlocks(fileSize);
            arrOfContiguousBlocks.set(i,index+i);
        }
        return arrOfContiguousBlocks;//return array that contains the contiguous memory indexes to be allocated.
    }
}
