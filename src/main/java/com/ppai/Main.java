package com.ppai;
import com.ppai.View.Monitor;

import java.sql.SQLException;


public class Main {
    public static void main(String[] args) throws SQLException {
        Monitor monitor = new Monitor();
        monitor.cerrarOrdenInsp();
    }
}
