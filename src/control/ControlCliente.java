
package control;

import javax.swing.JOptionPane;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import modelo.Cliente;
import modelo.dao.ClienteDao;
import vista.JFCliente;


public class ControlCliente implements ActionListener{
    
    private Cliente c;
    private JFCliente vCliente;
    private ClienteDao clienteDao;
    
    public ControlCliente() {
        clienteDao = new ClienteDao();  
    }    

    public ControlCliente(Cliente c, JFCliente vCliente) {
        this.c = c;
        this.vCliente = vCliente;
        clienteDao = new ClienteDao();
    }

    public ControlCliente(JFCliente vCliente) {
        this.vCliente = vCliente;
        clienteDao = new ClienteDao();
        this.actionListener();
    }
    
    private void actionListener() {
        this.vCliente.btnGuardar.setActionCommand("Guardar");
        this.vCliente.btnActualizar.setActionCommand("Actualizar");
        this.vCliente.btnBuscar.setActionCommand("Buscar");
        this.vCliente.btnEliminar.setActionCommand("Eliminar");

        this.vCliente.btnGuardar.addActionListener(this);        
        this.vCliente.btnActualizar.addActionListener(this);
        this.vCliente.btnBuscar.addActionListener(this);
        this.vCliente.btnEliminar.addActionListener(this); 
        
        //JOptionPane.showMessageDialog(null, "Si esta escuchando");
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()) {
            case "Guardar":
                guardarCliente();
                break;
            case "Actualizar":
                actualizarCliente();
                break;
            case "Buscar":
                buscarCliente();
                break;
            case "Eliminar":
                eliminarCliente();
                break;
        }
    }
    
    private void guardarCliente() {
        try {
            Cliente nuevoCliente = new Cliente();
            nuevoCliente.setCedula(Integer.parseInt(vCliente.txtCedula.getText()));
            nuevoCliente.setNombre(vCliente.txtNombre.getText());
            nuevoCliente.setEmail(vCliente.txtEmail.getText());
            nuevoCliente.setTelefono(vCliente.txtTelefono.getText());
            nuevoCliente.setQuienRecomienda(Integer.parseInt(vCliente.txtRecomendado.getText()));

            clienteDao.guardar(nuevoCliente);
            JOptionPane.showMessageDialog(vCliente, "Cliente guardado exitosamente!");
            limpiarCampos();

        } catch (NumberFormatException nfe) {
            JOptionPane.showMessageDialog(vCliente, "Por favor, ingrese valores válidos para los números.", "Error", JOptionPane.ERROR_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(vCliente, "Error al guardar el producto: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void buscarCliente() {
        try {
            int cedula = Integer.parseInt(vCliente.txtCedula.getText());
            Cliente cliente = clienteDao.buscarPorCedula(cedula);
            if (cliente != null) {
                vCliente.txtNombre.setText(cliente.getNombre());
                vCliente.txtEmail.setText(cliente.getEmail());
                vCliente.txtTelefono.setText(cliente.getTelefono());
                vCliente.txtRecomendado.setText(String.valueOf(cliente.getQuienRecomienda()));
                JOptionPane.showMessageDialog(vCliente, "Cliente encontrado!");
            } else {
                JOptionPane.showMessageDialog(vCliente, "Cliente no encontrado.");
                limpiarCampos();
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(vCliente, "Por favor ingrese una cedula válida.", "Error", JOptionPane.ERROR_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(vCliente, "Error al buscar el cliente: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void actualizarCliente() {
        try {
            Cliente cliente = new Cliente();
            cliente.setCedula(Integer.parseInt(vCliente.txtCedula.getText()));
            cliente.setNombre(vCliente.txtNombre.getText());
            cliente.setEmail(vCliente.txtEmail.getText());
            cliente.setTelefono(vCliente.txtTelefono.getText());
            cliente.setQuienRecomienda(Integer.parseInt(vCliente.txtRecomendado.getText()));

            boolean actualizado = clienteDao.actualizar(cliente);
            if (actualizado) {
                JOptionPane.showMessageDialog(vCliente, "Cliente actualizado exitosamente!");
            } else {
                JOptionPane.showMessageDialog(vCliente, "No se pudo actualizar el cliente.");
            }
        } catch (NumberFormatException nfe) {
            JOptionPane.showMessageDialog(vCliente, "Por favor, ingrese valores válidos para los números.", "Error", JOptionPane.ERROR_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(vCliente, "Error al actualizar el cliente: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void eliminarCliente() {
        try {
            int cedula = Integer.parseInt(vCliente.txtCedula.getText());
            int respuesta = JOptionPane.showConfirmDialog(vCliente, "¿Está seguro de eliminar el cliente?", "Confirmar eliminación", JOptionPane.YES_NO_OPTION);
            
            if (respuesta == JOptionPane.YES_OPTION) {
                boolean eliminado = clienteDao.eliminar(cedula);
                if (eliminado) {
                    JOptionPane.showMessageDialog(vCliente, "Cliente eliminado exitosamente.");
                    limpiarCampos();
                } else {
                    JOptionPane.showMessageDialog(vCliente, "No se pudo eliminar el cliente.");
                }
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(vCliente, "Por favor ingrese una cedula válida.", "Error", JOptionPane.ERROR_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(vCliente, "Error al eliminar el cliente: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void limpiarCampos() {
        vCliente.txtCedula.setText("");
        vCliente.txtNombre.setText("");
        vCliente.txtEmail.setText("");
        vCliente.txtTelefono.setText("");
        vCliente.txtRecomendado.setText("");
    }
    
}
