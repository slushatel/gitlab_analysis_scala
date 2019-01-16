
import org.apache.livy.LivyClient;
import org.apache.livy.LivyClientBuilder;

import java.io.File;
import java.net.URI;
import java.util.concurrent.ExecutionException;

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
            uploadJars(client, pathToJobJar + "/libs");
            uploadJars(client, pathToJobJar + "/build/libs");

//            Integer langs = client.submit(new GitlabCountLangAppJob(slices)).get();
//            System.out.println("count of proj:" + langs);
//            langs.forEach((key, value) -> System.out.println(key + ":" + value));
        } finally {
            client.stop(true);
        }
    }

    private static void uploadJars(LivyClient client, String dir) throws ExecutionException, InterruptedException {
        File dirFile = new File(dir);
        File[] filesList = dirFile.listFiles();
        for (File file : filesList) {
            if (file.getName().endsWith(".jar")) {
                System.out.println(file.getName());
                client.uploadJar(file).get();
            }
        }
    }
}



