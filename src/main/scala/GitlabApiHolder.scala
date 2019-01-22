import org.gitlab4j.api.GitLabApi

object GitlabApiHolder {

  def gitlabAPI() = {
    val url = "https://gitlab.com"
    val token = "WyEP1BD8c9umxf2VN4W-"
    val gitlabAPI = new GitLabApi(url, token)
    gitlabAPI
  }

}