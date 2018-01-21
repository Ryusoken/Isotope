package de.ryusoken.isotope;

import de.ryusoken.isotope.GUI.Gui;
import javafx.application.Application;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Reader.read();
        Application.launch(Gui.class, args);
    }

}
