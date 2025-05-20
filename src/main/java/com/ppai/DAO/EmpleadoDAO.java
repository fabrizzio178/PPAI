package com.ppai.DAO;

import com.ppai.Model.Empleado;
import com.ppai.Model.Rol;
import com.ppai.Database.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EmpleadoDAO {
    private RolDAO rolDAO;

    public EmpleadoDAO() {
        this.rolDAO = new RolDAO();
    }

    public List<Empleado> getAllEmpleados() throws SQLException {
        List<Empleado> empleados = new ArrayList<>();
        String query = "SELECT id, apellido, nombre, mail, telefono, rol_id FROM empleado";

        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                Rol rol = rolDAO.getRolById(rs.getInt("rol_id"));
                Empleado empleado = new Empleado(
                        rs.getString("apellido"),
                        rs.getString("nombre"),
                        rs.getString("mail"),
                        rs.getString("telefono"),
                        rol
                );
                empleados.add(empleado);
            }
        }
        return empleados;
    }

    public Empleado getEmpleadoById(int id) throws SQLException {
        String query = "SELECT apellido, nombre, mail, telefono, rol_id FROM empleado WHERE id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setInt(1, id);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    Rol rol = rolDAO.getRolById(rs.getInt("rol_id"));
                    return new Empleado(
                            rs.getString("apellido"),
                            rs.getString("nombre"),
                            rs.getString("mail"),
                            rs.getString("telefono"),
                            rol
                    );
                }
            }
        }
        return null;
    }

    public List<Empleado> getEmpleadosByRol(int rolId) throws SQLException {
        List<Empleado> empleados = new ArrayList<>();
        String query = "SELECT id, apellido, nombre, mail, telefono, rol_id FROM empleado WHERE rol_id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setInt(1, rolId);

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    Rol rol = rolDAO.getRolById(rs.getInt("rol_id"));
                    Empleado empleado = new Empleado(
                            rs.getString("apellido"),
                            rs.getString("nombre"),
                            rs.getString("mail"),
                            rs.getString("telefono"),
                            rol
                    );
                    empleados.add(empleado);
                }
            }
        }
        return empleados;
    }
}

