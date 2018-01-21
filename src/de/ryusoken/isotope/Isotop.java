package de.ryusoken.isotope;

import de.ryusoken.isotope.GUI.Objects;
import javafx.scene.control.Alert;
import javafx.scene.control.Hyperlink;

import javax.print.attribute.standard.MediaSize;
import java.util.ArrayList;

import static de.ryusoken.isotope.Reader.Isotope;

enum Zerfall {
    ALPHA,
    BETAPLUS,
    BETAMINUS,
    EE,
    STABIL
}

public class Isotop {

    public String Name;
    public String HWZ;
    public Zerfall zerfall;
    public boolean stabil;
    public int Protonen;
    public int Neutronen;
    public int Massenzahl;
    public String Element;

    public Isotop(String name, String hwz, Zerfall zerfall, boolean stabil, int protonen, String element) {
        this.Name = name;
        this.HWZ = hwz;
        this.zerfall = zerfall;
        this.stabil = stabil;
        this.Protonen = protonen;
        this.Element = element;
        this.Massenzahl = Isotop.getIsotopeMassenzahl(name);
        this.Neutronen = (Isotop.getIsotopeMassenzahl(name) - protonen);
    }

    //Updates the number of neutrons

    public Isotop Update() {
        this.setNeutronen((Isotop.getIsotopeMassenzahl(this.getName())- this.getProtonen()));
        return this;
    }

    // Get Isotope by Name; Returns the Entry of the ArrayList

    public static Isotop getIsotopByName(String name) {
        if(Isotope != null) {
            for(int i = 0; i < Isotope.size(); i++) {
                if(Isotope.get(i).getName().equals(name)) {
                    return Isotope.get(i);
                }
            }
            return null;
        } else {
            return null;
        }
    }

    // Get the mass number of a Isotope; Returns the mass number as int

    public static int getIsotopeMassenzahl(String name) {
        char[] chars = name.toCharArray();
        String number = "";
        for(int i = 0; i <= 2; i++) {
                number = number + chars[i];
        }

        return Integer.parseInt(number);
    }

    // Get Isotope by mass number and number of protons; returns the entry of the ArrayList

    public static Isotop getIsotopeByMassenzahl(int massenzahl, int protonen) {
        Reader.Isotope.clear();
        Reader.read();
        for(int i = 0; i < Reader.Isotope.size(); i++) {
            if(Reader.Isotope.get(i).getMassenzahl() == massenzahl && Reader.Isotope.get(i).getProtonen() == protonen) {
                return Reader.Isotope.get(i);
            }
        }
        return null;
    }

    // The radioactive collapse; Returns a value from getIsotopeByMassenzahl();

    public static Isotop Zerfall(Isotop iso) {
            if(iso.getZerfall().equals(Zerfall.ALPHA)) {
                iso.setProtonen(iso.getProtonen() - 2);
                iso.setNeutronen(iso.getNeutronen() - 2);
                iso.setMassenzahl(iso.getProtonen() + iso.getNeutronen());

                return getIsotopeByMassenzahl(iso.getMassenzahl(), iso.getProtonen());
            }

            if (iso.getZerfall().equals(Zerfall.BETAMINUS)) {
                iso.setProtonen(iso.getProtonen() + 1);
                iso.setNeutronen(iso.getNeutronen() - 1);

                return getIsotopeByMassenzahl(iso.getMassenzahl(), iso.getProtonen());
            }

            if(iso.getZerfall().equals(Zerfall.BETAPLUS)) {
                iso.setProtonen(iso.getProtonen() - 1);
                iso.setNeutronen(iso.getNeutronen() + 1);

                return getIsotopeByMassenzahl(iso.getMassenzahl(), iso.getProtonen());

            }

            if(iso.getZerfall().equals(Zerfall.EE)) {
                iso.setProtonen(iso.getProtonen() - 1);
                iso.setNeutronen(iso.getNeutronen() + 1);

                return getIsotopeByMassenzahl(iso.getMassenzahl(), iso.getProtonen());
            }
        return null;
    }


    // Gets all Elements; Return as ArrayList

    public static ArrayList<String> getAllElements() {
        ArrayList<String> e = new ArrayList<>();
        String s = "";
        for(int i = 0; i < Reader.Isotope.size(); i++) {
            if(!e.contains(Reader.Isotope.get(i).getElement())) {
                s = Reader.Isotope.get(i).getElement();
                if (!s.equals("Pt") && !s.equals("Au") && !s.equals("Hg")) {
                    e.add(Reader.Isotope.get(i).getElement());
                }
            }
        }
        return e;
    }

    //Gets all Isotopes of an Element by it's name; Returns an ArrayListe

    public static ArrayList<String> getAllIsotopes(String element) {
        ArrayList<String> e = new ArrayList<>();
        for(int i = 0; i < Reader.Isotope.size(); i++) {
            if(Reader.Isotope.get(i).getName().contains(element)) {
                e.add(Reader.Isotope.get(i).getName());
            }
        }
        return e;
    }

    public static ArrayList<String> Zerfallreihe() {

        ArrayList<String> reihe = new ArrayList<>();
        Isotop isotop = null;
        if(Isotop.getIsotopByName(Objects.Isotopes.getValue().toString()) != null) {

            isotop = Isotop.getIsotopByName(Objects.Isotopes.getValue().toString());
            Isotop temp = null;
            boolean isStable = isotop.isStabil();

            while(!isStable) {
                temp = Isotop.Zerfall(isotop);
                if(temp != null) {
                    try {
                        if(isotop.getElement().equals("Pt") || isotop.getElement().equals("Au") || isotop.getElement().equals("Hg")) {
                            Isotop.LowZerfall(isotop);
                            return reihe;
                        }
                        temp.setName(Isotop.getIsotopeByMassenzahl(temp.getMassenzahl(), temp.getProtonen()).getName());
                        if (isotop.getZerfall().equals(Zerfall.ALPHA)) {
                            reihe.add(isotop.getName() + " \u2192 \u03B1 zu: " + temp.getName());
                        } else if (isotop.getZerfall().equals(Zerfall.BETAPLUS)) {
                            reihe.add(isotop.getName() + " \u2192 \u03B2+ zu: " + temp.getName());
                        } else if (isotop.getZerfall().equals(Zerfall.BETAMINUS)) {
                            reihe.add(isotop.getName() + " \u2192 \u03B2- zu: " + temp.getName());
                        } else if (isotop.getZerfall().equals(Zerfall.EE)) {
                            reihe.add(isotop.getName() + " \u2192 \u03B5 zu: " + temp.getName());
                        }
                        System.out.println(temp.getName());
                        isotop = temp;
                        isStable = isotop.isStabil();
                    } catch (NullPointerException ex) {
                        System.out.println("A Null Pointer occured: " +  ex.getStackTrace());
                        isStable = true;
                    }
                } else {
                    Isotop.LowZerfall(isotop);
                    return reihe;
                }
            }

            if(isotop.getElement().equals("Pt") || isotop.getElement().equals("Au") || isotop.getElement().equals("Hg")) {
                Isotop.LowZerfall(isotop);
                return reihe;
            }
            reihe.add("Das Isotop " + isotop.getName() + " ist ein stabiles Isotop");
        } else {

        }
        return reihe;
    }

    public static Hyperlink[] ZerfallreiheToArray() {
        ArrayList<String> e = Zerfallreihe();
        for(int i = 0; i < e.size(); i++) {
            System.out.println(e.get(i));
        }
        Hyperlink[] reihe = new Hyperlink[e.size()];
        for(int i = 0; i < e.size(); i++) {
            reihe[i] = new Hyperlink(e.get(i));
        }
        return reihe;
    }

    public static void LowZerfall(Isotop isotop) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("ACHTUNG!");
        alert.setHeaderText(null);
        alert.setContentText("Das Isotop " + isotop.getName() + " zerfällt weiter zu Platin oder Gold. Dies würde nicht mehr viel Sinn machen, da die Zerfallsreihe dann zu lang wäre.\nEs könnte ebenfalls sein, dass das Isotop größer als Plutonium ist");
        alert.show();
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getHWZ() {
        return HWZ;
    }

    public void setHWZ(String HWZ) {
        this.HWZ = HWZ;
    }

    public Zerfall getZerfall() {
        return zerfall;
    }

    public void setZerfall(Zerfall zerfall) {
        this.zerfall = zerfall;
    }

    public boolean isStabil() {
        return stabil;
    }

    public void setStabil(boolean stabil) {
        this.stabil = stabil;
    }

    public int getProtonen() {
        return Protonen;
    }

    public void setProtonen(int protonen) {
        Protonen = protonen;
    }

    public int getNeutronen() {
        return Neutronen;
    }

    public void setNeutronen(int neutronen) {
        Neutronen = neutronen;
    }

    public int getMassenzahl() {
        return Massenzahl;
    }

    public void setMassenzahl(int massenzahl) {
        Massenzahl = massenzahl;
    }

    public String getElement() {
        return Element;
    }

    public void setElement(String element) {
        Element = element;
    }

}