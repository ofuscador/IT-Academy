package n1Exercise2;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.stream.Stream;
import auxiliar.Utils;

public class ShowDirTree {
    static int count = 0;
    
    public static void init(String[] args) {
        if (args.length < 1) {
            dirTree("");
        } else {
            dirTree(Utils.argsToString(args));
        }
    }
    
    public static void dirTree(String s) {
        try {
            Path path = Utils.inputPath(s);
            if (Files.exists(path)) {
                generateTreeDir(path);
            } else {
                System.out.println("the directory does not exist");
            }
        } catch (InvalidPathException e) {
            System.out.println(e.getMessage());
        }
    }
    
    private static void generateTreeDir(Path path) {
        try (Stream<Path> pathList = Files.list(path).sorted(Comparator.comparing(Files::isDirectory))) {
            pathList.forEach(file -> {
                String name = file.getParent().getFileName() + "\\" + file.getFileName();
                String date = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss").format(file.toFile().lastModified());
                if (!Files.isDirectory(file)) {
                    System.out.print("\t".repeat(count) + "[F]" + name + " " + date + "\n");
                } else {
                    System.out.print("\t".repeat(count) + "[D]" + name + " " + date + "\n");
                    count++;
                    dirTree(file.toString());
                    count--;
                }
            });
        } catch (IOException | NullPointerException e) {
            System.out.println(e.getMessage());
        }
    }
}