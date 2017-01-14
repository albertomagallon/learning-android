package alberto.herramientasmapas;


import alberto.activity.TabMapa;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Point;
import android.view.MotionEvent;
import android.widget.Toast;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapView;

public class MiOverlay extends com.google.android.maps.Overlay
{
    private GeoPoint punto;
	public MiOverlay(GeoPoint point){
    	super();
    	this.punto = point;
    }
	@Override
    public boolean draw(Canvas canvas, MapView mapView, boolean shadow, long when) 
    {
		super.draw(canvas, mapView, shadow);
		
		//se traduce el punto geo localizado a un punto en la pantalla
		Point scrnPoint = new Point();
		mapView.getProjection().toPixels(this.punto, scrnPoint);
		 
		//se construye un bitmap a partir de la imagen
		Bitmap marker = BitmapFactory.decodeResource(TabMapa.getInstance().getResources(), alberto.activity.R.drawable.tabmapa);
		 
		//se dibuja la imagen del marker
		canvas.drawBitmap(marker, scrnPoint.x - marker.getWidth() / 2, scrnPoint.y - marker.getHeight() / 2, null);
		 
		return true;

    }
	@Override
    public boolean onTouchEvent(MotionEvent event, MapView mapView) 
    {   
        //---when user lifts his finger---
        if (event.getAction() == 1) {                
            GeoPoint p = mapView.getProjection().fromPixels(
                (int) event.getX(),
                (int) event.getY());
                /*Toast.makeText(TabMapa.getInstance().getBaseContext(), 
                    p.getLatitudeE6() / 1E6 + "," + 
                    p.getLongitudeE6() /1E6 , 
                    Toast.LENGTH_SHORT).show();*/
                /*Toast.makeText(TabMapa.getInstance().getBaseContext(), "Hello!", Toast.LENGTH_LONG).show();*/
        }                            
        return false;
    }        
} 
