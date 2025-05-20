package com.ppai.DAO;

import com.ppai.Model.Usuario;
import com.ppai.Model.Empleado;
import com.ppai.Database.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UsuarioDAO {
    private EmpleadoDAO empleadoDAO;

    public UsuarioDAO() {
        this.empleadoDAO = new EmpleadoDAO();
    }

    public List<Usuario> getAllUsuarios() throws SQLException {
        List<Usuario> usuarios = new ArrayList<>();
        String query = "SELECT id, password, nombre_usuario, habilitado, empleado_id FROM usuario";

        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                Empleado empleado = empleadoDAO.getEmpleadoById(rs.getInt("empleado_id"));
                Usuario usuario = new Usuario(
                        rs.getString("password"),          // contrasena
                        rs.getString("nombre_usuario"),    // nombreUsuario
                        null,                             // perfil (último acceso no está en la BD)
                        rs.getInt("habilitado"),          // suscripcion
                        empleado
                );
                usuarios.add(usuario);
            }
        }
        return usuarios;
    }

    public Usuario getUsuarioById(int id) throws SQLException {
        String query = "SELECT password, nombre_usuario, habilitado, empleado_id FROM usuario WHERE id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setInt(1, id);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    Empleado empleado = empleadoDAO.getEmpleadoById(rs.getInt("empleado_id"));
                    return new Usuario(
                            rs.getString("password"),
                            rs.getString("nombre_usuario"),
                            null,                           // perfil (último acceso no está en la BD)
                            rs.getInt("habilitado"),
                            empleado
                    );
                }
            }
        }
        return null;
    }

    public Usuario getUsuarioByNombreUsuario(String nombreUsuario) throws SQLException {
        String query = "SELECT password, nombre_usuario, habilitado, empleado_id FROM usuario WHERE nombre_usuario = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, nombreUsuario);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    Empleado empleado = empleadoDAO.getEmpleadoById(rs.getInt("empleado_id"));
                    return new Usuario(
                            rs.getString("password"),
                            rs.getString("nombre_usuario"),
                            null,                           // perfil (último acceso no está en la BD)
                            rs.getInt("habilitado"),
                            empleado
                    );
                }
            }
        }
        return null;
    }
}
