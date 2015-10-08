package edu.pitt.mpqa

import java.util
import edu.pitt.mpqa.option._
import edu.pitt.mpqa.node._
import me.yuhuan.util.io._
import net.liftweb.json.JsonAST.JString
import net.liftweb.json._

import scala.collection.JavaConverters._
import scala.collection.mutable.ArrayBuffer
import scala.xml.XML



class Mpqa private(val documents: Seq[Document]) {

  //region Java
  def getDocuments: util.List[Document] = documents.asJava
  //endregion

  //regions Java Compatibility Methods
  def getAllSentences = allSentences.asJava
  def getAllSubjObjs = allSubjObjs.asJava
  def getAllDirectSubjectives = allDirectSubjectives.asJava
  def getAllExpressiveSubjectivities = allExpressiveSubjectivities.asJava
  def getAllObjectiveSpeechEvents = allObjectiveSpeechEvents.asJava
  def getAllETargets = allETargets.asJava
  def getAllSTargets = allSTargets.asJava
  def getAllTargetFrames = allTargetFrames.asJava
  //endregion


  def allSentences = documents.flatMap(_.sentences)

  def allSubjObjs = documents.flatMap(_.sentences).flatMap(_.subjObjs)

  def allDirectSubjectives = {
    documents
      .flatMap(_.sentences)
      .flatMap(_.subjObjs)
      .filter(_.isInstanceOf[DirectSubjective])
      .map(_.asInstanceOf[DirectSubjective])
  }

  def allExpressiveSubjectivities = {
    documents
      .flatMap(_.sentences)
      .flatMap(_.subjObjs)
      .filter(_.isInstanceOf[ExpressiveSubjectivity])
      .map(_.asInstanceOf[ExpressiveSubjectivity])
  }

  def allObjectiveSpeechEvents = {
    documents
      .flatMap(_.sentences)
      .flatMap(_.subjObjs)
      .filter(_.isInstanceOf[ObjectiveSpeechEvent])
      .map(_.asInstanceOf[ObjectiveSpeechEvent])
  }

  def allETargets = {
    allTargetFrames.flatMap(_.eTargets)
  }

  def allSTargets = {
    allObjectiveSpeechEvents.filter(_.sTarget != null).map(_.sTarget) ++
      allTargetFrames.flatMap(_.sTargets)
  }

  def allTargetFrames = {
    allDirectSubjectives.flatMap(_.attitudes).map(_.targetFrame).filter(_ != null) ++
    allExpressiveSubjectivities.map(_.targetFrame).filter(_ != null)
  }

}

/**
 * The engine of the corpus.
 *
 * An important reason why this engine is needed is that, to save memory usage,
 * the text span property in many classes like
 * [[Sentence Sentence]],
 * [[ETarget ETarget]], and
 * [[STarget STarget]]
 * use a [[Span TextSpan]] object to store only
 * the start and end character positions. The actual text strings can be
 * accessed in this object using the
 * [[edu.pitt.nlp.data.mpqa.Mpqa#view view]] method.
 *
 */

object Mpqa {

  //regions Java Compatibility Methods
  def createFromConfig(pathToConfigXml: String): Mpqa = apply(pathToConfigXml)
  //endregion

  /**
   * A mapping from document ID to the raw text of the document.
   */
  val docRawTexts = scala.collection.mutable.HashMap[String, String]()

  /**
   * A mapping from document ID to its JSON file.
   */
  val docJsonTexts = scala.collection.mutable.HashMap[String, String]()

  /**
   * Loads the MPQA corpus by a configuration file.
   *
   * This method loads the raw documents and JSON files pointed to by the configuration file first,
   * and then parses each JSON file (which corresponds to a document in MPQA) as a
   * [[Document Document]] object.
   *
   * @param pathToConfigXml The path to the configuration file.
   * @return An array of documents.
   */
  def apply(pathToConfigXml: String): Mpqa = {
    val configXml = XML.loadFile(pathToConfigXml)
    val pathToRawTextDir = (configXml \\ "Paths" \\ "RawDocsDir").text
    val pathToJsonDir = (configXml \\ "Paths" \\ "JsonDocsDir").text
    this(pathToJsonDir, pathToRawTextDir)
  }

  def apply(pathToJsonDir: String, pathToRawTextDir: String) = {
    loadDocRawTexts(pathToRawTextDir)
    loadDocJsonTexts(pathToJsonDir)

    // Parse each JSON file to a Document object.
    new Mpqa(docJsonTexts.values.map(d ⇒ parseJsonOfDocument(d)).toSeq)
  }


  //region Parsing Methods for Option Values

  private def parseUncertainty(json: JValue): Uncertainty = {
    json match {
      case JString(s) ⇒ {
        if (s == "somewhat-uncertain") Uncertainty.SomewhatUncertain
        else throw new Exception(s"Unknown Uncertainty $s")
      }
      case JNull ⇒ null
    }
  }

  private def parseIntensity(json: JValue): Intensity = {
     json match {
      case JString(s) ⇒ {
        s match {
          case "low-medium" => Intensity.LowMedium
          case "medium" => Intensity.Medium
          case "medium-high" => Intensity.MediumHigh
          case "n" => null
          case "high" => Intensity.High
          case "neutral" => Intensity.Neutral
          case "high-extreme" => Intensity.HighExtreme
          case "low" => Intensity.Low
          case "extreme" => Intensity.Extreme

        }
      }
      case JNull ⇒ null
    }
  }

  private def parseExpressionIntensity(json: JValue): ExpressionIntensity = {
    json match {
      case JString(s) ⇒ {
        s match {
          case "medium" => ExpressionIntensity.Medium
          case "high" =>  ExpressionIntensity.High
          case "neutral" => ExpressionIntensity.Neutral
          case "low" => ExpressionIntensity.Low
          case "extreme" => ExpressionIntensity.Extreme
        }
      }
      case JNull ⇒ null
    }
  }

  private def parsePolarity(json: JValue): Polarity = {
    json match {
      case JString(s) ⇒ {
        s match {
          case "uncertain-neutral" => Polarity.UncertainNegative
          case "None" => null
          case "sentiment" => Polarity.Sentiment
          case "negaitve" => Polarity.Negative  // This is not a mistake!
          case "positive" => Polarity.Positive
          case "both" => Polarity.Both
          case "negative" => Polarity.Negative
          case "neutral" => Polarity.Neutral
          case "uncertain-positive" => Polarity.UncertainPositive
          case "uncertain-both" => Polarity.UncertainBoth
          case "uncertain-negative" => Polarity.Negative
          case "attitude" => Polarity.Attitude
        }
      }
      case JNull ⇒ null
    }
  }

  private def parseAttitudeType(json: JValue): AttitudeType = {
    json match {
      case JString("sentiment") => AttitudeType.Sentiment
      case JString("arguing") => AttitudeType.Arguing
      case JString("intention") => AttitudeType.Intention
      case JString("other") => AttitudeType.Other
      case JString("interior") => AttitudeType.Interior
      case JString("speculation") => AttitudeType.Speculation
      case JString("agree") => AttitudeType.Agree
    }
  }

  private def parseETargetType(json: JValue): ETargetType = {
    json match {
      case JString("event") => ETargetType.Event
      case JString("entity") => ETargetType.Entity
    }
  }

  private def parseInsubstantiality(json: JValue): Insubstantiality = {
    json match {
      case JString(s) ⇒ {
        if (s == "c1") Insubstantiality.C1
        else if (s == "c2") Insubstantiality.C2
        else if (s == "c3") Insubstantiality.C3
        else null
      }
      case JNull ⇒ null
    }
  }

  private def parseNegatedOption(json: JValue): NegatedOption = {
    json match {
      case JString(s) ⇒ {
        s match {
          case "yes" ⇒ NegatedOption.Yes
          case "no" ⇒ NegatedOption.No
        }
      }
      case JNull ⇒ null
    }
  }

  private def parseReferredInSpanOption(json: JValue): ReferredInSpanOption = {
    json match {
      case JString(s) ⇒ {
        s match {
          case "yes" ⇒ ReferredInSpanOption.Yes
          case "no" ⇒ ReferredInSpanOption.No
        }
      }
      case JNull ⇒ null
    }
  }

  //endregion

  //region Parsing Methods for Nodes
  private def parseNestedSource(json: JValue): Seq[String] = {
    json match {
      case JArray(l) ⇒ {
        l.map(x ⇒ {
          val JString(s) = x
          s
        })
      }
    }
  }

  private def parseSTarget(json: JValue, parent: HasSTarget): STarget = {
    if (json != JNull) {

      val JString(id) = json \ "identifier"
      val uncertainty = parseUncertainty(json \ "uncertainty")
      val span = parseSpan(json \ "span")
      val sTarget = new STarget(parent, id, span, new ArrayBuffer[ETarget], uncertainty)

      parent match {
        case tf: TargetFrame ⇒ sTarget.eTargets = (json \ "eTargets").children.map(e ⇒ parseETarget(e, tf))
        case _ ⇒ { }
      }

      sTarget
    }
    else null
  }

  private def parseETarget(json: JValue, parent: TargetFrame): ETarget = {
    val eventOrEntity: ETargetType = json \ "eventOrEntity" match {
      case JString(s) ⇒ {
        if (s == "event") ETargetType.Event
        else if (s == "entity") ETargetType.Entity
        else null
      }
      case JNull ⇒ null
    }

    val JString(id) = json \ "identifier"

    val isNegated: NegatedOption = parseNegatedOption(json \ "isNegated")

    val isReferredInSpan: ReferredInSpanOption = parseReferredInSpanOption(json \ "isReferredInSpan")

    val span = parseSpan(json \ "span")

    new ETarget(parent, id, span, eventOrEntity, isNegated, isReferredInSpan)
  }

  private def parseAgentMention(json: JValue): Agent = {
    val JString(id) = json \ "identifier"
    val span = parseSpan(json \ "span")
    Agent(id, span)
  }

  private def parseSpan(json: JValue): Span = {
    json match {
      case JNothing ⇒ null
      case _ ⇒ {

        val start: Option[Int] = json \ "startPos" match {
          case JNothing ⇒ None
          case JNull ⇒ None
          case JInt(x) ⇒ Some(x.toInt)
        }

        val end: Option[Int] = json \ "endPos" match {
          case JNothing ⇒ None
          case JNull ⇒ None
          case JInt(x) ⇒ Some(x.toInt)
        }

        if (start == None && end == None)null
        else Span(start.get, end.get)
      }
    }
  }

  private def parseTargetFrame(json: JValue, parent: HasTargetFrame): TargetFrame = {

    (json \ "identifier") match {
      case JNothing ⇒ null
      case JString(id) ⇒ {
        val tf = new TargetFrame(parent, id, null, null)
        val newETargets = (json \ "newETargets").children.map(x ⇒ parseETarget(x, tf))
        val sTargets = (json \ "sTargets").children.map(x ⇒ parseSTarget(x, tf))

        tf.newETargets = newETargets
        tf.sTargets = sTargets

        tf
      }
    }
  }

  private def parseJsonOfDocument(json: String): Document = {
    val jsonRoot = parse(json)

    // Extract things that belong to this level
    // In the case of a document, we extract the identifier and agent mentions.
    val JString(docId) = jsonRoot \ "identifier"

    val scalaAgentMentions = (jsonRoot \ "agentMentions").children.map(x ⇒ {
      parseAgentMention(x)
    })

    val scalaRoot = new Document(
      docId,
      scalaAgentMentions,
      null
    )

    // Fringes for a synchronous DFS
    val jsonFringe = scala.collection.mutable.Stack[JValue](jsonRoot)
    val scalaFringe = scala.collection.mutable.Stack[AnyRef](scalaRoot)

    while (jsonFringe nonEmpty) {
      val jsonCur = jsonFringe.pop()
      val scalaCur = scalaFringe.pop()


      jsonCur \ "classType" match {
        case JString("YDocument") ⇒ {
          // I'm looking at a Document, so I should insert the sentences.
          val curScalaDocument = scalaCur.asInstanceOf[Document]
          val jsonSentences = (jsonCur \ "sentences").children
          val scalaSentences = jsonSentences.map(x ⇒ {
            val span = parseSpan(x \ "span")
            new Sentence(
              curScalaDocument,
              span,
              null
            )
          })
          curScalaDocument.sentences = scalaSentences

          // Maintain both fringes
          jsonFringe pushAll jsonSentences
          scalaFringe pushAll scalaSentences
        }
        case JString("YSentence") ⇒ {

          val jsonSuccessors = scala.collection.mutable.ArrayBuffer[JValue]()
          val scalaSuccessors = scala.collection.mutable.ArrayBuffer[AnyRef]()

          // I'm looking at sentences, so I should add subjobjs into the sentence.
          val scalaSentence = scalaCur.asInstanceOf[Sentence]
          val jsonAnnotations = (jsonCur \ "annotations").children
          val scalaAnnotations = scala.collection.mutable.ArrayBuffer[SubjObj]()
          for (jsonAnnotation ← jsonAnnotations) {

            // Some common properties of annotations
            val nestedSource = parseNestedSource(jsonAnnotation \ "nestedSource").toArray

            val span = parseSpan(jsonAnnotation \ "span")

            val insubstantiality: Insubstantiality = parseInsubstantiality(jsonAnnotation \ "insubstantiality")


            jsonAnnotation \ "classType" match {
              case JString("YDirectSubj") ⇒ {
                // Get information of the new DS at this level
                val exprIntensity = parseExpressionIntensity(jsonAnnotation \ "expressionIntensity")

                val intensity: Intensity = parseIntensity(jsonAnnotation \ "intensity")

                val scalaDirSubj = new DirectSubjective(
                  scalaSentence,
                  span,
                  nestedSource,
                  null,
                  exprIntensity,
                  intensity,
                  insubstantiality
                )

                scalaAnnotations += scalaDirSubj

                // Maintain DFS fringes
                jsonSuccessors += jsonAnnotation
                scalaSuccessors += scalaDirSubj
              }

              case JString("YExpressiveSubj") ⇒ {
                val intensity: Intensity = parseIntensity(jsonAnnotation \ "intensity")

                val polarity: Polarity = parsePolarity(jsonAnnotation \ "polarity")

                val scalaExprSubj = new ExpressiveSubjectivity(
                  scalaSentence, nestedSource, span, polarity, intensity, insubstantiality, null
                )

                // deal with target frame
                val scalaTargetFrame = parseTargetFrame(jsonAnnotation \ "targetFrame", scalaExprSubj)

                scalaExprSubj.targetFrame = scalaTargetFrame
                scalaAnnotations += scalaExprSubj
              }

              case JString("YObjSpeechEvent") ⇒ {

                val scalaObjSpeechEvent = new ObjectiveSpeechEvent(
                  scalaSentence, nestedSource, span, null, insubstantiality
                )
//
//                if (jsonAnnotation \ "target" != JNull) {
//                  val np = 0
//                }

                val scalaSTarget = parseSTarget(jsonAnnotation \ "target", scalaObjSpeechEvent)

                scalaObjSpeechEvent.sTarget = scalaSTarget
                scalaAnnotations += scalaObjSpeechEvent
              }
            }
          }

          scalaSentence.subjObjs = scalaAnnotations

          jsonFringe pushAll jsonSuccessors
          scalaFringe pushAll scalaSuccessors

          val bp = 0
        }

        case JString("YDirectSubj") ⇒ {
          // I'm looking at DS, so I should fill attitudes in them.
          val scalaDirSubj = scalaCur.asInstanceOf[DirectSubjective]
          val jsonAttitudes = jsonCur \ "attitudes"
          val scalaAttitudes = jsonAttitudes.children.map(x ⇒ {
            val attitudeType = parseAttitudeType(x \ "attitudeType")
            val JString(id) = x \ "identifier"
            val intensity = parseIntensity(x \ "intensity")
            val polarity = parsePolarity(x \ "polarity")
            val span = parseSpan(x \ "span")
            val nestedSource = parseNestedSource(x \ "nestedSource")
            val curAtt = new Attitude(scalaDirSubj, id, nestedSource, span, attitudeType, intensity, polarity, null)

            // deal with target frame
            val targetFrame = parseTargetFrame(x \ "targetFrame", curAtt)

            curAtt.targetFrame = targetFrame
            curAtt
          })

          scalaCur.asInstanceOf[DirectSubjective].attitudes = scalaAttitudes
        }
      }
    }
    scalaRoot
  }

  //endregion

  /**
   * Loads the raw document text files. The text files will be stored in the a map called
   * `docRawTexts`, in which each pair has the document id as the key, and the text content
   * as the value.
   * @param pathToRawTextDir The path to the raw text file directory.
   *                         In a standard MPQA release, this is usually the path to the
   *                         `doc/` directory.
   */
  private def loadDocRawTexts(pathToRawTextDir: String) = {
    val dateDirs = Directory.allSubdirectories(pathToRawTextDir)
    for (dateDir ← dateDirs) {
      val documents = dateDir.listFiles
      for (d ← documents) {
        val docName = dateDir.getName + "/" + d.getName
        val docContent = TextFile.readAll(d.getAbsolutePath)
        docRawTexts(docName) = docContent
      }
    }
  }

  /**
   * Loads the JSON files. The JSON files will be stored in `docJsonTexts`, in which
   * each pair has the document ID as the key, and the JSON file content as the value.   *
   *
   * @param pathToJsonDir The path to the JSON file directory.
   *                      This is usually the path to the `clean_anns/` directory.
   */
  private def loadDocJsonTexts(pathToJsonDir: String) = {
    val dateDirs = Directory.allSubdirectories(pathToJsonDir)
    for (dateDir ← dateDirs) {
      val documents = dateDir.listFiles
      for (d ← documents) {
        val docName = dateDir.getName + "/" + d.getName
        val docContent = TextFile.readAll(d.getAbsolutePath)
        docJsonTexts(docName) = docContent
      }
    }
  }

  /**
   * Views the actual text given a text span.
   * @param docName The document name.
   * @param span The span to be decoded.
   * @return The actual text of the given text span.
   */
  def view(docName: String, span: Span): String = {
    docRawTexts(docName).substring(span.startPos, span.endPos)
  }
}
