/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fxproject;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;

/**
 *
 * @author DELL
 */
public class Items_info{

    int id;
    String name;
    int price;
    int type;
     int clicked = 0;
    ObservableList<Items_info> data;

    Connection conn = null;
    PreparedStatement pst = null;
    ResultSet res = null;

    public Items_info() {
    }

    public Items_info(int id, String name, int price, int type) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.type = type;
    }


    public void getAllItems() throws SQLException {

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


    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }


}
