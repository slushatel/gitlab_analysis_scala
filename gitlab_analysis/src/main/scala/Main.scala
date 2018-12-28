
import org.gitlab.api.GitlabAPI

object Main {
  /* This is my first java program.
  * This will print 'Hello World' as the output
  */
  def main(args: Array[String]) {
    println("Hello, world!") // prints Hello World

    var gl: GitlabAPI = GitlabAPI.connect("http://gitlab-poc.globallogic.com", "KonoEtYRBWstrcaFxXrg")
    gl.getAllProjects().forEach();



  }
}

//gl = gitlab.Gitlab('http://gitlab.com', private_token='AtKBow56qjVEjfDxupsW')
//gl.auth()
//
g2 = gitlab.Gitlab('http://gitlab-poc.globallogic.com', private_token='KonoEtYRBWstrcaFxXrg')
g2.auth()

projects = g2.projects.list()
i = 0
for project in projects:
  i += 1
try:
lang = project.languages()
except Exception as ex:
  logging.error(ex)
lang = None
print(str(i) + ":" + project.name + ":" + str(lang))
if lang is not None:
for l in lang:
  print(l + ":" + str(lang[l]))


