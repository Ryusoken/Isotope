package de.ryusoken.isotope.GUI;

import de.ryusoken.isotope.Isotop;
import de.ryusoken.isotope.Reader;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.scene.control.Hyperlink;

import java.util.Arrays;

public class IsotopeChangeListener implements ChangeListener {

    @Override
    public void changed(ObservableValue observable, Object oldValue, Object newValue) {
        Objects.reihe = Isotop.ZerfallreiheToArray();
        Hyperlink s = new Hyperlink();
        Objects.Zerfallreihe.getChildren().clear();        for(int i = 0; i < Reader.Isotope.size(); i++) {
            System.out.println(Reader.Isotope.get(i).getZerfall());
        }
        for (int i=0; i < Objects.reihe.length; i++) {
            s = Objects.reihe[i];
            System.out.println(s);
            if(Objects.reihe[i] != null) {
                Objects.Zerfallreihe.getChildren().add(Objects.reihe[i]);
            }
        }


    }
}
