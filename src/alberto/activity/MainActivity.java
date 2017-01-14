package alberto.activity;

import android.app.Activity;
import android.app.TabActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MenuItem.OnMenuItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.TabHost;
import android.widget.Toast;
import android.widget.TabHost.TabSpec;

/*Hereda de TabActivity para distribuir el formulario en pesta�as*/
public class MainActivity extends TabActivity{
	public int requestCode;
	/*TabHost: Objeto para crear pesta�as*/
    private TabHost mTabHost;
    /*Resources: Objeto para acceder a los recursos (formularios, iconos, etc)*/
    private Resources mResources;
    
    /*Constante para restaurar pesta�a al volver a la aplicaci�n*/
    private static final String PREF_STICKY_TAB = "stickyTab";
	
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        /*Recupera el tabhost que est� usando el activity*/
        mTabHost = getTabHost();       
        /*Recupera los recursos instanciados en la aplicaci�n*/
        mResources = getResources(); 
        
        /*A�adir las pesta�as*/
        anadirTabLista();
        anadirTabMapa();               
        
        // Al abrir la aplicacion restauramos la �ltima pesta�a activada
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        int currentTab = prefs.getInt(PREF_STICKY_TAB, 0);
        mTabHost.setCurrentTab(currentTab);
    }
    
    @Override
    protected void onPause() {
        super.onPause();

        // Cuando se cierra la aplicaci�n guardamos la pesta�a activa
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = prefs.edit();
        int currentTab = mTabHost.getCurrentTab();
        editor.putInt(PREF_STICKY_TAB, currentTab);
        editor.commit();
    } 
    
	
    /* Pesta�a 1
     * Crear pesta�a asignando un tag, un formulario y un icono.  Asociarlo a un activity
     * mediante un intent. 
     */
    private void anadirTabLista() {
    	
    	Intent intent = new Intent(this, TabLista.class);
    	
        TabSpec spec = mTabHost.newTabSpec("Lista de ofertas");
        spec.setIndicator(mResources.getString(R.string.tabLista), mResources
                .getDrawable(R.drawable.tablista));
        spec.setContent(intent);

        mTabHost.addTab(spec);
  
    }
    
	/*
	 * Pesta�a 2
	 * Lo mismo que Pesta�a 1
	 */
     
    private void anadirTabMapa() {
    	
    	Intent intent = new Intent(this, TabMapa.class);
    	
        TabSpec spec = mTabHost.newTabSpec("Mapa");
        spec.setIndicator(mResources.getString(R.string.tabMapa), mResources
                .getDrawable(R.drawable.tabmapa));
        spec.setContent(intent);

        mTabHost.addTab(spec);
    }
    
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.layout.menu, menu);	        
        return true;
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intentFiltrado=new Intent(this,Submenufiltrado.class);
        startActivityForResult(intentFiltrado,1);        
        return true;
    }
    
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.d("CheckStartActivity","onActivityResult and resultCode = "+resultCode);
        // TODO Auto-generated method stub
        super.onActivityResult(requestCode, resultCode, data);
        
        switch(requestCode) { 
	    case (1) : { 
	      if (resultCode == Activity.RESULT_OK) { 	    	  
	    	  String SpinnerSel = data.getExtras().getString("Spinner");	    	  
	    			  //data.getStringExtra("Spinner");
	    	  int itemSel = data.getExtras().getInt("item");;
        	   Intent in = getIntent();
         	   in.putExtra("filtroSpinner",SpinnerSel);
         	    in.putExtra("filtroItem",itemSel);

	    	  
              }
              break; 
	     } 
	   } 
    

    }
}

