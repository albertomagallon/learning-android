package alberto.conexion_a_BD;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import alberto.activity.TabLista;
import alberto.activity.TabMapa;
import alberto.objetos.Coordenada;
import alberto.objetos.Negocio;
import alberto.objetos.Oferta;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.widget.Toast;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.OverlayItem;

public class Peticiones{
	 public Bitmap downloadFile(String imageHttpAddress) {
	        URL imageUrl = null;
	        Bitmap loadedImage = null;
	        HttpURLConnection conn = null; 
	        try {
	            imageUrl = new URL(imageHttpAddress);
	            conn = (HttpURLConnection) imageUrl.openConnection();
	            conn.connect();
	            loadedImage = BitmapFactory.decodeStream(conn.getInputStream());
	        } catch (IOException e) {
	            Toast.makeText(TabMapa.getInstance().getApplicationContext(), "Error cargando la imagen: "+e.getMessage(), Toast.LENGTH_LONG).show();
	        }finally{
	        	if(conn!=null){conn.disconnect();}
	        }
	        return loadedImage;
	    }
	 
	 public OverlayItem getOverlayItem(String nombre, String descripcion, String latitud, String longitud, Drawable drawable){
		 	String coordinates[] = {latitud,longitud};
	   		double lat = Double.parseDouble(coordinates[0]);
	   		double lng = Double.parseDouble(coordinates[1]);
	   		GeoPoint point = new GeoPoint((int) (lng * 1E6),(int) (lat * 1E6));
	   		OverlayItem overlayitem = new OverlayItem(point, nombre, descripcion);
	   		overlayitem.setMarker(TabMapa.getInstance().getItemizedoverlay().boundCenterBottomAux(drawable));
	   		return overlayitem;
	 }
	 
	 //Devuelve una lista con todas las coordenadas de los negocios de cada Oferta 
	 public  ArrayList<Coordenada> getCoordenadas(){
		 Post post = null;
		 //Crear una lista para almacenar las ofertas
		 ArrayList<Coordenada> MiLista = new ArrayList<Coordenada>();	 
		 try{			 
		 	//ImageView imagenView = new ImageView(TabMapa.getInstance().getBaseContext());

	    	//INICIO Llamada a Servidor Web PHP
			ArrayList<String> parametros = new ArrayList<String>();
			parametros.add("");
			
			//Llamada a Servidor Web PHP
			post = new Post();
			JSONArray datos = post.getServerData(parametros,"http://albertoseas.herobo.com/PHP/Coordenadas.php");
			if(datos!=null && datos.length()>0){
				//datos.length() 
				for(int i = 0 ; i <datos.length() ; i++){
						JSONObject json_data = datos.getJSONObject(i);
						
						/*Recuperar los campos de la base de datos en variables locales*/
						//int idNegocio = json_data.getInt("idNegocio");
						String Titulo = json_data.getString("Titulo");
						String Descripcion = json_data.getString("Descripcion");
				        String Latitud = json_data.getString("Latitud");
				        String Longitud = json_data.getString("Longitud");
				        int idCategoria = json_data.getInt("idCategoria");				        				        

						
				        /*Crear un objeto oferta*/
				        Coordenada coordenada = new Coordenada();
				        
				        /*Informar los datos del objeto*/
				        coordenada.setTitulo(Titulo);
				        coordenada.setDescripcion(Descripcion);
				        coordenada.setLatitud(Latitud);
				        coordenada.setLongitud(Longitud);				      
				        coordenada.setIdCategoria(idCategoria);

				        
				        /*Informar la imagen*/
						String urlImagen = "http://albertoseas.herobo.com/Categorias/" + idCategoria + ".png";
						Bitmap loadedImage = downloadFile(urlImagen);
						//imagenView.setImageBitmap(loadedImage);
						coordenada.setIcono(loadedImage);
						
						//OverlayItem overlay = getOverlayItem(Titulo,Descripcion,Longitud,Latitud,imagenView.getDrawable());
						//TabMapa.getInstance().anadePuntoAlMapaConItemizedOverlayEImagen(overlay);
				        /*Añadir objeto a la lista*/
				        MiLista.add(coordenada);
				}
			}
			//TabMapa.getInstance().anadePuntoAlMapaConItemizedOverlay("41.746726","-0.862427");
		}catch(Exception e){
			  Toast.makeText(TabMapa.getInstance().getBaseContext(), 
		                e.getMessage(), 
		                Toast.LENGTH_SHORT).show();
			}			
		 return MiLista;
		
		}	//FIN Llamada a Servidor Web PHP
	 
	//Devuelve una lista con todas las coordenadas de los negocios de cada Oferta 
		 public  ArrayList<Coordenada> getCoordenadasFiltradas(String Tabla, int item){
			 Post post = null;
			 //Crear una lista para almacenar las ofertas
			 ArrayList<Coordenada> MiLista = new ArrayList<Coordenada>();	 
			 try{			 
			 	//ImageView imagenView = new ImageView(TabMapa.getInstance().getBaseContext());

		    	//INICIO Llamada a Servidor Web PHP
				ArrayList<String> parametros = new ArrayList<String>();
				String sItem = item + "";
				parametros.add("Tabla");
				parametros.add(Tabla);
				parametros.add("Item");
				parametros.add(sItem);
				
				//Llamada a Servidor Web PHP
				post = new Post();
				JSONArray datos = post.getServerData(parametros,"http://albertoseas.herobo.com/PHP/CoordenadasFiltro.php");
				if(datos!=null && datos.length()>0){
					//datos.length() 
					for(int i = 0 ; i <datos.length() ; i++){
							JSONObject json_data = datos.getJSONObject(i);
							
							/*Recuperar los campos de la base de datos en variables locales*/
							//int idNegocio = json_data.getInt("idNegocio");
							String Titulo = json_data.getString("Titulo");
							String Descripcion = json_data.getString("Descripcion");
					        String Latitud = json_data.getString("Latitud");
					        String Longitud = json_data.getString("Longitud");
					        int idCategoria = json_data.getInt("idCategoria");				        				        

							
					        /*Crear un objeto oferta*/
					        Coordenada coordenada = new Coordenada();
					        
					        /*Informar los datos del objeto*/
					        coordenada.setTitulo(Titulo);
					        coordenada.setDescripcion(Descripcion);
					        coordenada.setLatitud(Latitud);
					        coordenada.setLongitud(Longitud);				      
					        coordenada.setIdCategoria(idCategoria);

					        
					        /*Informar la imagen*/
							String urlImagen = "http://albertoseas.herobo.com/Categorias/" + idCategoria + ".png";
							Bitmap loadedImage = downloadFile(urlImagen);
							//imagenView.setImageBitmap(loadedImage);
							coordenada.setIcono(loadedImage);
							
							//OverlayItem overlay = getOverlayItem(Titulo,Descripcion,Longitud,Latitud,imagenView.getDrawable());
							//TabMapa.getInstance().anadePuntoAlMapaConItemizedOverlayEImagen(overlay);
					        /*Añadir objeto a la lista*/
					        MiLista.add(coordenada);
					}
				}
				//TabMapa.getInstance().anadePuntoAlMapaConItemizedOverlay("41.746726","-0.862427");
			}catch(Exception e){
				  Toast.makeText(TabMapa.getInstance().getBaseContext(), 
			                e.getMessage(), 
			                Toast.LENGTH_SHORT).show();
				}			
			 return MiLista;
			
			}	//FIN Llamada a Servidor Web PHP
	 
	 private String  calcularFecha(String fecha){
		 	java.util.Date utilDate = new java.util.Date(); //fecha actual
		 	long lnMilisegundos = utilDate.getTime();
		 	java.sql.Timestamp sqlTimestamp = new java.sql.Timestamp(lnMilisegundos);
	        String fechaInicial = fecha;
	        String fechaFinal = sqlTimestamp.toString();
	        fechaFinal = fechaFinal.substring(0, 19);
	     /*   System.out.println("año :"+fecha.substring(0,4));
	        System.out.println("dia :"+fecha.substring(8,10));
	        System.out.println("mes :"+fecha.substring(5,7));
	        System.out.println("hora :"+fecha.substring(11,13));
	        System.out.println("minuto :"+fecha.substring(14,16));
	        System.out.println("seg :"+fecha.substring(17,19));*/
	   
	   
	       
	        java.util.GregorianCalendar jCal = new java.util.GregorianCalendar();
	        java.util.GregorianCalendar jCal2 = new java.util.GregorianCalendar();
	        //jCal.set(year, month, date, hourOfDay, minute)
	        jCal.set(Integer.parseInt(fechaInicial.substring(0,4)), Integer.parseInt(fechaInicial.substring(5,7))-1, Integer.parseInt(fechaInicial.substring(8,10)), Integer.parseInt(fechaInicial.substring(11,13)),Integer.parseInt(fechaInicial.substring(14,16)), Integer.parseInt(fechaInicial.substring(17,19)));
	        jCal2.set(Integer.parseInt(fechaFinal.substring(0,4)), Integer.parseInt(fechaFinal.substring(5,7))-1, Integer.parseInt(fechaFinal.substring(8,10)), Integer.parseInt(fechaFinal.substring(11,13)),Integer.parseInt(fechaFinal.substring(14,16)), Integer.parseInt(fechaFinal.substring(17,19)));
	       
	        //System.out.println("Date format " + dateformat.format(jCal.getTime()) + "\n");
	        //System.out.println("Date format " + dateformat.format(jCal2.getTime()) + "\n");
	 
	        long diferencia = jCal.getTime().getTime()-jCal2.getTime().getTime();
	        double minutos = diferencia / (1000 * 60);
	        long horas = (long) (minutos / 60);
	        long minuto = (long) (minutos%60);
	        long segundos = (long) diferencia % 1000;
	        long dias = horas/24;
	        //Calcular meses...
	        //Crear vector para almacenar los diferentes dias maximos segun correponda
	        String[] mesesAnio = new String[12];
	        mesesAnio[0] = "31";
	        //validacion de los años bisiestos
	        if (jCal.isLeapYear(jCal.YEAR)){mesesAnio[1] = "29";}else{mesesAnio[1] = "28";}
	        mesesAnio[2] = "31";
	        mesesAnio[3] = "30";
	        mesesAnio[4] = "31";
	        mesesAnio[5] = "30";
	        mesesAnio[6] = "31";
	        mesesAnio[7] = "31";
	        mesesAnio[8] = "30";
	        mesesAnio[9] = "31";
	        mesesAnio[10] = "30";
	        mesesAnio[11] = "31";
	        int diasRestantes = (int) dias;
	        //variable almacenará el total de meses que hay en esos dias
	        int totalMeses = 0;
	        int mesActual = jCal.MONTH;
	        //Restar los dias de cada mes desde la fecha de ingreso hasta que ya no queden sufcientes dias para
	        // completar un mes.
	        for (int i=0; i<=11; i++ ){
	            //Validar año, si sumando 1 al mes actual supera el fin de año,
	            // setea la variable a principio de año
	            if ((mesActual+1)>=12){
	                mesActual = i;
	            }
	            //Validar que el numero de dias resultantes de la resta de las 2 fechas, menos los dias
	            //del mes correspondiente sea mayor a cero, de ser asi totalMeses aumenta,continuar hasta
	            //que ya nos se cumpla.
	            if ((diasRestantes -Integer.parseInt(mesesAnio[mesActual]))>=0){
	                totalMeses ++;
	                diasRestantes = diasRestantes- Integer.parseInt(mesesAnio[mesActual]);
	                mesActual ++;
	            }else{
	                break;
	            }
	        }
	        //Resto de horas despues de sacar los dias
	        horas = horas % 24;
	        String salida ="";
	        if (totalMeses > 0){
	            if (totalMeses > 1)
	                salida = salida+  String.valueOf(totalMeses)+" Meses,  ";
	            else
	                salida = salida+  String.valueOf(totalMeses)+" Mes, ";
	        }
	        if (diasRestantes > 0){
	            if (diasRestantes > 1)
	                salida = salida+  String.valueOf(diasRestantes)+" Dias, ";
	            else
	                salida = salida+  String.valueOf(diasRestantes)+" Dia, ";
	        }
	       
	       
	         salida ="Quedan " + salida +String.valueOf(horas)+" horas y "+String.valueOf(minuto)+" minutos.";
	        return salida;
	        
	 }
	 //Devuelve una lista con todas las ofertas de la tabla Ofertas 
	 public  Negocio getNegocio(int idnegocio){
		 Post post = null;
		   /*Crear un objeto negocio*/
	        Negocio negocio = new Negocio();	        
		 		 		 
		 try{			 
	    	//INICIO Llamada a Servidor Web PHP
			ArrayList<String> parametros = new ArrayList<String>();
			String spNegocio = idnegocio + "";
			parametros.add("idnegocio");
			parametros.add(spNegocio);
			
			//Llamada a Servidor Web PHP
			post = new Post();
			JSONArray datos = post.getServerData(parametros,"http://albertoseas.herobo.com/PHP/Negocio.php");
			if(datos!=null && datos.length()>0){
				for(int i = 0 ; i <datos.length() ; i++){
						JSONObject json_data = datos.getJSONObject(i);
						
						/*Recuperar los campos de la base de datos en variables locales*/				        
				        int idNegocio = json_data.getInt("idNegocio");
					    String Nombre = json_data.getString("Nombre");
					    String Descripcion = json_data.getString("Descripcion");
					    String Direccion = json_data.getString("Direccion");
					    int idCategoria = json_data.getInt("idCategoria");
					    int idBarrio = json_data.getInt("idBarrio");
					    String sCategoria = json_data.getString("Categoria");;
					    String sBarrio = json_data.getString("Barrio");				        				        
				        
				        /*Informar los datos del objeto*/
				        negocio.setIdNegocio(idNegocio);
				        negocio.setNombre(Nombre);
				        negocio.setDescripcion(Descripcion);
				        negocio.setDireccion(Direccion);
				        negocio.setIdCategoria(idCategoria);
				        negocio.setIdBarrio(idBarrio);
				        negocio.setsBarrio(sBarrio);
				        negocio.setsCategoria(sCategoria);
				        				        				        				        

				}
			}			
		}catch(Exception e){
			  Toast.makeText(TabLista.getInstance().getBaseContext(), 
		                e.getMessage(), 
		                Toast.LENGTH_SHORT).show();
			}	
		
		return negocio;
		
		}	//FIN Llamada a Servidor Web PHP
	 
	 //Devuelve una lista con todas las ofertas de la tabla Ofertas 
	 public  ArrayList<Oferta> getOfertas(){
		 Post post = null;
		 //Crear una lista para almacenar las ofertas
		 ArrayList<Oferta> MiLista = new ArrayList<Oferta>();
		 		 		 
		 try{			 
	    	//INICIO Llamada a Servidor Web PHP
			ArrayList<String> parametros = new ArrayList<String>();
			parametros.add("");
			
			//Llamada a Servidor Web PHP
			post = new Post();
			JSONArray datos = post.getServerData(parametros,"http://albertoseas.herobo.com/PHP/Ofertas.php");
			if(datos!=null && datos.length()>0){
				for(int i = 0 ; i <datos.length() ; i++){
						JSONObject json_data = datos.getJSONObject(i);
						
						/*Recuperar los campos de la base de datos en variables locales*/
						int idOferta = json_data.getInt("idOferta");
				        String Titulo = json_data.getString("Titulo");
				        String Descripcion = json_data.getString("Descripcion");
				        String Descuento = json_data.getString("Descuento");
				        String Ahorro = json_data.getString("Ahorro");
				        String urlImagen = json_data.getString("urlImagen");
				        String FechaVig = json_data.getString("FechaVig");
				        int idNegocio = json_data.getInt("idNegocio");
				        
				        /*Crear un objeto oferta*/
				        Oferta oferta = new Oferta();
				        
				        /*Informar los datos del objeto*/
				        oferta.setIdOferta(idOferta);
				        oferta.setTitulo(Titulo);
				        oferta.setDescripcion(Descripcion);
				        oferta.setDescuento(Descuento);				      
				        oferta.setAhorro(Ahorro);
				        oferta.setUrlImagen(urlImagen);
				        FechaVig = calcularFecha(FechaVig);
				        oferta.setFechaVig(FechaVig);
				        oferta.setIdNegocio(idNegocio);
				        
				        /*Informar la imagen*/
				        String rutaImagen = "http://albertoseas.herobo.com/IMGOfertas/" + urlImagen;												
						oferta.setUrlImagen(rutaImagen);
				        
				        /*Añadir objeto a la lista*/
				        MiLista.add(oferta);

				}
			}			
		}catch(Exception e){
			  Toast.makeText(TabLista.getInstance().getBaseContext(), 
		                e.getMessage(), 
		                Toast.LENGTH_SHORT).show();
			}	
		return MiLista;
		
		}	//FIN Llamada a Servidor Web PHP
	 
	 //Devuelve una lista con todas las ofertas de la tabla Ofertas 
	 public  ArrayList<Oferta> getOfertasFiltradas(String Tabla, int item){
		 Post post = null;
		 //Crear una lista para almacenar las ofertas
		 ArrayList<Oferta> MiLista = new ArrayList<Oferta>();
		 		 		 
		 try{			 
	    	//INICIO Llamada a Servidor Web PHP
			ArrayList<String> parametros = new ArrayList<String>();
			String sItem = item + "";
			parametros.add("Tabla");
			parametros.add(Tabla);
			parametros.add("Item");
			parametros.add(sItem);
			
			
			//Llamada a Servidor Web PHP
			post = new Post();
			JSONArray datos = post.getServerData(parametros,"http://albertoseas.herobo.com/PHP/OfertasFiltro.php");
			if(datos!=null && datos.length()>0){
				for(int i = 0 ; i <datos.length() ; i++){
						JSONObject json_data = datos.getJSONObject(i);
						
						/*Recuperar los campos de la base de datos en variables locales*/
						int idOferta = json_data.getInt("idOferta");
				        String Titulo = json_data.getString("Titulo");
				        String Descripcion = json_data.getString("Descripcion");
				        String Descuento = json_data.getString("Descuento");
				        String Ahorro = json_data.getString("Ahorro");
				        String urlImagen = json_data.getString("urlImagen");
				        String FechaVig = json_data.getString("FechaVig");
				        int idNegocio = json_data.getInt("idNegocio");
				        
				        /*Crear un objeto oferta*/
				        Oferta oferta = new Oferta();
				        
				        /*Informar los datos del objeto*/
				        oferta.setIdOferta(idOferta);
				        oferta.setTitulo(Titulo);
				        oferta.setDescripcion(Descripcion);
				        oferta.setDescuento(Descuento);				      
				        oferta.setAhorro(Ahorro);
				        oferta.setUrlImagen(urlImagen);
				        FechaVig = calcularFecha(FechaVig);
				        oferta.setFechaVig(FechaVig);
				        oferta.setIdNegocio(idNegocio);
				        
				        /*Informar la imagen*/
				        String rutaImagen = "http://albertoseas.herobo.com/IMGOfertas/" + urlImagen;												
						oferta.setUrlImagen(rutaImagen);
				        
				        /*Añadir objeto a la lista*/
				        MiLista.add(oferta);

				}
			}			
		}catch(Exception e){
			  Toast.makeText(TabLista.getInstance().getBaseContext(), 
		                e.getMessage(), 
		                Toast.LENGTH_SHORT).show();
			}	
		return MiLista;
		
		}	//FIN Llamada a Servidor Web PHP
}
