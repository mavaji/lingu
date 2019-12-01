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

package parsmorph.web;

import core.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import parsmorph.model.Comment;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import java.io.Serializable;
import java.util.List;

/**
 * @author Vahid Mavaji
 */
@ManagedBean(name = "commentBean")
@RequestScoped
public class CommentBean implements Serializable {
    public Comment[] getLastComments() {
        Session session = HibernateUtil.currentSession();
        Transaction tx = session.beginTransaction();
        org.hibernate.Query query = session.getNamedQuery(Comment.GET_LAST_COMMENTS);
        List<Comment> comments = query.list();

        tx.commit();
        HibernateUtil.shutdown();
        return comments.toArray(new Comment[0]);
    }

    public Long getTotalComments() {
        Session session = HibernateUtil.currentSession();
        Transaction tx = session.beginTransaction();
        org.hibernate.Query query = session.getNamedQuery(Comment.GET_TOTAL_COUNT);
        List result = query.list();

        tx.commit();
        HibernateUtil.shutdown();
        return (Long) result.get(0);
    }
}
