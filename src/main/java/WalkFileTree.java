import java.io.IOException;
import java.nio.file.FileVisitOption;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.EnumSet;

import static java.nio.file.FileVisitOption.FOLLOW_LINKS;

public class  WalkFileTree implements Runnable {
    private int counterStrings = 0;
    private int numberFiles = 0;
    private Path sourcePath = null;

    WalkFileTree (Path path, int counter) {
            this.counterStrings = counter;
            this.sourcePath = path;
    }
    @Override
    public void run() {
        WalkCurrentDirectory walkCurrentDirectory = new WalkCurrentDirectory();
        System.out.println(Thread.currentThread().getName() + " run");
        if (!Files.isDirectory(sourcePath)){
            System.out.println(sourcePath.toAbsolutePath() + " - не папка");
        }else {
            try {
                EnumSet<FileVisitOption> opts = EnumSet.of(FOLLOW_LINKS);
                Files.walkFileTree(sourcePath, opts, Integer.MAX_VALUE, walkCurrentDirectory);
                Thread.sleep(2000);
            }catch (IOException e){
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        numberFiles = walkCurrentDirectory.getCountFile();
    }

    public Path getSourcePath() {
        return sourcePath;
    }

    public int getNumberFiles() {
        return numberFiles;
    }

    public int getCounterStrings() {
        return counterStrings;
    }
}
