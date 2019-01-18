import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Encoders;
import org.apache.spark.sql.SparkSession;
import org.gitlab4j.api.GitLabApi;
import org.gitlab4j.api.ProjectApi;
import org.gitlab4j.api.models.Project;
import utils.ClassLoaderUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class GitlabLangCount {
//    private static final Pattern SPACE = Pattern.compile(" ");

    public static void main(String[] args) throws Exception {

//        if (args.length < 1) {
//            System.err.println("Usage: JavaWordCount <file>");
//            System.exit(1);
//        }

        ClassLoaderUtil.showClassLoaderHierarchy(Thread.currentThread().getContextClassLoader());

        SparkSession spark = SparkSession
                .builder()
                .appName("JavaWordCount")
                .getOrCreate();



//        JavaRDD<String> lines = spark.read().textFile(args[0]).javaRDD();
//
//        JavaRDD<String> words = lines.flatMap(s -> Arrays.asList(SPACE.split(s)).iterator());
//
//        JavaPairRDD<String, Integer> ones = words.mapToPair(s -> new Tuple2<>(s, 1));
//
//        JavaPairRDD<String, Integer> counts = ones.reduceByKey((i1, i2) -> i1 + i2);
//
//        List<Tuple2<String, Integer>> output = counts.collect();
//        for (Tuple2<?, ?> tuple : output) {
//            System.out.println(tuple._1() + ": " + tuple._2());
//        }

        String url = "https://gitlab.com";
        String token = "WyEP1BD8c9umxf2VN4W-";
        GitLabApi gitlabAPI = new GitLabApi(url, token);
        ProjectApi projApi = gitlabAPI.getProjectApi();
//        List<Project> projList = projApi.getProjects(null, null, Constants.ProjectOrderBy.CREATED_AT,
//                Constants.SortOrder.ASC, null, null, null, null,
//                null, true);
        List<Project> projList = projApi.getProjects();
        System.out.println("projCount: " + projList.size());
        Dataset<Project> listDS = spark.createDataset(projList, Encoders.javaSerialization(Project.class));
        JavaRDD<Project> javaRDD = listDS.toJavaRDD();
        Map<String, Float> lang = javaRDD.map(GitlabLangCount::callMap).reduce(GitlabLangCount::callReduce);

        lang.forEach((key, value) -> {
            System.out.println(key + ":" + value);
        });

        spark.stop();
    }

//    def CLdump(cl: ClassLoader = Thread.currentThread().getContextClassLoader) = {
//        val f = classOf[ClassLoader].getDeclaredField("classes")
//        f.setAccessible(true)
//        dump(cl)
//        def dump(cl: ClassLoader): Unit = {
//        if (cl == null) return
//                val classes = f.get(cl).asInstanceOf[java.util.Vector[java.lang.Class[_]]]
//        println(cl)
//        println(classes.toArray.map(cl => "\t" + cl.toString).mkString("\n"))
//        dump(cl.getParent)
//  }
//    }

    public static Map<String, Float> callMap(Project project) {
        final Map<String, Float> lang = new HashMap<>();
        lang.put("new lang", 1f);
//            try {
//                lang = projApi.getProjectLanguages(project.getId());
//            } catch (GitLabApiException e) {
//                e.printStackTrace();
        return new HashMap<>();
//            }
//            lang.forEach((key, value) -> {
//                lang.put(key, lang.get(key) / 100);
//            });
//            return lang;
    }


    public static Map<String, Float> callReduce(Map<String, Float> lang1, Map<String, Float> lang2) {
        lang2.forEach((key, value) -> {
            lang1.put(key, lang1.getOrDefault(key, 0f) + value);
        });
        return lang1;
    }
}
