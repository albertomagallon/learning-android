package alberto.listapersonal.lazylist;

import java.util.ArrayList;
import java.util.ListIterator;

import alberto.activity.R;
import alberto.objetos.Oferta;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class LazyAdapter extends BaseAdapter {
    
    private Activity activity;
    private String[] data;
    private static LayoutInflater inflater=null;
    public ImageLoader imageLoader; 
    private ArrayList<Oferta> items;
    
    public LazyAdapter(Activity a, ArrayList<Oferta> items) {
        activity = a;
        this.items = items;
        //data=d;
        ListIterator<Oferta> iterador = items.listIterator(); 
        String mURLs [] = new String [items.size()];
         
        //Aca va la primera forma de iterar el ArrayList
        int contador = 0;
        //Mientras que el iterador tenga un proximo elemento
        while( iterador.hasNext() ) {
                  Oferta o = (Oferta) iterador.next(); //Obtengo el elemento contenido 
                  mURLs[contador] = o.getUrlImagen();
                  contador = contador + 1;
        }
        data = mURLs;
        inflater = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        imageLoader=new ImageLoader(activity.getApplicationContext());
       
    }

    public int getCount() {
        return data.length;
    }

    public Object getItem(int position) {
        return position;
    }

    public long getItemId(int position) {
        return position;
    }
    
    public View getView(int position, View convertView, ViewGroup parent) {
        View vi=convertView;
        if(convertView==null)
            vi = inflater.inflate(R.layout.lista_item, null);

        TextView titulo=(TextView)vi.findViewById(R.id.titulo);;
        TextView descripcion=(TextView)vi.findViewById(R.id.descripcion);;
        TextView tiempo=(TextView)vi.findViewById(R.id.tiempo);;
        ImageView image=(ImageView)vi.findViewById(R.id.image_view);

        if (items.get(position).getTitulo().equals("null")){
        	titulo.setText("Título no disponible");
        }else{
        	titulo.setText(items.get(position).getTitulo());	
        }        
        if (items.get(position).getDescripcion().equals("null")){
        	descripcion.setText("Descripcion no disponible");
        }else{
        	descripcion.setText(items.get(position).getDescripcion());
        }
        if (items.get(position).getFechaVig().equals("null")){
        	tiempo.setText("Fecha de vigencia no disponible");
        }else{
        	tiempo.setText(items.get(position).getFechaVig());
        }
        if (data[position].equals("null")){
        	
        }else{
        	imageLoader.DisplayImage(data[position], image);
        }        
        
        return vi;
    }
}