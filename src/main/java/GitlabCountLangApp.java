
import org.apache.livy.LivyClient;
import org.apache.livy.LivyClientBuilder;

import java.io.File;
import java.net.URI;
import java.util.Map;

/**
 * Example execution:
 * java -cp /pathTo/spark-core_2.11-*version*.jar:/pathTo/livy-api-*version*.jar:
 * /pathTo/livy-client-http-*version*.jar:/pathTo/livy-examples-*version*.jar
 * org.apache.livy.examples.PiApp http://livy-host:8998 2 d:\work\github_poc\gitlab_analysis_scala\libs\gitlab-analysis-scala-1.0-SNAPSHOT.jar
 */
public class GitlabCountLangApp {
    public static void main(String[] args) throws Exception {
        System.out.println("start");
        if (args.length != 3) {
            System.err.println("Usage: GitlabCountLangApp <livy url> <slices> <path-to-job-jar>");
            System.exit(-1);
        }
        String livyUrl = args[0];
        int slices = Integer.parseInt(args[1]);
        String pathToJobJar = args[2];

        LivyClient client = new LivyClientBuilder().setURI(new URI(livyUrl)).build();

        try {
            File dir = new File(pathToJobJar);
            File[] filesList = dir.listFiles();
            for (File file : filesList) {
                if (file.getName().endsWith(".jar")) {
                    System.out.println(file.getName());
                    client.uploadJar(file).get();
                }
            }

//            client.uploadJar(new File(pathToJobJar)).get();
//            client.uploadJar(new File("d:\\work\\github_poc\\gitlab_analysis_scala\\build\\libs\\gitlab4j-api-4.9.11.jar")).get();
            Map<String, Float> langs = client.submit(new GitlabCountLangAppJob(slices)).get();
            langs.forEach((key, value) -> System.out.println(key + ":" + value));
        } finally {
            client.stop(true);
        }
    }
}



