
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
import modelo.Cliente;

public class ClienteDao {
    
    Connection conexion;
    
     public ClienteDao() {
        try {
            this.conexion = Conexion.obtener();
        } catch (SQLException ex) {
            ex.printStackTrace();
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        }
    }
     
    public void guardar(Cliente cliente) {
    PreparedStatement consulta = null;
        try {
            consulta = conexion.prepareStatement("INSERT INTO cliente (nombre, email, telefono, cliente_recomendador_id) VALUES (?, ?, ?, ?)");
            consulta.setString(1, cliente.getNombre());
            consulta.setString(2, cliente.getEmail());
            consulta.setString(3, cliente.getTelefono());
            consulta.setInt(4, cliente.getQuienRecomienda());
            consulta.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(ClienteDao.class.getName()).log(Level.SEVERE, null, ex);
            throw new RuntimeException("Error al guardar el cliente en la base de datos.", ex);
        } finally {
            try {
                if (consulta != null) {
                    consulta.close();
                }
            } catch (SQLException ex) {
                Logger.getLogger(ClienteDao.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    public boolean eliminar(int cedula) throws SQLException, ClassNotFoundException {
        String query = "DELETE FROM cliente WHERE id = ?";

        try (Connection conexion = Conexion.obtener();
             PreparedStatement eliminar = conexion.prepareStatement(query)) {

            eliminar.setInt(1, cedula);
            int filasAfectadas = eliminar.executeUpdate();

            return filasAfectadas > 0; 
        } catch (SQLException ex) {
            Logger.getLogger(ClienteDao.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }
    
    public Cliente buscarPorCedula(int cedula) {
        Cliente cliente = null;
        String query = "SELECT id, nombre, email, telefono, cliente_recomendador_id FROM cliente WHERE id = ?";
        try (PreparedStatement buscar = conexion.prepareStatement(query)) {
            buscar.setInt(1, cedula);
            try (ResultSet resultado = buscar.executeQuery()) {
                if (resultado.next()) {
                    cliente = new Cliente();
                    cliente.setCedula(resultado.getInt("id"));
                    cliente.setNombre(resultado.getString("nombre"));
                    cliente.setEmail(resultado.getString("email"));
                    cliente.setTelefono(resultado.getString("telefono"));
                    cliente.setQuienRecomienda(resultado.getInt("cliente_recomendador_id"));
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(ClienteDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return cliente;
    }
    
    public boolean actualizar(Cliente cliente) {
        String query = "UPDATE cliente SET nombre = ?, email = ?, telefono = ?, cliente_recomendador_id = ? WHERE id = ?";
        try (PreparedStatement actualizar = conexion.prepareStatement(query)) {
            actualizar.setString(1, cliente.getNombre());
            actualizar.setString(2, cliente.getEmail());
            actualizar.setString(3, cliente.getTelefono());
            actualizar.setInt(4, cliente.getQuienRecomienda());
            actualizar.setInt(5, cliente.getCedula());
            int filasAfectadas = actualizar.executeUpdate();
            return filasAfectadas > 0;
        } catch (SQLException ex) {
            Logger.getLogger(ClienteDao.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }
    
}
