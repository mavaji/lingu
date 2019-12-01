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
@Table(name = "FL_AFFIX_TYPE")
public class AffixType extends BaseEntity {
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

    public static final String PLM = "PLM";
    public static final String INM = "INM";
    public static final String RCM = "RCM0";
    public static final String PEC = "PEC";
    public static final String EZM = "EZM";
    public static final String COC = "COC";
    public static final String CAM = "CAM";
    public static final String SAM = "SAM";
    public static final String CAdM = "CAdM";
    public static final String ONM = "ONM";
    public static final String NEM = "NEM";
    public static final String ISM = "ISM";
    public static final String IMM = "IMM";
    public static final String PPM = "PPM";
    public static final String PEI = "PEI";
    public static final String SUC = "SUC";
}
