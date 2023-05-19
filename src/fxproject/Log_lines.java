/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fxproject;

import java.util.List;

/**
 *
 * @author DELL
 */
public class Log_lines extends Table_log{
    int unit_price,quantity;
    String name;

    public Log_lines( int log_id, String name, int quantity,int unit_price) {
        super(log_id);
        this.unit_price = unit_price;
        this.quantity = quantity;
        this.name = name;
    }

    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    




    public int getUnit_price() {
        return unit_price;
    }

    public void setUnit_price(int unit_price) {
        this.unit_price = unit_price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public int getLog_id() {
        return log_id;
    }

    @Override
    public void setLog_id(int log_id) {
        this.log_id = log_id;
    }

    @Override
    public List<Log_lines> getLoglines() {
        return loglines;
    }

    @Override
    public void setLoglines(List<Log_lines> loglines) {
        this.loglines = loglines;
    }
    
    
    
}
