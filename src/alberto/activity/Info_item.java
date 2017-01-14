package alberto.activity;

import alberto.listapersonal.lazylist.ImageLoader;
import android.app.Activity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class Info_item extends Activity{
	public ImageLoader imageLoader;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.info_item);   
        
        Bundle extras = getIntent().getExtras();
        if(extras !=null)
        {
        
        //String idNegocio = extras.getString("idNegocio");
        String sTitulo = extras.getString("Titulo");
        String sDescripcion = extras.getString("Descripcion");
        String sDescuento = extras.getString("Descuento");
        String sAhorro = extras.getString("Ahorro");
        String sURL = extras.getString("URL");
        String sNombre = extras.getString("Nombre");
        String snDescripcion = extras.getString("nDescripcion");
        String sDireccion = extras.getString("nDireccion");
        String sBarrio = extras.getString("Barrio");
        String sCategoria = extras.getString("Categoria");
        String sTiempo = extras.getString("Tiempo");
        
        //Tratamiento de nulos
        if (sTitulo.equals("null")){
        	sTitulo = "No disponible";
        }
        if (sDescripcion.equals("null")){
        	sDescripcion = "No disponible";
        }
        if (sDescuento.equals("null")){
        	sDescuento = "No disponible";
        }      
        if (sAhorro.equals("null")){
        	sAhorro = "No disponible";
        }
        if (sNombre.equals("null")){
        	sNombre = "No disponible";
        }
        if (snDescripcion.equals("null")){
        	snDescripcion = "No disponible";
        }
        if (sDireccion.equals("null")){
        	sDireccion = "No disponible";
        }
        if (sBarrio.equals("null")){
        	sBarrio = "No disponible";
        }
        if (sCategoria.equals("null")){
        	sCategoria = "No disponible";
        }
        if (sTiempo.equals("null")){
        	sTiempo = "No disponible";
        }
        
        //ImageView image=(ImageView)findViewById(R.id.image_info);
        TextView textTitulo=(TextView)findViewById(R.id.textTitulo);
        TextView textDescripcion=(TextView)findViewById(R.id.textDescripcion);
        TextView textDescuento=(TextView)findViewById(R.id.textDescuento);
        TextView textAhorro=(TextView)findViewById(R.id.textAhorro);
        TextView textNombre=(TextView)findViewById(R.id.textNombre);
        TextView textnDescripcion=(TextView)findViewById(R.id.textnDescripcion);
        TextView textDireccion=(TextView)findViewById(R.id.textDireccion);
        TextView textBarrio=(TextView)findViewById(R.id.textBarrio);
        TextView textCategoria=(TextView)findViewById(R.id.textCategoria);
        TextView textTiempo=(TextView)findViewById(R.id.textTiempo);        
        
        //imageLoader.DisplayImage(sURL, image);
        textTitulo.setText(sTitulo);
        textDescripcion.setText(sDescripcion);
        textNombre.setText("Nombre del negocio: " + sNombre);
        textnDescripcion.setText(snDescripcion);
        textDireccion.setText("Direccion: " + sDireccion);
        textBarrio.setText("Esta negocio está ubicado en el barrio: " + sBarrio);
        textCategoria.setText("Esta oferta pertenece a la categoria: " + sCategoria);        
        textDescuento.setText("-> Descuento: " + sDescuento);
        textAhorro.setText("Ahorro estimado: " + sAhorro + " <-");
        textTiempo.setText(sTiempo);
        
        if (sURL == null || sURL.equals("")){
        }else{
    	ImageView image=(ImageView)findViewById(R.id.image_info);
    	imageLoader=new ImageLoader(this.getApplicationContext());
    	imageLoader.DisplayImage(sURL, image);
        }
        }
        
      }
    
}
