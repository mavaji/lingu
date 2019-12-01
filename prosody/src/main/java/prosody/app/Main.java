package prosody.app;

import prosody.service.PhonemeHolder;
import prosody.service.ProsodyAnalyzer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by IntelliJ IDEA.
 * User: vahid
 * Date: 5/18/12
 * Time: 7:52 PM
 * To change this template use File | Settings | File Templates.
 */
public class Main extends JFrame {
    private JButton writtenButton;
    private JLabel writtenLabel;
    private JTextField writtenField;
    private JTextArea writtenResult;

    private JButton phonemeButton;
    private JLabel phonemeLabel;
    private JTextField phonemeField;
    private JTextArea phonemeResult;

    ProsodyAnalyzer prosodyAnalyzer = new ProsodyAnalyzer();

    public Main() {
        setTitle("برنامه عروض فارسی");

        setSize(600, 600);


        Container contentPane = getContentPane();

        JPanel panel = new JPanel();

        writtenLabel = new JLabel("صورت نوشتاری");
        writtenField = new JTextField(50);
        writtenButton = new JButton("تحلیل عروضی");
        writtenButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String s = writtenField.getText();
                PhonemeHolder[] phonemeHolders= prosodyAnalyzer.getProsody(s, false);
                String result = "";
                for (PhonemeHolder phonemeHolder: phonemeHolders) {
                    result += "\n" + phonemeHolder.getPhonologicalForm() + "\n";
                    result += phonemeHolder.getRhythm().getSymbol() + "\n";
                    result += phonemeHolder.getRhythm().getRhythm() + "\n";
                    result += phonemeHolder.getRhythm().getName() + "\n";
                    result += "----------------------------------------------";
                }
                writtenResult.setText(result);
            }
        });
        writtenResult = new JTextArea(10, 50);
        panel.add(writtenLabel);
        panel.add(writtenField);
        panel.add(writtenButton);
        panel.add(writtenResult);

        phonemeLabel = new JLabel("صورت آوایی");
        phonemeField = new JTextField(50);
        phonemeButton = new JButton("تحلیل عروضی");
        phonemeButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String s = phonemeField.getText();
                s = s.replaceAll("'", "ʔ");
                s = s.replaceAll("S", "š");
                s = s.replaceAll("A", "â");
                s = s.replaceAll("j", "ǰ");
                s = s.replaceAll("C", "č");
                s = s.replaceAll("Z", "ž");
                PhonemeHolder[] phonemeHolders= prosodyAnalyzer.getProsody(s, true);
                String result = s;
                for (PhonemeHolder phonemeHolder: phonemeHolders) {
                    result += "\n" + phonemeHolder.getRhythm().getSymbol() + "\n";
                    result += phonemeHolder.getRhythm().getRhythm() + "\n";
                    result += phonemeHolder.getRhythm().getName() + "\n";
                    result += "----------------------------------------------";
                }
                phonemeResult.setText(result);
            }
        });

        phonemeResult = new JTextArea(10, 50);
        panel.add(phonemeLabel);
        panel.add(phonemeField);
        panel.add(phonemeButton);
        panel.add(phonemeResult);

        contentPane.add(panel);

    }
}
