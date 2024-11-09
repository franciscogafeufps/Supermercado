
package principal;

import javax.swing.JOptionPane;

import vista.JFProducto;
import vista.JFCliente;
import vista.JFProveedor;
import vista.JFVenta;
import vista.JFCompra;


import control.ControlProducto;
import control.ControlCliente;
import control.ControlCompra;
import control.ControlProveedor;
import control.ControlVenta;


public class Supermercado {
    
    public static void main(String [] args){
        
        //JOptionPane.showMessageDialog(null, "Inicializa el programa");
        
        JFProducto vProducto = new JFProducto();
        vProducto.setVisible(true);
        ControlProducto cProducto = new ControlProducto(vProducto);
        
        JFCliente vCliente = new JFCliente();
        vCliente.setVisible(true);
        ControlCliente cCliente = new ControlCliente(vCliente);
        
        JFProveedor vProveedor = new JFProveedor();
        vProveedor.setVisible(true);
        ControlProveedor cProveedor = new ControlProveedor(vProveedor);
        
        JFVenta vVenta = new JFVenta();
        vVenta.setVisible(true);
        ControlVenta cVenta = new ControlVenta(vVenta);
        
        JFCompra vCompra = new JFCompra();
        vCompra.setVisible(true);
        ControlCompra cCompra = new ControlCompra(vCompra);
    }
    
}
