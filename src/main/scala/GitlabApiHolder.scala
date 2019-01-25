import org.gitlab4j.api.GitLabApi

object GitlabApiHolder {
  //  val url = "https://gitlab.com"
  //  val token = "BwYBz5SCrYnAa6ENu1ZX"
  val url = "https://gitlab-poc.globallogic.com"
  val token = "q1NaycEiZam4sfQ42xjv"
  val gitlabAPI = new GitLabApi(url, token)

  //  val url = "https://gitlab-poc.globallogic.com"
  //  //        String url = "https://gitlab.com";
  //  val token = "q1NaycEiZam4sfQ42xjv" //gitlab-poc
  //  //        String token = "BwYBz5SCrYnAa6ENu1ZX"; //gitlab

  //  def gitlabAPI() = {
  //    return gitlabAPI
  //  }

}