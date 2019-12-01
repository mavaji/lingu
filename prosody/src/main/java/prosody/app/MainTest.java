package prosody.app;

import prosody.service.ProsodyAnalyzer;

import javax.swing.*;

/**
 * Created by IntelliJ IDEA.
 * User: vahid
 * Date: 5/18/12
 * Time: 7:57 PM
 * To change this template use File | Settings | File Templates.
 */
public class MainTest {
    public static void main(String[] args) {
        ProsodyAnalyzer prosodyAnalyzer = new ProsodyAnalyzer();

        //        String s = "یشنو از نی چون حکایت می کند";
        //        String s = "به نام خداوند جان و خرد";
        //        String s = "چو فردا برآید بلند آفتاب";
        //        String s = "ای همه هستی ز تو پیدا شده";

        Main m = new Main();
        m.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        m.show();

//        String s = JOptionPane.showInputDialog("poem");
//
//        s = s.replaceAll("'", "ʔ");
//        s = s.replaceAll("S", "š");
//        s = s.replaceAll("A", "â");
//        s = s.replaceAll("j", "ǰ");
//        s = s.replaceAll("C", "č");
//        s = s.replaceAll("Z", "ž");
//
//        System.out.println(s);
//        Rhythm[] rhythms1 = prosodyAnalyzer.getProsody(s);
//        for (Rhythm rhythm : rhythms1) {
//            System.out.println(rhythm.getSymbol());
//            System.out.println(rhythm.getRhythm());
//            System.out.println(rhythm.getName());
//            System.out.println("**********************************");
//        }

    }
}
