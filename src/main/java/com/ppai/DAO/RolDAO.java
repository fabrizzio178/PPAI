package com.ppai.DAO;

import com.ppai.Model.Rol;
import com.ppai.Database.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RolDAO {

    public ArrayList<Rol> getAllRoles() throws SQLException {
        ArrayList<Rol> roles = new ArrayList<>();
        String query = "SELECT descripcion_rol, nombre FROM rol";

        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                Rol rol = new Rol(
                        rs.getString("descripcion_rol"),
                        rs.getString("nombre")
                );
                roles.add(rol);
            }
        }
        return roles;
    }

    public Rol getRolById(int id) throws SQLException {
        String query = "SELECT descripcion_rol, nombre FROM rol WHERE id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setInt(1, id);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return new Rol(
                            rs.getString("descripcion_rol"),
                            rs.getString("nombre")
                    );
                }
            }
        }
        return null;
    }

    public Rol getRolByNombre(String nombre) throws SQLException {
        String query = "SELECT descripcion_rol, nombre FROM rol WHERE nombre = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, nombre);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return new Rol(
                            rs.getString("descripcion"),
                            rs.getString("nombre")
                    );
                }
            }
        }
        return null;
    }
}

