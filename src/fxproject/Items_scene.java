/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fxproject;

import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 *
 * @author DELL
 */
public class Items_scene extends Application {

    public Items_scene() {
    }

    Connection conn = null;
    PreparedStatement pst = null;
    ResultSet res = null;
    ObservableList<Items_info> data;

    TableView<Items_info> table;
    int max_id = 0;



    @Override
    public void start(Stage primaryStage) throws Exception {

        table = new TableView<>();
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        table.setPlaceholder(new Label("No rows to display"));

        TableColumn c1 = new TableColumn("ID");
        c1.setCellValueFactory(new PropertyValueFactory<>("id"));

        TableColumn c2 = new TableColumn("Name");
        c2.setCellValueFactory(new PropertyValueFactory<>("name"));

        TableColumn c3 = new TableColumn("Price");
        c3.setCellValueFactory(new PropertyValueFactory<>("price"));

        table.getColumns().addAll(c1, c2, c3);
        VBox v = new VBox(table);
        v.setPrefSize(500, 500);
        v.setPadding(new Insets(20));
        try {
            show();
        } catch (Exception e) {
            System.err.println(e.toString());
        }
        Label l5 = new Label("Update Item Price by ID: ");
        Label l6 = new Label("ID");
        TextField t5 = new TextField();
        Label l7 = new Label("Price");
        TextField t6 = new TextField();
        Button btn_update = new Button("Update");

        GridPane update_grid = new GridPane();

        update_grid.add(l5, 0, 0, 2, 1);
        update_grid.add(l6, 0, 1);
        update_grid.add(t5, 1, 1);
        update_grid.add(l7, 0, 2);
        update_grid.add(t6, 1, 2);
        update_grid.add(btn_update, 0, 3, 2, 1);

        update_grid.setVgap(10);
        update_grid.setHgap(10);
        update_grid.setPadding(new Insets(20));

        Label l8 = new Label("Delete Item by ID: ");
        Label l9 = new Label("ID");
        TextField t7 = new TextField();
        Button btn_delete = new Button("Delete");

        GridPane delete_grid = new GridPane();

        delete_grid.add(l8, 0, 0, 2, 1);
        delete_grid.add(l9, 0, 1);
        delete_grid.add(t7, 1, 1);
        delete_grid.add(btn_delete, 0, 2, 2, 1);

        delete_grid.setVgap(10);
        delete_grid.setHgap(10);
        delete_grid.setPadding(new Insets(20));

        Label l10 = new Label("add");
        Label l11 = new Label("Name");
        TextField t10 = new TextField();
        Label l12 = new Label("Price");
        TextField t11 = new TextField();
        Button btnadd = new Button("Add Items");

        RadioButton rd1 = new RadioButton("Meals");
        RadioButton rd2 = new RadioButton("Drinks");
        RadioButton rd3 = new RadioButton("Play Station");

        ToggleGroup radioGroup = new ToggleGroup();
        rd1.setToggleGroup(radioGroup);
        rd2.setToggleGroup(radioGroup);
        rd3.setToggleGroup(radioGroup);

        HBox rd = new HBox(rd1, rd2, rd3);

        GridPane grid_add = new GridPane();

        grid_add.add(l10, 0, 0, 2, 1);
        grid_add.add(l11, 0, 1);
        grid_add.add(t10, 1, 1);
        grid_add.add(l12, 0, 2);
        grid_add.add(t11, 1, 2);
        grid_add.add(rd, 0, 3, 2, 1);
        grid_add.add(btnadd, 0, 4, 2, 1);

        grid_add.setVgap(10);
        grid_add.setHgap(10);
        grid_add.setPadding(new Insets(20));

        GridPane root = new GridPane();
        root.add(v, 0, 0, 3, 2);
        root.add(grid_add, 3, 0);
        root.add(update_grid, 3, 1);
        root.add(delete_grid, 3, 2);

        root.setAlignment(Pos.CENTER);
        root.setPadding(new Insets(50, 50, 50, 50));

        BorderPane bp = new BorderPane();

        Label head = new Label("Items Inforamtion");
        head.setId("headline");
        head.setPadding(new Insets(50, 600, 50, 600));
        head.setAlignment(Pos.CENTER);
        Button back = new Button("<--Back To Dashboard");
        back.setId("backb");
        VBox top = new VBox(back, head);
        top.setPadding(new Insets(20, 20, 20, 20));
        top.setSpacing(20);
//        top.setAlignment(Pos.CENTER);
        bp.setCenter(root);
        bp.setTop(top);

        Scene scene = new Scene(bp, 1530, 800);
        scene.getStylesheets().add((new File("C:\\Users\\DELL\\Documents\\NetBeansProjects\\FXproject\\src\\fxproject\\csssheet.css")).toURI().toString());

        primaryStage.setTitle("Tables");
        primaryStage.setScene(scene);
        primaryStage.show();


        btnadd.setOnAction((ActionEvent event) -> {
            conn = dbConn.DBConnection();
            String sql = "Insert into Items (TABLE_ITEMS_ID, name, price,FK_ITEM_TYPE) Values(?,?,?,?)";
            RadioButton selectedRadioButton = (RadioButton) radioGroup.getSelectedToggle();
            try {
                pst = conn.prepareStatement(sql);
                pst.setInt(1, max_id + 1);
                pst.setString(2, t10.getText());
                pst.setString(3, t11.getText());

                if (selectedRadioButton.getText().equals("Meals")) {
                    pst.setInt(4, 1);
                } else if (selectedRadioButton.getText().equals("Drinks")) {
                    pst.setInt(4, 2);
                } else {
                    pst.setInt(4, 3);
                }

                int i = pst.executeUpdate();
                if (i == 1) {
                    max_id++;
                    System.out.println("Data is inserted");
                }
                pst.close();
                conn.close();
                show();
            } catch (Exception ex) {
                System.out.println(ex.toString());
            }
        });
        btn_update.setOnAction((ActionEvent event) -> {

            String id = t5.getText();
            String m = t6.getText();

            String sql = "Update ITEMS set price = ? where TABLE_ITEMS_ID = ?";
            conn = dbConn.DBConnection();
            try {
                pst = conn.prepareStatement(sql);
                pst.setString(1, m);
                pst.setString(2, id);
                pst.executeUpdate();
                pst.close();
                conn.close();
                show();
            } catch (Exception ex) {
                System.out.println(ex.toString());
            }

        });

        btn_delete.setOnAction(e -> {

            String id = t7.getText();
            String sql = "Delete from ITEMS where TABLE_ITEMS_ID = ?";

            conn = dbConn.DBConnection();
            try {
                pst = conn.prepareStatement(sql);
                pst.setString(1, id);
                pst.executeUpdate();
                pst.close();
                conn.close();
                show();
            } catch (Exception ex) {
                System.out.println(ex.toString());
            }

        });

        back.setOnAction((ActionEvent e) -> {
            FXproject main = new FXproject();
            try {
                main.start(primaryStage);
            } catch (Exception ex) {
                Logger.getLogger(Items_scene.class.getName()).log(Level.SEVERE, null, ex);
            }
        });

    }

    public void show() throws SQLException {

        data = FXCollections.observableArrayList();
        conn = dbConn.DBConnection();

        pst = conn.prepareStatement("select * from ITEMS order by TABLE_ITEMS_ID");
        res = pst.executeQuery();

        while (res.next()) {
            data.add(new Items_info(res.getInt(1), res.getString(2), res.getInt(3),res.getInt(4)));
            max_id = res.getInt(1);
        }
        pst.close();
        conn.close();
        table.setItems(data);
    }

}
