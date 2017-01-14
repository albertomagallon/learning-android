package alberto.objetos;

import android.graphics.Bitmap;

public class Coordenada {
	private String Titulo = "";
	private String Descripcion = "";
	private String Latitud = "";
	private String Longitud = "";
	private int idCategoria = 0;
	private Bitmap icono;
	
	/* Setters y Getters*/
	public String getTitulo() {
		return Titulo;
	}
	public void setTitulo(String titulo) {
		Titulo = titulo;
	}
	public String getDescripcion() {
		return Descripcion;
	}
	public void setDescripcion(String descripcion) {
		Descripcion = descripcion;
	}
	public String getLatitud() {
		return Latitud;
	}
	public void setLatitud(String latitud) {
		Latitud = latitud;
	}
	public String getLongitud() {
		return Longitud;
	}
	public void setLongitud(String longitud) {
		Longitud = longitud;
	}
	public int getIdCategoria() {
		return idCategoria;
	}
	public void setIdCategoria(int idCategoria) {
		this.idCategoria = idCategoria;
	}
	public Bitmap getIcono() {
		return icono;
	}
	public void setIcono(Bitmap icono) {
		this.icono = icono;
	}
	
	
}
