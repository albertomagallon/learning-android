package alberto.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.Spinner;

public class Submenufiltrado extends Activity implements OnCheckedChangeListener{
	private int radioCheckedId = -1;
    private RadioGroup radiogroup;
    private RadioButton radioBtn1,radioBtn2;
    private Spinner spinnerCategoria, spinnerBarrio;

	
     @Override
     public void onCreate(Bundle icicle){
         super.onCreate(icicle);
              setContentView(R.layout.submenufiltrado);
       
         //RadioGroup
         radiogroup = (RadioGroup) findViewById(R.id.RadioGroup);
         radioBtn1 = (RadioButton)findViewById(R.id.radio1);
         radioBtn1.setChecked(true);
         //radioBtn2 = (RadioButton)findViewById(R.id.radio2);
              
         //Spinner Categoria
         spinnerCategoria = (Spinner)findViewById(R.id.categoriaSpineer);
                
            ArrayAdapter<CharSequence> adapterCategoria = ArrayAdapter.createFromResource(
                    this,R.array.Categoria, android.R.layout.simple_spinner_item);
       
         adapterCategoria.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
       
         spinnerCategoria.setAdapter(adapterCategoria);
         
         //Spinner Barrio
         spinnerBarrio = (Spinner)findViewById(R.id.barrioSpineer);
         
         ArrayAdapter<CharSequence> adapterBarrio = ArrayAdapter.createFromResource(
                 this,R.array.Barrio, android.R.layout.simple_spinner_item);
    
         adapterBarrio.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
    
         spinnerBarrio.setAdapter(adapterBarrio);
         spinnerBarrio.setEnabled(false);
         
         //Métodos
         radiogroup.setOnCheckedChangeListener(this);
       
         final Button changeButton = (Button) findViewById(R.id.aplicarButton);
   
         changeButton.setOnClickListener(new OnClickListener() {
           
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
           	 /*El item Categoría seleccionado lo envío normal y 
            	el item Barrio lo envío negativo para distinguirlo del item Categoría.
            	*/
       

            	      
               Intent resultIntent = getIntent();  
                int filtradoSel=-999;
                //El item Categoría seleccionado lo envío normal
               if (spinnerCategoria.isEnabled()) {
            	   filtradoSel = (int) spinnerCategoria.getSelectedItemId();
            	   resultIntent.putExtra("Spinner", "Categoria");
            	   resultIntent.putExtra("item", filtradoSel);
               }else if(spinnerBarrio.isEnabled()){
            	   filtradoSel = (int) spinnerBarrio.getSelectedItemId();
            	   resultIntent.putExtra("Spinner", "Barrio");            	   
            	   resultIntent.putExtra("item", filtradoSel);
               }
                
                setResult(Activity.RESULT_OK, resultIntent);
           		finish();
                //changeOption(spinnerBarrio);
            	//Intent in = new Intent();
                //setResult(filtradoSel,in);//Here I am Setting the Requestcode 1, you can put according to your requirement
                //finish();
           
            }
        });

     }

     public void onCheckedChanged(RadioGroup group, int checkedId) {
         radioCheckedId = checkedId;
         if (radioCheckedId == R.id.radio1) {
	           spinnerCategoria.setEnabled(true);
	           spinnerBarrio.setSelection(0);
	           spinnerBarrio.setEnabled(false);
	        }
	        if (radioCheckedId == R.id.radio2) {
	           spinnerCategoria.setSelection(0);
	           spinnerCategoria.setEnabled(false);
	           spinnerBarrio.setEnabled(true);
	        }
     }

     public void onClick(View v)
     {
    	        if (radioCheckedId == R.id.radio1) {
     	           spinnerCategoria.setEnabled(false);
     	           spinnerBarrio.setEnabled(true);
    	        }
    	        if (radioCheckedId == R.id.radio2) {
    	           spinnerCategoria.setEnabled(false);
    	           spinnerBarrio.setEnabled(true);
    	        }
    	        
     }

}