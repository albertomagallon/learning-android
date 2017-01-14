package alberto.herramientasmapas;



import java.util.ArrayList;
import java.util.ListIterator;


//import seas.activity.principal.R;
import alberto.activity.Info_item;
import alberto.activity.R;
import alberto.activity.TabLista;
import alberto.activity.TabMapa;
import alberto.conexion_a_BD.Peticiones;
import alberto.objetos.Coordenada;
import alberto.objetos.Negocio;
import alberto.objetos.Oferta;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.maps.ItemizedOverlay;
import com.google.android.maps.OverlayItem;

	public class MiItemizedOverlay extends ItemizedOverlay<OverlayItem>{
		ArrayList<OverlayItem> mOverlays=new ArrayList<OverlayItem>();
		private ProgressDialog progressDialog;
		public Context mContext;
	    private Thread tt;
	    private ArrayList<Oferta> Ofertas;
	    private Negocio negocio;
	    public int item_sel;
	    public int idNeg = 0;
	    public Intent i;
		
		public MiItemizedOverlay(Context context) {
			super(boundCenterBottom(TabMapa.getInstance().getResources().getDrawable(R.drawable.tabmapa)));
			mContext = context;
		}
		public void addOverlay(OverlayItem overlay) {
			mOverlays.add(overlay);
			populate();
		}
		//Dado una imagen la ajusta para que el punto 0,0 de éste esté en el centro de la parte inferior
		public Drawable boundCenterBottomAux(Drawable marker){
			return boundCenterBottom(marker);
		}
		@Override
		protected OverlayItem createItem(int i) {
			// TODO Auto-generated method stub
			return mOverlays.get(i);	
		}
		@Override
		public int size() {
			// TODO Auto-generated method stub
			return mOverlays.size();
		}
		@Override
		protected boolean onTap(int index) {
		 /* OverlayItem item = mOverlays.get(index);*/
		  i= new Intent(mContext,Info_item.class);		  
		  processThread();

		  item_sel = index;

		  return true;
		}
		
	    /*HILO PARA CREAR LA LISTA*/
	    private Handler handler = new Handler() {
	        @Override
	        public void handleMessage(Message msg) {
	          super.handleMessage(msg);
	             
	          // AQUÍ RELLENAMOS LA LISTA...             	        
	          progressDialog.dismiss();
	          recuperarOferta();
	          try {
	              tt.join();
	              } catch (InterruptedException e) {
	              Log.d("Error hilo", "El hilo de crear la lista de ofertas ha fallado: " + e.toString());
	              }
	               
	        }
	  };
	     
	  private void recuperarOferta(){
		  
	      	ListIterator<Oferta> iterador = Ofertas.listIterator(); //Le solicito a la lista que me devuelva un iterador con todos los el elementos contenidos en ella
	    	 boolean continua = true;	    	
	    	 OverlayItem item = mOverlays.get(item_sel);
	    	//Mientras que el iterador tenga un proximo elemento
	    	while(iterador.hasNext() & continua) {
	    		Oferta oferta = (Oferta) iterador.next(); //Obtengo el elemento contenido
	    		if (oferta.getTitulo().equals(item.getTitle())) {
	    			idNeg = oferta.getIdNegocio();
	    		    i.putExtra("idNegocio",oferta.getIdNegocio());
	    		    i.putExtra("Titulo",oferta.getTitulo());
	    		    i.putExtra("Descripcion",oferta.getDescripcion());
	    		    i.putExtra("Descuento",oferta.getDescuento());
	    		    i.putExtra("Ahorro",oferta.getAhorro());
	    		    i.putExtra("URL",oferta.getUrlImagen());
	    		    i.putExtra("Tiempo",oferta.getFechaVig());	    		    
	    		    continua = false;
	    		}
	    	}	          	        
	         
	    	if(idNeg !=0){
	          negocioThread(idNeg);
	    	}else{
	    		Toast.makeText(mContext, "Detalles de la oferta no disponibles", Toast.LENGTH_LONG).show();
	    	}
	  }

	  public void processThread() {
	          //Creamos un progressDialog que haga un loading mientras carga la lista....
	          progressDialog = ProgressDialog.show(mContext, "", "Descargando oferta...");

	          tt = new Thread() {
	              public void run() {
	            	  Looper.prepare();

	                          // Calculos para crear la lista...
	            	  
	            	  			Peticiones peticion = new Peticiones();	            	  		
	            	  		    Ofertas = peticion.getOfertas();	            	  		
	            	  			
	            	
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
	        informarNegocio();        
	        try {
	            tt.join();
	            } catch (InterruptedException e) {
	            Log.d("Error hilo", "El hilo de crear la lista de ofertas ha fallado: " + e.toString());
	            }
	             
	      }
	};
	private void informarNegocio(){	    
	    /*Informar los datos del objeto*/        
	    i.putExtra("Nombre",negocio.getNombre());
	    i.putExtra("nDescripcion",negocio.getDescripcion());
	    i.putExtra("nDireccion",negocio.getDireccion());
	    i.putExtra("Barrio",negocio.getsBarrio());
	    i.putExtra("Categoria",negocio.getsCategoria());
	    
	    mContext.startActivity(i);
	}

	public void negocioThread(final int idNegocio) {
	        //Creamos un progressDialog que haga un loading mientras carga la lista....
	        progressDialog = ProgressDialog.show(mContext, "", "Cargando detalles de la oferta...");

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

	}

