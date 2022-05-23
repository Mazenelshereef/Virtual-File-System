import java.util.ArrayList;

public interface Allocator {
    ArrayList<Integer> allocate(int fileSize) throws Exception; //return array of free indexes to be allocated
}
