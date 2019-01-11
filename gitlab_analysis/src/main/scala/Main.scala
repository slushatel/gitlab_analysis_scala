
//import org.gitlab.api.GitlabAPI
import org.gitlab4j.api.GitLabApi
//import scala.collection.JavaConverters._
import org.apache.spark.SparkContext
import org.apache.spark.SparkConf

object Main {
  def main(args: Array[String]) {
    val url = "https://gitlab-poc.globallogic.com"
    val token = ""
    val gitlabAPI = new GitLabApi(url, token)
    val projApi = gitlabAPI.getProjectApi
    val projList = projApi.getProjects()
    var n = 0
    projList.forEach(project => {
      n += 1
      println(n + ": " + project.getName)
      val lang = projApi.getProjectLanguages(project.getId)
      lang.entrySet().forEach(l => {
        println(l.getKey + ":" + l.getValue)
      })
      println()
    })




  }


//  def calc: Unit = {
//    val lines = sc.textFile("data.txt")
//    val lineLengths = lines.map(s => s.length)
//    val totalLength = lineLengths.reduce((a, b) => a + b)
//  }
}

//class Point(val xc: Int, val yc: Int) {
//  var x: Int = xc
//  var y: Int = yc
//
//  def move(dx: Int, dy: Int) {
//    x = x + dx
//    y = y + dy
//  }
//}
