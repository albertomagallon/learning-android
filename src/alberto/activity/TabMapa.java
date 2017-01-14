package alberto.activity;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

import alberto.herramientasmapas.MyLocationListener;

import alberto.conexion_a_BD.Peticiones;
import alberto.herramientasmapas.MiItemizedOverlay;
import alberto.objetos.Coordenada;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.widget.ImageView;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;
import com.google.android.maps.OverlayItem;


public class TabMapa extends MapActivity {
	private LocationManager lm = null;
    private MyLocationListener locationListener = null;
    private String best = null;
    private MapView mapView;
    private MapController mc;
    List<Overlay> mapOverlays;
    private MiItemizedOverlay itemizedoverlay;
	private Peticiones peticion = null;
	private ArrayList<Coordenada> MiLista; 

    private String filtradoSpinner;
    private int filtradoItem;
    
	private ProgressDialog progressDialog;
    private Thread tt;
    
	 private static TabMapa tabMapa= null;
	    public static TabMapa getInstance(){
	    	if(tabMapa == null){
	    		tabMapa = new  TabMapa();
	    	}
	    	return tabMapa;
	    }
	
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tabmapa);            
        tabMapa = this;
        peticion = new Peticiones();

	    processThread();

    }
    
    /*HILO PARA CREAR EL MAPA*/
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
          super.handleMessage(msg);
             
          // AQUÍ NO HACEMOS NADA             
          //anadePuntoAlMapaConItemizedOverlay("41.6319677","-0.8851575");
          crearMapa();
          cargaCoordenadas();
  		  registraServiciosDeLocalizaciónGPS();
  		  mc.setZoom(12);
          progressDialog.dismiss();
          try {
              tt.join();
              } catch (InterruptedException e) {
              Log.d("Error hilo", "El hilo de crear el mapa ha fallado: " + e.toString());
              }
               
        }
  };
     

  public void processThread() {
          //Creamos un progressDialog que haga un loading mientras carga la lista....
          progressDialog = ProgressDialog.show(TabMapa.this, "", "Geoposicionando ofertas...");

          tt = new Thread() {
              public void run() {
            	  Looper.prepare();	
                          // Calculos para crear el mapa...            	  			            	  		
        	  			if (filtradoSpinner == null || filtradoSpinner.equals("")){
        	  				MiLista = peticion.getCoordenadas();
        	  			}else if (filtradoItem != 0){
        	  				mapView.getOverlays().clear();    	  			    
        	  				MiLista = peticion.getCoordenadasFiltradas(filtradoSpinner, filtradoItem);        	  				
        	  			}else{        	  				        	  				
        	  				MiLista = peticion.getCoordenadas();
        	  			}
            	  			    
             	  		  handler.sendEmptyMessage(0);

              }
          };
          tt.start();
  }

	 private OverlayItem getOverlayItem(String nombre, String descripcion, String latitud, String longitud, Drawable drawable){
		 	String coordinates[] = {latitud,longitud};
	   		double lat = Double.parseDouble(coordinates[0]);
	   		double lng = Double.parseDouble(coordinates[1]);
	   		GeoPoint point = new GeoPoint((int) (lng * 1E6),(int) (lat * 1E6));
	   		OverlayItem overlayitem = new OverlayItem(point, nombre, descripcion);
	   		overlayitem.setMarker(TabMapa.getInstance().getItemizedoverlay().boundCenterBottomAux(drawable));
	   		return overlayitem;
	 }
	 
	    private void registraServiciosDeLocalizaciónGPS(){
	      	 // Localización Gps
	   	    // LocationManager
	   	    // LocationListener
	   	    // LocationProvider
	   	    // Proveedor que necesitamos de Localización y nos permite acceder rápidamente 
	   	    lm = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
	   //
	   	    Criteria criteria = new Criteria();
	   	    best = lm.getBestProvider(criteria, true);
	   	    //a la última ubicación conocida.
	   	    Location location = lm.getLastKnownLocation(best);
	   	    if(location!=null){
	   	    	//anadePuntoAlMapaConItemizedOverlay(String.valueOf(location.getLatitude()),String.valueOf(location.getLongitude()));
	   	    }
	   	    locationListener = new MyLocationListener();
	   	    // Cuando registres cambios de localización en:
	   	    // Registrar oyentes de ubicación
	   	    lm.requestLocationUpdates(
	   	    		best,  //receptor GPS, más preciso y más habitual y disponible en el emulador //nombre del proveedor
	   	    		//NETWORK_PROVIDER, GPS_PROVIDER
	   	            //
	   	            4000,//60000, //ms milisegundos retraso de actualización (para no recibir actualizaciones con mucha frecuencia)
	   	            0,//10, // metros Distancia mínima con el punto anterior// Nunca un valor inferior a 60000ms agota batería (cambios ignorados)
	   	            locationListener); //Escuchador de eventos
	      }
    private void crearMapa(){
    	
    	mapView = (MapView) findViewById(R.id.mapView);
	    mc = mapView.getController();
	    mapView.setBuiltInZoomControls(true);
	    mapView.setTraffic(true);
    	itemizedoverlay = new MiItemizedOverlay(this);	
	}
    public void cargaCoordenadas(){    	
    	//ArrayList<Coordenada> MiLista = peticion.getCoordenadas();	 
    	ListIterator<Coordenada> iterador = MiLista.listIterator(); //Le solicito a la lista que me devuelva un iterador con todos los el elementos contenidos en ella
    	 
    	ImageView imagenView = new ImageView(TabMapa.getInstance().getBaseContext());
    	 
    	//Mientras que el iterador tenga un proximo elemento
    	while(iterador.hasNext() ) {
    		Coordenada coordenada = (Coordenada) iterador.next(); //Obtengo el elemento contenido
    		imagenView.setImageBitmap(coordenada.getIcono());
    		OverlayItem overlay = getOverlayItem(coordenada.getTitulo(),coordenada.getDescripcion(),coordenada.getLongitud(),coordenada.getLatitud(),imagenView.getDrawable());
			try {
				TabMapa.getInstance().anadePuntoAlMapaConItemizedOverlayEImagen(overlay);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    	}
 }
    
    public void anadePuntoAlMapaConItemizedOverlayEImagen(OverlayItem overlayItem) throws Exception{    	
		mapOverlays = mapView.getOverlays();
		itemizedoverlay.addOverlay(overlayItem);
   		mapOverlays.add(itemizedoverlay);
	    mc.setZoom(14);
	    mc.animateTo(overlayItem.getPoint());
	    mapView.invalidate();
 }
    
    public void anadePuntoAlMapaConItemizedOverlay(String latitud, String longitud){
	 	String coordinates[] = {latitud,longitud};
	   		double lat = Double.parseDouble(coordinates[0]);
	   		double lng = Double.parseDouble(coordinates[1]);
	mapOverlays = mapView.getOverlays();
	    //mapOverlays.clear();
	// Trabaja en microgrados para las coordenadas y por eso hay que multiplicar por un millón
	    GeoPoint point = new GeoPoint((int) (lat * 1E6),(int) (lng * 1E6));
	    OverlayItem overlayitem = new OverlayItem(point, "Hola, Seas!", "I'm Seas!!");
	    overlayitem.setMarker(itemizedoverlay.boundCenterBottomAux(this.getResources().getDrawable(R.drawable.tabmapa)));
	    itemizedoverlay.addOverlay(overlayitem);
	    mapOverlays.add(itemizedoverlay);
    mc.setZoom(17);
    mc.animateTo(point);
    mapView.invalidate();
 }
    
    public synchronized void anadePuntoAlMapaConOverlay(String latitud, String longitud){
  	   String coordinates[] = {latitud,longitud};
  	   double lat = Double.parseDouble(coordinates[0]);
        double lng = Double.parseDouble(coordinates[1]);
        GeoPoint p;
         p = new GeoPoint(
                 (int) (lat * 1E6), // Trabaja en microgrados para las coordenadas y por eso hay que multiplicar por un millón
                 (int) (lng * 1E6));
         mapOverlays = mapView.getOverlays();
         mapOverlays.clear();
         alberto.herramientasmapas.MiOverlay marker = new alberto.herramientasmapas.MiOverlay(p);
     	mapOverlays.add(itemizedoverlay);
     	mapOverlays.add(marker);
     	mc.animateTo(p);
         mc.setZoom(14);
     	mapView.invalidate();
     }

    @Override
	protected boolean isRouteDisplayed() {
		// TODO Auto-generated method stub
		return false;
	}
    /***
   	* CONTROL DE ESTADOS DEL ACTIVITY
   */
 /* 	@Override
  	protected void onDestroy() {
  		// TODO Auto-generated method stub
  		super.onDestroy();
  	}*/
 	@Override
  	protected void onPause() {
  		// TODO Auto-generated method stub
  		lm.removeUpdates(locationListener); //Detener las actualizaciones
  		super.onPause();
  	}
  	@Override
  	protected void onResume() {
        super.onResume();
        Bundle extras = getParent().getIntent().getExtras();
        if(extras !=null)
        {
        filtradoSpinner = extras.getString("filtroSpinner");
        filtradoItem = extras.getInt("filtroItem");                    
        processThread();
        }
  		
/*  Si dejo actualizar abenda		lm.requestLocationUpdates(
   	    		best,  //receptor GPS, más preciso y más habitual y disponible en el emulador //nombre del proveedor
   	    		//NETWORK_PROVIDER, GPS_PROVIDER
   	            //
   	            4000,//60000, //ms milisegundos retraso de actualización (para no recibir actualizaciones con mucha frecuencia)
   	            0,//10, // metros Distancia mínima con el punto anterior// Nunca un valor inferior a 60000ms agota batería (cambios ignorados)
   	            locationListener); //Escuchador de eventos*/
//  		super.onResume();
  	}
    
  /*  @Override
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
    }*/
    
	/***
	* GETTER/SETTER
	* @return
	*/
	public MapView getMapView() {
		return mapView;
	}
	public void setMapView(MapView mapView) {
		this.mapView = mapView;
	}
	public MapController getMc() {
		return mc;
	}
	public void setMc(MapController mc) {
		this.mc = mc;
	}
	public MiItemizedOverlay getItemizedoverlay() {
		return itemizedoverlay;
	}


/*	@Override
	protected void onPause() {
		//lm.removeUpdates(locationListener);
		// TODO Auto-generated method stub
		super.onPause();
	}*/


}


