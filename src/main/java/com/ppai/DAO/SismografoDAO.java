package com.ppai.DAO;

import com.ppai.Model.Sismografo;
import com.ppai.Model.EstacionSismologica;
import com.ppai.Model.Estado;
import com.ppai.Model.CambioEstado;
import com.ppai.Database.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SismografoDAO {
    private EstacionSismologicaDAO estacionDAO;
    private EstadoDAO estadoDAO;
    private CambioEstadoDAO cambioEstadoDAO;

    public SismografoDAO() {
        this.estacionDAO = new EstacionSismologicaDAO();
        this.estadoDAO = new EstadoDAO();
        this.cambioEstadoDAO = new CambioEstadoDAO();
    }

    public Sismografo getSismografoById(int id) throws SQLException {
        String query = "SELECT fecha_adquisicion, identificador_sismografo, numero_serie, " +
                "serie_temporal, modelo, reparacion, estacion_sismologica_id, estado_actual_id " +
                "FROM sismografo WHERE id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setInt(1, id);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    EstacionSismologica estacion = estacionDAO.getEstacionById(rs.getInt("estacion_sismologica_id"));
                    Estado estadoActual = estadoDAO.getEstadoById(rs.getInt("estado_actual_id"));
                    ArrayList<CambioEstado> cambiosEstado = cambioEstadoDAO.getCambiosEstadoBySismografoId(id);

                    return new Sismografo(
                            rs.getTimestamp("fecha_adquisicion").toLocalDateTime(),
                            rs.getString("identificador_sismografo"),
                            rs.getString("numero_serie"),
                            rs.getString("serie_temporal"),
                            rs.getString("modelo"),
                            rs.getInt("reparacion") == 1,
                            estacion,
                            estadoActual,
                            cambiosEstado
                    );
                }
            }
        }
        return null;
    }

    public ArrayList<Sismografo> getAllSismografos() throws SQLException {
        ArrayList<Sismografo> sismografos = new ArrayList<>();
        String query = "SELECT id, fecha_adquisicion, identificador_sismografo, numero_serie, " +
                "serie_temporal, modelo, reparacion, estacion_sismologica_id, estado_actual_id " +
                "FROM sismografo";

        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                int sismografoId = rs.getInt("id");
                EstacionSismologica estacion = estacionDAO.getEstacionById(rs.getInt("estacion_sismologica_id"));
                Estado estadoActual = estadoDAO.getEstadoById(rs.getInt("estado_actual_id"));
                ArrayList<CambioEstado> cambiosEstado = cambioEstadoDAO.getCambiosEstadoBySismografoId(sismografoId);

                Sismografo sismografo = new Sismografo(
                        rs.getTimestamp("fecha_adquisicion").toLocalDateTime(),
                        rs.getString("identificador_sismografo"),
                        rs.getString("numero_serie"),
                        rs.getString("serie_temporal"),
                        rs.getString("modelo"),
                        rs.getInt("reparacion") == 1,
                        estacion,
                        estadoActual,
                        cambiosEstado
                );
                sismografos.add(sismografo);
            }
        }
        return sismografos;
    }
}
