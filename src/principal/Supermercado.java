
package principal;

import javax.swing.JOptionPane;

import vista.JFProducto;
import vista.JFCliente;
import vista.JFProveedor;

import control.ControlProducto;
import control.ControlCliente;
import control.ControlProveedor;


public class Supermercado {
    
    public static void main(String [] args){
        
        JOptionPane.showMessageDialog(null, "Inicializa el programa");
        
        JFProducto vProducto = new JFProducto();
        vProducto.setVisible(true);
        ControlProducto cProducto = new ControlProducto(vProducto);
        
        JFCliente vCliente = new JFCliente();
        vCliente.setVisible(true);
        ControlCliente cCliente = new ControlCliente(vCliente);
        
        JFProveedor vProveedor = new JFProveedor();
        vProveedor.setVisible(true);
        ControlProveedor cProveedor = new ControlProveedor(vProveedor);
    }
    
}
