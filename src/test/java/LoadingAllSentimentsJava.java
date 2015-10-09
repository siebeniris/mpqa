/**
 * Created by Yuhuan Jiang (jyuhuan@gmail.com) on 10/9/15.
 */

import edu.pitt.mpqa.Mpqa;
import edu.pitt.mpqa.node.Attitude;
import edu.pitt.mpqa.node.DirectSubjective;
import edu.pitt.mpqa.node.Sentence;
import edu.pitt.mpqa.option.AttitudeType;

import java.util.List;
import java.util.stream.Collectors;

public class LoadingAllSentimentsJava {

    public static void main(String[] args) {

        List<DirectSubjective> allDS = Mpqa.getDocuments().stream()
                .flatMap(document -> document.getSentences().stream())
                .flatMap(sentence -> sentence.getSubjObjs().stream())
                .filter(subjObj -> subjObj instanceof DirectSubjective)
                .map(subjObj -> (DirectSubjective)subjObj)
                .collect(Collectors.toList());

        List<Attitude> allSentiments = allDS.stream()
                .flatMap(ds -> ds.getAttitudes().stream())
                .filter(attitude -> attitude.getAttitudeType().equals(AttitudeType.Sentiment))
                .collect(Collectors.toList());

    }

}
