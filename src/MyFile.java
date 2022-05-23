import java.util.ArrayList;

public class MyFile {
    private String filePath;
        private String fileName;
        private Directory parent;
        private ArrayList<Integer> allocatedBlocks;
        private boolean isDeleted;

        public MyFile(String fileName){
            this.fileName = fileName;
        }

        public MyFile(String filePath, ArrayList<Integer> allocatedBlocks) {
            this.filePath = filePath;
            this.fileName = filePath.substring(filePath.lastIndexOf("/")+1);
            this.allocatedBlocks = allocatedBlocks;
            this.isDeleted=false;
        }

        public ArrayList<Integer> getAllocatedBlocks() {
            return allocatedBlocks;
        }

        public String getFilePath() {
            return filePath;
        }

        public void setAllocatedBlocks(ArrayList<Integer> allocatedBlocks) {
            this.allocatedBlocks = allocatedBlocks;
        }

        public void setFilePath(String filePath) {
            this.filePath = filePath;
        }

        public void setDeleted(boolean deleted) {
            this.isDeleted = deleted;
        }

        public String getFilename() {
            return fileName;
        }

        public void setFilename(String filename) {
            this.fileName = filename;
        }

        public boolean isDeleted() {
            return isDeleted;
        }

        public void setParent(Directory parent) {
            this.parent = parent;
        }

        public Directory getParent() {
            return parent;
        }
}
