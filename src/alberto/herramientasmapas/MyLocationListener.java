package alberto.herramientasmapas;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.OverlayItem;

import alberto.activity.R;
import alberto.activity.TabMapa;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.widget.Toast;

public class MyLocationListener implements LocationListener 
{
	// MÁS IMPORTANTE
		// CUANDO CAMBIE LA POSICIÓN
		// CUANDO EL PROVEEDOR OBSERVA QUE HAY UN CAMBIO DE POSICIÓN
	public void onLocationChanged(Location loc) {
	try{
		String longitud;
		String latitud;
		if (loc != null) {
        	longitud = String.valueOf(loc.getLongitude());
        	latitud= String.valueOf(loc.getLatitude());
            Toast.makeText(TabMapa.getInstance().getBaseContext(), 
                "Location changed : Lat: " + loc.getLatitude() + 
                " Lng: " + loc.getLongitude(), 
                Toast.LENGTH_SHORT).show();
            if(overlayItemAnterior!=null){
            	//Act6SeasTuristicLocationZaragoza.getInstance().getMapOverlays().remove(overlayItemAnterior);
            }
            OverlayItem overlay = getOverlayItem("Yo","Estoy situado aquí. ",latitud,longitud,TabMapa.getInstance().getResources().getDrawable(R.drawable.personap));
			TabMapa.getInstance().anadePuntoAlMapaConItemizedOverlayEImagen(overlay);
            //TabMapa.getInstance().anadePuntoAlMapaConOverlay(latitud,longitud);
			overlayItemAnterior = overlay;
        }
	}catch(Exception e){
		  Toast.makeText(TabMapa.getInstance().getBaseContext(), 
	                "Error al cargar la Geolocalizción. ", 
	                Toast.LENGTH_SHORT).show();
	}
    }
	private OverlayItem overlayItemAnterior = null;
	private OverlayItem getOverlayItem( String nombre, String descripcion, String latitud, String longitud, Drawable drawable){
	 	String coordinates[] = {latitud,longitud};
   		double lat = Double.parseDouble(coordinates[0]);
   		double lng = Double.parseDouble(coordinates[1]);
   		GeoPoint point = new GeoPoint((int) (lat * 1E6),(int) (lng * 1E6));
   		OverlayItem overlayitem = new OverlayItem(point, nombre, descripcion);
   		overlayitem.setMarker(TabMapa.getInstance().getItemizedoverlay().boundCenterBottomAux(drawable));
   		return overlayitem;
 }
	////////////////////////////////////////////////////////////////////////////////
	// AYUDAN A MEJORAR LA LOCALIZACIÓN EN EL CASO DE QUE UN PROVEEDOR FALLE.
	// MENSAJES Y RECURRIR A OTROS PROVEEDORES
	//////////////////////////////////////////////////////////////////////////////
	public void onProviderDisabled(String provider) {
        // TODO Auto-generated method stub
		 Intent intent = new Intent( android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
		 TabMapa.getInstance().startActivity(intent);
    }
    public void onProviderEnabled(String provider) {
        // TODO Auto-generated method stub
    }
    public void onStatusChanged(String provider, int status, 
        Bundle extras) {
        // TODO Auto-generated method stub
    }
}