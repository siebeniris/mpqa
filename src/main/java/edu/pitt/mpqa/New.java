package edu.pitt.mpqa;

import edu.pitt.mpqa.core.Span;
import edu.pitt.mpqa.node.*;
import edu.pitt.mpqa.option.*;

/**
 * @author Yuhuan Jiang (jyuhuan@gmail.com).
 */
public class New {

    public static Agent Agent() {
        return new Agent(null, null, null);
    }

    public static Agent Agent(Document parent, String id, Span span) {
        return new Agent(parent, id, span);
    }


    public static Document Document() {
        return new Document(null, null, null, null);
    }

    public static Document Document(String name, String text, java.util.List<Agent> agents, java.util.List<Sentence> sentences) {
        Document result = New.Document();
        result.setName(name);
        result.setText(text);
        result.setAgents(agents);
        result.setSentences(sentences);
        return result;
    }


    public static Sentence Sentence() {
        return new Sentence(null, null, null);
    }

    public static Sentence Sentence(Span span, java.util.List<SubjObj> subjObjs, Document parent) {
        Sentence result = New.Sentence();
        result.setSpan(span);
        result.setSubjObjs(subjObjs);
        result.setParent(parent);
        return result;
    }


    public static DirectSubjective DirectSubjective() {
        return new DirectSubjective(null, null, null, null, null, null, null, null);
    }

    public static DirectSubjective DirectSubjective(Span span,
                                                    String id,
                                                    NestedSource nestedSource,
                                                    java.util.List<Attitude> attitudes,
                                                    ExpressionIntensity expressionIntensity,
                                                    Intensity intensity,
                                                    Insubstantiality insubstantiality,
                                                    Sentence parent) {
        DirectSubjective result = New.DirectSubjective();
        result.setSpan(span);
        result.setId(id);
        result.setNestedSource(nestedSource);
        result.setAttitudes(attitudes);
        result.setExpressionIntensity(expressionIntensity);
        result.setIntensity(intensity);
        result.setInsubstantiality(insubstantiality);
        result.setParent(parent);
        return result;
    }


    public static ExpressiveSubjectivity ExpressiveSubjectivity() {
        return new ExpressiveSubjectivity(null, null, null, null, null, null, null, null);
    }

    public static ExpressiveSubjectivity ExpressiveSubjectivity(Span span,
                                                                String id,
                                                                NestedSource nestedSource,
                                                                TargetFrame targetFrame,
                                                                Polarity polarity,
                                                                Intensity intensity,
                                                                Insubstantiality insubstantiality,
                                                                Sentence parent) {
        ExpressiveSubjectivity result = New.ExpressiveSubjectivity();
        result.setSpan(span);
        result.setId(id);
        result.setNestedSource(nestedSource);
        result.setTargetFrame(targetFrame);
        result.setPolarity(polarity);
        result.setIntensity(intensity);
        result.setInsubstantiality(insubstantiality);
        result.setParent(parent);
        return result;
    }


    public static ObjectiveSpeech ObjectiveSpeech() {
        return new ObjectiveSpeech(null, null, null, null, null, null);
    }

    public static ObjectiveSpeech ObjectiveSpeech(Span span,
                                                  String id,
                                                  NestedSource nestedSource,
                                                  TargetFrame targetFrame,
                                                  Insubstantiality insubstantiality,
                                                  Sentence parent) {
        ObjectiveSpeech result = New.ObjectiveSpeech();
        result.setSpan(span);
        result.setId(id);
        result.setNestedSource(nestedSource);
        result.setTargetFrame(targetFrame);
        result.setInsubstantiality(insubstantiality);
        result.setParent(parent);
        return result;
    }


    public static Attitude Attitude() {
        return new Attitude(null, null, null, null, null, null, null, null);
    }

    public static Attitude Attitude(Span span,
                                    String id,
                                    NestedSource nestedSource,
                                    TargetFrame targetFrame,
                                    AttitudeType attitudeType,
                                    Intensity intensity,
                                    Polarity polarity,
                                    DirectSubjective parent
                                    ) {
        Attitude result = New.Attitude();
        result.setSpan(span);
        result.setId(id);
        result.setNestedSource(nestedSource);
        result.setTargetFrame(targetFrame);
        result.setType(attitudeType);
        result.setIntensity(intensity);
        result.setPolarity(polarity);
        result.setParent(parent);
        return result;
    }


    public static TargetFrame TargetFrame() {
        return new TargetFrame(null, null, null, null);
    }

    public static TargetFrame TargetFrame(String id,
                                          java.util.List<STarget> sTargets,
                                          java.util.List<ETarget> newETargets,
                                          HasTargetFrame parent) {
        TargetFrame result = New.TargetFrame();
        result.setId(id);
        result.setSTargets(sTargets);
        result.setNewETargets(newETargets);
        result.setParent(parent);
        return result;
    }


    public static STarget STarget() {
        return new STarget(null, null, null, null, null);
    }

    public static STarget STarget(Span span,
                                  String id,
                                  Uncertainty uncertainty,
                                  java.util.List<ETarget> eTargets,
                                  TargetFrame parent) {
        STarget result = New.STarget();
        result.setSpan(span);
        result.setId(id);
        result.setUncertainty(uncertainty);
        result.setETargets(eTargets);
        result.setParent(parent);
        return result;
    }


    public static ETarget ETarget() {
        return new ETarget(null, null, null, null, null, null);
    }

    public static ETarget ETarget(Span span,
                                  String id,
                                  ETargetType eTargetType,
                                  NegatedOption isNegated,
                                  ReferredInSpanOption isReferredInSpan,
                                  java.util.List<HasETarget> parent) {
        ETarget result = New.ETarget();
        result.setSpan(span);
        result.setId(id);
        result.setType(eTargetType);
        result.setIsNegated(isNegated);
        result.setIsReferredInSpan(isReferredInSpan);
        result.setParent(parent);
        return result;
    }

    public static NestedSource NestedSource() {
        return new NestedSource(null, null, null);
    }

    public static NestedSource NestedSource(Span spanOfImmediateSource,
                                            java.util.List<String> agentIDs,
                                            Uncertainty uncertainty) {
        NestedSource result = New.NestedSource();
        result.setSpanOfImmediateSource(spanOfImmediateSource);
        result.setAgentIDs(agentIDs);
        result.setUncertainty(uncertainty);
        return result;
    }

}
