import edu.pitt.mpqa._
import me.yuhuan.util.io.TextFile

/**
 * Created by Yuhuan Jiang (jyuhuan@gmail.com) on 10/8/15.
 */

object LoadingAllSentiments {

  def main(args: Array[String]) {

    MpqaConfig.GatePluginsHome = "/Applications/GATE_Developer_8.0/plugins/"
    MpqaConfig.GateSiteConfigFile = "/Applications/GATE_Developer_8.0/gate.xml/"
    MpqaConfig.MpqaXmlDir = "/Users/yuhuan/Dropbox/Projects/MPQA3.0_NAACL2015/man_anns/"

    val x = Mpqa.allDocuments


    val bp = 0

  }

}
