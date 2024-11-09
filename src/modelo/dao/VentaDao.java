package modelo.dao;

import modelo.Venta;
import modelo.Cliente;
import modelo.Producto;
import servicios.Conexion;
import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class VentaDao {
    
    private Connection conexion;

    public VentaDao() {
        try {
            this.conexion = Conexion.obtener();
        } catch (SQLException | ClassNotFoundException ex) {
            ex.printStackTrace();
        }
    }

    public void guardar(Venta venta) {
        String query = "INSERT INTO venta (cliente_id, producto_id, cantidad, fecha) VALUES (?, ?, ?, ?)";
        try (PreparedStatement ps = conexion.prepareStatement(query)) {
            ps.setInt(1, venta.getCliente().getCedula()); // Debes ajustar esto según cómo obtienes el ID del cliente
            ps.setInt(2, venta.getProducto().getCodigo()); // Ajusta según el ID del producto
            ps.setInt(3, venta.getCantidad());
            ps.setTimestamp(4, new Timestamp(venta.getFecha().getTime())); // Ajusta la fecha
            ps.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(VentaDao.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public Venta buscarPorId(int id) {
        String query = "SELECT * FROM venta WHERE id = ?";
        try (PreparedStatement ps = conexion.prepareStatement(query)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Venta venta = new Venta();
                    venta.setId(rs.getInt("id"));
                    
                    // Cargar el cliente usando ClienteDao
                    ClienteDao clienteDao = new ClienteDao();
                    Cliente cliente = clienteDao.buscarPorCedula(rs.getInt("cliente_id"));
                    venta.setCliente(cliente);

                    // Cargar el producto usando ProductoDao
                    ProductoDao productoDao = new ProductoDao();
                    Producto producto = productoDao.buscarPorCodigo(rs.getInt("producto_id"));
                    venta.setProducto(producto);
                    
                    venta.setCantidad(rs.getInt("cantidad"));
                    venta.setFecha(rs.getTimestamp("fecha"));
                    return venta;
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(VentaDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public boolean actualizar(Venta venta) {
        String query = "UPDATE venta SET cliente_id = ?, producto_id = ?, cantidad = ?, fecha = ? WHERE id = ?";
        try (PreparedStatement ps = conexion.prepareStatement(query)) {
            ps.setInt(1, venta.getCliente().getCedula());
            ps.setInt(2, venta.getProducto().getCodigo());
            ps.setInt(3, venta.getCantidad());
            ps.setDate(4, new java.sql.Date(venta.getFecha().getTime()));
            ps.setInt(5, venta.getId());
            int filasAfectadas = ps.executeUpdate();
            return filasAfectadas > 0;
        } catch (SQLException ex) {
            Logger.getLogger(VentaDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    public boolean eliminar(int id) {
        String query = "DELETE FROM venta WHERE id = ?";
        try (PreparedStatement ps = conexion.prepareStatement(query)) {
            ps.setInt(1, id);
            int filasAfectadas = ps.executeUpdate();
            return filasAfectadas > 0;
        } catch (SQLException ex) {
            Logger.getLogger(VentaDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    public List<Venta> obtenerTodas() {
        List<Venta> ventas = new ArrayList<>();
        String query = "SELECT * FROM venta";
        try (PreparedStatement ps = conexion.prepareStatement(query);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Venta venta = new Venta();
                venta.setId(rs.getInt("id"));
                // Aquí debes cargar el cliente y producto por sus IDs
                venta.setCantidad(rs.getInt("cantidad"));
                venta.setFecha(rs.getDate("fecha"));
                ventas.add(venta);
            }
        } catch (SQLException ex) {
            Logger.getLogger(VentaDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return ventas;
    }
    
    
    public List<Venta> obtenerVentas() throws SQLException, ClassNotFoundException {
        List<Venta> ventas = new ArrayList<>();
        String query = "SELECT id, cliente_id, producto_id, cantidad, fecha FROM venta";

        try (PreparedStatement seleccionar = conexion.prepareStatement(query);
             ResultSet consulta = seleccionar.executeQuery()) {

            while (consulta.next()) {
                Venta venta = new Venta();
                venta.setId(consulta.getInt("id"));

                // Cargar el cliente usando ClienteDao
                ClienteDao clienteDao = new ClienteDao();
                Cliente cliente = clienteDao.buscarPorCedula(consulta.getInt("cliente_id"));
                venta.setCliente(cliente);

                // Cargar el producto usando ProductoDao
                ProductoDao productoDao = new ProductoDao();
                Producto producto = productoDao.buscarPorCodigo(consulta.getInt("producto_id"));
                venta.setProducto(producto);

                venta.setCantidad(consulta.getInt("cantidad"));
                venta.setFecha(consulta.getTimestamp("fecha")); // Usar Timestamp para compatibilidad con java.util.Date

                ventas.add(venta);
            }
        } catch (SQLException ex) {
            Logger.getLogger(VentaDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return ventas;
    }

    
}
