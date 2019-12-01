package core.util;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.AnnotationConfiguration;
import org.hibernate.cfg.Configuration;

/**
 * @author Vahid Mavaji
 */
public class HibernateUtil {

    private static SessionFactory sessionFactory;
    protected static Configuration config;

    static {
        try {
            config = new AnnotationConfiguration().configure();
            sessionFactory = config.buildSessionFactory();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public static void shutdown() {
        getSessionFactory().close();
    }

    @SuppressWarnings("unchecked")
    public static final ThreadLocal session = new ThreadLocal();

    @SuppressWarnings("unchecked")
    public static Session currentSession() {
        Session s = (Session) session.get();
        if (s == null) {
            s = sessionFactory.openSession();
            session.set(s);
        }
        return s;
    }

    @SuppressWarnings("unchecked")
    public static void closeSession() {
        Session s = (Session) session.get();
        if (s != null)
            s.close();
        session.set(null);
    }
}
