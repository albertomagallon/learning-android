package alberto.objetos;


public class Negocio {
	private int idNegocio =0;
    private String Nombre = "";
    private String Descripcion = "";
    private String Direccion = "";
    private int idCategoria = 0;
    private String sCategoria = "";
    private int idBarrio = 0;
    private String sBarrio = "";
    
    
	public String getDireccion() {
		return Direccion;
	}
	public void setDireccion(String direccion) {
		Direccion = direccion;
	}
	public int getIdNegocio() {
		return idNegocio;
	}
	public void setIdNegocio(int idNegocio) {
		this.idNegocio = idNegocio;
	}
	public String getNombre() {
		return Nombre;
	}
	public void setNombre(String nombre) {
		Nombre = nombre;
	}
	public String getDescripcion() {
		return Descripcion;
	}
	public void setDescripcion(String descripcion) {
		Descripcion = descripcion;
	}
	public int getIdCategoria() {
		return idCategoria;
	}
	public void setIdCategoria(int idCategoria) {
		this.idCategoria = idCategoria;
	}
	public String getsCategoria() {
		return sCategoria;
	}
	public void setsCategoria(String sCategoria) {
		this.sCategoria = sCategoria;
	}
	public int getIdBarrio() {
		return idBarrio;
	}
	public void setIdBarrio(int idBarrio) {
		this.idBarrio = idBarrio;
	}
	public String getsBarrio() {
		return sBarrio;
	}
	public void setsBarrio(String sBarrio) {
		this.sBarrio = sBarrio;
	}

}
