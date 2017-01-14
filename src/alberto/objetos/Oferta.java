package alberto.objetos;

import android.graphics.Bitmap;

public class Oferta {
		private int idOferta =0;
        private String Titulo = "";
        private String Descripcion = "";
        private String Descuento = "";
        private String Ahorro = "";
        private String urlImagen = "";
        private String FechaVig = "";
        private int idNegocio = 0;
        private Bitmap imagen;
        
        /* Setters y Getters*/
		public int getIdOferta() {
			return idOferta;
		}
		public void setIdOferta(int idOferta) {
			this.idOferta = idOferta;
		}
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
		public String getDescuento() {
			return Descuento;
		}
		public void setDescuento(String descuento) {
			Descuento = descuento;
		}
		public String getAhorro() {
			return Ahorro;
		}
		public void setAhorro(String ahorro) {
			Ahorro = ahorro;
		}
		public String getUrlImagen() {
			return urlImagen;
		}
		public void setUrlImagen(String urlImagen) {
			this.urlImagen = urlImagen;
		}
		public String getFechaVig() {
			return FechaVig;
		}
		public void setFechaVig(String fechaVig) {
			FechaVig = fechaVig;
		}
		public int getIdNegocio() {
			return idNegocio;
		}
		public void setIdNegocio(int idNegocio) {
			this.idNegocio = idNegocio;
		}
		public Bitmap getImagen() {
			return imagen;
		}
		public void setImagen(Bitmap imagen) {
			this.imagen = imagen;
		}

    
}
