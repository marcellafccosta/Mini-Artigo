import java.io.IOException;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        // Lista de URLs dos repositórios a serem analisados
        List<String> repoUrls = List.of(
            "https://github.com/GuillaumeFalourd/java-training-api",
            "https://github.com/scottyab/secure-preferencest",
            "https://github.com/desmond1121/Android-Ptr-Comparison",
            "https://github.com/bitcraze/crazyflie-android-client",
            "https://github.com/JetBrains-Research/anti-copy-paster",
            "https://github.com/KassuK1/BlackOut",
            "https://github.com/Mine2Gether/m2g_android_miner",
            "https://github.com/Janix520/EasyMedia",
            "https://github.com/piyush6348/Design-Patterns",
            "https://github.com/wildma/AndroidNotes"
            // Adicione mais URLs conforme necessário
        );

        for (String repoUrl : repoUrls) {
            try {
                ProjectAnalyzer analyzer = new ProjectAnalyzer(repoUrl);
                analyzer.analyze();
            } catch (Exception e) {
                System.err.println("Erro ao analisar o repositório: " + repoUrl);
                e.printStackTrace();
            }
        }
    }
}