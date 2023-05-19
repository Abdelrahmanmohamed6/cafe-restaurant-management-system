/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMain.java to edit this template
 */
package fxproject;

import java.io.File;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Side;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 *
 * @author DELL
 */
public class FXproject extends Application {

    @Override
    public void start(Stage primaryStage) throws SQLException, Exception {
        TabPane tabPane = new TabPane();
        tabPane.setSide(Side.TOP);
        tabPane.setPrefSize(1510, 800);

        Tab tab1 = new Tab("Dash Board");Tab tab2 = new Tab("Dash Board");Tab tab3 = new Tab("Items");Tab tab4 = new Tab("Tables");
        tab1.setClosable(false);tab2.setClosable(false);tab3.setClosable(false);tab4.setClosable(false);
//
        tabPane.getTabs().add(tab1);tabPane.getTabs().add(tab2);tabPane.getTabs().add(tab3);tabPane.getTabs().add(tab4);
//        
        Tables_scene tc = new Tables_scene();
        VBox vx = tc.show_tables();
        tab1.setContent(vx);
//      
        FlowPane root = new FlowPane();
//
        root.setPadding(new Insets(20, 0, 20, 10));
        root.setAlignment(Pos.CENTER);
        root.setVgap(50);      //gap with element and each other
        root.setHgap(50);
        root.getChildren().add(tabPane);
//        
        tab2.setOnSelectionChanged((value) -> {
            try {
                DashBoard d = new DashBoard();
                HBox h = d.show_dash();
                tab2.setContent(h);
//                d.start(primaryStage);
            } catch (Exception ex) {
                Logger.getLogger(FXproject.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
//        
        tab3.setOnSelectionChanged((value) -> {
            try {
                Items_scene is = new Items_scene();
                is.start(primaryStage);
            } catch (Exception ex) {
                Logger.getLogger(FXproject.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
//
        tab4.setOnSelectionChanged((value) -> {
            try {
                Tables_scene ts = new Tables_scene();
                ts.start(primaryStage);
            } catch (Exception ex) {
                Logger.getLogger(FXproject.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
//
        Scene scene = new Scene(root, 1530, 800);
        primaryStage.setTitle("FXproject");
        scene.getStylesheets().add((new File("C:\\Users\\DELL\\Documents\\NetBeansProjects\\FXproject\\src\\fxproject\\csssheet.css")).toURI().toString());
        primaryStage.setScene(scene);
        primaryStage.show();

    }

    public static void main(String[] args) {
        launch(args);
    }

}
