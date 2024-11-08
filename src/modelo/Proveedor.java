
package modelo;


public class Proveedor {
    
    private int id;
    private String nombre;
    private String contacto;
    private String telefono;

    public Proveedor() {
    }

    public Proveedor(int id, String nombre, String contacto, String telefono) {
        this.id = id;
        this.nombre = nombre;
        this.contacto = contacto;
        this.telefono = telefono;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getContacto() {
        return contacto;
    }

    public void setContacto(String contacto) {
        this.contacto = contacto;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    @Override
    public String toString() {
        return "Proveedor{" + "id=" + id + ", nombre=" + nombre + ", contacto=" + contacto + ", telefono=" + telefono + '}';
    }
    
    
    
}
