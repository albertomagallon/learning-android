package alberto.activity;

import java.util.ArrayList;

import alberto.conexion_a_BD.Peticiones;
import alberto.listapersonal.lazylist.ImageLoader;
import alberto.listapersonal.lazylist.LazyAdapter;
import alberto.objetos.Oferta;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import alberto.activity.R;
import alberto.activity.R.string;
import alberto.objetos.Negocio;

public class TabLista extends Activity {	
	
	private ProgressDialog progressDialog;
    private Thread tt;
    private ArrayList<Oferta> Ofertas;
    private Negocio negocio;
    ListView list;
    LazyAdapter adapter;
    private String filtradoSpinner;
    private int filtradoItem;
    private int idNegocio;
    private int posicion;
    
    private static TabLista tabLista= null;
    public static TabLista getInstance(){
    	if(tabLista == null){
    		tabLista = new  TabLista();
    	}
    	return tabLista;
    }
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tablista);      

        /*Crear la lista mediante hilo*/
        processThread();

    }          
    
    @Override
    public void onResume()
    {
        super.onResume();
        Bundle extras = getParent().getIntent().getExtras();
        if(extras !=null)
        {
        filtradoSpinner = extras.getString("filtroSpinner");
        filtradoItem = extras.getInt("filtroItem");                    
        processThread();
        }
        //Toast.makeText(this, "onResume: " + filtradoSpinner +" item " + filtradoItem, Toast.LENGTH_LONG).show();
    }

    
    private void crearLista(ArrayList<Oferta> Ofertas){
        list=(ListView)findViewById(R.id.lista);
        adapter=new LazyAdapter(this, Ofertas);
        list.setAdapter(adapter);
        
        Button b=(Button)findViewById(R.id.button1);
        b.setOnClickListener(listener);
        
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> av, View v, int pos, long id) {
                onListItemClick(v,pos,id);
            }
        });    	
    }
    
    protected void onListItemClick(View v, int pos, long id) {
       // Toast.makeText(this, "onClick: " + pos, Toast.LENGTH_LONG).show();
        idNegocio = Ofertas.get(pos).getIdNegocio();
        posicion = pos;
        negocioThread(idNegocio);
    }
    
    @Override
    public void onDestroy()
    {
        list.setAdapter(null);
        super.onDestroy();
    }
    
    public OnClickListener listener=new OnClickListener(){
        public void onClick(View arg0) {
            adapter.imageLoader.clearCache();
            adapter.notifyDataSetChanged();
        }
    };
    

    /*HILO PARA CREAR LA LISTA*/
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
          super.handleMessage(msg);
             
          // AQUÍ RELLENAMOS LA LISTA...             
          //setListAdapter(new OfertaAdapter(TabLista.this, R.layout.lista_item, Ofertas));          
          crearLista(Ofertas);
          progressDialog.dismiss();
          try {
              tt.join();
              } catch (InterruptedException e) {
              Log.d("Error hilo", "El hilo de crear la lista de ofertas ha fallado: " + e.toString());
              }
               
        }
  };
     

  public void processThread() {
          //Creamos un progressDialog que haga un loading mientras carga la lista....
          progressDialog = ProgressDialog.show(TabLista.this, "", "Cargando lista de ofertas...");

          tt = new Thread() {
              public void run() {
            	  Looper.prepare();

                          // Calculos para crear la lista...
            	  
            	  			Peticiones peticion = new Peticiones();
            	  			if (filtradoSpinner == null || filtradoSpinner.equals("")){
            	  				Ofertas = peticion.getOfertas();
            	  			}else if (filtradoItem != 0){
            	  				Ofertas = peticion.getOfertasFiltradas(filtradoSpinner, filtradoItem);
            	  			}else{
            	  				Ofertas = peticion.getOfertas();		
            	  			}
            	  			
            	
                          // Llamamos al handler para decirle que hemos acabado cálculos
                  handler.sendEmptyMessage(0);
              }
          };
          tt.start();
  }
  
  
  /*HILO PARA CREAR LA LISTA*/
  private Handler handlerNegocio = new Handler() {
      @Override
      public void handleMessage(Message msg) {
        super.handleMessage(msg);
           
        // AQUÍ RELLENAMOS LA LISTA...             
        //setListAdapter(new OfertaAdapter(TabLista.this, R.layout.lista_item, Ofertas));          
        // Esto para realizar la llamada
        progressDialog.dismiss();
        informarNegocio(posicion);        
        try {
            tt.join();
            } catch (InterruptedException e) {
            Log.d("Error hilo", "El hilo de crear la lista de ofertas ha fallado: " + e.toString());
            }
             
      }
};
   

public void negocioThread(final int idNegocio) {
        //Creamos un progressDialog que haga un loading mientras carga la lista....
        progressDialog = ProgressDialog.show(TabLista.this, "", "Cargando detalles de la oferta...");

        tt = new Thread() {
            public void run() {
          	  Looper.prepare();

                        // Calculos para crear la lista...
          	  
          	  			Peticiones peticion = new Peticiones();
          	  			negocio = peticion.getNegocio(idNegocio);
          	  			
          	
                        // Llamamos al handler para decirle que hemos acabado cálculos
                handlerNegocio.sendEmptyMessage(0);
            }
        };
        tt.start();
}

private void informarNegocio(int pos){
    Intent i=new Intent(this,Info_item.class);        
    /*Informar los datos del objeto*/        

    i.putExtra("idNegocio",Ofertas.get(pos).getIdNegocio());
    i.putExtra("Titulo",Ofertas.get(pos).getTitulo());
    i.putExtra("Descripcion",Ofertas.get(pos).getDescripcion());
    i.putExtra("Descuento",Ofertas.get(pos).getDescuento());
    i.putExtra("Ahorro",Ofertas.get(pos).getAhorro());
    i.putExtra("URL",Ofertas.get(pos).getUrlImagen());
    i.putExtra("Tiempo",Ofertas.get(pos).getFechaVig());
    i.putExtra("Nombre",negocio.getNombre());
    i.putExtra("nDescripcion",negocio.getDescripcion());
    i.putExtra("nDireccion",negocio.getDireccion());
    i.putExtra("Barrio",negocio.getsBarrio());
    i.putExtra("Categoria",negocio.getsCategoria());

    
    startActivity(i);  
}
  
  @Override
  protected void onActivityResult(int requestCode, int resultCode, Intent data) {
      Log.d("CheckStartActivity","onActivityResult and resultCode = "+resultCode);
      // TODO Auto-generated method stub
      super.onActivityResult(requestCode, resultCode, data);
      if(resultCode==1){
          Toast.makeText(this, "Pass", Toast.LENGTH_LONG).show();
      }
      else{
          Toast.makeText(this, "Fail", Toast.LENGTH_LONG).show();
      }
  }

 

  	/*Clase para crear el adaptador de lista personalizado*/
    private class OfertaAdapter extends ArrayAdapter<Oferta> {
    	 
        private ArrayList<Oferta> items;
     
        public OfertaAdapter(Context context, int textViewResourceId, ArrayList<Oferta> items) {
            super(context, textViewResourceId, items);
            this.items = items;
        }
     
        /*
         * Sobreescribimos el método getView() de la clase padre ArrayAdapter
         * Se ejecuta una vez por cada elemento de la lista
         * (non-Javadoc)
         * @see android.widget.ArrayAdapter#getView(int, android.view.View, android.view.ViewGroup)
         */
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View v = convertView;
            if (v == null) {
                LayoutInflater vi = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                v = vi.inflate(R.layout.lista_item, null);
            }
            Oferta Oferta = items.get(position);
            if (Oferta != null) {
                TextView ttitulo = (TextView) v.findViewById(R.id.titulo);
                TextView tdescripcion = (TextView) v.findViewById(R.id.descripcion);
                TextView ttiempo = (TextView) v.findViewById(R.id.tiempo);  
                //Cargar imagen
                ImageView imageView = (ImageView) findViewById(R.id.image_view);
                if (imageView != null){
                	imageView.setImageBitmap(Oferta.getImagen());
                	//imageView.setEnabled(true);
                	//imageView.setEnabled(false);
                	
                }
                if (ttitulo != null) {
                    ttitulo.setText(Oferta.getTitulo());
                }
                if (tdescripcion != null) {
                    tdescripcion.setText(Oferta.getDescripcion());                    
                }
                if (ttiempo != null) {
                    ttiempo.setText(Oferta.getFechaVig());                    
                }	
            }
            return v;
        }
    }
  
  
    
}
