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
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Separator;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 *
 * @author DELL
 */
public class Tables_scene extends Application {

    public VBox show_tables() throws SQLException {

        FlowPane tables = new FlowPane(Orientation.HORIZONTAL, 30, 30);
        tables.setPrefWrapLength(1500);
        tables.setAlignment(Pos.CENTER);

        FlowPane pss = new FlowPane(Orientation.HORIZONTAL, 30, 30);
        pss.setPrefWrapLength(1500);
        pss.setAlignment(Pos.TOP_CENTER);
        Separator separator = new Separator(Orientation.HORIZONTAL);
        Label lt = new Label("Cafe & Restaurant");
        Label lp = new Label("PalyStation Rooms");
        lt.setId("headline");
        lp.setId("headline");

        VBox v = new VBox(lt, tables, separator, lp, pss);
        v.setSpacing(50);
        VBox vx = new VBox();

        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setContent(v);
        scrollPane.pannableProperty().set(true);
        scrollPane.fitToWidthProperty().set(true);
        scrollPane.fitToHeightProperty().set(true);
        scrollPane.hbarPolicyProperty().setValue(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setPrefViewportHeight(1000);
        vx.getChildren().addAll(scrollPane);

        conn = dbConn.DBConnection();

        try {

            pst = conn.prepareStatement("select * from tables order by id");
            res = pst.executeQuery();

            while (res.next()) {
                Containers c = new Containers();
                c.setTable_Id(res.getInt(1));

                String type = res.getString(2);
                String name = res.getString(3);
                Label tname = new Label(name);
                if (type.equals("table")) {
                    BorderPane bp = c.container();
                    tables.getChildren().add(bp);
                    bp.setTop(tname);
                } else {
                    BorderPane bp = c.ps_container();
                    pss.getChildren().add(bp);
                    bp.setTop(tname);
                }
            }

            pst.close();
            conn.close();
        } catch (Exception e) {
            System.out.println(e.toString());
            e.printStackTrace();
        }
        return vx;
    }
    int table_id;
    int ps_id;
    Connection conn = null;
    PreparedStatement pst = null;
    ResultSet res = null;
    ObservableList<Tables_info> data;
    TableView<Tables_info> table;

    PreparedStatement pst_selectmax = null;
    ResultSet res_selectmax = null;

    PreparedStatement pst_selectmin = null;
    ResultSet res_selectmin = null;

    @Override
    public void start(Stage primaryStage) throws Exception {

        table = new TableView<>();
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        table.setPlaceholder(new Label("No rows to display"));

        TableColumn c1 = new TableColumn("ID");
        c1.setCellValueFactory(new PropertyValueFactory<>("id"));

        TableColumn c2 = new TableColumn("Type");
        c2.setCellValueFactory(new PropertyValueFactory<>("roomtype"));

        TableColumn c3 = new TableColumn("Name");
        c3.setCellValueFactory(new PropertyValueFactory<>("name"));

        table.getColumns().addAll(c1, c2, c3);
        VBox v = new VBox(table);
        v.setPadding(new Insets(20));
        v.setPrefSize(500, 500);

        try {
            show();
        } catch (SQLException e) {
            System.err.println(e.toString());
        }

        Label l1 = new Label("Add Table");
        Button add_table = new Button("Add Table");
        Button add_ps = new Button("Add PS");

        GridPane add_grid = new GridPane();
        add_grid.add(l1, 0, 0, 2, 1);
        add_grid.add(add_table, 0, 1);
        add_grid.add(add_ps, 1, 1);

        add_grid.setVgap(10);
        add_grid.setHgap(10);
        add_grid.setPadding(new Insets(20));

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
        GridPane root = new GridPane();
        root.add(v, 0, 0, 3, 2);
        root.add(add_grid, 3, 0);
        root.add(delete_grid, 3, 1);
        root.setAlignment(Pos.CENTER);

        BorderPane bp = new BorderPane();
        Label head = new Label("Tabels Inforamtion");
        head.setId("headline");
        head.setPadding(new Insets(50, 600, 50, 600));
        head.setAlignment(Pos.CENTER);

        Button back = new Button("<--Back To Dashboard");
        back.setId("backb");
        VBox top = new VBox(back, head);
        bp.setCenter(root);
        bp.setTop(top);
        top.setPadding(new Insets(20, 20, 20, 20));
        top.setSpacing(20);

        Scene scene = new Scene(bp, 1530, 800);
        scene.getStylesheets().add((new File("C:\\Users\\DELL\\Documents\\NetBeansProjects\\FXproject\\src\\fxproject\\csssheet.css")).toURI().toString());

        primaryStage.setTitle("Tables");
        primaryStage.setScene(scene);
        primaryStage.show();

        add_ps.setOnAction((ActionEvent event) -> {
            conn = dbConn.DBConnection();

            ps_id++;
            String type = "ps";
            String name = type + " " + ps_id;

            conn = dbConn.DBConnection();
            String sql = "Insert into tables (ID, ROOMTYPE, NAME) Values(?,?,?)";

            try {
                pst = conn.prepareStatement(sql);
                pst.setInt(1, ps_id);
                pst.setString(2, type);
                pst.setString(3, name);

                int i = pst.executeUpdate();
                if (i == 1) {
                    System.out.println("Data is inserted");
                }
                pst.close();
                conn.close();

                show();
            } catch (SQLException ex) {
                System.out.println(ex.toString());
            }
        });

        add_table.setOnAction((ActionEvent event) -> {

            conn = dbConn.DBConnection();

            table_id++;
            String type = "table";
            String name = type + " " + table_id;

            conn = dbConn.DBConnection();
            String sql = "Insert into tables (ID, ROOMTYPE, NAME) Values(?,?,?)";

            try {
                pst = conn.prepareStatement(sql);

                pst.setInt(1, table_id);
                pst.setString(2, type);
                pst.setString(3, name);

                int i = pst.executeUpdate();
                if (i == 1) {
                    System.out.println("Data is inserted");
                }
                pst.close();
                conn.close();
                show();
            } catch (SQLException ex) {
                System.out.println(ex.toString());
            }
        });

        btn_delete.setOnAction(e -> {

            String id = t7.getText();
            String sql = "Delete from tables where ID = ?";

            conn = dbConn.DBConnection();
            try {
                pst = conn.prepareStatement(sql);
                pst.setString(1, id);
                pst.executeUpdate();
                pst.close();
                conn.close();
                show();
            } catch (SQLException ex) {
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

        pst = conn.prepareStatement("select * from tables order by id");
        pst_selectmax = conn.prepareStatement("select max (id) from tables where id >100");
        pst_selectmin = conn.prepareStatement("select max (id) from tables where id <100");

        res = pst.executeQuery();
        res_selectmax = pst_selectmax.executeQuery();
        res_selectmin = pst_selectmin.executeQuery();

        while (res.next()) {
            data.add(new Tables_info(res.getInt(1), res.getString(2), res.getString(3)));
        }

        while (res_selectmax.next()) {
            ps_id = res_selectmax.getInt(1);
        }
        while (res_selectmin.next()) {
            table_id = res_selectmin.getInt(1);
        }
        if (ps_id == 0) {
            ps_id = 100;
        }
        if (table_id == 0) {
            table_id = 0;
        }
        pst.close();
        pst_selectmax.close();
        pst_selectmin.close();

        conn.close();
        table.setItems(data);
    }
}
