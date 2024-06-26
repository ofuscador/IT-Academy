package auxiliar;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Stream;

public class Utils {
    public static Path inputPath(String s) {
        Path p = null;
        Scanner sc = new Scanner(System.in);
        if (s.isEmpty()) {
            System.out.println("Input the path:");
            s = sc.nextLine();
        }
        try {
            p = Paths.get(s);
        } catch (InvalidPathException e) {
            System.out.println(e.getMessage());
        }
        return p;
    }
    
    public static String argsToString(String[] args) {
        String result = "";
        for (int i = 0; i < args.length; i++) {
            if (i < args.length - 1) {
                result += args[i] + " ";
            } else {
                result += args[i];
            }
        }
        return result;
    }
    
    public static Path selectPath(String subPath) {
        if (System.getProperty("java.class.path").equals(".")) {
            Path p = Paths.get("./" + subPath);
            if (Files.notExists(p)) {
                File directory = new File(p.toString());
                directory.mkdir();
            }
            return p;
        } else {
            Path p = Paths.get(System.getProperty("java.class.path")).getParent().getParent().resolve(Paths.get("src/main/java/" + subPath));
            
            if (Files.notExists(p)) {
                File directory = new File(p.toString());
                directory.mkdir();
            }
            return p;
        }
    }
    
    public static List<Path> dirFiles(Path p) {
        List<Path> list = null;
        if (Files.exists(p)) {
            try (Stream<Path> pathStream = Files.list(p).sorted()) {
                list = pathStream.filter(p1 -> p1.toFile().isFile()).toList();
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        } else {
            System.out.println("Path not found.");
        }
        return list;
    }
    
    public static File inputFile(Path p, String s) throws InvalidPathException {
        File file=null;
        if (!dirFiles(p).isEmpty()) {
            System.out.println("List of files in path:");
            dirFiles(p).forEach(p1 -> System.out.println(p1.getFileName()));
            Scanner sc = new Scanner(System.in);
            if (s.isEmpty()) {
                System.out.println("Chose a File:");
                s = sc.nextLine();
            }
            file = new File(p + "\\" + s);
        } else {
            System.out.println("The path is empty.");
        }
        return file;
    }
}
