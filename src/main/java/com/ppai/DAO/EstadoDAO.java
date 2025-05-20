package com.ppai.DAO;

import com.ppai.Model.Estado;
import com.ppai.Database.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EstadoDAO {

    public List<Estado> getAllEstados() throws SQLException {
        List<Estado> estados = new ArrayList<>();
        String query = "SELECT id, ambito, nombre_estado FROM estado";

        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                Estado estado = new Estado(
                        rs.getString("ambito"),
                        rs.getString("nombre_estado")
                );
                estados.add(estado);
            }
        }
        return estados;
    }

    public Estado getEstadoById(int id) throws SQLException {
        String query = "SELECT ambito, nombre_estado FROM estado WHERE id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setInt(1, id);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return new Estado(
                            rs.getString("ambito"),
                            rs.getString("nombre_estado")
                    );
                }
            }
        }
        return null;
    }

    public List<Estado> getEstadosByAmbito(String ambito) throws SQLException {
        List<Estado> estados = new ArrayList<>();
        String query = "SELECT id, ambito, nombre_estado FROM estado WHERE ambito = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, ambito);

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    Estado estado = new Estado(
                            rs.getString("ambito"),
                            rs.getString("nombre_estado")
                    );
                    estados.add(estado);
                }
            }
        }
        return estados;
    }

    public Estado getEstadoByAmbitoYNombre(String ambito, String nombreEstado) throws SQLException {
        String query = "SELECT id, ambito, nombre_estado FROM estado WHERE ambito = ? AND nombre_estado = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, ambito);
            pstmt.setString(2, nombreEstado);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return new Estado(
                            rs.getString("ambito"),
                            rs.getString("nombre_estado")
                    );
                }
            }
        }
        return null;
    }
}
