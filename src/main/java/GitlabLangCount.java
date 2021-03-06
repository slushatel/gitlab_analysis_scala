import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Encoders;
import org.apache.spark.sql.SparkSession;
import org.gitlab4j.api.Constants;
import org.gitlab4j.api.GitLabApi;
import org.gitlab4j.api.GitLabApiException;
import org.gitlab4j.api.ProjectApi;
import org.gitlab4j.api.models.Project;
import serialization.StatisticRequestBody;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;
import java.util.stream.Stream;
//import scala.collection.JavaConverters

public final class GitlabLangCount {
    public static void main(String[] args) throws Exception {
        SparkSession spark = SparkSession
                .builder()
                .appName("JavaWordCount")
                .getOrCreate();
        spark.sparkContext().setLogLevel("ERROR");

//        String url = "https://gitlab-poc.globallogic.com";
        String url = "https://gitlab.com";
        System.out.println(url);
//        String token = "q1NaycEiZam4sfQ42xjv"; //gitlab-poc
        String token = "BwYBz5SCrYnAa6ENu1ZX"; //gitlab
        GitLabApi gitlabAPI = new GitLabApi(url, token);
        ProjectApi projApi = gitlabAPI.getProjectApi();

        List<Project> projList = projApi.getProjects(null, null, Constants.ProjectOrderBy.CREATED_AT,
                Constants.SortOrder.ASC, null, null, null, null,
                null, true, 1, 100);
//        List<Project> projList = projApi.getProjects();
        System.out.println("Projects count: " + projList.size());

        Stream<Integer> str = projList.stream().map(project -> project.getId());
//        Stream<Long> str = projList.parallelStream().map(project -> project.getStatistics().getStorageSize());

        Dataset<Integer> listDS = spark.createDataset(str.collect(Collectors.toList()), Encoders.INT());
        JavaRDD<Integer> javaRDD = listDS.toJavaRDD();
        javaRDD.repartition(10);
        Map<String, Float> lang = javaRDD.map(GitlabLangCount::callMap).reduce(GitlabLangCount::callReduce);

        System.out.println("calculation finished");

        AtomicReference<Float> sum = new AtomicReference<>((float) 0);
        lang.forEach((key, value) -> {
            sum.updateAndGet(v -> new Float((float) (v + value)));
            System.out.println(key + ":" + value);
        });
        System.out.println("all lang sum: " + sum);

        spark.stop();

        saveResult(lang);
    }

    private static void saveResult(Map<String, Float> lang) {
        try {
            Client client = ClientBuilder.newClient();
            WebTarget webResource = client.target("http://192.168.99.1:8080/statistics");

            ObjectMapper objectMapper = new ObjectMapper();

            List<StatisticRequestBody> langList = lang.entrySet().stream()
                    .map(entry -> new StatisticRequestBody(
                            "language percent by projects count", "GitLab", entry.getKey(), new Date(), entry.getValue())
            ).collect(Collectors.toList());

            String json_input = objectMapper.writeValueAsString(langList);
            Response response = webResource.request().accept(MediaType.APPLICATION_JSON)
                    .post(Entity.entity(json_input, MediaType.APPLICATION_JSON), Response.class);

            if (response.getStatus() != 200) {
                throw new RuntimeException("Failed : HTTP error code : "
                        + response.getStatus());
            }

            System.out.println("Output from Server .... \n");
            String output = response.toString();
            System.out.println(output);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Map<String, Float> callMap(Integer projectId) {
        final Map<String, Float> lang;
        GitLabApi gitlabAPI = GitlabApiHolder.gitlabAPI();
        ProjectApi projApi = gitlabAPI.getProjectApi();
        try {
            lang = projApi.getProjectLanguages(projectId);
        } catch (GitLabApiException e) {
            e.printStackTrace();
            return new HashMap<>();
        }

        lang.forEach((key, value) -> {
            lang.put(key, lang.get(key) / 100);
        });
        return lang;
    }


    public static Map<String, Float> callReduce(Map<String, Float> lang1, Map<String, Float> lang2) {
        lang2.forEach((key, value) -> {
            lang1.put(key, lang1.getOrDefault(key, 0f) + value);
        });
        return lang1;
    }
}

