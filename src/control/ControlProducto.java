package control;

import javax.swing.JOptionPane;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import modelo.Producto;
import modelo.dao.ProductoDao;
import vista.JFProducto;

public class ControlProducto implements ActionListener {
    
    private Producto p;
    private JFProducto vProducto;
    private ProductoDao productoDao;

    public ControlProducto() {
        productoDao = new ProductoDao();  // Crear una única instancia del DAO
    }    

    public ControlProducto(Producto p, JFProducto vProducto) {
        this.p = p;
        this.vProducto = vProducto;
        productoDao = new ProductoDao();
    }

    public ControlProducto(JFProducto vProducto) {
        this.vProducto = vProducto;
        productoDao = new ProductoDao();
        this.actionListener();
    }
    
    private void actionListener() {
        this.vProducto.btnGuardar.setActionCommand("Guardar");
        this.vProducto.btnActualizar.setActionCommand("Actualizar");
        this.vProducto.btnBuscar.setActionCommand("Buscar");
        this.vProducto.btnEliminar.setActionCommand("Eliminar");

        this.vProducto.btnGuardar.addActionListener(this);        
        this.vProducto.btnActualizar.addActionListener(this);
        this.vProducto.btnBuscar.addActionListener(this);
        this.vProducto.btnEliminar.addActionListener(this); 
        
        //JOptionPane.showMessageDialog(null, "Si esta escuchando");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()) {
            case "Guardar":
                guardarProducto();
                break;
            case "Actualizar":
                actualizarProducto();
                break;
            case "Buscar":
                buscarProducto();
                break;
            case "Eliminar":
                eliminarProducto();
                break;
        }
    }

    private void guardarProducto() {
        try {
            Producto nuevoProducto = new Producto();
            nuevoProducto.setCodigo(Integer.parseInt(vProducto.txtCodigo.getText()));
            nuevoProducto.setNombre(vProducto.txtNombre.getText());
            nuevoProducto.setDescripcion(vProducto.txtDescripcion.getText());
            nuevoProducto.setPrecio(Double.parseDouble(vProducto.txtPrecio.getText()));
            nuevoProducto.setProveedor(Integer.parseInt(vProducto.txtProveedor.getText()));

            productoDao.guardar(nuevoProducto);
            JOptionPane.showMessageDialog(vProducto, "Producto guardado exitosamente!");
            limpiarCampos();

        } catch (NumberFormatException nfe) {
            JOptionPane.showMessageDialog(vProducto, "Por favor, ingrese valores válidos para los números.", "Error", JOptionPane.ERROR_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(vProducto, "Error al guardar el producto: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void buscarProducto() {
        try {
            int codigo = Integer.parseInt(vProducto.txtCodigo.getText());
            Producto producto = productoDao.buscarPorCodigo(codigo);
            if (producto != null) {
                vProducto.txtNombre.setText(producto.getNombre());
                vProducto.txtDescripcion.setText(producto.getDescripcion());
                vProducto.txtPrecio.setText(String.valueOf(producto.getPrecio()));
                vProducto.txtProveedor.setText(String.valueOf(producto.getProveedor()));
                JOptionPane.showMessageDialog(vProducto, "Producto encontrado!");
            } else {
                JOptionPane.showMessageDialog(vProducto, "Producto no encontrado.");
                limpiarCampos();
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(vProducto, "Por favor ingrese un código válido.", "Error", JOptionPane.ERROR_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(vProducto, "Error al buscar el producto: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void actualizarProducto() {
        try {
            Producto producto = new Producto();
            producto.setCodigo(Integer.parseInt(vProducto.txtCodigo.getText()));
            producto.setNombre(vProducto.txtNombre.getText());
            producto.setDescripcion(vProducto.txtDescripcion.getText());
            producto.setPrecio(Double.parseDouble(vProducto.txtPrecio.getText()));
            producto.setProveedor(Integer.parseInt(vProducto.txtProveedor.getText()));

            boolean actualizado = productoDao.actualizar(producto);
            if (actualizado) {
                JOptionPane.showMessageDialog(vProducto, "Producto actualizado exitosamente!");
            } else {
                JOptionPane.showMessageDialog(vProducto, "No se pudo actualizar el producto.");
            }
        } catch (NumberFormatException nfe) {
            JOptionPane.showMessageDialog(vProducto, "Por favor, ingrese valores válidos para los números.", "Error", JOptionPane.ERROR_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(vProducto, "Error al actualizar el producto: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void eliminarProducto() {
        try {
            int codigo = Integer.parseInt(vProducto.txtCodigo.getText());
            int respuesta = JOptionPane.showConfirmDialog(vProducto, "¿Está seguro de eliminar el producto?", "Confirmar eliminación", JOptionPane.YES_NO_OPTION);
            
            if (respuesta == JOptionPane.YES_OPTION) {
                boolean eliminado = productoDao.eliminar(codigo);
                if (eliminado) {
                    JOptionPane.showMessageDialog(vProducto, "Producto eliminado exitosamente.");
                    limpiarCampos();
                } else {
                    JOptionPane.showMessageDialog(vProducto, "No se pudo eliminar el producto.");
                }
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(vProducto, "Por favor ingrese un código válido.", "Error", JOptionPane.ERROR_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(vProducto, "Error al eliminar el producto: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void limpiarCampos() {
        vProducto.txtCodigo.setText("");
        vProducto.txtNombre.setText("");
        vProducto.txtDescripcion.setText("");
        vProducto.txtPrecio.setText("");
        vProducto.txtProveedor.setText("");
    }
}
