package com.ppai.DAO;

import com.ppai.Model.OrdenDeInspeccion;
import com.ppai.Model.EstacionSismologica;
import com.ppai.Model.Estado;
import com.ppai.Model.Empleado;
import com.ppai.Database.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OrdenDeInspeccionDAO {
    private EstacionSismologicaDAO estacionDAO;
    private EstadoDAO estadoDAO;
    private EmpleadoDAO empleadoDAO;

    public OrdenDeInspeccionDAO() {
        this.estacionDAO = new EstacionSismologicaDAO();
        this.estadoDAO = new EstadoDAO();
        this.empleadoDAO = new EmpleadoDAO();
    }

    public ArrayList<OrdenDeInspeccion> getAllOrdenes() throws SQLException {
        ArrayList<OrdenDeInspeccion> ordenes = new ArrayList<>();
        String query = "SELECT id, numero_orden, fecha_hora_finalizacion, fecha_hora_cierre, " +
                "fecha_hora_inicio, observacion_cierre, tarea_asignada, estacion_sismologica_id, " +
                "estado_id, empleado_id FROM orden_inspeccion";

        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                EstacionSismologica estacion = estacionDAO.getEstacionById(rs.getInt("estacion_sismologica_id"));
                Estado estado = estadoDAO.getEstadoById(rs.getInt("estado_id"));
                Empleado empleado = empleadoDAO.getEmpleadoById(rs.getInt("empleado_id"));

                OrdenDeInspeccion orden = new OrdenDeInspeccion(
                        rs.getInt("numero_orden"),
                        rs.getTimestamp("fecha_hora_finalizacion") != null ? rs.getTimestamp("fecha_hora_finalizacion").toLocalDateTime() : null,
                        rs.getTimestamp("fecha_hora_cierre") != null ? rs.getTimestamp("fecha_hora_cierre").toLocalDateTime() : null,
                        rs.getTimestamp("fecha_hora_inicio").toLocalDateTime(),
                        rs.getString("observacion_cierre"),
                        rs.getString("tarea_asignada"),
                        estacion,
                        estado,
                        empleado
                );
                ordenes.add(orden);
            }
        }
        return ordenes;
    }

    public OrdenDeInspeccion getOrdenById(int id) throws SQLException {
        String query = "SELECT numero_orden, fecha_hora_finalizacion, fecha_hora_cierre, " +
                "fecha_hora_inicio, observacion_cierre, tarea_asignada, estacion_sismologica_id, " +
                "estado_id, empleado_id FROM orden_inspeccion WHERE id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setInt(1, id);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    EstacionSismologica estacion = estacionDAO.getEstacionById(rs.getInt("estacion_sismologica_id"));
                    Estado estado = estadoDAO.getEstadoById(rs.getInt("estado_id"));
                    Empleado empleado = empleadoDAO.getEmpleadoById(rs.getInt("empleado_id"));

                    return new OrdenDeInspeccion(
                            rs.getInt("numero_orden"),
                            rs.getTimestamp("fecha_hora_finalizacion").toLocalDateTime(),
                            rs.getTimestamp("fecha_hora_cierre").toLocalDateTime(),
                            rs.getTimestamp("fecha_hora_inicio").toLocalDateTime(),
                            rs.getString("observacion_cierre"),
                            rs.getString("tarea_asignada"),
                            estacion,
                            estado,
                            empleado
                    );
                }
            }
        }
        return null;
    }

    public List<OrdenDeInspeccion> getOrdenesByEmpleado(int empleadoId) throws SQLException {
        List<OrdenDeInspeccion> ordenes = new ArrayList<>();
        String query = "SELECT id, numero_orden, fecha_hora_finalizacion, fecha_hora_cierre, " +
                "fecha_hora_inicio, observacion_cierre, tarea_asignada, estacion_sismologica_id, " +
                "estado_id FROM orden_inspeccion WHERE empleado_id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setInt(1, empleadoId);

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    EstacionSismologica estacion = estacionDAO.getEstacionById(rs.getInt("estacion_sismologica_id"));
                    Estado estado = estadoDAO.getEstadoById(rs.getInt("estado_id"));
                    Empleado empleado = empleadoDAO.getEmpleadoById(empleadoId);

                    OrdenDeInspeccion orden = new OrdenDeInspeccion(
                            rs.getInt("numero_orden"),
                            rs.getTimestamp("fecha_hora_finalizacion").toLocalDateTime(),
                            rs.getTimestamp("fecha_hora_cierre").toLocalDateTime(),
                            rs.getTimestamp("fecha_hora_inicio").toLocalDateTime(),
                            rs.getString("observacion_cierre"),
                            rs.getString("tarea_asignada"),
                            estacion,
                            estado,
                            empleado
                    );
                    ordenes.add(orden);
                }
            }
        }
        return ordenes;
    }
}
