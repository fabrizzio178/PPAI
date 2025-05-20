package com.ppai.DAO;

import com.ppai.Model.CambioEstado;
import com.ppai.Model.Estado;
import com.ppai.Model.Empleado;
import com.ppai.Database.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;

public class CambioEstadoDAO {
    private EstadoDAO estadoDAO;
    private EmpleadoDAO empleadoDAO;

    public CambioEstadoDAO() {
        this.estadoDAO = new EstadoDAO();
        this.empleadoDAO = new EmpleadoDAO();
    }

    public ArrayList<CambioEstado> getCambiosEstadoBySismografoId(int sismografoId) throws SQLException {
        ArrayList<CambioEstado> cambiosEstado = new ArrayList<>();
        String query = "SELECT id, fecha_hora_fin, fecha_hora_inicio, estado_id, responsable_id " +
                "FROM cambio_estado WHERE sismografo_id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setInt(1, sismografoId);

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    Estado estado = estadoDAO.getEstadoById(rs.getInt("estado_id"));
                    Empleado responsable = empleadoDAO.getEmpleadoById(rs.getInt("responsable_id"));

                    Timestamp fechaHoraFin = rs.getTimestamp("fecha_hora_fin");
                    CambioEstado cambioEstado = new CambioEstado(
                            rs.getTimestamp("fecha_hora_inicio").toLocalDateTime(),
                            fechaHoraFin != null ? fechaHoraFin.toLocalDateTime() : null,
                            estado,
                            responsable
                    );
                    cambiosEstado.add(cambioEstado);
                }
            }
        }
        return cambiosEstado;
    }

    public CambioEstado getCambioEstadoById(int id) throws SQLException {
        String query = "SELECT fecha_hora_fin, fecha_hora_inicio, estado_id, responsable_id " +
                "FROM cambio_estado WHERE id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setInt(1, id);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    Estado estado = estadoDAO.getEstadoById(rs.getInt("estado_id"));
                    Empleado responsable = empleadoDAO.getEmpleadoById(rs.getInt("responsable_id"));

                    Timestamp fechaHoraFin = rs.getTimestamp("fecha_hora_fin");
                    return new CambioEstado(
                            rs.getTimestamp("fecha_hora_inicio").toLocalDateTime(),
                            fechaHoraFin != null ? fechaHoraFin.toLocalDateTime() : null,
                            estado,
                            responsable
                    );
                }
            }
        }
        return null;
    }
}
