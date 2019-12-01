package core.dao;

import core.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * @author Vahid Mavaji
 */
public abstract class BaseDao implements Serializable {
    public List findByNamedQuery(String namedQuery) {
        return findByNamedQuery(namedQuery, null);
    }

    public List findByNamedQuery(String namedQuery, Map<String, Object> params) {
        Session session = HibernateUtil.currentSession();
        Transaction tx = session.beginTransaction();
        org.hibernate.Query query = session.getNamedQuery(namedQuery);

        if (params != null) {
            for (String key : params.keySet()) {
                query.setParameter(key, params.get(key));
            }
        }

        List result = query.list();

        tx.commit();
        HibernateUtil.shutdown();

        return result;
    }

    public List find(String q) {
        Session session = HibernateUtil.currentSession();
        Transaction tx = session.beginTransaction();
        org.hibernate.Query query = session.createQuery(q);

        List result = query.list();

        tx.commit();
        HibernateUtil.shutdown();

        return result;
    }
}
