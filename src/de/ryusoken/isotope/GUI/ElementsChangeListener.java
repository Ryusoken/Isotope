package de.ryusoken.isotope.GUI;


import de.ryusoken.isotope.Isotop;
import javafx.beans.binding.ObjectExpression;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableListValue;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;

import javax.print.attribute.standard.MediaSize;
import java.util.ArrayList;

public class ElementsChangeListener implements ChangeListener {

    @Override
    public void changed(ObservableValue ov, Object t, Object tl) {
        Objects.Isotopes.getItems().clear();
        Objects.Isotopes.getItems().addAll(Isotop.getAllIsotopes(tl.toString()));
        Objects.Isotopes.setDisable(false);
    }
}
