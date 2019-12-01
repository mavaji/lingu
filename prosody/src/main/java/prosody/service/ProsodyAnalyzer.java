package prosody.service;

import org.apache.commons.lang.ArrayUtils;
import parsmorph.service.PhonemeAnalyzer;
import prosody.dao.RhythmBean;
import prosody.model.Rhythm;

/**
 * @author Vahid Mavaji
 */
public class ProsodyAnalyzer {
    private PhonemeAnalyzer phonemeAnalyzer = new PhonemeAnalyzer();
    private char[] shortVowels = new char[]{'a', 'e', 'o'};
    private char[] longVowels = new char[]{'â', 'i', 'u'};
    private RhythmBean rhythmBean = new RhythmBean();
    private Rhythm[] rhythms;

    public ProsodyAnalyzer() {
        init();
    }

    public void init() {
        rhythms = rhythmBean.getAllRhythms();
    }

    public PhonemeHolder[] getProsody(String poem, boolean phoneme) {
        PhonemeHolder[] phonemeHolders = segment(poem, phoneme);

        PhonemeHolder[] results = new PhonemeHolder[0];
        for (PhonemeHolder phonemeHolder : phonemeHolders) {
            int min = Integer.MAX_VALUE;
            int index = 0;

            for (int i = 0; i < rhythms.length; i++) {
                int distance = getLevenshteinDistance(phonemeHolder.getSegment(), rhythms[i].getSymbol());
                if (distance < min) {
                    min = distance;
                    index = i;
                }
            }

            results = (PhonemeHolder[]) ArrayUtils.add(results,
                    new PhonemeHolder(phonemeHolder.getPhonologicalForm(),
                            phonemeHolder.getSegment(), rhythms[index]));
        }

        return results;
    }

    public PhonemeHolder[] segment(String poem, boolean phoneme) {
        String[] phonologicalForms;
        if (phoneme) {
            phonologicalForms = new String[]{poem};
        } else {
            phonologicalForms = phonemeAnalyzer.textToPhoneme(poem);
        }

        PhonemeHolder[] phonemeHolders = new PhonemeHolder[0];
        for (String phonologicalForm : phonologicalForms) {

            String result = "";
            String[] syllables = syllables(phonologicalForm);
            for (int i = 1; i < syllables.length; i++) {
                result = getRhythmicSyllable(syllables[i]) + result;
            }
//            segments = (String[]) ArrayUtils.add(segments, result + "-");
            phonemeHolders = (PhonemeHolder[]) ArrayUtils.add(phonemeHolders, new PhonemeHolder(phonologicalForm, result));
        }
        return phonemeHolders;
    }

    public String[] syllables(String phonologicalForm) {
        phonologicalForm = phonologicalForm.replaceAll(" ", "");
        if (ArrayUtils.contains(shortVowels, phonologicalForm.charAt(0)) || ArrayUtils.contains(longVowels, phonologicalForm.charAt(0))) {
            phonologicalForm = "ʔ" + phonologicalForm;
        }
        /****/
//        phonologicalForm = phonologicalForm.replaceAll("ʔ", "");
//        phonologicalForm = phonologicalForm.replaceAll("va", "o");
        phonologicalForm = phonologicalForm.replaceAll("beh", "be");
        /****/
        String[] syllables = new String[0];
        for (int i = phonologicalForm.length() - 1; i >= 1; i--) {
            char c = phonologicalForm.charAt(i);

            if (ArrayUtils.contains(shortVowels, c) || ArrayUtils.contains(longVowels, c)) {
                String syllable = phonologicalForm.substring(i - 1);
                syllables = (String[]) ArrayUtils.add(syllables, syllable);

                if (i >= 1) {
                    phonologicalForm = phonologicalForm.substring(0, i - 1);
                    i = i - 1;
                }
            }
        }

        return syllables;
    }

    public String getSyllable(String s) {
        String syllable = "C";

        if (ArrayUtils.contains(shortVowels, s.charAt(1))) {
            syllable += "V";
        } else {
            syllable += "L";
        }

        for (int i = 2; i < s.length(); i++) {
            syllable += "C";
        }

        return syllable;
    }

    public String getRhythmicSyllable(String s) {
        String result = "";

        String syllable = getSyllable(s);

        if (syllable.equals("CV")) {
            result = "U";
        } else if (syllable.equals("CL") || syllable.equals("CVC")) {
            result = "-";
        } else if (syllable.equals("CVCC") || syllable.equals("CLC") || syllable.equals("CLCC")) {
            result = "-U";
        }

        return result;
    }

    public static int getLevenshteinDistance(String s, String t) {
        if (s == null || t == null) {
            throw new IllegalArgumentException("Strings must not be null");
        }

        /*
          The difference between this impl. and the previous is that, rather 
           than creating and retaining a matrix of size s.length()+1 by t.length()+1, 
           we maintain two single-dimensional arrays of length s.length()+1.  The first, d,
           is the 'current working' distance array that maintains the newest distance cost
           counts as we iterate through the characters of String s.  Each time we increment
           the index of String t we are comparing, d is copied to p, the second int[].  Doing so
           allows us to retain the previous cost counts as required by the algorithm (taking 
           the minimum of the cost count to the left, up one, and diagonally up and to the left
           of the current cost count being calculated).  (Note that the arrays aren't really 
           copied anymore, just switched...this is clearly much better than cloning an array 
           or doing a System.arraycopy() each time  through the outer loop.)
      
           Effectively, the difference between the two implementations is this one does not 
           cause an out of memory condition when calculating the LD over two very large strings.  		
        */

        int n = s.length(); // length of s
        int m = t.length(); // length of t

        if (n == 0) {
            return m;
        } else if (m == 0) {
            return n;
        }

        int p[] = new int[n + 1]; //'previous' cost array, horizontally
        int d[] = new int[n + 1]; // cost array, horizontally
        int _d[]; //placeholder to assist in swapping p and d

        // indexes into strings s and t
        int i; // iterates through s
        int j; // iterates through t

        char t_j; // jth character of t

        int cost; // cost

        for (i = 0; i <= n; i++) {
            p[i] = i;
        }

        for (j = 1; j <= m; j++) {
            t_j = t.charAt(j - 1);
            d[0] = j;

            for (i = 1; i <= n; i++) {
                cost = s.charAt(i - 1) == t_j ? 0 : 1;
                // minimum of cell to the left+1, to the top+1, diagonally left and up +cost				
                d[i] = Math.min(Math.min(d[i - 1] + 1, p[i] + 1), p[i - 1] + cost);
            }

            // copy current distance counts to 'previous row' distance counts
            _d = p;
            p = d;
            d = _d;
        }

        // our last action in the above loop was to switch d and p, so p now 
        // actually has the most recent cost counts
        return p[n];
    }
}
