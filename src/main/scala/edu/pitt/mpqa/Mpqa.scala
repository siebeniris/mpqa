package edu.pitt.mpqa
import java.io.File
import edu.pitt.mpqa.node._
import edu.pitt.mpqa.option.AttitudeType
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


  private val xmls = FileUtils.listFiles(new File(getClass.getResource("/gate-xmls").toURI), TrueFileFilter.INSTANCE, TrueFileFilter.INSTANCE).filter(_.getName == "annotatedBeforePublish.xml")
  val documents = xmls.map(d => MpqaLoader.load(d.getAbsolutePath))

  def allSentences = documents.flatMap(_.sentences)

  def allSubjObjs = documents.flatMap(_.sentences).flatMap(_.subjObjs)

  def allDirectSubjectives = {
    for {
      document ← documents
      sentence ← document.sentences
      subjObj ← sentence.subjObjs
      if subjObj.isInstanceOf[DirectSubjective]
    } yield subjObj.asInstanceOf[DirectSubjective]
  }

  def allExpressiveSubjectivities = {
    for {
      document ← documents
      sentence ← document.sentences
      subjObj ← sentence.subjObjs
      if subjObj.isInstanceOf[ExpressiveSubjectivity]
    } yield subjObj.asInstanceOf[ExpressiveSubjectivity]
  }

  def allObjectiveSpeechEvents = {
    for {
      document ← documents
      sentence ← document.sentences
      subjObj ← sentence.subjObjs
      if subjObj.isInstanceOf[ObjectiveSpeech]
    } yield subjObj.asInstanceOf[ObjectiveSpeech]
  }

  def allSentiments = {
    for {
      ds <- allDirectSubjectives
      att <- ds.attitudes
      if att.typ == AttitudeType.Sentiment
    } yield att
  }


}
