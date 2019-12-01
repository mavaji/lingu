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
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @author Vahid Mavaji
 */
@Entity
@Table(name = "FL_SYNTACTIC_CATEGORY")
public class SyntacticCategory extends BaseEntity {
    public static final String N1 = "N1";
    public static final String N2 = "N2";
    public static final String N3 = "N3";
    public static final String N4 = "N4";
    public static final String N5 = "N5";
    public static final String N6 = "N6";
    public static final String N7 = "N7";
    public static final String N8 = "N8";
    public static final String N9 = "N9";
    public static final String NA = "NA";
    public static final String A0 = "A0";
    public static final String A1 = "A1";
    public static final String A2 = "A2";
    public static final String Ad = "Ad";
    public static final String No = "No";
    public static final String Nu = "Nu";
    public static final String V1 = "V1";
    public static final String V2 = "V2";
    public static final String V3 = "V3";
    public static final String V4 = "V4";
    public static final String V5 = "V5";
    public static final String V1IM = "V1IM";

    public static final String[] NOUNS = {N1, N2, N3, N4, N5, N6, N7, N8, N9, NA};
    public static final String[] ADJECTIVES = {A0, A1, A2};
    public static final String[] ADVERBS = {Ad};
    public static final String[] NUMBERS = {No, Nu};
    public static final String[] VERBS = {V1, V2, V3, V4, V5, V1IM};

    private String name;
    private String code;

    @Column(name = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "code")
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
