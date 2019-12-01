package prosody.service;

import prosody.model.Rhythm;

/**
 * Created by IntelliJ IDEA.
 * User: vahid
 * Date: 5/18/12
 * Time: 8:20 PM
 * To change this template use File | Settings | File Templates.
 */
public class PhonemeHolder {
    private String phonologicalForm;
    private String segment;
    private Rhythm rhythm;

    public PhonemeHolder() {
    }

    public PhonemeHolder(String phonologicalForm, String segment) {
        this.phonologicalForm = phonologicalForm;
        this.segment = segment;
    }

    public PhonemeHolder(String phonologicalForm, String segment, Rhythm rhythm) {
        this.phonologicalForm = phonologicalForm;
        this.segment = segment;
        this.rhythm = rhythm;
    }

    public String getPhonologicalForm() {
        return phonologicalForm;
    }

    public void setPhonologicalForm(String phonologicalForm) {
        this.phonologicalForm = phonologicalForm;
    }

    public String getSegment() {
        return segment;
    }

    public void setSegment(String segment) {
        this.segment = segment;
    }

    public Rhythm getRhythm() {
        return rhythm;
    }

    public void setRhythm(Rhythm rhythm) {
        this.rhythm = rhythm;
    }
}
