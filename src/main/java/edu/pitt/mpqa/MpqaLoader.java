package edu.pitt.mpqa;

import java.io.File;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

import edu.pitt.mpqa.node.Document;
import edu.pitt.mpqa.node.Sentence;
import edu.pitt.mpqa.node.SubjObj;
import gate.Annotation;
import gate.AnnotationSet;
import gate.Gate;
import gate.util.GateException;

/**
 * @author Lingjia Deng.
 */
public class MpqaLoader {
	
	public static void main(String[] args) throws MalformedURLException, GateException{
        Gate.setPluginsHome(new File("/Applications/GATE_Developer_7.0/plugins"));
        Gate.setSiteConfigFile(new File("/Applications/GATE_Developer_7.0/gate.xml"));

        Document d = load("/Users/Lingjia/Dropbox/MPQA3.0_NAACL2015/man_anns/temp_fbis/20.46.58-22510/annotatedBeforePublish.xml");
		verify(d);
	}
	
	public static Document load(String pathToGateXml) throws MalformedURLException, GateException{
		Document document = New.Document();
		
		document.setName(pathToGateXml);
		document.setText(ReadGate.readText(pathToGateXml));
		document.setSentences(ReadGate.readGateAnnotations(pathToGateXml));
        for(Sentence sentence:document.getSentences()){
			sentence.setParent(document);
        }
        
		return document;
	}
	
	public static void verify(Document document){
		System.out.println(document.getSentences().size());
		for(Sentence sentence:document.getSentences()){
			System.out.println(document.getText().substring(sentence.getSpan().start(), sentence.getSpan().end()));
			System.out.println(sentence.getSubjObjs().size());
			for (SubjObj subjObj:sentence.getSubjObjs()){
				if (subjObj.getNestedSource() != null){
					System.out.print("<");
					System.out.print(document.getText().substring(subjObj.getNestedSource().getSpanOfImmediateSource().start(), 
							subjObj.getNestedSource().getSpanOfImmediateSource().end()));
					System.out.print(">");
					System.out.println(subjObj.getNestedSource().getSpanOfImmediateSource().start()+","+subjObj.getNestedSource().getSpanOfImmediateSource().end());
				}
				else{
					System.out.println("< null >");
				}
				System.out.print("=== ");
				System.out.print(subjObj.getSpan().start()+","+subjObj.getSpan().end()+"  ");
				System.out.print(document.getText().substring(subjObj.getSpan().start(), 
						subjObj.getSpan().end()));
				System.out.println("===");
			}
		}
		
		return;
	}
	
	public static ArrayList<Document> load(List<String> pathsToGateXml) throws MalformedURLException, GateException {
		ArrayList<Document> result = new ArrayList<Document>();
		for (String p: pathsToGateXml) result.add(load(p));
		return result;
	}

}
