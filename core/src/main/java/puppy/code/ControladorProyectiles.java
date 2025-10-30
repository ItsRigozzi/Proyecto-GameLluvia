package puppy.code;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;

public class ControladorProyectiles {
        private Array<EntidadJuego> proyectiles;
        private long ultimoProyectilTiempo;
        private Texture texturaMoneda;
        private Texture texturaBolaFuego;
        private Sound sonidoMoneda;
        private Music musicaFondo;
	   
	public ControladorProyectiles(Texture texturaMonedaRecibida, Texture texturaBolaFuegoRecibida, Sound ss, Music mm) {
		musicaFondo = mm;
		sonidoMoneda = ss;
		this.texturaMoneda = texturaMonedaRecibida;
		this.texturaBolaFuego = texturaBolaFuegoRecibida;
	}
	
	public void crear() {
                proyectiles = new Array<EntidadJuego>();
		crearProyectil();
	      // start the playback of the background music immediately
	      musicaFondo.setLooping(true);
	      musicaFondo.play();
	}
	
	private void crearProyectil() {
            float xPos = MathUtils.random(0, 800-64);
	    
	    EntidadJuego nuevoProyectil;
	    
	    // CAMBIO 3: Creamos objetos reales (Moneda o BolaFuego)
	    if (MathUtils.random(1,10) < 5) {
	    	// Menos del 50% de probabilidad: Bola de Fuego (Malo)
	    	nuevoProyectil = new BolaFuego(texturaBolaFuego, xPos, 480);
	    } else {
	    	// El resto: Moneda (Bueno)
	    	nuevoProyectil = new Moneda(texturaMoneda, xPos, 480);
	    }
	    
	    proyectiles.add(nuevoProyectil);
	    ultimoProyectilTiempo = TimeUtils.nanoTime();
	   }
	
   public boolean actualizarMovimiento(Heroe heroe) { 
            // generar gotas de lluvia 
	   if(TimeUtils.nanoTime() - ultimoProyectilTiempo > 100000000) crearProyectil();
	   
	   for (int i=0; i < proyectiles.size; i++ ) {
		   EntidadJuego proyectil = proyectiles.get(i);
		   
		   // 4. ACTUALIZACIÓN POLIMÓRFICA: 
		   // Cada proyectil se mueve según su propia lógica (Moneda.actualizar o BolaFuego.actualizar)
		   proyectil.actualizar(Gdx.graphics.getDeltaTime());
		   
		   // Eliminar si cae al suelo
		   if(proyectil.getHitbox().y + proyectil.getHitbox().height < 0) {
		 	   proyectiles.removeIndex(i);
		 	   i--;
		 	   continue; // Pasamos a la siguiente iteración
		   }
		   
		   // 5. REVISAR COLISIÓN
		   if(proyectil.getHitbox().overlaps(heroe.getHitbox())) { 
			   
			   if(proyectil instanceof BolaFuego) { // Colisión con Bola de Fuego (Mala)
		 	 	   heroe.restarVida();
		 	 	   if (heroe.getVidas() <= 0)
		 	 		   return false;
			   } else if (proyectil instanceof Moneda) { // Colisión con Moneda (Buena)
		 	 	   heroe.sumarPuntos(10);
		 	 	   sonidoMoneda.play();
			   }
			   
			   proyectiles.removeIndex(i);
			   i--;
		   }
            }
	   return true; 
   }
   
   public void dibujarProyectiles(SpriteBatch batch) { 
            // DIBUJO POLIMÓRFICO: Ya no necesitamos saber si es Moneda o Bola de Fuego,
	    // el método dibujar() de EntidadJuego lo hace por nosotros.
	    for (EntidadJuego proyectil : proyectiles) {
	        proyectil.dibujar(batch);
	   }
   }
   public void destruir() {
      sonidoMoneda.dispose();
      musicaFondo.dispose();
   }
   public void pausar() {
	  musicaFondo.stop();
   }
   public void continuar() {
	  musicaFondo.play();
   }
   
}
