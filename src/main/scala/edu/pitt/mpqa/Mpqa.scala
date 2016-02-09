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

  Gate.setPluginsHome(new File(MpqaConfig.GatePluginsHome))
  Gate.setSiteConfigFile(new File(MpqaConfig.GateSiteConfigFile))
  Gate.init()

  var allDocuments: Iterable[Document] = null
  reload()

  def reload() = {
    val xmls = FileUtils.listFiles(new File(MpqaConfig.MpqaXmlDir), TrueFileFilter.INSTANCE, TrueFileFilter.INSTANCE).filter(_.getName == "annotatedBeforePublish.xml")
    allDocuments = xmls.map(d => MpqaLoader.load(d.getAbsolutePath))
  }



  val documents: Map[String, Document] = allDocuments.map(d => d.name -> d).toMap

  def allSentences = allDocuments.flatMap(_.sentences)

  def apply(docName: String): Document = documents(docName)

  def allSubjObjs = allDocuments.flatMap(_.sentences).flatMap(_.subjObjs)

  def allDirectSubjectives = {
    for {
      document ← allDocuments
      sentence ← document.sentences
      subjObj ← sentence.subjObjs
      if subjObj.isInstanceOf[DirectSubjective]
    } yield subjObj.asInstanceOf[DirectSubjective]
  }

  def allExpressiveSubjectivities = {
    for {
      document ← allDocuments
      sentence ← document.sentences
      subjObj ← sentence.subjObjs
      if subjObj.isInstanceOf[ExpressiveSubjectivity]
    } yield subjObj.asInstanceOf[ExpressiveSubjectivity]
  }

  def allObjectiveSpeechEvents = {
    for {
      document ← allDocuments
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
