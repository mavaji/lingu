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

import parsmorph.model.Affix;

/**
 * @author Vahid Mavaji
 */
public class MorphemeHolder {
    private String morpheme;
    private Affix[] affixes;

    public MorphemeHolder(String morpheme, Affix[] affixes) {
        this.morpheme = morpheme;
        this.affixes = affixes;
    }

    public String getMorpheme() {
        return morpheme;
    }

    public void setMorpheme(String morpheme) {
        this.morpheme = morpheme;
    }

    public Affix[] getAffixes() {
        return affixes;
    }

    public void setAffixes(Affix[] affixes) {
        this.affixes = affixes;
    }
}
