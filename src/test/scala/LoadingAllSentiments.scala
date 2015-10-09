import edu.pitt.mpqa.Mpqa
import edu.pitt.mpqa.node.DirectSubjective
import edu.pitt.mpqa.option.{AttitudeType, Intensity}
import edu.pitt.mpqa.Mpqa._

/**
 * Created by Yuhuan Jiang (jyuhuan@gmail.com) on 10/8/15.
 */

object LoadingAllSentiments {

  def main(args: Array[String]) {

    val allDS = for {
      document ← Mpqa.documents
      sentence ← document.sentences
      subjObj ← sentence.subjObjs
      if subjObj.isInstanceOf[DirectSubjective]
    } yield subjObj.asInstanceOf[DirectSubjective]


    val allSentiments = for {
      ds ← allDS
      attitude ← ds.attitudes
      if attitude.attitudeType == AttitudeType.Sentiment
    } yield attitude


    val bp = 0
  }

}
