
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
import modelo.Producto;

public class ProductoDao {
    
    Connection conexion;
    
    public ProductoDao() {
        try {
            this.conexion = Conexion.obtener();
        } catch (SQLException ex) {
            ex.printStackTrace();
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        }
    }
    
    public void guardar(Producto producto) {
    PreparedStatement consulta = null;
        try {
            consulta = conexion.prepareStatement("INSERT INTO producto (nombre, descripcion, precio, proveedor_id) VALUES (?, ?, ?, ?)");
            consulta.setString(1, producto.getNombre());
            consulta.setString(2, producto.getDescripcion());
            consulta.setDouble(3, producto.getPrecio());
            consulta.setInt(4, producto.getProveedor());
            consulta.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(ProductoDao.class.getName()).log(Level.SEVERE, null, ex);
            throw new RuntimeException("Error al guardar el producto en la base de datos.", ex);
        } finally {
            try {
                if (consulta != null) {
                    consulta.close();
                }
            } catch (SQLException ex) {
                Logger.getLogger(ProductoDao.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    // Método para obtener todos los productos
    public List<Producto> obtenerTodos() {
        List<Producto> productos = new ArrayList<>();
        PreparedStatement seleccionar = null;
        ResultSet consulta = null;

        try {
            seleccionar = conexion.prepareStatement("SELECT id, nombre, descripcion, precio, proveedor_id FROM producto");
            consulta = seleccionar.executeQuery();

            while (consulta.next()) {
                Producto producto = new Producto();
                producto.setCodigo(consulta.getInt("id"));
                producto.setNombre(consulta.getString("nombre"));
                producto.setDescripcion(consulta.getString("descripcion"));
                producto.setPrecio(consulta.getDouble("precio"));
                producto.setProveedor(consulta.getInt("proveedor_id"));
                productos.add(producto);
            }
        } catch (SQLException ex) {
            Logger.getLogger(ProductoDao.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                if (consulta != null) consulta.close();
                if (seleccionar != null) seleccionar.close();
                if (conexion != null) conexion.close(); // Cerrar conexión
            } catch (SQLException ex) {
                Logger.getLogger(ProductoDao.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return productos;
    }
    
    public boolean eliminar(int codigo) throws SQLException, ClassNotFoundException {
        String query = "DELETE FROM producto WHERE id = ?";

        try (Connection conexion = Conexion.obtener();
             PreparedStatement eliminar = conexion.prepareStatement(query)) {

            eliminar.setInt(1, codigo);
            int filasAfectadas = eliminar.executeUpdate();

            return filasAfectadas > 0; // Retorna verdadero si se eliminó al menos un registro
        } catch (SQLException ex) {
            Logger.getLogger(ProductoDao.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }
    
    // Método en ProductoDao para buscar un producto por código
    public Producto buscarPorCodigo(int codigo) {
        Producto producto = null;
        String query = "SELECT id, nombre, descripcion, precio, proveedor_id FROM producto WHERE id = ?";
        try (PreparedStatement buscar = conexion.prepareStatement(query)) {
            buscar.setInt(1, codigo);
            try (ResultSet resultado = buscar.executeQuery()) {
                if (resultado.next()) {
                    producto = new Producto();
                    producto.setCodigo(resultado.getInt("id"));
                    producto.setNombre(resultado.getString("nombre"));
                    producto.setDescripcion(resultado.getString("descripcion"));
                    producto.setPrecio(resultado.getDouble("precio"));
                    producto.setProveedor(resultado.getInt("proveedor_id"));
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(ProductoDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return producto;
    }
    
    // Método en ProductoDao para actualizar un producto
    public boolean actualizar(Producto producto) {
        String query = "UPDATE producto SET nombre = ?, descripcion = ?, precio = ?, proveedor_id = ? WHERE id = ?";
        try (PreparedStatement actualizar = conexion.prepareStatement(query)) {
            actualizar.setString(1, producto.getNombre());
            actualizar.setString(2, producto.getDescripcion());
            actualizar.setDouble(3, producto.getPrecio());
            actualizar.setInt(4, producto.getProveedor());
            actualizar.setInt(5, producto.getCodigo());
            int filasAfectadas = actualizar.executeUpdate();
            return filasAfectadas > 0; // Retorna verdadero si se actualizó al menos un registro
        } catch (SQLException ex) {
            Logger.getLogger(ProductoDao.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }
    
    
    
}
