/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fxproject;

import java.sql.SQLException;
import javafx.scene.layout.Pane;

/**
 *
 * @author DELL
 */
public class Tables_info {

    int id;
    String name;
    String roomtype;

    public Tables_info() {
    }

    public Tables_info(int id, String name, String roomtype) {
        this.id=id;
        this.name = name;
        this.roomtype = roomtype;
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

    public String getRoomtype() {
        return roomtype;
    }

    public void setRoomtype(String roomtype) {
        this.roomtype = roomtype;
    }

}
