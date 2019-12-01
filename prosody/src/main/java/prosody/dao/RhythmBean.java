package prosody.dao;

import core.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import prosody.model.Rhythm;

import java.io.Serializable;
import java.util.List;

/**
 * @author Vahid Mavaji
 */
public class RhythmBean implements Serializable {
    public Rhythm[] getAllRhythms() {
        Session session = HibernateUtil.currentSession();
        Transaction tx = session.beginTransaction();
        org.hibernate.Query query = session.getNamedQuery(Rhythm.GET_ALL_RHYTHMS);
        List<Rhythm> rhythms = query.list();

        tx.commit();
        HibernateUtil.shutdown();
        return rhythms.toArray(new Rhythm[0]);
    }
}
