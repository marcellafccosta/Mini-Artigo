import java.io.File;
import java.io.IOException;

public class ProjectAnalyzer {
    private String repoUrl;
    private String localPath;

    public ProjectAnalyzer(String repoUrl) {
        this.repoUrl = repoUrl;
        this.localPath = "repos/" + repoUrl.substring(repoUrl.lastIndexOf('/') + 1, repoUrl.length() - 4);
    }

    public void analyze() throws IOException, InterruptedException {
        cloneRepository();
        int loc = countLinesOfCode();
        int authorCount = countAuthors();
        boolean hasTestFiles = checkForTestFiles();

        // Exiba ou salve os resultados conforme necessário
        System.out.printf("Repositório: %s\nLOC: %d\nAutores: %d\nPossui arquivos de teste: %b\n", repoUrl, loc, authorCount, hasTestFiles);
    }

    private void cloneRepository() throws IOException, InterruptedException {
        File repoDir = new File(localPath);
        if (!repoDir.exists()) {
            GitUtils.runCommand("git clone " + repoUrl + " " + localPath);
        }
    }

    private int countLinesOfCode() throws IOException {
        return GitUtils.countLinesOfCode(localPath, ".java");
    }

    private int countAuthors() throws IOException, InterruptedException {
        return GitUtils.countAuthors(localPath);
    }

    private boolean checkForTestFiles() throws IOException {
        return GitUtils.checkForTestFiles(localPath);
    }
}
