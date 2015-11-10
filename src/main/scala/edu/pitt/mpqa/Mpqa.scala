package edu.pitt.mpqa

import java.util
import java.util.Scanner
import java.util.zip.ZipInputStream
import edu.pitt.mpqa.option._
import edu.pitt.mpqa.node._
import me.yuhuan.util.StandardStrings
import me.yuhuan.util.io._
import net.liftweb.json.JsonAST.JString
import net.liftweb.json._

import scala.collection.JavaConverters._
import scala.collection.mutable.ArrayBuffer
import scala.xml.XML
import scala.collection.mutable

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

  /**
   * A mapping from document ID to the raw text of the document.
   */
  private[mpqa] val docRawTexts = loadZippedDocs("/raw.zip")

  /**
   * A mapping from document ID to its JSON file.
   */
  private[mpqa] val docJsonTexts = loadZippedDocs("/json.zip")



  // Parse the json files as a sequence of MPQA Documents
  val documents: Seq[Document] = docJsonTexts.values.map(d ⇒ parseJsonOfDocument(d)).toSeq
  val documentById: Map[String, Document] = documents.map(d ⇒ d.id → d).toMap

  /**
   * Views the actual text given a text span.
   * Provides a way to reveal the real text that a `Span` refers to.
   * @param docName The document name.
   * @param span The span to be decoded.
   * @return The actual text of the given text span.
   */
  private[mpqa] def view(docName: String, span: Span): String = {
    docRawTexts(docName).substring(span.startPos, span.endPos)
  }

  //region Java Compatibility Methods
  def getDocuments: util.List[Document] = documents.asJava
  def getDocumentById(id: String): Document = documentById(id)
  def getAllSentences = allSentences.asJava
  def getAllSubjObjs = allSubjObjs.asJava
  def getAllDirectSubjectives = allDirectSubjectives.asJava
  def getAllExpressiveSubjectivities = allExpressiveSubjectivities.asJava
  def getAllObjectiveSpeechEvents = allObjectiveSpeechEvents.asJava
  def getAllETargets = allETargets.asJava
  def getAllSTargets = allSTargets.asJava
  def getAllTargetFrames = allTargetFrames.asJava
  //endregion



  //region API Methods for Users

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
      if subjObj.isInstanceOf[ObjectiveSpeechEvent]
    } yield subjObj.asInstanceOf[ObjectiveSpeechEvent]
  }

  def allETargets = allTargetFrames.flatMap(_.eTargets)

  def allSTargets = {
    allObjectiveSpeechEvents.filter(_.sTarget != null).map(_.sTarget) ++
      allTargetFrames.flatMap(_.sTargets)
  }

  def allTargetFrames = {
    allDirectSubjectives.flatMap(_.attitudes).map(_.targetFrame).filter(_ != null) ++
      allExpressiveSubjectivities.map(_.targetFrame).filter(_ != null)
  }

  //endregion

  //region Utility Functions


  /**
   * Loads the JSON files. The JSON files will be stored in `docJsonTexts`, in which
   * each pair has the document ID as the key, and the JSON file content as the value.   *
   *
   * @param pathToJsonDir The path to the JSON file directory.
   *                      This is usually the path to the `clean_anns/` directory.
   */
  private def loadZippedDocs(resourcePath: String): mutable.HashMap[String, String] = {

    val docs = scala.collection.mutable.HashMap[String, String]()

    val inputStream = this.getClass.getResourceAsStream(resourcePath)
    val zipInputStream = new ZipInputStream(inputStream)
    var curZipEntry = zipInputStream.getNextEntry
    while (curZipEntry != null) {
      if (!curZipEntry.isDirectory) {
        val zipEntryName = curZipEntry.getName
        val parts = zipEntryName.split('/')
        val docName = parts.takeRight(2).mkString("/")
        val sb = new StringBuilder()
        val scanner = new Scanner(zipInputStream, "UTF-8")
        while (scanner.hasNextLine) {
          sb append scanner.nextLine()
          sb append StandardStrings.NewLine
        }
        docs(docName) = sb.toString()
      }
      curZipEntry = zipInputStream.getNextEntry
    }
    docs
  }

  //endregion



  //region Parsing Methods for Option Values

  private def parseUncertainty(json: JValue): Uncertainty = {
    json match {
      case JString(s) ⇒ {
        if (s == "somewhat-uncertain") Uncertainty.SomewhatUncertain
        else throw new Exception(s"Unknown Uncertainty $s")
      }
      case JNull ⇒ null
      case JNothing ⇒ null
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

  private def parseImmediateSourceMention(json: JValue): ImmediateSourceMention = {
    if (json != null) {
      val nestedSource = parseNestedSource(json \ "nested_source")

      if (json \ "nested_source" == null) {
        val bp = 0
      }

      val span = parseSpan(json \ "span")
      val uncertainty = parseUncertainty(json \ "uncertainty")

      if (nestedSource != null) new ImmediateSourceMention(nestedSource, span, uncertainty) else null
    }
    else null
  }

  private def parseNestedSource(json: JValue): Seq[String] = {
    json match {
      case JArray(l) ⇒ {
        l.map(x ⇒ {
          val JString(s) = x
          s
        })
      }
      case JNothing ⇒ null
    }
  }

  private def parseSTarget(json: JValue, parent: HasSTarget): STarget = {
    if (json != JNull) {

      val JString(id) = json \ "identifier"
      val uncertainty = parseUncertainty(json \ "uncertainty")
      val span = parseSpan(json \ "span")
      val sTarget = new STarget(parent, id, span, new ArrayBuffer[ETarget], uncertainty)

      parent match {
        case tf: TargetFrame ⇒ sTarget.eTargets = (json \ "eTargets").children.map(e ⇒ parseETarget(e, sTarget))
        case _ ⇒ { }
      }

      sTarget
    }
    else null
  }

  private def parseETarget(json: JValue, parent: HasETarget): ETarget = {
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

                val immSourceMention = parseImmediateSourceMention(jsonAnnotation \ "immSourceMention")

                val scalaDirSubj = new DirectSubjective(
                  scalaSentence,
                  span,
                  nestedSource,
                  immSourceMention,
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
                val immSourceMention = parseImmediateSourceMention(jsonAnnotation \ "immSourceMention")

                val scalaExprSubj = new ExpressiveSubjectivity(
                  scalaSentence, nestedSource, immSourceMention, span, polarity, intensity, insubstantiality, null
                )

                // deal with target frame
                val scalaTargetFrame = parseTargetFrame(jsonAnnotation \ "targetFrame", scalaExprSubj)

                scalaExprSubj.targetFrame = scalaTargetFrame
                scalaAnnotations += scalaExprSubj
              }

              case JString("YObjSpeechEvent") ⇒ {
                val immSourceMention = parseImmediateSourceMention(jsonAnnotation \ "immSourceMention")

                val scalaObjSpeechEvent = new ObjectiveSpeechEvent(
                  scalaSentence, nestedSource, immSourceMention, span, null, insubstantiality
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
            val immSourceMention = parseImmediateSourceMention(x \ "immSourceMention")
            val curAtt = new Attitude(scalaDirSubj, id, nestedSource, immSourceMention, span, attitudeType, intensity, polarity, null)

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

}
