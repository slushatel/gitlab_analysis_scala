import org.apache.livy.Job;
import org.apache.livy.JobContext;
import org.apache.spark.api.java.function.Function;
import org.apache.spark.api.java.function.Function2;
import org.gitlab4j.api.Constants;
import org.gitlab4j.api.GitLabApi;
import org.gitlab4j.api.GitLabApiException;
import org.gitlab4j.api.ProjectApi;
import org.gitlab4j.api.models.Project;
import org.gitlab4j.api.models.Visibility;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GitlabCountLangAppJob implements Job<Map<String, Float>>, Serializable {

    private final int slices;
//    private transient final ProjectApi projApi;

    public GitlabCountLangAppJob(int slices) {
        this.slices = slices;
//        String url = "https://gitlab-poc.globallogic.com";
        String url = "https://gitlab.com";
        String token = "WyEP1BD8c9umxf2VN4W-";
        GitLabApi gitlabAPI = new GitLabApi(url, token);
//        projApi = gitlabAPI.getProjectApi();
    }

    @Override
    public Map<String, Float> call(JobContext ctx) throws Exception {
        String url = "https://gitlab.com";
        String token = "WyEP1BD8c9umxf2VN4W-";
        GitLabApi gitlabAPI = new GitLabApi(url, token);
        ProjectApi projApi = gitlabAPI.getProjectApi();
        List<Project> projList = projApi.getProjects();
//        List<Project> projList = projApi.getProjects(null, null, Constants.ProjectOrderBy.CREATED_AT,
//                Constants.SortOrder.ASC, null, null, null, null,
//                null, true);
        System.out.println("projCount: " + projList.size());
        return ctx.sc().parallelize(projList, slices).map(new MapLang()).reduce(new ReduceLang());
    }

    class MapLang implements Function<Project, Map<String, Float>> {
        @Override
        public Map<String, Float> call(Project project) {
            final Map<String, Float> lang;
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
    }

    class ReduceLang implements Function2<Map<String, Float>, Map<String, Float>, Map<String, Float>> {
        @Override
        public Map<String, Float> call(Map<String, Float> lang1, Map<String, Float> lang2) {
            lang2.forEach((key, value) -> {
                lang1.put(key, lang1.getOrDefault(key, 0f) + value);
            });
            return lang1;
        }
    }
}

