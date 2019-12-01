package ganjoor.web;

import core.util.ResourceBundleUtil;
import ganjoor.service.GanjoorAnalyzer;
import org.apache.commons.lang.StringUtils;
import parsmorph.model.Affix;
import parsmorph.service.Possibility;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import java.util.List;

/**
 * @author Vahid Mavaji
 */
@ManagedBean(name = "controllerBean")
@RequestScoped
public class ControllerBean {
    private GanjoorAnalyzer analyser;

    private String inputText;

    private String resultText;

    public GanjoorAnalyzer getAnalyser() {
        return analyser;
    }

    public void setAnalyser(GanjoorAnalyzer analyser) {
        this.analyser = analyser;
    }

    public String getInputText() {
        return inputText;
    }

    public void setInputText(String inputText) {
        this.inputText = inputText;
    }

    public String getResultText() {
        return resultText;
    }

    public void setResultText(String resultText) {
        this.resultText = resultText;
    }

    public String analyse() {
        if (StringUtils.isBlank(inputText)) {
            resultText = ResourceBundleUtil.getResource("nothing.found");
            return "success";
        }

        StringBuffer sb = new StringBuffer();
        List<Possibility> possibilities = analyser.analyze(inputText);

        for (Possibility possibility : possibilities) {
            sb.append(ResourceBundleUtil.getResource("syntactic.category"));
            sb.append(": ");
            sb.append(possibility.getStem().getCategory().getName());
            sb.append("\n");

            sb.append(ResourceBundleUtil.getResource("written.form"));
            sb.append(": ");
            sb.append(possibility.getStem().getWrittenForm());
            sb.append("\n");
            sb.append(ResourceBundleUtil.getResource("phonological.form"));
            sb.append(": ");
            sb.append(possibility.getStem().getPhonologicalForm());
            sb.append("\n");
            sb.append(ResourceBundleUtil.getResource("stress.pattern"));
            sb.append(": ");
            sb.append(possibility.getStem().getStressPattern());
            sb.append("\n");
            sb.append(ResourceBundleUtil.getResource("frequency"));
            sb.append(": ");
            sb.append(possibility.getStem().getFreq());
            sb.append("\n");

            if (possibility.getPrefixes() != null && possibility.getPrefixes().length != 0) {
                sb.append("\n");
                sb.append(ResourceBundleUtil.getResource("prefixes"));
                sb.append(": \n");
                for (Affix prefix : possibility.getPrefixes()) {
                    sb.append(prefix.getWrittenForm());
                    sb.append("(");
                    sb.append(prefix.getPhonologicalForm());
                    sb.append(")");
                    sb.append(": ");
                    sb.append(prefix.getAffixType().getName());
                    sb.append("\n");
                }
            }

//            if (possibility.getSpecials() != null && !possibility.getSpecials().isEmpty()) {
//                sb.append("\n");
//                sb.append(ResourceBundleUtil.getResource("specials"));
//                sb.append(": \n");
//                for (Affix special : possibility.getSpecials()) {
//                    sb.append(special.getWrittenForm());
//                    sb.append("(");
//                    sb.append(special.getPhonologicalForm());
//                    sb.append(")");
//                    sb.append(": ");
//                    sb.append(special.getAffixType().getName());
//                    sb.append("\n");
//                }
//            }

            if (possibility.getSuffixes() != null && possibility.getSuffixes().length != 0) {
                sb.append("\n");
                sb.append(ResourceBundleUtil.getResource("suffixes"));
                sb.append(": \n");
                for (Affix suffix : possibility.getSuffixes()) {
                    sb.append(suffix.getWrittenForm());
                    sb.append("(");
                    sb.append(suffix.getPhonologicalForm());
                    sb.append(")");
                    sb.append(": ");
                    sb.append(suffix.getAffixType().getName());
                    sb.append("\n");
                }
            }


            for (int i = 0; i < 130; i++) {
                sb.append("-");
            }
            sb.append("\n");
        }

        resultText = sb.toString();
        if (StringUtils.isBlank(resultText)) {
            resultText = ResourceBundleUtil.getResource("nothing.found");
            return "success";
        }
        return "success";
    }
}
