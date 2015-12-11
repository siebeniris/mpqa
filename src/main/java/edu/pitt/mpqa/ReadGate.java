package edu.pitt.mpqa;

import java.io.File;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import gate.Annotation;
import gate.AnnotationSet;
import gate.Document;
import gate.Factory;
import gate.FeatureMap;
import gate.Gate;
import gate.Utils;
import gate.util.GateException;

import edu.pitt.mpqa.core.Span;
import edu.pitt.mpqa.node.*;
import edu.pitt.mpqa.option.*;

/**
 * @author Lingjia Deng.
 */
public class ReadGate {
	
	public static String readText(String pathToGateXml) throws MalformedURLException, GateException{
		File f  = new File(pathToGateXml);
		Gate.init();			
		Document doc = (Document) 
				Factory.createResource("gate.corpora.DocumentImpl", 
				  Utils.featureMap(gate.Document.DOCUMENT_URL_PARAMETER_NAME, 
				f.toURI().toURL(),
				gate.Document.DOCUMENT_ENCODING_PARAMETER_NAME, "UTF-8")); 
		
		return doc.getContent().toString();
	}
	
	public static List<Sentence> readGateAnnotations(String pathToGateXml) throws GateException, MalformedURLException{
		File f  = new File(pathToGateXml);
		Gate.init();			
		Document doc = (Document) 
				Factory.createResource("gate.corpora.DocumentImpl", 
				  Utils.featureMap(gate.Document.DOCUMENT_URL_PARAMETER_NAME, 
				f.toURI().toURL(),
				gate.Document.DOCUMENT_ENCODING_PARAMETER_NAME, "UTF-8")); 
			
		doc.setMarkupAware(new Boolean(false));
		AnnotationSet annos = doc.getAnnotations("MPQA");
		
		HashMap<String, ETarget> eTargets = new HashMap<String, ETarget>();
		// eTarget
		for (Annotation anno:annos.get("eTarget")){
			Span span = new Span(Math.toIntExact(anno.getStartNode().getOffset()), 
					Math.toIntExact(anno.getEndNode().getOffset()));
			
			ETarget eTarget = New.ETarget();
			eTarget.setSpan(span);
			eTarget.setParents(new ArrayList<HasETarget>());
			
			FeatureMap features = anno.getFeatures();
			for (Object feature:features.keySet()){
				String featureName = feature.toString();
				String featureValue = features.get(featureName).toString();
				if (featureName.equals("id"))
					eTarget.setId(featureValue);
				else if (featureName.equals("isNegated")){
					if (featureValue.toLowerCase().equals("yes"))
						eTarget.setIsNegated(NegatedOption.Yes);
					else	
						eTarget.setIsNegated(NegatedOption.No);
				}
				else if (featureName.equals("isReferredInSpan")){
					if (featureValue.toLowerCase().equals("yes"))
						eTarget.setIsReferredInSpan(ReferredInSpanOption.Yes);
					else
						eTarget.setIsReferredInSpan(ReferredInSpanOption.No);
				}
				else if (featureName.equals("type")){
					if (featureValue.toLowerCase().equals("entity"))
						eTarget.setType(ETargetType.Entity);
					else if (featureValue.toLowerCase().equals("event"))
						eTarget.setType(ETargetType.Event);
					else
						eTarget.setType(ETargetType.Other);
				}
			}
			eTargets.put(eTarget.getId(), eTarget);
		}  // eTarget
		
		// sTarget
		HashMap<String, STarget> sTargets = new HashMap<String, STarget>();
		for(Annotation anno:annos.get("sTarget")){
			Span span = new Span(Math.toIntExact(anno.getStartNode().getOffset()), 
					Math.toIntExact(anno.getEndNode().getOffset()));
			
			STarget sTarget = New.STarget();
			sTarget.setSpan(span);
			ArrayList<ETarget> tmpETargets = new ArrayList<ETarget>();
			
			FeatureMap features = anno.getFeatures();
			for (Object feature:features.keySet()){
				String featureName = feature.toString();
				String featureValue = features.get(featureName).toString();
				if (featureName.equals("id"))
					sTarget.setId(featureValue);
				else if (featureName.equals("eTarget-link")){
					String[] eTargetIds = featureValue.split(",");
					for(String eTargetId:eTargetIds){
						if ( !eTargetId.isEmpty() && eTargetId.length()!=0){
							if (eTargetId.equals("none"))
								continue;
							if (eTargets.containsKey(eTargetId)) {
								tmpETargets.add(eTargets.get(eTargetId));
								if (!eTargets.get(eTargetId).getParents().contains(sTarget))
									eTargets.get(eTargetId).getParents().add(sTarget);
							}
						}
					}  // each eTargetId
					sTarget.setETargets(tmpETargets);
				}
			}
			sTargets.put(sTarget.getId(), sTarget);
		}    // sTarget
		
		// targetFrame
		HashMap<String, TargetFrame> targetFrames = new HashMap<String, TargetFrame>();
		for (Annotation anno:annos.get("targetFrame")){
			TargetFrame targetFrame = New.TargetFrame();
			List<STarget> tmpSTargets = new ArrayList<STarget>();
			List<ETarget> tmpETargets = new ArrayList<ETarget>();
			
			FeatureMap features = anno.getFeatures();
			for (Object feature:features.keySet()){
				String featureName = feature.toString();
				String featureValue = features.get(featureName).toString();
				if (featureName.equals("id"))
					targetFrame.setId(featureValue);
				else if (featureName.equals("newETarget-link")){
					String[] eTargetIds = featureValue.split(",");
					for(String eTargetId:eTargetIds){
						if ( !eTargetId.isEmpty() && eTargetId.length()!=0){
							if (eTargetId.equals("none"))
								continue;
							if (eTargets.containsKey(eTargetId)) {
								tmpETargets.add(eTargets.get(eTargetId));
								if (!eTargets.get(eTargetId).getParents().contains(targetFrame))
									eTargets.get(eTargetId).getParents().add(targetFrame);
							}
						}
					}  // each eTargetId
					targetFrame.setNewETargets(tmpETargets);
				}
				else if (featureName.equals("sTarget-link")){
					String[] sTargetIds = featureValue.split(",");
					for(String sTargetId:sTargetIds){
						if ( !sTargetId.isEmpty() && sTargetId.length()!=0){
							if (sTargetId.equals("none"))
								continue;
							if (sTargets.containsKey(sTargetId)){
								tmpSTargets.add(sTargets.get(sTargetId));
								sTargets.get(sTargetId).setParent(targetFrame);
							}
							
						}
					}  // each eTargetId
					targetFrame.setSTargets(tmpSTargets);
				}
			}
			targetFrames.put(targetFrame.getId(), targetFrame);
		}   // target frame
		
		// nested-source and agent
		ArrayList<NestedSource> sources = new ArrayList<NestedSource>();
		ArrayList<Agent> agents = new ArrayList<Agent>();
		
		for (Annotation anno:annos.get("agent")){
			Span span = new Span(Math.toIntExact(anno.getStartNode().getOffset()), 
					Math.toIntExact(anno.getEndNode().getOffset()));
			
			NestedSource source = New.NestedSource();
			source.setSpanOfImmediateSource(span);
			
			Agent agent = New.Agent();
			agent.setSpan(span);
			
			FeatureMap features = anno.getFeatures();
			for (Object feature:features.keySet()){
				String featureName = feature.toString();
				String featureValue = features.get(featureName).toString();
				
				if (featureName.equals("nested-source")){
					String[] ids = featureValue.split(",");
					List<String> tmpIds = new ArrayList<String>();
					for (String id:ids)
						if (!id.isEmpty())
							tmpIds.add(id);
					source.setAgentIDs(tmpIds);
				}
				else if (featureName.equals("id")){
					agent.setId(featureValue);
				}
				else if (featureName.contains("uncertain")){
					source.setUncertainty(Uncertainty.SomewhatUncertain);
				}		
			}
			
			if (features.keySet().contains("nested-source"))
				sources.add(source);
			if (features.keySet().contains("id"))
				agents.add(agent);
			// add the writer and implicit agent id as sources
			if (span.start()==0 && span.end()==0){
				List<String> agentIds = new ArrayList<String>();
				agentIds.add(agent.getId());
				source.setAgentIDs(agentIds);
				Span spanTmp = new Span(0,0);
				source.setSpanOfImmediateSource(spanTmp);
				sources.add(source);
			}
		}
		
		// attitude
		HashMap<String, Attitude> attitudes = new HashMap<String, Attitude>();
		for (Annotation anno:annos.get("attitude")){
			Span span = new Span(Math.toIntExact(anno.getStartNode().getOffset()), 
					Math.toIntExact(anno.getEndNode().getOffset()));
			
			Attitude attitude = New.Attitude();
			attitude.setSpan(span);
			
			FeatureMap features = anno.getFeatures();
			boolean flag = false;
			for (Object feature:features.keySet()){
				String featureName = feature.toString();
				String featureValue = features.get(featureName).toString();
				if (featureName.equals("id"))
					attitude.setId(featureValue);
				else if (featureName.equals("attitude-type")){
					// type
					if (featureValue.startsWith("sentiment"))
						attitude.setType(AttitudeType.Sentiment);
					else if (featureValue.startsWith("arguing"))
						attitude.setType(AttitudeType.Arguing);
					else if (featureValue.startsWith("intention"))
						attitude.setType(AttitudeType.Intention);
					else if (featureValue.startsWith("interior"))
						attitude.setType(AttitudeType.Interior);
					else if (featureValue.startsWith("speculation"))
						attitude.setType(AttitudeType.Speculation);
					else if (featureValue.startsWith("agree"))
						attitude.setType(AttitudeType.Agree);
					else
						attitude.setType(AttitudeType.Other);
					// polarity
					if (featureValue.contains("pos") && featureValue.contains("uncertain")){
						attitude.setPolarity(Polarity.UncertainPositive);
					}
					else if (featureValue.contains("pos") && !featureValue.contains("uncertain")){
						attitude.setPolarity(Polarity.Positive);
					}
					else if (featureValue.contains("neg") && featureValue.contains("uncertain")){
						attitude.setPolarity(Polarity.UncertainNegative);
					}
					else if (featureValue.contains("neg") && !featureValue.contains("uncertain")){
						attitude.setPolarity(Polarity.Negative);
					}
					else if (featureValue.contains("neu") && featureValue.contains("uncertain")){
						attitude.setPolarity(Polarity.UncertainNeutral);
					}
					else if (featureValue.contains("neu") && !featureValue.contains("uncertain")){
						attitude.setPolarity(Polarity.Neutral);
					}
					else if (featureValue.contains("both") && featureValue.contains("uncertain")){
						attitude.setPolarity(Polarity.UncertainBoth);
					}
					else if (featureValue.contains("both") && !featureValue.contains("uncertain")){
						attitude.setPolarity(Polarity.Both);
					}
				}
				else if (featureName.equals("targetFrame-link")){
					flag = true;
					if (featureValue.equals("none"))
						attitude.setTargetFrame(null);
					if (targetFrames.containsKey(featureValue)){
						TargetFrame tmpTF = targetFrames.get(featureValue);
						attitude.setTargetFrame(tmpTF);
						targetFrames.get(featureValue).setParent(attitude);
					}
				}
				else if (featureName.equals("intensity")){
					if (featureValue.equals("low"))
						attitude.setIntensity(Intensity.Low);
					else if (featureValue.equals("low-medium"))
						attitude.setIntensity(Intensity.LowMedium);
					else if (featureValue.equals("medium"))
						attitude.setIntensity(Intensity.Medium);
					else if (featureValue.equals("medium-high"))
						attitude.setIntensity(Intensity.MediumHigh);
					else if (featureValue.equals("high"))
						attitude.setIntensity(Intensity.High);
					else if (featureValue.equals("high-extreme"))
						attitude.setIntensity(Intensity.HighExtreme);
					else if (featureValue.equals("extreme"))
						attitude.setIntensity(Intensity.Extreme);
					else if (featureValue.equals("neutral"))
						attitude.setIntensity(Intensity.Neutral);
				}
				else if (featureName.equals("nested-source")){
					// later we get the nested source of attitudes from direct subjs
				}
			}  // each feature
			attitudes.put(attitude.getId(), attitude);
		} // attitude
		
		// ose
		ArrayList<ObjectiveSpeech> oses = new ArrayList<ObjectiveSpeech>();
		for (Annotation anno:annos.get("objective-speech-event")){
			Span span = new Span(Math.toIntExact(anno.getStartNode().getOffset()), 
					Math.toIntExact(anno.getEndNode().getOffset()));
			
			ObjectiveSpeech ose = New.ObjectiveSpeech();
			ose.setSpan(span);
			
			FeatureMap features = anno.getFeatures();
			for (Object feature:features.keySet()){
				String featureName = feature.toString();
				String featureValue = features.get(featureName).toString();
				if (featureName.equals("id"))
					ose.setId(featureValue);
				else if (featureName.equals("insubstantial")){
					if (featureValue.toLowerCase().equals("c1"))
						ose.setInsubstantiality(Insubstantiality.C1);
					else if (featureValue.toLowerCase().equals("c2"))
						ose.setInsubstantiality(Insubstantiality.C2);
					else if (featureValue.toLowerCase().equals("c3"))
						ose.setInsubstantiality(Insubstantiality.C3);
				}
				else if (featureName.equals("targetFrame-link")){
					if (featureValue.equals("none"))
						ose.setTargetFrame(null);
					if (targetFrames.containsKey(featureValue)){
						ose.setTargetFrame(targetFrames.get(featureValue));
						targetFrames.get(featureValue).setParent(ose);
					}
				}
				else if (featureName.equals("nested-source")){
					// temporarily we create a fake NestedSource, recording the agent ids only
					NestedSource tmp = New.NestedSource();
					List<String> agentIds = new ArrayList<String>();
					for(String agentId:featureValue.split(",")){
						if (!agentId.isEmpty() && agentId.length()>0)
							agentIds.add(agentId);
					}
					tmp.setAgentIDs(agentIds);
					ose.setNestedSource(tmp);
				}
			}
			
			oses.add(ose);
		}
		
		// ese
		ArrayList<ExpressiveSubjectivity> eses = new ArrayList<ExpressiveSubjectivity>();
		for(Annotation anno:annos.get("expressive-subjectivity")){
			Span span = new Span(Math.toIntExact(anno.getStartNode().getOffset()), 
						Math.toIntExact(anno.getEndNode().getOffset()));
				
			ExpressiveSubjectivity ese = New.ExpressiveSubjectivity();
			ese.setSpan(span);
			
			FeatureMap features = anno.getFeatures();
			for (Object feature:features.keySet()){
				String featureName = feature.toString();
				String featureValue = features.get(featureName).toString();
				if (featureName.equals("id"))
					ese.setId(featureValue);
				else if (featureName.equals("targetFrame-link")){
					if (featureValue.equals("none"))
						ese.setTargetFrame(null);
					else if (targetFrames.containsKey(featureValue)){
						ese.setTargetFrame(targetFrames.get(featureValue));
						targetFrames.get(featureValue).setParent(ese);
					}
				}
				else if (featureName.equals("polarity")){
					if (featureValue.contains("pos") && featureValue.contains("uncertain")){
						ese.setPolarity(Polarity.UncertainPositive);
					}
					else if (featureValue.contains("pos") && !featureValue.contains("uncertain")){
						ese.setPolarity(Polarity.Positive);
					}
					else if (featureValue.contains("neg") && featureValue.contains("uncertain")){
						ese.setPolarity(Polarity.UncertainNegative);
					}
					else if (featureValue.contains("neg") && !featureValue.contains("uncertain")){
						ese.setPolarity(Polarity.Negative);
					}
					else if (featureValue.contains("neu") && featureValue.contains("uncertain")){
						ese.setPolarity(Polarity.UncertainNeutral);
					}
					else if (featureValue.contains("neu") && !featureValue.contains("uncertain")){
						ese.setPolarity(Polarity.Neutral);
					}
					else if (featureValue.contains("both") && featureValue.contains("uncertain")){
						ese.setPolarity(Polarity.UncertainBoth);
					}
					else if (featureValue.contains("both") && !featureValue.contains("uncertain")){
						ese.setPolarity(Polarity.Both);
					}
				}
				else if (featureName.equals("intensity")){
					if (featureValue.equals("low"))
						ese.setIntensity(Intensity.Low);
					else if (featureValue.equals("low-medium"))
						ese.setIntensity(Intensity.LowMedium);
					else if (featureValue.equals("medium"))
						ese.setIntensity(Intensity.Medium);
					else if (featureValue.equals("medium-high"))
						ese.setIntensity(Intensity.MediumHigh);
					else if (featureValue.equals("high"))
						ese.setIntensity(Intensity.High);
					else if (featureValue.equals("high-extreme"))
						ese.setIntensity(Intensity.HighExtreme);
					else if (featureValue.equals("extreme"))
						ese.setIntensity(Intensity.Extreme);
					else if (featureValue.equals("neutral"))
						ese.setIntensity(Intensity.Neutral);
				}
				else if (featureName.equals("insubstantial")){
					if (featureValue.toLowerCase().equals("c1"))
						ese.setInsubstantiality(Insubstantiality.C1);
					else if (featureValue.toLowerCase().equals("c2"))
						ese.setInsubstantiality(Insubstantiality.C2);
					else if (featureValue.toLowerCase().equals("c3"))
						ese.setInsubstantiality(Insubstantiality.C3);
				}
				else if (featureName.equals("nested-source")){
					// temporarily we create a fake NestedSource, recording the agent ids only
					NestedSource tmp = New.NestedSource();
					List<String> agentIds = new ArrayList<String>();
					for(String agentId:featureValue.split(",")){
						if (!agentId.isEmpty() && agentId.length()>0)
							agentIds.add(agentId);
					}
					tmp.setAgentIDs(agentIds);
					ese.setNestedSource(tmp);
				}
			}
			eses.add(ese);
		}
		
		// ds
		ArrayList<DirectSubjective> dss = new ArrayList<DirectSubjective>();
		for(Annotation anno:annos.get("direct-subjective")){
			Span span = new Span(Math.toIntExact(anno.getStartNode().getOffset()), 
						Math.toIntExact(anno.getEndNode().getOffset()));
				
			DirectSubjective ds = New.DirectSubjective();
			ds.setSpan(span);
			
			FeatureMap features = anno.getFeatures();
			for (Object feature:features.keySet()){
				String featureName = feature.toString();
				String featureValue = features.get(featureName).toString();
				
				if (featureName.equals("id"))
					ds.setId(featureValue);
				else if (featureName.equals("attitude-link")){
					String[] ids = featureValue.split(",");
					List<Attitude> tmpAttitudes = new ArrayList<Attitude>();
					for(String id:ids){
						if (!id.isEmpty() && id.length()!=0){
							if (id.equals("none"))
								continue;
							if (attitudes.containsKey(id)){
								tmpAttitudes.add(attitudes.get(id));
								attitudes.get(id).setParent(ds);
							}
						}
					}
					ds.setAttitudes(tmpAttitudes);
				}
				else if (featureName.equals("intensity")){
					if (featureValue.equals("low"))
						ds.setIntensity(Intensity.Low);
					else if (featureValue.equals("low-medium"))
						ds.setIntensity(Intensity.LowMedium);
					else if (featureValue.equals("medium"))
						ds.setIntensity(Intensity.Medium);
					else if (featureValue.equals("medium-high"))
						ds.setIntensity(Intensity.MediumHigh);
					else if (featureValue.equals("high"))
						ds.setIntensity(Intensity.High);
					else if (featureValue.equals("high-extreme"))
						ds.setIntensity(Intensity.HighExtreme);
					else if (featureValue.equals("extreme"))
						ds.setIntensity(Intensity.Extreme);
					else if (featureValue.equals("neutral"))
						ds.setIntensity(Intensity.Neutral);
				}
				else if (featureName.equals("expression-intensity")){
					if (featureValue.equals("low"))
						ds.setExpressionIntensity(ExpressionIntensity.Low);
					else if (featureValue.equals("medium"))
						ds.setExpressionIntensity(ExpressionIntensity.Medium);
					else if (featureValue.equals("high"))
						ds.setExpressionIntensity(ExpressionIntensity.High);
					else if (featureValue.equals("extreme"))
						ds.setExpressionIntensity(ExpressionIntensity.Extreme);
					else if (featureValue.equals("neutral"))
						ds.setExpressionIntensity(ExpressionIntensity.Neutral);
				}
				else if (featureName.equals("insubstantial")){
					if (featureValue.toLowerCase().equals("c1"))
						ds.setInsubstantiality(Insubstantiality.C1);
					else if (featureValue.toLowerCase().equals("c2"))
						ds.setInsubstantiality(Insubstantiality.C2);
					else if (featureValue.toLowerCase().equals("c3"))
						ds.setInsubstantiality(Insubstantiality.C3);
				}
				else if (featureName.equals("nested-source")){
					// temporarily we create a fake NestedSource, recording the agent ids only
					NestedSource tmp = New.NestedSource();
					List<String> agentIds = new ArrayList<String>();
					for(String agentId:featureValue.split(",")){
						if (!agentId.isEmpty() && agentId.length()>0)
							agentIds.add(agentId);
					}
					tmp.setAgentIDs(agentIds);
					ds.setNestedSource(tmp);
				}
			}  // each feature
			dss.add(ds);
		}  // ds
		
		// sentence
		List<Sentence> sentences = new ArrayList<Sentence>();
		
		for (Annotation anno:annos.get("sentence")){
			Span span = new Span(Math.toIntExact(anno.getStartNode().getOffset()), 
					Math.toIntExact(anno.getEndNode().getOffset()));
			
			Sentence sentence = New.Sentence();
			sentence.setSpan(span);
			
			List<SubjObj> subjobjs = new ArrayList<SubjObj>();
			// put ds
			for(DirectSubjective ds:dss){
				if (span.subsumes(ds.getSpan()) ){
					// insert ds sorted by start position
					int n=subjobjs.size();
					if (n<1)
						subjobjs.add(ds);
					else{
						int i=0;
						for (;i<n;i++){
							if (ds.getSpan().start()<subjobjs.get(i).getSpan().start()){
								subjobjs.add(i, ds);
								break;
							}
						}
						if (i==n)
							subjobjs.add(ds);
					}

					ds.setParent(sentence);
					NestedSource nestedSource = findNestedSource(ds, sources);
					ds.setNestedSource(nestedSource);
					if (ds.getAttitudes() != null && ds.getAttitudes().size() >0)
						for(Attitude attitude:ds.getAttitudes()){
							attitude.setNestedSource(nestedSource);
						}
				}
			}
			
			// put ese
			for(ExpressiveSubjectivity ese:eses){
				if (span.subsumes(ese.getSpan())){
					// insert ese sorted by start position
					int n=subjobjs.size();
					if (n<1)
						subjobjs.add(ese);
					else{
						int i=0;
						for (;i<n;i++){
							if (ese.getSpan().start()<subjobjs.get(i).getSpan().start()){
								subjobjs.add(i, ese);
								break;
							}
						}
						if (i==n)
							subjobjs.add(ese);
					}

					ese.setParent(sentence);
					NestedSource nestedSource = findNestedSource(ese, sources);
					ese.setNestedSource(nestedSource);
				}
			}
			
			// put ose
			for(ObjectiveSpeech ose:oses){
				if (span.subsumes(ose.getSpan()) ){
					// insert ose sorted by start position
					int n=subjobjs.size();
					if (n<1)
						subjobjs.add(ose);
					else{
						int i=0;
						for (;i<n;i++){
							if (ose.getSpan().start()<subjobjs.get(i).getSpan().start()){
								subjobjs.add(i, ose);
								break;
							}
						}
						if (i==n)
							subjobjs.add(ose);
					}

					ose.setParent(sentence);
					NestedSource nestedSource = findNestedSource(ose, sources);
					ose.setNestedSource(nestedSource);
				}
			}
			
			sentence.setSubjObjs(subjobjs);
			
			// insert sentence sorted by start position
			int n=sentences.size();
			if (n<1)
				sentences.add(sentence);
			else{
				int i=0;
				for (;i<n;i++){
					if (sentence.getSpan().start()<sentences.get(i).getSpan().start()){
						sentences.add(i, sentence);
						break;
					}
				}
				if (i==n)
					sentences.add(sentence);
			}
		}
		
		return sentences;
	}
	
	private static NestedSource findNestedSource(SubjObj subjObj, ArrayList<NestedSource> sources){
		ArrayList<NestedSource> sourcesInSentence = new ArrayList<NestedSource>();
		for (NestedSource source:sources){
			if (subjObj.getParent().getSpan().subsumes(source.getSpanOfImmediateSource()))
				sourcesInSentence.add(source);
			if (source.getSpanOfImmediateSource().start()==0 && source.getSpanOfImmediateSource().end()==0)
				sourcesInSentence.add(source);
		}
		NestedSource candidate = findNestedSourceExactMatch(subjObj,sourcesInSentence);
		if (candidate == null)
			candidate = findNestedSourceIgnoringDuplicateAgents(subjObj,sourcesInSentence);
		if (candidate == null)
			candidate = findNestedSourceIgnoringDuplicateAgents(subjObj,sources);

		return candidate;
	}

	private static NestedSource findNestedSourceExactMatch(SubjObj subjObj, ArrayList<NestedSource> sources){
		ArrayList<NestedSource> candidates = new ArrayList<NestedSource>();

		String agentIdsAsString = String.join(",", subjObj.getNestedSource().getAgentIDs());
		for (NestedSource source:sources){
			if (String.join(",",source.getAgentIDs()).equals(agentIdsAsString)) {
				int distance;
				if (source.getSpanOfImmediateSource().start()<subjObj.getSpan().start()) {
					distance = Math.abs(subjObj.getSpan().start() - source.getSpanOfImmediateSource().end());
				}
				else {
					distance = Math.abs(source.getSpanOfImmediateSource().start() - subjObj.getSpan().end());
				}
				// insert source sorted by distance
				int n=candidates.size();
				if (n<1)
					candidates.add(source);
				else {
					int i = 0;
					for (; i < n; i++) {
						int distanceOfIthCandidate;
						if (candidates.get(i).getSpanOfImmediateSource().start()<subjObj.getSpan().start()) {
							distanceOfIthCandidate = Math.abs(subjObj.getSpan().start() - candidates.get(i).getSpanOfImmediateSource().end());
						}
						else {
							distanceOfIthCandidate = Math.abs(candidates.get(i).getSpanOfImmediateSource().start() - subjObj.getSpan().end());
						}

						if (distance < distanceOfIthCandidate) {
							candidates.add(i, source);
							break;
						}
					}
					if (i == n)
						candidates.add(source);
				}
			}  // same agent id
		}  // each source

		// if we haven't find any exactly matching source
		if (candidates.size()==0){
			return null;
		}
		else
			return candidates.get(0);

	}

	private static NestedSource findNestedSourceIgnoringDuplicateAgents(SubjObj subjObj, ArrayList<NestedSource> sources){
		ArrayList<NestedSource> candidates = new ArrayList<NestedSource>();

		// we don't consider duplicate agent ids
		HashSet<String> uniqueAgentIds = new HashSet<String>();
		for(String agentId:subjObj.getNestedSource().getAgentIDs()){
			uniqueAgentIds.add(agentId);
		}
		String agentIdsAsString = String.join(",", uniqueAgentIds);
		for (NestedSource source:sources){
			HashSet<String> uniqueAgentIdsInSource = new HashSet<String>();
			for(String agentId:source.getAgentIDs()){
				uniqueAgentIdsInSource.add(agentId);
			}
			String agentIdsAsStringInSource = String.join(",", uniqueAgentIdsInSource);
			if (agentIdsAsStringInSource.equals(agentIdsAsString)) {
				int distance;
				if (source.getSpanOfImmediateSource().start()<subjObj.getSpan().start()) {
					distance = Math.abs(subjObj.getSpan().start() - source.getSpanOfImmediateSource().end());
				}
				else {
					distance = Math.abs(source.getSpanOfImmediateSource().start() - subjObj.getSpan().end());
				}
				// insert source sorted by distance
				int n=candidates.size();
				if (n<1)
					candidates.add(source);
				else {
					int i = 0;
					for (; i < n; i++) {
						int distanceOfIthCandidate;
						if (candidates.get(i).getSpanOfImmediateSource().start()<subjObj.getSpan().start()) {
							distanceOfIthCandidate = Math.abs(subjObj.getSpan().start() - candidates.get(i).getSpanOfImmediateSource().end());
						}
						else {
							distanceOfIthCandidate = Math.abs(candidates.get(i).getSpanOfImmediateSource().start() - subjObj.getSpan().end());
						}

						if (distance < distanceOfIthCandidate ) {
							candidates.add(i, source);
							break;
						}
					}
					if (i == n)
						candidates.add(source);
				}
			}  // same agent id
		}  // each source

		if (candidates.size()==0)
			return null;
		else
			return candidates.get(0);

	}

}

	