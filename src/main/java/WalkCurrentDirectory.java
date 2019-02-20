import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;

public class WalkCurrentDirectory extends SimpleFileVisitor<Path> {
    private int countFile = 0;

    public int getCountFile() {
        return countFile;
    }

    @Override
    public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
        synchronized (this){
            countFile++;
            return FileVisitResult.CONTINUE;
        }
    }

}
