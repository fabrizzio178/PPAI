package com.ppai.DAO;

import com.ppai.Model.TipoMotivo;
import com.ppai.Database.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TipoMotivoDAO {

    public List<TipoMotivo> getAllTiposMotivo() throws SQLException {
        List<TipoMotivo> tiposMotivo = new ArrayList<>();
        String query = "SELECT id, nombre FROM tipo_motivo";

        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                TipoMotivo tipoMotivo = new TipoMotivo(
                        rs.getString("nombre")
                );
                tiposMotivo.add(tipoMotivo);
            }
        }
        return tiposMotivo;
    }

    public TipoMotivo getTipoMotivoById(int id) throws SQLException {
        String query = "SELECT nombre FROM tipo_motivo WHERE id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setInt(1, id);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return new TipoMotivo(
                            rs.getString("nombre")
                    );
                }
            }
        }
        return null;
    }

    public TipoMotivo getTipoMotivoByDescripcion(String descripcion) throws SQLException {
        String query = "SELECT id, nombre FROM tipo_motivo WHERE nombre = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, descripcion);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return new TipoMotivo(
                            rs.getString("nombre")
                    );
                }
            }
        }
        return null;
    }
}