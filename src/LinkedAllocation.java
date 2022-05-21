import java.util.ArrayList;

public class LinkedAllocation implements Allocation{
    VirtualFileSystem System;
    DirectoryFileStructures.Directory rootDirectory;
    ArrayList<Integer> arrOfLinkedBlocks;

    public LinkedAllocation() {
        ArrayList<Integer> arrOfLinkedBlocks= new ArrayList<Integer>() ;
    }

    public ArrayList<Integer> Allocate(int fileSize)
  {
      for(int i=0; i<fileSize; i++)
      {
         int index = System.findFirstEmpty();
          //handle if there is no remaining block
          arrOfLinkedBlocks.add(index);
      }
      return arrOfLinkedBlocks;//return array that contains the sequence of pointers from start to end
  }
}
