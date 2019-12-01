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

package parsmorph.model;

import core.model.BaseEntity;

import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;

/**
 * @author Vahid Mavaji
 */
@MappedSuperclass
public abstract class Affix extends BaseEntity {
    private String phonologicalForm;
    private Integer position;
    private String stressPattern;
    private Boolean vowel;
    private Integer affixLen;
    private SyntacticCategory stemCategory;
    private AffixType affixType;
    private String writtenForm;

    @Column(name = "phonologicalForm")
    public String getPhonologicalForm() {
        return phonologicalForm;
    }

    public void setPhonologicalForm(String phonologicalForm) {
        this.phonologicalForm = phonologicalForm;
    }

    @Column(name = "position")
    public Integer getPosition() {
        return position;
    }

    public void setPosition(Integer position) {
        this.position = position;
    }

    @Column(name = "stressPattern")
    public String getStressPattern() {
        return stressPattern;
    }

    public void setStressPattern(String stressPattern) {
        this.stressPattern = stressPattern;
    }

    @Column(name = "vowel")
    public Boolean getVowel() {
        return vowel;
    }

    public void setVowel(Boolean vowel) {
        this.vowel = vowel;
    }

    @Column(name = "affixLen")
    public Integer getAffixLen() {
        return affixLen;
    }

    public void setAffixLen(Integer affixLen) {
        this.affixLen = affixLen;
    }

    @ManyToOne
    @JoinColumn(name = "syntacticCategory_id")
    public SyntacticCategory getStemCategory() {
        return stemCategory;
    }

    public void setStemCategory(SyntacticCategory stemCategory) {
        this.stemCategory = stemCategory;
    }

    @ManyToOne
    @JoinColumn(name = "affixType_id")
    public AffixType getAffixType() {
        return affixType;
    }

    public void setAffixType(AffixType affixType) {
        this.affixType = affixType;
    }

    @Column(name = "writtenForm")
    public String getWrittenForm() {
        return writtenForm;
    }

    public void setWrittenForm(String writtenForm) {
        this.writtenForm = writtenForm;
    }
}
