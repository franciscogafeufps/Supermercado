
package modelo.dao;

import servicios.Conexion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import modelo.Proveedor;


public class ProveedorDao {
    
    Connection conexion;

    public ProveedorDao() {
        try {
            this.conexion = Conexion.obtener();
        } catch (SQLException ex) {
            ex.printStackTrace();
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        }
    }
    
    public void guardar(Proveedor proveedor) {
    PreparedStatement consulta = null;
        try {
            consulta = conexion.prepareStatement("INSERT INTO proveedor (nombre, contacto, telefono) VALUES (?, ?, ?)");
            consulta.setString(1, proveedor.getNombre());
            consulta.setString(2, proveedor.getContacto());
            consulta.setString(3, proveedor.getTelefono());            
            consulta.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(ProveedorDao.class.getName()).log(Level.SEVERE, null, ex);
            throw new RuntimeException("Error al guardar el proveedor en la base de datos.", ex);
        } finally {
            try {
                if (consulta != null) {
                    consulta.close();
                }
            } catch (SQLException ex) {
                Logger.getLogger(ProveedorDao.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    public boolean eliminar(int id) throws SQLException, ClassNotFoundException {
        String query = "DELETE FROM proveedor WHERE id = ?";

        try (Connection conexion = Conexion.obtener();
             PreparedStatement eliminar = conexion.prepareStatement(query)) {

            eliminar.setInt(1, id);
            int filasAfectadas = eliminar.executeUpdate();

            return filasAfectadas > 0; 
        } catch (SQLException ex) {
            Logger.getLogger(ProveedorDao.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }
    
    public Proveedor buscarPorId(int id) {
        Proveedor proveedor = null;
        String query = "SELECT id, nombre, contacto, telefono FROM proveedor WHERE id = ?";
        try (PreparedStatement buscar = conexion.prepareStatement(query)) {
            buscar.setInt(1, id);
            try (ResultSet resultado = buscar.executeQuery()) {
                if (resultado.next()) {
                    proveedor = new Proveedor();
                    proveedor.setId(resultado.getInt("id"));
                    proveedor.setNombre(resultado.getString("nombre"));
                    proveedor.setContacto(resultado.getString("contacto"));
                    proveedor.setTelefono(resultado.getString("telefono"));
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(ProveedorDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return proveedor;
    }
    
    public boolean actualizar(Proveedor proveedor) {
        String query = "UPDATE proveedor SET nombre = ?, contacto = ?, telefono = ? WHERE id = ?";
        try (PreparedStatement actualizar = conexion.prepareStatement(query)) {
            actualizar.setString(1, proveedor.getNombre());
            actualizar.setString(2, proveedor.getContacto());
            actualizar.setString(3, proveedor.getTelefono());
            actualizar.setInt(4, proveedor.getId());
            int filasAfectadas = actualizar.executeUpdate();
            return filasAfectadas > 0;
        } catch (SQLException ex) {
            Logger.getLogger(ProveedorDao.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }
    
    
}
