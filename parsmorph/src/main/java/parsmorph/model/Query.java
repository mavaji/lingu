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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;

/**
 * @author Vahid Mavaji
 */
@Entity
@Table(name = "PM_QUERY")
public class Query extends BaseEntity {
    private String query;
    private Date date;
    private String ip;
    private String result;
    private Boolean phonology;

    public Query() {
    }

    public Query(String query, String ip, String result) {
        this(query, ip, result, false);
    }

    public Query(String query, String ip, String result, Boolean phonology) {
        this.query = query;
        this.ip = ip;
        this.result = result;
        this.date = new Date();
        this.phonology = phonology;
    }

    @Column(name = "query")
    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    @Column(name = "date")
    @Temporal(TemporalType.DATE)
    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Column(name = "ip")
    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    @Column(name = "result")
    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    @Column(name = "phonology")
    public Boolean getPhonology() {
        return phonology;
    }

    public void setPhonology(Boolean phonology) {
        this.phonology = phonology;
    }
}
