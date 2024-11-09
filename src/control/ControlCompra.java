
package control;

import modelo.Compra;
import modelo.dao.CompraDao;
import vista.JFCompra;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.sql.Timestamp;
import modelo.Producto;
import modelo.Proveedor;

public class ControlCompra implements ActionListener{
    
    private JFCompra vCompra;
    private CompraDao compraDao;
    
    public ControlCompra(JFCompra vCompra) {
        this.vCompra = vCompra;
        this.compraDao = new CompraDao();

        this.vCompra.btnGuardar.addActionListener(this);
        this.vCompra.btnActualizar.addActionListener(this);
        this.vCompra.btnBuscar.addActionListener(this);
        this.vCompra.btnEliminar.addActionListener(this);
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()) {
            case "Guardar":
                guardarCompra();
                break;
            case "Actualizar":
                actualizarCompra();
                break;
            case "Buscar":
                buscarCompra();
                break;
            case "Eliminar":
                eliminarCompra();
                break;
        }
    }
    
    private void guardarCompra() {
        Compra compra = new Compra();

        // Asignar el cliente
        Proveedor proveedor = new Proveedor();
        proveedor.setId(Integer.parseInt(vCompra.txtProveedor.getText()));
        compra.setProveedor(proveedor);

        // Asignar el producto
        Producto producto = new Producto();
        producto.setCodigo(Integer.parseInt(vCompra.txtProducto.getText()));
        compra.setProducto(producto);

        // Asignar otros campos
        compra.setCantidad(Integer.parseInt(vCompra.txtCantidad.getText()));
        compra.setFecha(new Timestamp(new Date().getTime())); // Fecha actual
        
        if (compra.getProveedor() == null || compra.getProducto() == null) {
            JOptionPane.showMessageDialog(vCompra, "Por favor, complete todos los datos.");
            return;
        }

        compraDao.guardar(compra);
        JOptionPane.showMessageDialog(vCompra, "Compra guardada exitosamente.");
        
        limpiarCampos();
    }
    
    private void buscarCompra() {
        int id = Integer.parseInt(vCompra.txtIdentificador.getText());
        Compra compra = compraDao.buscarPorId(id);

        if (compra != null) {
            // Rellenar los campos de texto en la vista
            vCompra.txtProveedor.setText(String.valueOf(compra.getProveedor().getId()));
            vCompra.txtProducto.setText(String.valueOf(compra.getProducto().getCodigo()));
            vCompra.txtCantidad.setText(String.valueOf(compra.getCantidad()));
            vCompra.txtFecha.setText(compra.getFecha().toString());

            // Mostrar la venta en el JTextArea
            vCompra.areaResultados.setText(compra.toString());
        } else {
            JOptionPane.showMessageDialog(vCompra, "Compra no encontrada.");
            vCompra.areaResultados.setText("No se encontró ninguna compra con el ID proporcionado.");
        }
    }
    
    private void actualizarCompra() {
        int id = Integer.parseInt(vCompra.txtIdentificador.getText());
        Compra compra = compraDao.buscarPorId(id);
        
        if (compra != null) {
            compra.setCantidad(Integer.parseInt(vCompra.txtCantidad.getText()));
            compra.setFecha(Timestamp.valueOf(vCompra.txtFecha.getText()));

            if (compraDao.actualizar(compra)) {
                JOptionPane.showMessageDialog(vCompra, "Compra actualizada exitosamente.");
            } else {
                JOptionPane.showMessageDialog(vCompra, "Error al actualizar la compra.");
            }
        } else {
            JOptionPane.showMessageDialog(vCompra, "Compra no encontrada.");
        }
    }
    
    private void eliminarCompra() {
        int id = Integer.parseInt(vCompra.txtIdentificador.getText());
        
        if (compraDao.eliminar(id)) {
            JOptionPane.showMessageDialog(vCompra, "Compra eliminada exitosamente.");
        } else {
            JOptionPane.showMessageDialog(vCompra, "Error al eliminar la compra.");
        }
    }
    
    private void limpiarCampos() {
        vCompra.txtIdentificador.setText("");
        vCompra.txtProveedor.setText("");
        vCompra.txtProducto.setText("");
        vCompra.txtCantidad.setText("");
        vCompra.txtFecha.setText("");        
    }
    
    
}
