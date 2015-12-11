package edu.pitt.mpqa
import java.io.File
import gate.Gate
import org.apache.commons.io.FileUtils
import org.apache.commons.io.filefilter.TrueFileFilter
import scala.collection.JavaConversions._

/**
  * @author Yuhuan Jiang (jyuhuan@gmail.com).
  */
object Mpqa {
  Gate.setPluginsHome(new File("/Applications/GATE_Developer_8.0/plugins/"))
  Gate.setSiteConfigFile(new File("/Applications/GATE_Developer_8.0/gate.xml/"))
  Gate.init()

  private val xmls = FileUtils.listFiles(new File("/Users/yuhuan/Dropbox/Projects/MPQA3.0_NAACL2015/man_anns"), TrueFileFilter.INSTANCE, TrueFileFilter.INSTANCE).filter(_.getName == "annotatedBeforePublish.xml")
  val documents = xmls.map(d => MpqaLoader.load(d.getAbsolutePath))
}
