
package control;

import javax.swing.JOptionPane;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import modelo.Proveedor;
import modelo.dao.ProveedorDao;
import vista.JFProveedor;

public class ControlProveedor implements ActionListener{
    
    private Proveedor p;
    private JFProveedor vProveedor;
    private ProveedorDao proveedorDao;
    
    public ControlProveedor() {
        proveedorDao = new ProveedorDao();  
    }    

    public ControlProveedor(Proveedor p, JFProveedor vProveedor) {
        this.p = p;
        this.vProveedor = vProveedor;
        proveedorDao = new ProveedorDao();
    }

    public ControlProveedor(JFProveedor vProveedor) {
        this.vProveedor = vProveedor;
        proveedorDao = new ProveedorDao();
        this.actionListener();
    }
    
    private void actionListener() {
        this.vProveedor.btnGuardar.setActionCommand("Guardar");
        this.vProveedor.btnActualizar.setActionCommand("Actualizar");
        this.vProveedor.btnBuscar.setActionCommand("Buscar");
        this.vProveedor.btnEliminar.setActionCommand("Eliminar");

        this.vProveedor.btnGuardar.addActionListener(this);        
        this.vProveedor.btnActualizar.addActionListener(this);
        this.vProveedor.btnBuscar.addActionListener(this);
        this.vProveedor.btnEliminar.addActionListener(this); 
        
        JOptionPane.showMessageDialog(null, "Si esta escuchando Proveedor");
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()) {
            case "Guardar":
                guardarProveedor();
                break;
            case "Actualizar":
                actualizarProveedor();
                break;
            case "Buscar":
                buscarProveedor();
                break;
            case "Eliminar":
                eliminarProveedor();
                break;
        }
    }
    
    private void guardarProveedor() {
        try {
            Proveedor nuevoProveedor = new Proveedor();
            nuevoProveedor.setId(Integer.parseInt(vProveedor.txtIdentificador.getText()));
            nuevoProveedor.setNombre(vProveedor.txtNombre.getText());
            nuevoProveedor.setContacto(vProveedor.txtContacto.getText());
            nuevoProveedor.setTelefono(vProveedor.txtTelefono.getText());

            proveedorDao.guardar(nuevoProveedor);
            JOptionPane.showMessageDialog(vProveedor, "Proveedor guardado exitosamente!");
            limpiarCampos();

        } catch (NumberFormatException nfe) {
            JOptionPane.showMessageDialog(vProveedor, "Por favor, ingrese valores válidos para los números.", "Error", JOptionPane.ERROR_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(vProveedor, "Error al guardar el proveedor: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void buscarProveedor() {
        try {
            int id = Integer.parseInt(vProveedor.txtIdentificador.getText());
            Proveedor proveedor = proveedorDao.buscarPorId(id);
            if (proveedor != null) {
                vProveedor.txtNombre.setText(proveedor.getNombre());
                vProveedor.txtContacto.setText(proveedor.getContacto());
                vProveedor.txtTelefono.setText(proveedor.getTelefono());
                JOptionPane.showMessageDialog(vProveedor, "Proveedor encontrado!");
            } else {
                JOptionPane.showMessageDialog(vProveedor, "Proveedor no encontrado.");
                limpiarCampos();
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(vProveedor, "Por favor ingrese un identificador válido.", "Error", JOptionPane.ERROR_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(vProveedor, "Error al buscar el proveedor: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void actualizarProveedor() {
        try {
            Proveedor proveedor = new Proveedor();
            proveedor.setId(Integer.parseInt(vProveedor.txtIdentificador.getText()));
            proveedor.setNombre(vProveedor.txtNombre.getText());
            proveedor.setContacto(vProveedor.txtContacto.getText());
            proveedor.setTelefono(vProveedor.txtTelefono.getText());

            boolean actualizado = proveedorDao.actualizar(proveedor);
            if (actualizado) {
                JOptionPane.showMessageDialog(vProveedor, "Proveedor actualizado exitosamente!");
            } else {
                JOptionPane.showMessageDialog(vProveedor, "No se pudo actualizar el cliente.");
            }
        } catch (NumberFormatException nfe) {
            JOptionPane.showMessageDialog(vProveedor, "Por favor, ingrese valores válidos para los números.", "Error", JOptionPane.ERROR_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(vProveedor, "Error al actualizar el proveedor: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void eliminarProveedor() {
        try {
            int id = Integer.parseInt(vProveedor.txtIdentificador.getText());
            int respuesta = JOptionPane.showConfirmDialog(vProveedor, "¿Está seguro de eliminar el proveedor?", "Confirmar eliminación", JOptionPane.YES_NO_OPTION);
            
            if (respuesta == JOptionPane.YES_OPTION) {
                boolean eliminado = proveedorDao.eliminar(id);
                if (eliminado) {
                    JOptionPane.showMessageDialog(vProveedor, "proveedor eliminado exitosamente.");
                    limpiarCampos();
                } else {
                    JOptionPane.showMessageDialog(vProveedor, "No se pudo eliminar el proveedor.");
                }
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(vProveedor, "Por favor ingrese un identificador válido.", "Error", JOptionPane.ERROR_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(vProveedor, "Error al eliminar el proveedor: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void limpiarCampos() {
        vProveedor.txtIdentificador.setText("");
        vProveedor.txtNombre.setText("");
        vProveedor.txtContacto.setText("");
        vProveedor.txtTelefono.setText("");
    }
    
}
