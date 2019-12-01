/*
 * Copyright 2012 Vahid Mavaji
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

package parsmorph.service;

import org.apache.commons.lang.ArrayUtils;
import parsmorph.dao.AffixAUXDao;
import parsmorph.dao.AffixDRVDao;
import parsmorph.dao.AffixINFLDao;
import parsmorph.dao.EntryDao;
import parsmorph.model.Affix;
import parsmorph.model.AffixType;
import parsmorph.model.Entry;
import parsmorph.model.SyntacticCategory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Vahid Mavaji
 */
public class MorphologicalAnalyser {

    public MorphologicalAnalyser() {
        init();
    }

    public void init() {
        initNounSuffixes();
        initAdjectiveSuffixes();
        initAdverbSuffixes();
        initNumberSuffixes();
        initVerbSuffixes();
        initVerbPrefixes();
        initDerivationals();
    }

    AffixINFLDao affixINFLDao = new AffixINFLDao();
    AffixDRVDao affixDRVDao = new AffixDRVDao();
    AffixAUXDao affixAUXDao = new AffixAUXDao();
    EntryDao entryDao = new EntryDao();

    Map<Integer, Affix[]> verbPrefixes = new HashMap<Integer, Affix[]>();
    Map<Integer, Affix[]> verbSuffixes = new HashMap<Integer, Affix[]>();
    Map<Integer, Affix[]> numberSuffixes = new HashMap<Integer, Affix[]>();
    Map<Integer, Affix[]> adverbSuffixes = new HashMap<Integer, Affix[]>();
    Map<Integer, Affix[]> adjectiveSuffixes = new HashMap<Integer, Affix[]>();
    Map<Integer, Affix[]> nounSuffixes = new HashMap<Integer, Affix[]>();

    Affix[] derivationalsSuffixes;
    Affix[] derivationalsPrefixes;

    public void initVerbPrefixes() {
        verbPrefixes.put(2, affixINFLDao.getByAffixType(new String[]{AffixType.NEM}));
        verbPrefixes.put(1, affixINFLDao.getByAffixType(new String[]{AffixType.IMM, AffixType.INM, AffixType.ISM}));
    }

    public void initVerbSuffixes() {
        verbSuffixes.put(3, affixINFLDao.getByAffixType(new String[]{AffixType.PEC, AffixType.SUC}));
        verbSuffixes.put(2, affixINFLDao.getByAffixType(new String[]{AffixType.PEI}));
        verbSuffixes.put(1, affixINFLDao.getByAffixType(new String[]{AffixType.PPM}));
    }

    public void initNumberSuffixes() {
        numberSuffixes.put(4, affixINFLDao.getByAffixType(new String[]{AffixType.COC}));
        numberSuffixes.put(3, affixINFLDao.getByAffixType(new String[]{AffixType.INM, AffixType.RCM, AffixType.PEC, AffixType.EZM}));
        numberSuffixes.put(2, affixINFLDao.getByAffixType(new String[]{AffixType.PLM}));
        numberSuffixes.put(1, affixINFLDao.getByAffixType(new String[]{AffixType.ONM}));
    }

    public void initAdverbSuffixes() {
        adverbSuffixes.put(2, affixINFLDao.getByAffixType(new String[]{AffixType.EZM, AffixType.PEC}));
        adverbSuffixes.put(1, affixINFLDao.getByAffixType(new String[]{AffixType.CAdM, AffixType.PLM}));
    }

    public void initAdjectiveSuffixes() {
        adjectiveSuffixes.put(4, affixINFLDao.getByAffixType(new String[]{AffixType.COC}));
        adjectiveSuffixes.put(3, affixINFLDao.getByAffixType(new String[]{AffixType.INM, AffixType.RCM, AffixType.PEC, AffixType.EZM}));
        adjectiveSuffixes.put(2, affixINFLDao.getByAffixType(new String[]{AffixType.PLM}));
        adjectiveSuffixes.put(1, affixINFLDao.getByAffixType(new String[]{AffixType.CAM, AffixType.SAM}));
    }

    public void initNounSuffixes() {
        nounSuffixes.put(3, affixINFLDao.getByAffixType(new String[]{AffixType.COC}));
        nounSuffixes.put(2, affixINFLDao.getByAffixType(new String[]{AffixType.IMM, AffixType.RCM, AffixType.PEC, AffixType.EZM}));
        nounSuffixes.put(1, affixINFLDao.getByAffixType(new String[]{AffixType.PLM}));
    }

    public void initDerivationals() {
        derivationalsSuffixes = affixDRVDao.getSuffixes();
        derivationalsPrefixes = affixDRVDao.getPrefixes();
    }

    public MorphemeHolder removeSuffix(String morpheme, Affix[] suffixes) {
        if (suffixes == null) {
            return new MorphemeHolder(morpheme, new Affix[0]);
        }

        Affix suffix = null;
        for (Affix s : suffixes) {
            String writtenForm = s.getWrittenForm();

            if (morpheme.endsWith(writtenForm)) {
                morpheme = morpheme.substring(0, morpheme.lastIndexOf(writtenForm));
                suffix = s;
                break;
            }
        }

        morpheme = morpheme.trim();
        return new MorphemeHolder(morpheme, new Affix[]{suffix});
    }

    public MorphemeHolder removePrefix(String morpheme, Affix[] prefixes) {
        Affix prefix = null;
        for (Affix p : prefixes) {
            String writtenForm = p.getWrittenForm();
            if (morpheme.startsWith(writtenForm)) {
                morpheme = morpheme.substring(writtenForm.length());
                prefix = p;
                break;
            }
        }

        morpheme = morpheme.trim();
        return new MorphemeHolder(morpheme, new Affix[]{prefix});
    }

    public Entry getEntry(String morpheme, String[] syntacticCategories) {
        Entry[] entries = entryDao.getByWrittenForm(morpheme);
        if (entries.length != 0) {
            for (Entry entry : entries) {
                if (ArrayUtils.contains(syntacticCategories, entry.getCategory().getCode())) {
                    return entry;
                }
            }
        }

        return null;
    }

    public Possibility analyseNoun(String morpheme) {
        List<Affix[]> affixesList = new ArrayList<Affix[]>();
        affixesList.add(nounSuffixes.get(3));
        affixesList.add(nounSuffixes.get(2));
        affixesList.add(nounSuffixes.get(1));

        Possibility possibility = analyseNonVerb(morpheme, SyntacticCategory.NOUNS, affixesList, true);
        if (possibility != null) {
            possibility.setCategory(Category.NOUN);
        }

        return possibility;
    }

    public Possibility analyseAdjective(String morpheme) {
        List<Affix[]> affixesList = new ArrayList<Affix[]>();
        affixesList.add(adjectiveSuffixes.get(4));
        affixesList.add(adjectiveSuffixes.get(3));
        affixesList.add(adjectiveSuffixes.get(2));
        affixesList.add(adjectiveSuffixes.get(1));

        Possibility possibility = analyseNonVerb(morpheme, SyntacticCategory.ADJECTIVES, affixesList);
        if (possibility != null) {
            possibility.setCategory(Category.ADJECTIVE);
        }

        return possibility;
    }

    public Possibility analyseAdverb(String morpheme) {
        List<Affix[]> affixesList = new ArrayList<Affix[]>();
        affixesList.add(adverbSuffixes.get(2));
        affixesList.add(adverbSuffixes.get(1));


        Possibility possibility = analyseNonVerb(morpheme, SyntacticCategory.ADVERBS, affixesList);
        if (possibility != null) {
            possibility.setCategory(Category.ADVERB);
        }

        return possibility;
    }

    public Possibility analyseNumber(String morpheme) {
        List<Affix[]> affixesList = new ArrayList<Affix[]>();
        affixesList.add(numberSuffixes.get(4));
        affixesList.add(numberSuffixes.get(3));
        affixesList.add(numberSuffixes.get(2));
        affixesList.add(numberSuffixes.get(1));

        Possibility possibility = analyseNonVerb(morpheme, SyntacticCategory.NUMBERS, affixesList);
        if (possibility != null) {
            possibility.setCategory(Category.NUMBER);
        }

        return possibility;
    }

    public int getMaxIter(List<Affix[]> affixesList) {
        return getMaxIter(affixesList, 10);
    }

    public int getMaxIter(List<Affix[]> affixesList, int minIter) {
        int maxIter = 1;
        for (Affix[] s1 : affixesList) {
            if (s1 != null && s1.length > 0) {
                maxIter *= s1.length;
            }
        }

        maxIter = Math.min(maxIter, minIter);
        return maxIter;
    }

    public Possibility analyseNonVerb(String morpheme, String[] syntacticCategories, List<Affix[]> affixes_list) {
        return analyseNonVerb(morpheme, syntacticCategories, affixes_list, false);
    }

    public Possibility analyseNonVerb(String morpheme, String[] syntacticCategories, List<Affix[]> affixesList, boolean patch) {
        Entry entry = getEntry(morpheme, syntacticCategories);
        if (entry != null) {
            return new Possibility(entry);
        }

        String originalMorpheme = morpheme;

        int maxIter = getMaxIter(affixesList);

        List<Affix> affixList = new ArrayList<Affix>();
        for (int j = 0; j < maxIter; j++) {
            for (Affix[] anAffixesList : affixesList) {
                MorphemeHolder morphemeHolder = removeSuffix(morpheme, anAffixesList);
                morpheme = morphemeHolder.getMorpheme();
                Affix al = morphemeHolder.getAffixes()[0];
                if (al != null) {
                    affixList.add(al);
                }
            }

            if (patch && affixList.size() != 0) {
                Affix lastAffix = affixList.get(affixList.size() - 1);
                if (lastAffix != null) {
                    if (lastAffix.getWrittenForm().equals("گان") || lastAffix.getWrittenForm().equals("کان")) {
                        morpheme += "ه";
                    }
                }
            }

            entry = getEntry(morpheme, syntacticCategories);
            if (entry != null) {
                return new Possibility(entry, affixList.toArray(new Affix[0]));
            }

            for (int i = 0; i < affixesList.size(); i++) {
                if (i >= affixList.size()) {
                    continue;
                }

                if (affixesList.contains(affixList.get(i))) {
                    affixesList.set(i, (Affix[]) ArrayUtils.removeElement(affixesList.get(i), affixList.get(i)));
                }
            }

            morpheme = originalMorpheme;
        }

        return null;
    }

    public MorphemeHolder removeAuxVerbEndings(String morpheme) {

        Affix[] endings = affixAUXDao.getAuxEndings();
        List<Affix> affixes = new ArrayList<Affix>();
        while (true) {
            boolean found = false;
            for (Affix aux : endings) {
                String writtenForm = aux.getWrittenForm();
                if (morpheme.endsWith(writtenForm)) {
                    morpheme = morpheme.substring(0, morpheme.lastIndexOf(writtenForm));
                    morpheme = morpheme.trim();

                    affixes.add(aux);

                    found = true;
                    break;
                }
            }

            if (!found) {
                break;
            }
        }

        return new MorphemeHolder(morpheme, affixes.toArray(new Affix[0]));
    }

    public MorphemeHolder removeAuxVerbBeginnings(String morpheme) {
        Affix[] beginnings = affixAUXDao.getAuxBeginnings();
        List<Affix> affixes = new ArrayList<Affix>();
        while (true) {
            boolean found = false;
            for (Affix aux : beginnings) {
                String writtenForm = aux.getWrittenForm();
                if (morpheme.startsWith(writtenForm)) {
                    morpheme = morpheme.substring(writtenForm.length());
                    morpheme = morpheme.trim();

                    affixes.add(aux);

                    found = true;
                    break;
                }
            }

            if (!found) {
                break;
            }
        }

        return new MorphemeHolder(morpheme, affixes.toArray(new Affix[0]));
    }

    public Possibility analyseVerb(String morpheme) {
        Entry entry = getEntry(morpheme, SyntacticCategory.VERBS);
        if (entry != null) {
            return new Possibility(Category.VERB, entry);
        }

        Affix[] auxiliariesPrefixes = new Affix[0];
        Affix[] auxiliariesSuffixes = new Affix[0];
        MorphemeHolder result = removeAuxVerbEndings(morpheme);
        morpheme = result.getMorpheme();
        Affix[] endings = result.getAffixes();
        if (endings != null) {
            auxiliariesSuffixes = (Affix[]) ArrayUtils.addAll(auxiliariesSuffixes, endings);
        }

        String originalMorpheme = morpheme;
        Affix[] suffixes3 = verbSuffixes.get(3);
        Affix[] suffixes2 = verbSuffixes.get(2);
        Affix[] suffixes1 = verbSuffixes.get(1);

        Affix[] prefixes2 = verbPrefixes.get(2);
        Affix[] prefixes1 = verbPrefixes.get(1);

        List<Affix[]> affixesList = new ArrayList<Affix[]>();
        affixesList.add(suffixes1);
        affixesList.add(suffixes2);
        affixesList.add(suffixes3);
        affixesList.add(prefixes1);
        affixesList.add(prefixes2);

        int maxIter = getMaxIter(affixesList);

        for (int i = 0; i < maxIter; i++) {
            Affix[] suffixes = new Affix[0];
            MorphemeHolder morphemeHolder = removeSuffix(morpheme, suffixes3);
            morpheme = morphemeHolder.getMorpheme();
            Affix suffix3 = morphemeHolder.getAffixes()[0];

            result = removeAuxVerbBeginnings(morpheme);
            morpheme = result.getMorpheme();
            Affix[] endings3 = result.getAffixes();

            morphemeHolder = removeSuffix(morpheme, suffixes2);
            morpheme = morphemeHolder.getMorpheme();
            Affix suffix2 = morphemeHolder.getAffixes()[0];

            result = removeAuxVerbEndings(morpheme);
            morpheme = result.getMorpheme();
            Affix[] endings2 = result.getAffixes();

            morphemeHolder = removeSuffix(morpheme, suffixes1);
            morpheme = morphemeHolder.getMorpheme();
            Affix suffix1 = morphemeHolder.getAffixes()[0];

            result = removeAuxVerbEndings(morpheme);
            morpheme = result.getMorpheme();
            Affix[] endings1 = result.getAffixes();

            morphemeHolder = removePrefix(morpheme, prefixes2);
            morpheme = morphemeHolder.getMorpheme();
            Affix prefix2 = morphemeHolder.getAffixes()[0];

            morphemeHolder = removePrefix(morpheme, prefixes1);
            morpheme = morphemeHolder.getMorpheme();
            Affix prefix1 = morphemeHolder.getAffixes()[0];

            result = removeAuxVerbBeginnings(morpheme);
            morpheme = result.getMorpheme();
            Affix[] beginnings = result.getAffixes();
            auxiliariesPrefixes = (Affix[]) ArrayUtils.addAll(auxiliariesPrefixes, beginnings);

            entry = getEntry(morpheme, SyntacticCategory.VERBS);
            if (entry != null) {
                Affix[] prefixes = new Affix[0];
                if (prefix2 != null) {
                    prefixes = (Affix[]) ArrayUtils.add(prefixes, prefix2);
                }

                if (prefix1 != null) {
                    prefixes = (Affix[]) ArrayUtils.add(prefixes, prefix1);
                }

                if (suffix3 != null) {
                    suffixes = (Affix[]) ArrayUtils.add(suffixes, suffix3);
                }

                if (endings3 != null) {
                    suffixes = (Affix[]) ArrayUtils.addAll(suffixes, endings3);
                }

                if (suffix2 != null) {
                    suffixes = (Affix[]) ArrayUtils.add(suffixes, suffix2);
                }

                if (endings2 != null) {
                    suffixes = (Affix[]) ArrayUtils.addAll(suffixes, endings2);
                }

                if (suffix1 != null) {
                    suffixes = (Affix[]) ArrayUtils.add(suffixes, suffix1);
                }

                if (endings1 != null) {
                    suffixes = (Affix[]) ArrayUtils.addAll(suffixes, endings1);
                }

                return new Possibility(Category.VERB, entry, null, prefixes, suffixes,
                        auxiliariesSuffixes, auxiliariesPrefixes, null, null, null);
            }

            try {
                suffixes3 = (Affix[]) ArrayUtils.removeElement(suffixes3, suffix3);
                suffixes2 = (Affix[]) ArrayUtils.removeElement(suffixes2, suffix2);
                suffixes1 = (Affix[]) ArrayUtils.removeElement(suffixes1, suffix1);
            } catch (Exception e) {
                e.printStackTrace();
            }

            morpheme = originalMorpheme;
        }

        return null;
    }

    public DerivationHolder analyseDerivationalSuffixes(String morpheme) {
        Affix[] derivationalSuffixes = new Affix[0];
        String originalMorpheme = morpheme;
        for (Affix derivational : derivationalsSuffixes) {
            String writtenForm = derivational.getWrittenForm();
            if (morpheme.endsWith(writtenForm)) {
                morpheme = morpheme.substring(0, morpheme.lastIndexOf(writtenForm));
                Entry[] entries = entryDao.getByWrittenForm(morpheme);
                if (entries.length == 0) {
                    morpheme = originalMorpheme;
                } else {
                    originalMorpheme = morpheme;
                    morpheme = entries[0].getWrittenForm();
                    derivationalSuffixes = (Affix[]) ArrayUtils.add(derivationalSuffixes, derivational);
                }
            }
        }

        return new DerivationHolder(morpheme, derivationalSuffixes);
    }

    public DerivationHolder analyseDerivationalPrefixes(String morpheme) {
        Affix[] derivationalPrefixes = new Affix[0];
        String originalMorpheme = morpheme;
        for (Affix derivational : derivationalsPrefixes) {
            String writtenForm = derivational.getWrittenForm();
            if (morpheme.startsWith(writtenForm)) {
                morpheme = morpheme.substring(writtenForm.length());
                Entry[] entries = entryDao.getByWrittenForm(morpheme);
                if (entries.length == 0) {
                    morpheme = originalMorpheme;
                } else {
                    originalMorpheme = morpheme;
                    morpheme = entries[0].getWrittenForm();
                    derivationalPrefixes = (Affix[]) ArrayUtils.add(derivationalPrefixes, derivational);
                }
            }
        }
        return new DerivationHolder(morpheme, derivationalPrefixes);
    }

    public Possibility analyseDerivationals(Possibility possibility) {
        String morpheme = possibility.getStem().getWrittenForm();

        DerivationHolder derivationHolder = analyseDerivationalPrefixes(morpheme);
        morpheme = derivationHolder.getMorpheme();
        Affix[] derivationalPrefixes = derivationHolder.getDerivationalAffixes();

        derivationHolder = analyseDerivationalSuffixes(morpheme);
        morpheme = derivationHolder.getMorpheme();
        Affix[] derivationalSuffixes = derivationHolder.getDerivationalAffixes();

        if (derivationalSuffixes.length == 0) {
            derivationalSuffixes = null;
        }

        if (derivationalPrefixes.length == 0) {
            derivationalPrefixes = null;
        }

        if (entryDao.getByWrittenForm(morpheme).length == 0) {
            return null;
        }

        possibility.setDerivationalSuffixes(derivationalSuffixes);
        possibility.setDerivationalPrefixes(derivationalPrefixes);
        possibility.setRoot(entryDao.getByWrittenForm(morpheme)[0]);

        return possibility;
    }

    public Possibility analyseCompounds(Possibility possibility, String originalMorpheme) {
        List<Entry[]> compounds = new ArrayList<Entry[]>();
        String morpheme;
        if (possibility != null) {
            if (possibility.getRoot() != null) {
                morpheme = possibility.getRoot().getWrittenForm();
            } else {
                morpheme = possibility.getStem().getWrittenForm();
            }
        } else if (originalMorpheme != null) {
            morpheme = originalMorpheme;
        } else {
            return null;
        }

        String morphemeTrim = morpheme.replaceAll(" ", "");

        int max = morphemeTrim.length();
        for (int i = 2; i < max; i++) {
            String morpheme1 = morphemeTrim.substring(0, i);
            String morpheme2 = morphemeTrim.substring(i, max);
            Entry[] entries1 = entryDao.getByWrittenForm(morpheme1);
            Entry[] entries2 = entryDao.getByWrittenForm(morpheme2);
            if (entries1.length == 0 || entries2.length == 0) {
                continue;
            } else {
                compounds.add(new Entry[]{entries1[0], entries2[0]});
            }
        }

        if (possibility == null) {
            Entry stem = new Entry();
            stem.setWrittenForm(morpheme);
            possibility = new Possibility(stem);
        }
        possibility.setCompounds(compounds);

        return possibility;
    }

    public Possibility[] analyse(String morpheme) {
        if (morpheme.equals("")) {
            return null;
        }

        morpheme = morpheme.replaceAll("\u200c", "");
        morpheme = morpheme.replaceAll("\u200e", "");
        morpheme = morpheme.replaceAll("\u200f", "");
        morpheme = morpheme.trim();

        Possibility[] possibilities = new Possibility[0];

        Possibility nounPossibility = analyseNoun(morpheme);
        Possibility numberPossibility = analyseNumber(morpheme);
        Possibility adjectivePossibility = analyseAdjective(morpheme);
        Possibility adverbPossibility = analyseAdverb(morpheme);
        Possibility verbPossibility = analyseVerb(morpheme);

        if (nounPossibility != null) {
            possibilities = (Possibility[]) ArrayUtils.add(possibilities, nounPossibility);
        }

        if (numberPossibility != null) {
            possibilities = (Possibility[]) ArrayUtils.add(possibilities, numberPossibility);
        }

        if (adjectivePossibility != null) {
            possibilities = (Possibility[]) ArrayUtils.add(possibilities, adjectivePossibility);
        }

        if (adverbPossibility != null) {
            possibilities = (Possibility[]) ArrayUtils.add(possibilities, adverbPossibility);
        }

        if (verbPossibility != null) {
            possibilities = (Possibility[]) ArrayUtils.add(possibilities, verbPossibility);
        }

        Possibility[] resultDeriv = new Possibility[0];
        for (Possibility possibility : possibilities) {
            resultDeriv = (Possibility[]) ArrayUtils.add(resultDeriv, analyseDerivationals(possibility));
        }
        if (possibilities.length == 0) {
            Entry stem = new Entry();
            stem.setWrittenForm(morpheme);
            Possibility possibility = new Possibility(stem);
            resultDeriv = (Possibility[]) ArrayUtils.add(resultDeriv, analyseDerivationals(possibility));
        }

        Possibility[] result = new Possibility[0];
        if (resultDeriv.length > 0) {
            for (Possibility possibility : resultDeriv) {
                Possibility p = analyseCompounds(possibility, null);
                if (p != null) {
                    result = (Possibility[]) ArrayUtils.add(result, p);
                }
            }
        }

        Possibility compoundPossibility = analyseCompounds(null, morpheme);
        if (compoundPossibility != null && compoundPossibility.getCompounds() != null && compoundPossibility.getCompounds().size() > 0) {
            result = (Possibility[]) ArrayUtils.add(result, compoundPossibility);
        }

        return result;
    }
}
