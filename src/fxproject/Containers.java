/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fxproject;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.Background;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 *
 * @author DELL
 */
public class Containers extends Application {

    ObservableList<Items_info> data;
    ObservableList<Log_lines> log;

    Connection conn = null;
    PreparedStatement pst = null;
    PreparedStatement pst2 = null;
    ResultSet res = null;
    //
    int log_id = 10000;
    int tt_id = 0;
    //
    String selected_value;
    String quantity_num = "1";
    String ps = null;
    //
    int unit_price = 0;
    int total_price = 0;
    int rest = 0;
    int clicked = 0;

    boolean flag = false;
    int selecteditem_id;

    Alert a3 = new Alert(Alert.AlertType.ERROR, "Field/'s Must Not be null");
//    a3.setTitle("Invalid");

    public Containers() {
        try {
            show();//show must before filter
        } catch (SQLException ex) {
            Logger.getLogger(Containers.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public int getTable_Id() {
        return tt_id;
    }

    public void setTable_Id(int tt_id) {
        this.tt_id = tt_id;
    }
//
//
//    

    public String end_time(int time) {
        LocalTime myObj = LocalTime.now();
        DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("hh:mm");
        LocalTime x = myObj.minusMinutes(-time);
        String end = myFormatObj.format(x);
        return end;
    }
//
//    
//    
//    public boolean comboboxsearch(String newValue) throws Exception {
//        show();//show must before filter
//        FilteredList<Items_info> filter = new FilteredList<>(data, e -> true);
//        flag = false;
//        filter.setPredicate(item -> {
//            if (item.getName().toLowerCase().contains(newValue)) {
//                flag = true;
//            }
//            return flag;
//        });
//
//        SortedList<Items_info> sort = new SortedList<>(filter);
////        sort.comparatorProperty().bind(sort.comparatorProperty());
//        return flag;
//    }
//
//    
//      

    public boolean has_items(int log_num) {
        flag = false;
        conn = dbConn.DBConnection();
        try {
            pst = conn.prepareStatement("select log_id from log");
            res = pst.executeQuery();

            while (res.next()) {
                if (log_num == res.getInt(1)) {
                    flag = true;
                }
            }
            pst.close();
            conn.close();

        } catch (Exception ex) {
            System.out.println(ex.toString());
        }
        return flag;
    }
//    
//    
    TextField t_pay;
    Label l_total_price;
    Button btn_pay;
    BorderPane chkout = new BorderPane();

    public BorderPane container() throws SQLException {

        ComboBox comboBox = new ComboBox();
        comboBox.setPrefSize(135, 30);
        comboBox.setPromptText("Select Item");
        comboBox.setMaxWidth(Double.MAX_VALUE);
//        comboBox.setEditable(true);

        conn = dbConn.DBConnection();
        try {
            pst = conn.prepareStatement("select * from items where FK_ITEM_TYPE<3 ");
            res = pst.executeQuery();
            while (res.next()) {
                comboBox.getItems().add(res.getString(2));
            }
            pst.close();
            conn.close();
        } catch (Exception ex) {
            System.out.println(ex.toString());
        }

        Button btn_add = new Button("add");
        btn_add.setPrefSize(50, 30);

        String number[] = {"1", "2", "3", "4", "5"};

        String j = number[0];
        ComboBox quantity = new ComboBox(FXCollections.observableArrayList(number));
        quantity.setPromptText(j);
        quantity.setPrefSize(65, 30);
        quantity.setEditable(true);

        HBox order_inf = new HBox(comboBox, quantity, btn_add);
        order_inf.getMaxWidth();
//
        VBox vs = new VBox();
        vs.setPrefSize(250, 200);
        vs.getChildren().add(show_itemsInEachtable(getTable_Id()));
//
        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setContent(vs);
        scrollPane.pannableProperty().set(true);
        scrollPane.fitToWidthProperty().set(true);
        scrollPane.fitToHeightProperty().set(true);
        scrollPane.hbarPolicyProperty().setValue(ScrollPane.ScrollBarPolicy.NEVER);
//       
///////////////////////////
//
        Pane pane = new Pane();
        VBox vcc = new VBox(order_inf, scrollPane);
        pane.getChildren().add(vcc);
//        
        chkout.setTop(checkout());
//
        BorderPane bp = new BorderPane();
        bp.setCenter(pane);
        bp.setBottom(chkout);
//
        comboBox.setOnAction((event) -> {
            selected_value = (String) comboBox.getValue();
        });

        quantity.setOnAction((event) -> {
            quantity_num = (String) quantity.getValue();
            if (quantity.getEditor().getText().isEmpty() || quantity_num == null) {
                quantity_num = "1";
            }
        });
        t_pay.setOnAction((event) -> {
            btn_pay.setText("pay(" + t_pay.getText() + ")");

        });
        btn_add.setOnAction((ActionEvent e) -> {
            if (selected_value == null || quantity_num == null) {
                a3.show();
            } else {
                add_itemToTable(selected_value);
                gettotalpriceoftable(getTable_Id());
                comboBox.getSelectionModel().clearSelection();
                comboBox.setPromptText("Select Item");
                comboBox.getPromptText();
                quantity.getSelectionModel().clearSelection();
                quantity.setPromptText(j);
            }
            selected_value = null;
            quantity_num = "1";
        }
        );
//
//        
//            

        return bp;

    }

    public BorderPane ps_container() throws SQLException {

        BorderPane container = container();
        container.setTop(new Label("ps " + Integer.toString(getTable_Id())));

        BorderPane bpp = new BorderPane();
        ComboBox comboBox = new ComboBox();
        comboBox.setPromptText("Choose ps time");
        comboBox.setPromptText("select item");

        conn = dbConn.DBConnection();
        try {
            pst = conn.prepareStatement("select * from items where FK_ITEM_TYPE=3 ");
            res = pst.executeQuery();
            while (res.next()) {
                comboBox.getItems().add(res.getString(2));
            }
            pst.close();
            conn.close();
        } catch (Exception ex) {
            System.out.println(ex.toString());
        }

        Button btn_start = new Button("Start");
        btn_start.setMaxWidth(Double.MAX_VALUE);
        Label startin = new Label("startin");
        Label endin = new Label("endin");
        Label startresult = new Label("-------------");
        Label endresult = new Label("-------------");

        GridPane grid = new GridPane();
        grid.add(comboBox, 0, 0);
        grid.add(btn_start, 1, 0);
        grid.add(startin, 0, 1);
        grid.add(startresult, 1, 1);
        grid.add(endin, 0, 2);
        grid.add(endresult, 1, 2);
        grid.add(container, 0, 3, 3, 3);

        bpp.setCenter(grid);

        comboBox.setOnAction((event) -> {
            ps = (String) comboBox.getValue();
            System.out.println(ps);
        });

        LocalTime myObj = LocalTime.now();
        DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("hh:mm");
        String start = myFormatObj.format(myObj);
        Button btn_end = new Button("end");
        btn_start.setOnAction((ActionEvent e) -> {
            try {
                switch (ps) {
                    case "playStation 15 min" ->
                        endresult.setText(end_time(15));
                    case "PlayStation 30 min" ->
                        endresult.setText(end_time(30));
                    case "open ps" ->
                        grid.add(btn_end, 1, 2);
                }
                quantity_num = "1";
                add_itemToTable(ps);
                startresult.setText(start);
            } catch (Exception ex) {
                Logger.getLogger(Containers.class.getName()).log(Level.SEVERE, null, ex);
            }

        });

        return bpp;
    }
//    
//    
//    
//   
    VBox final_checkout;

    public VBox checkout() {
        t_pay = new TextField();
        t_pay.setPromptText("Add Value TO Pay");
        total_price = gettotalpriceoftable(getTable_Id());
        btn_pay = new Button("pay(" + total_price + ")");
        btn_pay.setMaxWidth(Double.MAX_VALUE);
        HBox chekout = new HBox(t_pay, btn_pay);
        chekout.getMaxWidth();
//        
        Label total = new Label("Total");
        l_total_price = new Label(Integer.toString(total_price));
        HBox total_info = new HBox(total, l_total_price);
        total_info.getMaxWidth();
        total_info.setSpacing(20);
//
        final_checkout = new VBox(chekout, total_info);
        VBox vx = new VBox();
        btn_pay.setOnAction(
                (ActionEvent e) -> {
                    Label finish = new Label("This Table Finished ");
                    finish.setStyle("-fx-text-fill: red");
//                    
                    Label pay_part = new Label("-" + t_pay.getText());
                    Label l_pay_part = new Label("paid ");
//                          
                    HBox x = new HBox(l_pay_part, pay_part);
                    x.setSpacing(20);
//                    
                    int paid = Integer.parseInt(t_pay.getText());
                    if (clicked == 0) {
                        rest = gettotalpriceoftable(getTable_Id()) - paid;
                    } else {
                        rest -= paid;
                    }
                    Label fff = new Label("you must return  --->" + rest);

                    if (rest >= 0) {
                        vx.getChildren().add(x);
                        if (rest == 0) {
                            vx.getChildren().add(finish);
                            btn_pay.setDisable(true);
                        }
                    } else {
                        vx.getChildren().add(x);
                        vx.getChildren().add(fff);
                        vx.getChildren().add(finish);
                        btn_pay.setDisable(true);
                    }

                    chkout.setCenter(vx);
                    clicked++;
                }
        );
        return final_checkout;
    }

    public int gettotalpriceoftable(int t_id) {
        total_price = 0;
        String sql = "select sum (UNIT_PRICE) from logline where FK_LOG_ID=?";
        conn = dbConn.DBConnection();
        try {
            pst = conn.prepareStatement(sql);
            pst.setInt(1, t_id + log_id);
            res = pst.executeQuery();
            if (res.next()) {
                total_price = res.getInt(1);
            }

            pst.close();
            conn.close();
            if (l_total_price != null) {
                l_total_price.setText(Integer.toString(total_price));
            }

        } catch (Exception ex) {
            System.out.println(ex.toString());
        }
        return total_price;
    }
    VBox vv;

    public VBox show_itemsInEachtable(int t_id) {
        vv = new VBox();
        String sql = "select l.FK_LOG_ID , i.name , l.QUANTITY, l.UNIT_PRICE  from LOGLINE l left outer join ITEMS i on(i.TABLE_ITEMS_ID=l.FK_TABLE_ITEMS_ID) where FK_LOG_ID=?";
        conn = dbConn.DBConnection();
        try {

            pst = conn.prepareStatement(sql);
            pst.setInt(1, log_id + t_id);
            res = pst.executeQuery();

            while (res.next()) {
                HBox h = insertToShow(res.getString(2), res.getInt(3), res.getInt(4));
                vv.getChildren().add(h);
            }
            pst.close();
            conn.close();

        } catch (Exception ex) {
            System.out.println(ex.toString());
        }
        return vv;
    }
    HBox hss;
    Label l1;
    Label l2;

    public HBox insertToShow(String name, int quantity, int unit_price) {
        l1 = new Label(name + " x" + quantity);
        l2 = new Label(Integer.toString(unit_price));

        Button btn_delete = new Button("🗑️");
        btn_delete.setBackground(Background.EMPTY);

        VBox items_name = new VBox();
        VBox items_totalprice = new VBox();
        VBox item_edit = new VBox();

        items_name.setPrefWidth(120);
        items_totalprice.setPrefWidth(40);

        items_name.getChildren().add(l1);
        items_totalprice.getChildren().add(l2);
        item_edit.getChildren().add(btn_delete);

        hss = new HBox();
        hss.getChildren().addAll(items_name, items_totalprice, item_edit);

        hss.setSpacing(20);

        return hss;
    }

    int price = 0;
    String name;

    public void getitem_information(String selected_item) {
        FilteredList<Items_info> filter = new FilteredList<>(data, e -> true);
        flag = false;
        filter.setPredicate(item -> {//or can use for each
            if (item.getName().equalsIgnoreCase(selected_item)) {
                flag = true;
                name = item.getName();
                selecteditem_id = item.getId();
                price = item.getPrice();
                return flag;
            }
            return flag;
        });
        unit_price = Integer.parseInt(quantity_num) * price;
        rest += unit_price;
    }

    public void add_itemToTable(String selected_item) {
        getitem_information(selected_item);
        int log_num = log_id + getTable_Id();
        boolean hasiems = has_items(log_num);

        System.out.println("table of log :" + log_num + "  --> has items ? :" + hasiems);
        System.out.println("***************");
        Date dNow = new Date();
        SimpleDateFormat ft = new SimpleDateFormat("MM.dd hh:mm:ss");

        conn = dbConn.DBConnection();
        int i2 = 0;
        try {
            if (!hasiems) {
                pst2 = conn.prepareStatement("Insert into LOG (LOG_ID) Values(?)");
                pst2.setInt(1, log_num);
                i2 = pst2.executeUpdate();
                pst2.close();
            }
//
            pst = conn.prepareStatement("Insert into LOGLINE (FK_TABLE_ITEMS_ID, FK_LOG_ID, UNIT_PRICE, QUANTITY,TIMEDATE) Values(?,?,?,?,?)");
            pst.setInt(1, selecteditem_id);
            pst.setInt(2, log_num);
            pst.setInt(3, unit_price);
            pst.setInt(4, Integer.parseInt(quantity_num));
            pst.setString(5, ft.format(dNow));
            int i1 = pst.executeUpdate();

            if (i1 == 1) {
                vv.getChildren().add(insertToShow(name, Integer.parseInt(quantity_num), unit_price));
            }
            pst.close();
            conn.close();
        } catch (Exception ex) {
            System.out.println(ex.toString());
        }
    }

    public void show() throws SQLException {

        data = FXCollections.observableArrayList();
        conn = dbConn.DBConnection();

        pst = conn.prepareStatement("select * from ITEMS order by TABLE_ITEMS_ID");
        res = pst.executeQuery();

        while (res.next()) {
            data.add(new Items_info(res.getInt(1), res.getString(2), res.getInt(3), res.getInt(4)));
        }

        pst.close();
        conn.close();
    }
//    public int getItemId(String newValue) throws Exception {
////        show();//show must before filter
//        FilteredList<Items_info> filter = new FilteredList<>(data, e -> true);
//
//        flag = false;
//        selecteditem_id = -1;
//        filter.setPredicate(item -> {//or can use for each
//            if (item.getName().equalsIgnoreCase(newValue)) {
//                flag = true;
//                selecteditem_id = item.getId();
//                return flag;
//            }
//            return flag;
//        });
//        return selecteditem_id;
//    }
//    
//    
//    
//    
//    public int getTable_Id(BorderPane bp) {  /////////gettable id by border pane
//        int a = -1;
//        if (bp.getTop().toString().length() < 35) {
//
//        } else {
//
//            String iii = bp.getTop().toString();
//            char[] chh = new char[5];
//            iii.getChars(iii.indexOf(" ") + 1, iii.length() - 1, chh, 0);
//            a = Integer.parseInt(String.valueOf(chh).trim());
//        }
//        return a;
//    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

}
