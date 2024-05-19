import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Stream;

public class GitUtils {
    public static void runCommand(String command) throws IOException, InterruptedException {
        String os = System.getProperty("os.name").toLowerCase();
        ProcessBuilder builder = new ProcessBuilder();

        if (os.contains("win")) {
            builder.command("cmd.exe", "/c", command);
        } else {
            builder.command("sh", "-c", command);
        }

        builder.redirectErrorStream(true);
        Process process = builder.start();

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }
        }

        process.waitFor();
    }

    public static int countLinesOfCode(String repoPath, String fileExtension) throws IOException {
        try (Stream<String> lines = Files.walk(Paths.get(repoPath))
                                         .filter(Files::isRegularFile)
                                         .filter(path -> path.toString().endsWith(fileExtension))
                                         .flatMap(path -> {
                                             try {
                                                 return Files.lines(path);
                                             } catch (IOException e) {
                                                 throw new RuntimeException(e);
                                             }
                                         })) {
            return (int) lines.count();
        }
    }

    public static int countAuthors(String repoPath) throws IOException, InterruptedException {
        Set<String> authors = new HashSet<>();
        ProcessBuilder builder = new ProcessBuilder();
        String os = System.getProperty("os.name").toLowerCase();

        if (os.contains("win")) {
            builder.command("cmd.exe", "/c", "git log --pretty=format:\"%ae\"");
        } else {
            builder.command("sh", "-c", "git log --pretty=format:'%ae'");
        }

        builder.directory(new File(repoPath));
        builder.redirectErrorStream(true);
        Process process = builder.start();

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
            String line;
            while ((line = reader.readLine()) != null) {
                authors.add(line);
            }
        }

        process.waitFor();
        return authors.size();
    }

    public static boolean checkForTestFiles(String repoPath) throws IOException {
        try (Stream<String> paths = Files.walk(Paths.get(repoPath))
                                         .filter(Files::isRegularFile)
                                         .map(path -> path.toString())) {
            return paths.anyMatch(path -> path.toLowerCase().contains("test") && path.endsWith(".java"));
        }
    }
}
