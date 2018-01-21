package de.ryusoken.isotope;

import java.io.*;
import java.util.ArrayList;

public class Reader {


    public static ArrayList<Isotop> Isotope = new ArrayList();

    static String path = "Isotope.csv";
    static BufferedReader reader = null;
    static String line;
    static InputStream is;
    static UnicodeBOMInputStream ubis;

    public static void read() {
        try {
            is = Reader.class.getResourceAsStream(path);
            reader = new BufferedReader(new InputStreamReader(is));
            ubis = new UnicodeBOMInputStream(is);
            ubis.skipBOM();

            while((line = reader.readLine()) != null) {
                String[] seperated = line.split(";");
                Isotop isotop = new Isotop(seperated[0], "000", Zerfall.ALPHA, false, 20, seperated[4]);

                if(seperated[2].contains("a")) {            // Alpha
                    isotop.setZerfall(Zerfall.ALPHA);
                } else if (seperated[2].contains("b" + "+")) {      // Beta +
                    isotop.setZerfall(Zerfall.BETAPLUS);
                } else if (seperated[2].contains("b" + "-")) {      // Beta -
                    isotop.setZerfall(Zerfall.BETAMINUS);
                } else if (seperated[2].contains("K")) {            // Elektronenaufnahme
                    isotop.setZerfall(Zerfall.EE);
                } else if (seperated[2].equals("0")){
                    isotop.setZerfall(Zerfall.STABIL);
                }

                if(seperated[1].contains("stabil"))                // Stabiles Isotop
                    isotop.setStabil(true);

                try {                                              // Protonenkonfiguration
                    isotop.setProtonen(Integer.parseInt(seperated[3]));
                } catch (NumberFormatException ex) {
                    ex.printStackTrace();
                }

                isotop.setHWZ(seperated[1]);
                isotop.Update();
                Isotope.add(isotop);
            }

        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        try {
            is.close();
            reader.close();
            ubis.close();
        } catch(IOException ex) {
            ex.printStackTrace();
        }

    }

}
