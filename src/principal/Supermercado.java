
package principal;

import javax.swing.JOptionPane;

import vista.JFProducto;
import control.ControlProducto;


public class Supermercado {
    
    public static void main(String [] args){
        
        JOptionPane.showMessageDialog(null, "Inicializa el programa");
        
        JFProducto vProducto = new JFProducto();
        vProducto.setVisible(true);
        ControlProducto cProducto = new ControlProducto(vProducto);
        
    }
    
}
