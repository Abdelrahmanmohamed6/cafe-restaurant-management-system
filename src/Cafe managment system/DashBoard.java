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
import java.util.Arrays;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;

/**
 *
 * @author DELL
 */
public class DashBoard extends Application {

    ObservableList<Log_lines> log;
//
    Connection conn = null;
    PreparedStatement pst = null;
    ResultSet res = null;
//    
    String selected_table;
    int selected_table_id = 0;
//
    Button btn;
    Containers c = new Containers();

    int[] ax;
    int quantity = 0;

    VBox v1;
    VBox v2;
    VBox v3;
    int log_id = 10000;

//    
    String item_name;
    int item_id = 0;
//    
    VBox temp;
    VBox iv = new VBox();
//    
    EventHandler<MouseEvent> eventHandler = new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent e) {

            Button btt = (Button) e.getSource();
            item_name = btt.getText();
            item_id = Integer.parseInt(btt.getId());
            ax[item_id - 1] += 1;
            quantity = ax[item_id - 1];
            int log_num = log_id + selected_table_id;
            c.getitem_information(item_name);
//            if(!log.isEmpty()){
            if (quantity == 1) {
                iv.getChildren().add(c.insertToShow(item_name, quantity, c.unit_price));
                log.add(new Log_lines(log_num, item_name, quantity, c.unit_price));
            } else if (quantity > 1 && !log.isEmpty()) {
                c.l1.setText(item_name + " x" + quantity);
                c.l2.setText(Integer.toString(c.unit_price * quantity));
                log.forEach((i) -> {
                    if (i.getName().equals(item_name)) {
                        i.setQuantity(quantity);
                    }
                });
            }
        }
    };

    BorderPane bp = new BorderPane();

    public HBox show_dash() throws SQLException {
        log = FXCollections.observableArrayList();

        v1 = new VBox();
        v2 = new VBox();
        v3 = new VBox();

        v1.setSpacing(10);
        v2.setSpacing(10);
        v3.setSpacing(10);

//        
        showi();
        ax = new int[c.data.size()];
//  
        Button btn_convert = new Button("Add To Table ");
//        btn_convert.getMaxWidth();
        ComboBox comboBox = new ComboBox();
        comboBox.setPrefSize(135, 30);
        comboBox.setPromptText("Select Table");
        comboBox.setMaxWidth(Double.MAX_VALUE);
        temp = new VBox(comboBox, iv, btn_convert);
        temp.getMaxWidth();
//        
        bp.setTop(temp);

        HBox h = new HBox(v1, v2, v3, bp);
        h.setAlignment(Pos.CENTER);
        h.setPadding(new Insets(50));
        h.setSpacing(50);
        h.getMaxWidth();
        h.getMaxHeight();
//        
        conn = dbConn.DBConnection();
        try {
            pst = conn.prepareStatement("select * from tables order by id");
            res = pst.executeQuery();
            while (res.next()) {
                comboBox.getItems().add(res.getString(3));
            }
            pst.close();
            conn.close();
        } catch (Exception ex) {
            System.out.println(ex.toString());
        }
//         
        btn_convert.setOnAction((event) -> {
            log.forEach((i) -> {
                if (i.getLog_id() == selected_table_id + 10000) {
                    c.quantity_num = Integer.toString(i.getQuantity());
                    c.add_itemToTable(i.getName());
                }
            });
            iv.getChildren().clear();
            log.clear();
            Arrays.fill(ax, 0);
        });

        ScrollPane scrollPane = new ScrollPane();
        scrollPane.pannableProperty().set(true);
        scrollPane.fitToWidthProperty().set(true);
        scrollPane.fitToHeightProperty().set(true);
        scrollPane.hbarPolicyProperty().setValue(ScrollPane.ScrollBarPolicy.NEVER);

        comboBox.setOnAction((event) -> {
            Arrays.fill(ax, 0);
            iv.getChildren().clear();
            selected_table = (String) comboBox.getValue();
            char[] chh = new char[5];
            selected_table.getChars(selected_table.indexOf(" ") + 1, selected_table.length(), chh, 0);
            selected_table_id = Integer.parseInt(String.valueOf(chh).trim());
            c.setTable_Id(selected_table_id);
            btn_convert.setText("Add To Table >> " + selected_table_id);
//
            VBox vv = c.show_itemsInEachtable(selected_table_id);
            vv.setPrefHeight(30);
            vv.setAlignment(Pos.TOP_RIGHT);
            scrollPane.setContent(vv);
//
            bp.setCenter(scrollPane);
            c.chkout.setTop(c.checkout());
            bp.setBottom(c.chkout);
        });
        return h;
    }
//

    public void showi() throws SQLException {
        int t_type;
        c.show();

        for (Items_info i : c.data) {
            btn = new Button(i.getName());
            btn.setPrefSize(100, 50);
            btn.setId(Integer.toString(i.getId()));
            btn.addEventFilter(MouseEvent.MOUSE_CLICKED, eventHandler);
            t_type = i.getType();

            switch (t_type) {
                case 1 ->
                    v1.getChildren().add(btn);
                case 2 ->
                    v2.getChildren().add(btn);
                case 3 ->
                    v3.getChildren().add(btn);
                default -> {
                }
            }
        }
    }
//

    @Override
    public void start(Stage primaryStage) throws Exception {
        HBox h = show_dash();
        Scene scene = new Scene(h, 1530, 800);
        scene.getStylesheets().add((new File("C:\\Users\\DELL\\Documents\\NetBeansProjects\\FXproject\\src\\fxproject\\csssheet.css")).toURI().toString());

        primaryStage.setTitle("Tables");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

}
