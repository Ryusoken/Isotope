package de.ryusoken.isotope.GUI;

import de.ryusoken.isotope.Isotop;
import de.ryusoken.isotope.Reader;
import javafx.application.Application;
import javafx.beans.binding.ObjectExpression;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.lang.annotation.ElementType;

public class Gui extends Application {

    @Override
    public void init() throws Exception {
        Objects.top = new HBox();
        Objects.Zerfallreihe = new VBox();
        Objects.Elements = new ComboBox();
        Objects.Isotopes = new ComboBox();
        Objects.Isotopes.setDisable(true);
        Objects.Isotopes.setPrefSize(100,30);
        Objects.Isotopes.getSelectionModel().selectedItemProperty().addListener(new IsotopeChangeListener());
        Objects.Elements.setPrefSize(100,30);
        Objects.Elements.getItems().addAll(Isotop.getAllElements());
        Objects.Elements.getSelectionModel().selectedItemProperty().addListener(new ElementsChangeListener());
        addHbox();
        addVbox();
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        BorderPane pane = new BorderPane();
        pane.setTop(Objects.top);
        pane.setCenter(Objects.Zerfallreihe);
        primaryStage.setTitle("Isotope Rechner");
        primaryStage.setScene(new Scene(pane, 800,600));
        primaryStage.show();

    }

    public void addHbox() {
        Objects.top.setPadding(new Insets(15,15,15,15));
        Objects.top.setSpacing(10);
        Objects.top.setStyle("-fx-background-color: beige");
        Objects.top.getChildren().add(Objects.Elements);
        Objects.top.getChildren().add(Objects.Isotopes);

    }

    public void addVbox() {
        Objects.Zerfallreihe.setPadding(new Insets(15,15,15,15));
        Objects.Zerfallreihe.setSpacing(8);

        Text title = new Text("Zerfallsreihe:");

    }

}
