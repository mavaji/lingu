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

import javax.persistence.*;

/**
 * @author Vahid Mavaji
 */
@Entity
@Table(name = "FL_ENTRY")
@NamedQueries({
        @NamedQuery(name = Entry.GET_BY_WRITTEN_FORM, query = "from Entry e where e.entryLen>1 and e.writtenForm= :writtenForm")
})
public class Entry extends BaseEntity {
    public static final String GET_BY_WRITTEN_FORM = "get_by_written_form";

    private String writtenForm;
    private String phonologicalForm;
    private String freq;
    private String stressPattern;
    private Integer entryLen;

    private SyntacticCategory category;

    @Column(name = "writtenForm")
    public String getWrittenForm() {
        return writtenForm;
    }

    public void setWrittenForm(String writtenForm) {
        this.writtenForm = writtenForm;
    }

    @Column(name = "phonologicalForm")
    public String getPhonologicalForm() {
        return phonologicalForm;
    }

    public void setPhonologicalForm(String phonologicalForm) {
        this.phonologicalForm = phonologicalForm;
    }

    @Column(name = "freq")
    public String getFreq() {
        return freq;
    }

    public void setFreq(String freq) {
        this.freq = freq;
    }

    @Column(name = "stressPattern")
    public String getStressPattern() {
        return stressPattern;
    }

    public void setStressPattern(String stressPattern) {
        this.stressPattern = stressPattern;
    }

    @Column(name = "entryLen")
    public Integer getEntryLen() {
        return entryLen;
    }

    public void setEntryLen(Integer entryLen) {
        this.entryLen = entryLen;
    }

    @ManyToOne
    @JoinColumn(name = "syntacticCategory_Id")
    public SyntacticCategory getCategory() {
        return category;
    }

    public void setCategory(SyntacticCategory category) {
        this.category = category;
    }
}
