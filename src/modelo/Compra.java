
package modelo;

import java.util.Date;

public class Compra {
    
    private int id; 
    private Proveedor proveedor;
    private Producto producto; 
    private int cantidad;
    private Date fecha;

    public Compra() {
    }

    public Compra(int id, Proveedor proveedor, Producto producto, int cantidad, Date fecha) {
        this.id = id;
        this.proveedor = proveedor;
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

    public Proveedor getProveedor() {
        return proveedor;
    }

    public void setProveedor(Proveedor proveedor) {
        this.proveedor = proveedor;
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
        return "Compra{" + "id=" + id + ", proveedor=" + proveedor + ", producto=" + producto + ", cantidad=" + cantidad + ", fecha=" + fecha + '}';
    }   
}
