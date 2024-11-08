
package modelo;

import java.util.Date;



public class Venta {
    
    private int id; 
    private Cliente cliente;
    private Producto producto; 
    private int cantidad;
    private Date fecha;

    public Venta() {
    }

    public Venta(int id, Cliente cliente, Producto producto, int cantidad, Date fecha) {
        this.id = id;
        this.cliente = cliente;
        this.producto = producto;
        this.cantidad = cantidad;
        this.fecha = fecha;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }
    
    @Override
    public String toString() {
        return "ID de Venta: " + id +
               "\nCliente: " + (cliente != null ? cliente.getCedula() : "No disponible") +
               "\nProducto: " + (producto != null ? producto.getCodigo() : "No disponible") +
               "\nCantidad: " + cantidad +
               "\nFecha: " + fecha;
    }

    
    
}
