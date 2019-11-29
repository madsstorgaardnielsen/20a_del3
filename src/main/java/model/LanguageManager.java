package model;
import model.*;
import view.*;
import controller.*;
import javax.swing.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class LanguageManager {
    int response;
    static String file;

    public void selectLanguage() {
        Object[] option = {"Dansk", "English"};
        JPanel panel = new JPanel();
        panel.add(new JLabel("Choose language / Vælg sprog"));

        JDialog.setDefaultLookAndFeelDecorated(true);
        response = JOptionPane.showOptionDialog(null, panel, "Language manager",
                JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE,
                null, option, null);

//        if (response == JOptionPane.NO_OPTION) {
//            //response value er 1
//            //System.out.println("ENGELSK VALGT");
//            //System.out.println(response);
//
//        } else if (response == JOptionPane.YES_OPTION) {
//            //response value er 0
//            //System.out.println("DANSK VALGT");
//            //System.out.println(response);
//        }

        if (response == 0) {
            file = "language/dansk.txt";
        } else {
            file = "language/english.txt";
        }
    }

    public String translate(String lineSearch) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String lineFinder;
            String returnstring = "";
            boolean lineFound = false;
            while (!lineFound) {
                lineFinder = reader.readLine();
                //System.out.println(lineFinder);
                if (lineSearch.equals(lineFinder)) {
                    returnstring = reader.readLine();
                    lineFound = true;
                }
            }
            reader.close();

            return returnstring;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "error in languagemanager";
    }
}
