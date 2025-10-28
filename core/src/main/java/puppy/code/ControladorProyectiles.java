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
	private Array<Rectangle> posicionesProyectiles;
	private Array<Integer> tiposProyectiles;
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
		posicionesProyectiles = new Array<Rectangle>();
		tiposProyectiles = new Array<Integer>();
		crearProyectil();
	      // start the playback of the background music immediately
	      musicaFondo.setLooping(true);
	      musicaFondo.play();
	}
	
	private void crearProyectil() {
	      Rectangle proyectil = new Rectangle();
	      proyectil.x = MathUtils.random(0, 800-64);
	      proyectil.y = 480;
	      proyectil.width = 64;
	      proyectil.height = 64;
	      posicionesProyectiles.add(proyectil);
	      // ver el tipo de gota
	      if (MathUtils.random(1,10)<5)	    	  
	         tiposProyectiles.add(1);
	      else 
	    	 tiposProyectiles.add(2);
	      ultimoProyectilTiempo = TimeUtils.nanoTime();
	   }
	
   public boolean actualizarMovimiento(Heroe heroe) { 
	   // generar gotas de lluvia 
	   if(TimeUtils.nanoTime() - ultimoProyectilTiempo > 100000000) crearProyectil();
	  
	   
	   // revisar si las gotas cayeron al suelo o chocaron con el tarro
	   for (int i=0; i < posicionesProyectiles.size; i++ ) {
		  Rectangle raindrop = posicionesProyectiles.get(i);
	      raindrop.y -= 300 * Gdx.graphics.getDeltaTime();
	      //cae al suelo y se elimina
	      if(raindrop.y + 64 < 0) {
	    	  posicionesProyectiles.removeIndex(i); 
	    	  tiposProyectiles.removeIndex(i);
	      }
	      if(raindrop.overlaps(heroe.getHitbox())) { //la gota choca con el tarro
	    	if(tiposProyectiles.get(i)==1) { // gota dañina
	    	  heroe.restarVida();
	    	  if (heroe.getVidas()<=0)
	    		 return false; // si se queda sin vidas retorna falso /game over
	    	  posicionesProyectiles.removeIndex(i);
	          tiposProyectiles.removeIndex(i);
	      	}else { // gota a recolectar
	    	  heroe.sumarPuntos(10);
	          sonidoMoneda.play();
	          posicionesProyectiles.removeIndex(i);
	          tiposProyectiles.removeIndex(i);
	      	}
	      }
	   } 
	  return true; 
   }
   
   public void dibujarProyectiles(SpriteBatch batch) { 
	   
	  for (int i=0; i < posicionesProyectiles.size; i++ ) {
		  Rectangle raindrop = posicionesProyectiles.get(i);
		  if(tiposProyectiles.get(i)==1) // gota dañina
	         batch.draw(texturaBolaFuego, raindrop.x, raindrop.y); 
		  else
			 batch.draw(texturaMoneda, raindrop.x, raindrop.y); 
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
