package control;

import modelo.Venta;
import modelo.dao.VentaDao;
import vista.JFVenta;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.sql.Timestamp;
import modelo.Cliente;
import modelo.Producto;

public class ControlVenta implements ActionListener {

    private JFVenta vVenta;
    private VentaDao ventaDao;

    public ControlVenta(JFVenta vVenta) {
        this.vVenta = vVenta;
        this.ventaDao = new VentaDao();

        this.vVenta.btnGuardar.addActionListener(this);
        this.vVenta.btnActualizar.addActionListener(this);
        this.vVenta.btnBuscar.addActionListener(this);
        this.vVenta.btnEliminar.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()) {
            case "Guardar":
                guardarVenta();
                break;
            case "Actualizar":
                actualizarVenta();
                break;
            case "Buscar":
                buscarVenta();
                break;
            case "Eliminar":
                eliminarVenta();
                break;
        }
    }

    private void guardarVenta() {
        Venta venta = new Venta();

        // Asignar el cliente
        Cliente cliente = new Cliente();
        cliente.setCedula(Integer.parseInt(vVenta.txtCliente.getText()));
        venta.setCliente(cliente);

        // Asignar el producto
        Producto producto = new Producto();
        producto.setCodigo(Integer.parseInt(vVenta.txtProducto.getText()));
        venta.setProducto(producto);

        // Asignar otros campos
        venta.setCantidad(Integer.parseInt(vVenta.txtCantidad.getText()));
        venta.setFecha(new Timestamp(new Date().getTime())); // Fecha actual
        
        if (venta.getCliente() == null || venta.getProducto() == null) {
            JOptionPane.showMessageDialog(vVenta, "Por favor, complete todos los datos.");
            return;
        }

        ventaDao.guardar(venta);
        JOptionPane.showMessageDialog(vVenta, "Venta guardada exitosamente.");
        
        limpiarCampos();
    }

    private void buscarVenta() {
        int id = Integer.parseInt(vVenta.txtIdentificador.getText());
        Venta venta = ventaDao.buscarPorId(id);

        if (venta != null) {
            // Rellenar los campos de texto en la vista
            vVenta.txtCliente.setText(String.valueOf(venta.getCliente().getCedula()));
            vVenta.txtProducto.setText(String.valueOf(venta.getProducto().getCodigo()));
            vVenta.txtCantidad.setText(String.valueOf(venta.getCantidad()));
            vVenta.txtFecha.setText(venta.getFecha().toString());

            // Mostrar la venta en el JTextArea
            vVenta.areaResultados.setText(venta.toString());
        } else {
            JOptionPane.showMessageDialog(vVenta, "Venta no encontrada.");
            vVenta.areaResultados.setText("No se encontró ninguna venta con el ID proporcionado.");
        }
    }


    private void actualizarVenta() {
        int id = Integer.parseInt(vVenta.txtIdentificador.getText());
        Venta venta = ventaDao.buscarPorId(id);
        
        if (venta != null) {
            venta.setCantidad(Integer.parseInt(vVenta.txtCantidad.getText()));
            venta.setFecha(Timestamp.valueOf(vVenta.txtFecha.getText()));

            if (ventaDao.actualizar(venta)) {
                JOptionPane.showMessageDialog(vVenta, "Venta actualizada exitosamente.");
            } else {
                JOptionPane.showMessageDialog(vVenta, "Error al actualizar la venta.");
            }
        } else {
            JOptionPane.showMessageDialog(vVenta, "Venta no encontrada.");
        }
    }

    private void eliminarVenta() {
        int id = Integer.parseInt(vVenta.txtIdentificador.getText());
        
        if (ventaDao.eliminar(id)) {
            JOptionPane.showMessageDialog(vVenta, "Venta eliminada exitosamente.");
        } else {
            JOptionPane.showMessageDialog(vVenta, "Error al eliminar la venta.");
        }
    }
    
    private void limpiarCampos() {
        vVenta.txtIdentificador.setText("");
        vVenta.txtCliente.setText("");
        vVenta.txtProducto.setText("");
        vVenta.txtCantidad.setText("");
        vVenta.txtFecha.setText("");        
    }
    
    
}
