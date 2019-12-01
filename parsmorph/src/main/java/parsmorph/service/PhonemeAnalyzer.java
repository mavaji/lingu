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
import org.apache.commons.lang.StringUtils;

/**
 * @author Vahid Mavaji
 */
public class PhonemeAnalyzer {
    public String[] textToPhoneme(String text) {
        String[] chunks = text.split(" ");
        MorphologicalAnalyser morphologicalAnalyser = new MorphologicalAnalyser();

        String chunkPhonemes[][] = new String[chunks.length][];

        for (int i = 0; i < chunks.length; i++) {
            String chunk = chunks[i];

            Possibility[] possibilities = morphologicalAnalyser.analyse(chunk);

            String lastPholonology = null;
            for (Possibility possibility : possibilities) {
                String toPhonology = possibility.toPhonology();
                if (!toPhonology.equals(lastPholonology) && !StringUtils.isBlank(toPhonology)) {
                    chunkPhonemes[i] = (String[]) ArrayUtils.add(chunkPhonemes[i], toPhonology);
                    lastPholonology = toPhonology;
                }
            }
        }

        String[] phonemes = {};
        for (String[] chunkPhoneme : chunkPhonemes) {
            if (chunkPhoneme == null) {
                continue;
            }
            for (int i = 0; i < chunkPhoneme.length - 1; i++) {
                phonemes = (String[]) ArrayUtils.addAll(phonemes, phonemes);
            }
            if (phonemes.length == 0) {
                phonemes = (String[]) ArrayUtils.addAll(phonemes, chunkPhoneme);
            } else {
                for (int i = 0; i < chunkPhoneme.length; i++) {
                    String c = chunkPhoneme[i];
                    int index = i * (phonemes.length / chunkPhoneme.length);
                    for (int j = index; j < index + (phonemes.length / chunkPhoneme.length); j++) {
                        phonemes[j] += " " + c;
                    }
                }
            }
        }
        return phonemes;
    }
}
