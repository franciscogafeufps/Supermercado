
package modelo.dao;

import modelo.Compra;
import modelo.Cliente;
import modelo.Producto;
import servicios.Conexion;
import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import modelo.Proveedor;


public class CompraDao {
    
    private Connection conexion;

    public CompraDao() {
                try {
            this.conexion = Conexion.obtener();
        } catch (SQLException | ClassNotFoundException ex) {
            ex.printStackTrace();
        }
    }
    
    public void guardar(Compra compra) {
        String query = "INSERT INTO compra (proveedor_id, producto_id, cantidad, fecha) VALUES (?, ?, ?, ?)";
        try (PreparedStatement ps = conexion.prepareStatement(query)) {
            ps.setInt(1, compra.getProveedor().getId()); // Debes ajustar esto según cómo obtienes el ID del proveedor
            ps.setInt(2, compra.getProducto().getCodigo()); // Ajusta según el ID del producto
            ps.setInt(3, compra.getCantidad());
            ps.setTimestamp(4, new Timestamp(compra.getFecha().getTime())); // Ajusta la fecha
            ps.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(VentaDao.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public Compra buscarPorId(int id) {
        String query = "SELECT * FROM compra WHERE id = ?";
        try (PreparedStatement ps = conexion.prepareStatement(query)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Compra compra = new Compra();
                    compra.setId(rs.getInt("id"));
                    
                    ProveedorDao proveedor = new ProveedorDao();
                    compra.setProveedor(proveedor.buscarPorId(rs.getInt("proveedor_id")));
                    ProductoDao producto = new ProductoDao();
                    compra.setProducto(producto.buscarPorCodigo(rs.getInt("producto_id")));
                                     
                    compra.setCantidad(rs.getInt("cantidad"));
                    compra.setFecha(rs.getTimestamp("fecha"));
                    return compra;
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(VentaDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    public boolean actualizar(Compra compra) {
        String query = "UPDATE compra SET proveedor_id = ?, producto_id = ?, cantidad = ?, fecha = ? WHERE id = ?";
        try (PreparedStatement ps = conexion.prepareStatement(query)) {
            ps.setInt(1, compra.getProveedor().getId());
            ps.setInt(2, compra.getProducto().getCodigo());
            ps.setInt(3, compra.getCantidad());
            ps.setDate(4, new java.sql.Date(compra.getFecha().getTime()));
            ps.setInt(5, compra.getId());
            int filasAfectadas = ps.executeUpdate();
            return filasAfectadas > 0;
        } catch (SQLException ex) {
            Logger.getLogger(CompraDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
    
    public boolean eliminar(int id) {
        String query = "DELETE FROM compra WHERE id = ?";
        try (PreparedStatement ps = conexion.prepareStatement(query)) {
            ps.setInt(1, id);
            int filasAfectadas = ps.executeUpdate();
            return filasAfectadas > 0;
        } catch (SQLException ex) {
            Logger.getLogger(CompraDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
    
    public List<Compra> obtenerTodas() {
        List<Compra> compras = new ArrayList<>();
        String query = "SELECT * FROM compra";
        try (PreparedStatement ps = conexion.prepareStatement(query);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Compra compra = new Compra();
                compra.setId(rs.getInt("id"));
                
                // Cargar el cliente usando ProveedorDao
                ProveedorDao proveedorDao = new ProveedorDao();
                Proveedor proveedor = proveedorDao.buscarPorId(rs.getInt("proveedor_id"));
                compra.setProveedor(proveedor);

                // Cargar el producto usando ProductoDao
                ProductoDao productoDao = new ProductoDao();
                Producto producto = productoDao.buscarPorCodigo(rs.getInt("producto_id"));
                compra.setProducto(producto);
                
                compra.setCantidad(rs.getInt("cantidad"));
                compra.setFecha(rs.getDate("fecha"));
                compras.add(compra);
            }
        } catch (SQLException ex) {
            Logger.getLogger(VentaDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return compras;
    }
    
    public List<Compra> obtenerCompras() throws SQLException, ClassNotFoundException {
        List<Compra> compras = new ArrayList<>();
        String query = "SELECT id, cliente_id, producto_id, cantidad, fecha FROM compra";

        try (PreparedStatement seleccionar = conexion.prepareStatement(query);
             ResultSet consulta = seleccionar.executeQuery()) {

            while (consulta.next()) {
                Compra compra = new Compra();
                compra.setId(consulta.getInt("id"));

                // Cargar el cliente usando ProveedorDao
                ProveedorDao proveedorDao = new ProveedorDao();
                Proveedor proveedor = proveedorDao.buscarPorId(consulta.getInt("proveedor_id"));
                compra.setProveedor(proveedor);

                // Cargar el producto usando ProductoDao
                ProductoDao productoDao = new ProductoDao();
                Producto producto = productoDao.buscarPorCodigo(consulta.getInt("producto_id"));
                compra.setProducto(producto);

                compra.setCantidad(consulta.getInt("cantidad"));
                compra.setFecha(consulta.getTimestamp("fecha")); 

                compras.add(compra);
            }
        } catch (SQLException ex) {
            Logger.getLogger(CompraDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return compras;
    }
    
}
