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
public class Table_log {

    int log_id;

    List<Log_lines> loglines;

    //aggregaton
    public Table_log(int log_id) {
        this.log_id = log_id;
    }

    public Table_log() {
    }

    public int getLog_id() {
        return log_id;
    }

    public void setLog_id(int log_id) {
        this.log_id = log_id;
    }

    public List<Log_lines> getLoglines() {
        return loglines;
    }

    public void setLoglines(List<Log_lines> loglines) {
        this.loglines = loglines;
    }

}
