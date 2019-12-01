package ganjoor.app;

import ganjoor.dao.CategoryDao;
import ganjoor.dao.PoemDao;
import ganjoor.dao.PoetDao;
import ganjoor.dao.VerseDao;
import ganjoor.model.Poet;
import ganjoor.model.Verse;
import org.apache.commons.lang.ArrayUtils;
import parsmorph.dao.AffixDRVDao;
import parsmorph.dao.EntryDao;
import parsmorph.model.Affix;
import parsmorph.model.Entry;
import parsmorph.model.SyntacticCategory;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Vahid Mavaji
 */
public class App {

    public static void main(String[] args) {
        CategoryDao categoryDao = new CategoryDao();

        PoetDao poetDao = new PoetDao();

        PoemDao poemDao = new PoemDao();

        VerseDao verseDao = new VerseDao();

        AffixDRVDao affixDRVDao = new AffixDRVDao();

        EntryDao entryDao = new EntryDao();


        countAffixesForPoems(verseDao, affixDRVDao, entryDao);
        getProductivity(verseDao, affixDRVDao, entryDao);
        countByCentury(verseDao);
    }

    public static void countByCentury(VerseDao verseDao) {
        for (long i = 4; i <= 14; i++) {
            Long c = verseDao.countByCentury(i);
            System.out.println(i + " = " + c + " | " + c / 336985D);
        }

    }

    public static void getProductivity(VerseDao verseDao, AffixDRVDao affixDRVDao, EntryDao entryDao) {

        Affix[] derivationals = affixDRVDao.getSuffixes();
        try {
            String fileName = "g:/poets/" + "poets" + ".txt";
            BufferedWriter bw = new BufferedWriter(new FileWriter(fileName));

            for (Long century = 4L; century <= 14L; century++) {

                for (Affix affix : derivationals) {
                    Map<String, Long> frequencies = new HashMap<String, Long>();

                    Verse[] verses = verseDao.getByPoetAndText(century, affix.getWrittenForm());
                    for (Verse verse : verses) {
                        String[] words = verse.getText().split(" ");
                        for (String word : words) {
                            if (word.endsWith(affix.getWrittenForm())) {
                                String stem = word.substring(0, word.lastIndexOf(affix.getWrittenForm()));
                                Entry[] entries = entryDao.getByWrittenForm(stem);
                                if (entries.length != 0) {
                                    Entry entry = entries[0];
                                    if (ArrayUtils.contains(SyntacticCategory.ADJECTIVES, entry.getCategory().getCode())
                                            || ArrayUtils.contains(SyntacticCategory.ADVERBS, entry.getCategory().getCode())
                                            || ArrayUtils.contains(SyntacticCategory.NOUNS, entry.getCategory().getCode())
                                            || ArrayUtils.contains(SyntacticCategory.VERBS, entry.getCategory().getCode())) {
                                        if (frequencies.containsKey(word)) {
                                            frequencies.put(word, frequencies.get(word) + 1);
                                        } else {
                                            frequencies.put(word, 1L);
                                        }
                                    }
                                }
                            }
                        }
                    }

                    Double hapaxeCount = 0D;
                    Double tokenCount = 0D;
                    for (String key : frequencies.keySet()) {
                        Long c = frequencies.get(key);
                        tokenCount += c;
                        if (c == 1) {
                            hapaxeCount++;
                        }

                    }
                    Double productivity = 0D;
                    if (tokenCount != 0) {
                        productivity = hapaxeCount / tokenCount;
                    }
                    bw.write(century + "," + affix.getWrittenForm() + "," + productivity);
                    bw.write("\r\n");
                }
            }

            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private static void countAffixesForPoems(VerseDao verseDao, AffixDRVDao affixDRVDao, EntryDao entryDao) {
        Affix[] derivationals = affixDRVDao.getSuffixes();
        derivationals = (Affix[]) ArrayUtils.subarray(derivationals, 0, 5);

        Long[] poets = Poet.getAllPoets();

        for (Long poetId : poets) {
            Map<Affix, Long> counts = new HashMap<Affix, Long>();

            for (Affix affix : derivationals) {
                long count = 0;
                Verse[] verses = verseDao.getByPoetAndText(poetId, affix.getWrittenForm());

                for (Verse verse : verses) {
                    String[] words = verse.getText().split(" ");
                    for (String word : words) {
                        if (word.endsWith(affix.getWrittenForm())) {
                            Entry[] entries = entryDao.getByWrittenForm(word.substring(0, word.lastIndexOf(affix.getWrittenForm())));
                            if (entries.length != 0) {
                                Entry entry = entries[0];
                                if (ArrayUtils.contains(SyntacticCategory.NOUNS, entry.getCategory().getCode()) ||
                                        ArrayUtils.contains(SyntacticCategory.VERBS, entry.getCategory().getCode())) {
//                                System.out.println(verse.getText());
//                                System.out.println("------------------------------------");
//                                System.out.println(word);
//                                System.out.println("------------------------------------");
//                                System.out.println(affix.getWrittenForm());
//                                System.out.println("**************************************************");
                                    count++;
                                }
                            }
                        }
                    }
                }

                if (count != 0) {
                    counts.put(affix, count);
                }
            }

            try {
                String fileName = "g:/poets/" + Poet.getPoetCodes().get(poetId) + ".txt";
                BufferedWriter bw = new BufferedWriter(new FileWriter(fileName));

                for (Affix affix : counts.keySet()) {
//                System.out.println(affix.getWrittenForm()+" -> "+counts.get(affix));
                    bw.write(affix.getWrittenForm() + ", " + counts.get(affix));
                    bw.write("\r\n");
                }

                bw.close();
            } catch (IOException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }
        }
    }


}
