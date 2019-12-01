package tfarsdat.app;

import tfarsdat.service.Distance;

import java.sql.*;

/**
 * Created by IntelliJ IDEA.
 * User: vahid
 * Date: 4/28/11
 * Time: 3:01 PM
 * To change this template use File | Settings | File Templates.
 */
public class App {
    class unit {
        public
        char prev;
        public char next;
        public String first;
        public String second;

        // set the variables
        void set(char p, char n, String f, String s) {
            prev = p;
            next = n;
            first = f;
            second = s;
        }

        // merge this unit with u
        void merge(unit u) {
            next = u.next;
            first = first + u.first;
            second = second + u.second;
        }
    }

    String[] separate(String diff) {
//        size_t i = diff.find("#s", 0);
        int i = diff.indexOf("#s", 0);
        if (i == 0) {
            return separate(diff.substring(1, diff.length() - 2 + 1));
        }
        if (i == -1)
            return new String[0];
        String s = diff.substring(0, i);
        String[] v = separate(diff.substring(i + 1, diff.length() - i - 2 + (i + 1)));
        return v;
    }

    unit summarize(String diff) {
        unit u = new unit();
        switch (diff.charAt(1)) {
            case 'i':
                u.set(diff.charAt(7), diff.charAt(11), "", diff.substring(3, 1 + 3));
                if (diff.length() > 12)
                    u.merge(summarize(diff.substring(12, diff.length() - 12 + 12)));
                return u;
            case 'd':
                u.set(diff.charAt(7), diff.charAt(11), diff.substring(3, 1 + 3), "");
                if (diff.length() > 12)
                    u.merge(summarize(diff.substring(12, diff.length() - 12 + 12)));
                return u;
            case 'r':
                u.set(diff.charAt(10), diff.charAt(14), diff.substring(3, 1 + 3), diff.substring(6, 1 + 6));
                if (diff.length() > 15)
                    u.merge(summarize(diff.substring(15, diff.length() - 15 + 15)));
                return u;
            default:
                return u;
        }
    }


    String getDifference(String s1, String s2) {
        s1 = "$" + s1 + "$";
        s2 = "$" + s2 + "$";
//        String dif = Distance.LD(s1 + 1, s1.length() - 2, s2 + 1, s2.length() - 2);
        String dif = Distance.LD(s1, s1.length() - 1, s2, s2.length() - 1);

//        String[] difs = separate(dif + "#s ");
        String[] difs = dif.split("#s");
        unit u;

        StringBuffer sb = new StringBuffer();
        for (String s : difs) {
            if (s.length() == 0) continue;
            u = summarize(s);
//            System.out.println("prev = " + u.prev + "\n" +
//                    "next = " + u.next + "\n" +
//                    u.first + " -> " + u.second + "\n\n");
            sb.append(u.prev).append(u.first).append(u.next).append("->").append(u.prev).append(u.second).append(u.next).append(" ");
        }
        return sb.toString();
    }


    public static void main(String[] args) {
        App app = new App();

//        String s1 = "vAksanhAyeSAn";
//        String s2 = "vAksanASun";
//        System.out.println(app.getDifference(s1, s2));

        app.updateDB();
    }

    public void updateDB() {
        Connection c = null;
        Statement q = null;
        ResultSet r = null;
        try {
            Driver d = (Driver) Class.forName("sun.jdbc.odbc.JdbcOdbcDriver").newInstance();
            java.util.Properties prop = new java.util.Properties();
            prop.put("charSet", "UTF8");

            c = DriverManager.getConnection(
                    "jdbc:odbc:Driver={Microsoft Access Driver (*.mdb)};DBQ=./db/TFarsDat.mdb"
                    , prop);
            Statement p = c.createStatement();
            q = c.createStatement();

            r = p.executeQuery("select * from Main");
        } catch (Exception e) {
            return;
        }

        try {
            while (r.next()) {
                try {
                    Long id = r.getLong("id");
                    String phonemic = r.getString("Phonemic");
                    String phonetic = r.getString("Phonetic");

                    if (phonemic.contains("+")) {
                        phonemic = phonemic.replaceAll("\\+", "");
                    }
                    phonemic = phonemic.replaceAll("\'", "C");

                    if (phonetic.contains("+")) {
                        phonetic = phonetic.substring(0, phonetic.indexOf("+"));
                    }
                    phonetic = phonetic.replaceAll("\'", "C");

                    String diff = getDifference(phonemic, phonetic);
                    String[] diffs = diff.split(" ");
                    for (String d : diffs) {
//                        q.executeUpdate("update Main set conversion=" + "'" + +"'" + " where id=" + id);
                        q.executeUpdate("INSERT INTO Conversion ( conversion ) VALUES ('" + d + "')");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            c.commit();

            r.close();
            c.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}

