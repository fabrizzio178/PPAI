package com.ppai.DAO;

import com.ppai.Model.EstacionSismologica;
import com.ppai.Database.DatabaseConnection;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class EstacionSismologicaDAO {

    public ArrayList<EstacionSismologica> getAllEstaciones() throws SQLException {
        ArrayList<EstacionSismologica> estaciones = new ArrayList<>();
        String query = "SELECT id, codigo_estacion, documento_certificacion_adq, fecha_solicitud_adquisicion, " +
                "latitud, longitud, nombre, numero_certificacion_adquisicion FROM estacion_sismologica";

        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                EstacionSismologica estacion = new EstacionSismologica(
                        rs.getInt("codigo_estacion"),
                        rs.getString("documento_certificacion_adq"),
                        rs.getTimestamp("fecha_solicitud_adquisicion").toLocalDateTime(),
                        rs.getDouble("latitud"),
                        rs.getDouble("longitud"),
                        rs.getString("nombre"),
                        rs.getInt("numero_certificacion_adquisicion")
                );
                estaciones.add(estacion);
            }
        }
        return estaciones;
    }

    public EstacionSismologica getEstacionById(int id) throws SQLException {
        String query = "SELECT codigo_estacion, documento_certificacion_adq, fecha_solicitud_adquisicion, " +
                "latitud, longitud, nombre, numero_certificacion_adquisicion " +
                "FROM estacion_sismologica WHERE id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setInt(1, id);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return new EstacionSismologica(
                            rs.getInt("codigo_estacion"),
                            rs.getString("documento_certificacion_adq"),
                            rs.getTimestamp("fecha_solicitud_adquisicion").toLocalDateTime(),
                            rs.getDouble("latitud"),
                            rs.getDouble("longitud"),
                            rs.getString("nombre"),
                            rs.getInt("numero_certificacion_adquisicion")
                    );
                }
            }
        }
        return null;
    }

    public EstacionSismologica getEstacionByNombre(String nombre) throws SQLException {
        String query = "SELECT codigo_estacion, documento_certificacion_adq, fecha_solicitud_adquisicion, " +
                "latitud, longitud, nombre, numero_certificacion_adquisicion " +
                "FROM estacion_sismologica WHERE nombre = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, nombre);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return new EstacionSismologica(
                            rs.getInt("codigo_estacion"),
                            rs.getString("documento_certificacion_adq"),
                            rs.getTimestamp("fecha_solicitud_adquisicion").toLocalDateTime(),
                            rs.getDouble("latitud"),
                            rs.getDouble("longitud"),
                            rs.getString("nombre"),
                            rs.getInt("numero_certificacion_adquisicion")
                    );
                }
            }
        }
        return null;
    }

    public EstacionSismologica getEstacionByCodigoEstacion(int codigoEstacion) throws SQLException {
        String query = "SELECT codigo_estacion, documento_certificacion_adq, fecha_solicitud_adquisicion, " +
                "latitud, longitud, nombre, numero_certificacion_adquisicion " +
                "FROM estacion_sismologica WHERE codigo_estacion = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setInt(1, codigoEstacion);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return new EstacionSismologica(
                            rs.getInt("codigo_estacion"),
                            rs.getString("documento_certificacion_adq"),
                            rs.getTimestamp("fecha_solicitud_adquisicion").toLocalDateTime(),
                            rs.getDouble("latitud"),
                            rs.getDouble("longitud"),
                            rs.getString("nombre"),
                            rs.getInt("numero_certificacion_adquisicion")
                    );
                }
            }
        }
        return null;
    }
}
