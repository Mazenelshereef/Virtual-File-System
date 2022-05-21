import java.util.ArrayList;

public interface Allocation {
    ArrayList<Integer> Allocate(int fileSize); //return array of free indexes to be allocated
    ArrayList<Integer> ToDeallocate(DirectoryFileStructures.File file);//return array of file blocks

}
