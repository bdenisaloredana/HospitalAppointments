package com.example.demo1.Utils;

import java.sql.SQLException;

public interface Observer {
    void update() throws SQLException;
}