import java.util.ArrayList;

public class MyFile {
    private String filePath;
        private String filename;
        private ArrayList<Integer> allocatedBlocks;
        private boolean isDeleted;
        public MyFile(String filePath, ArrayList<Integer> allocatedBlocks) {
            this.filePath = filePath;
            this.filename = filePath.substring(filePath.lastIndexOf("/")+1);
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
            return filename;
        }

        public void setFilename(String filename) {
            this.filename = filename;
        }

        public boolean isDeleted() {
            return isDeleted;
        }
}
