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
import java.util.Date;

/**
 * @author Vahid Mavaji
 */
@Entity
@Table(name = "PM_COMMENT")
@NamedQueries({
        @NamedQuery(name = Comment.GET_LAST_COMMENTS, query = "from Comment c order by c.date desc "),
        @NamedQuery(name = Comment.GET_TOTAL_COUNT, query = "select count(*) from Comment ")
})
public class Comment extends BaseEntity {
    public static final String GET_LAST_COMMENTS = "get_last_commetns";
    public static final String GET_TOTAL_COUNT = "get_total_count";

    private String name;
    private String email;
    private String body;
    private Date date;
    private String ip;

    public Comment() {
    }

    public Comment(String name, String email, String body, String ip) {
        this.name = name;
        this.email = email;
        this.body = body;
        this.ip = ip;
        this.date = new Date();
    }

    @Column(name = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "email")
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Column(name = "body")
    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    @Column(name = "date")
    @Temporal(TemporalType.TIMESTAMP)
    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getIp() {
        return ip;
    }

    @Column(name = "ip")
    public void setIp(String ip) {
        this.ip = ip;
    }
}
