import sbt.Keys._
import sbt._

import scala.io.BufferedSource
import scala.io.Source._

object ReadJvmOptsForkPlugin extends AutoPlugin {

  object autoImport {
    val readJvmOptsForFork = taskKey[List[String]]("readJvmOptsForFork")
  }

  import autoImport._

  var jvmOptsForkFileName = ".jvmopts.fork"

  override def projectSettings = Seq(
    readJvmOptsForFork := {
      var source: BufferedSource = null
      try {
        sLog.value.info(s"Try add/replace jvmOpts from file: ${jvmOptsForkFileName}")
        source = fromFile(jvmOptsForkFileName, "utf-8")
        source.getLines().toList
      } catch {
        case ex: Throwable =>
          sLog.value.warn(s"${ex.getMessage}")
          List()
      } finally {
        if (source != null) source.close()
      }
    }
  )
}