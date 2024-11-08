
package modelo;


public class Cliente {
    
    private int cedula; 
    private String nombre; 
    private String email;
    private String telefono; 
    private int quienRecomienda;

    public Cliente() {
    }

    public Cliente(int cedula, String nombre, String email, String telefono, int quienRecomienda) {
        this.cedula = cedula;
        this.nombre = nombre;
        this.email = email;
        this.telefono = telefono;
        this.quienRecomienda = quienRecomienda;
    }

    public int getCedula() {
        return cedula;
    }

    public void setCedula(int cedula) {
        this.cedula = cedula;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public int getQuienRecomienda() {
        return quienRecomienda;
    }

    public void setQuienRecomienda(int quienRecomienda) {
        this.quienRecomienda = quienRecomienda;
    }

    @Override
    public String toString() {
        return "Cliente{" + "cedula=" + cedula + ", nombre=" + nombre + ", email=" + email + ", telefono=" + telefono + ", quienRecomienda=" + quienRecomienda + '}';
    }
    
    
    
}
