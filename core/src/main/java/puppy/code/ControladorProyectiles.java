package puppy.code;

import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;
import com.badlogic.gdx.Gdx;

/**
 * REQUISITO GM2.2: Aplicación del Patrón de Diseño Template Method (Contexto).
 * * * * Esta clase (ControladorProyectiles) actúa como el "Contexto" que utiliza
 * * las clases concretas (Moneda, BolaFuego, etc.) que implementan la plantilla
 * * abstracta EntidadJuego. Demuestra el Polimorfismo al manejar una
 * * única lista de 'EntidadJuego'.
 */
public class ControladorProyectiles {
        
        /**
         * (GM2.2) Almacena una colección de la clase abstracta (EntidadJuego)
         * para manejar todos los proyectiles de forma polimórfica.
         */
        private Array<EntidadJuego> proyectiles;
        private long ultimoProyectilTiempo;
        private Texture texturaMoneda;
        private Texture texturaBolaFuego;
        private Sound sonidoMoneda;
        private Music musicaFondo;
        private Texture texturaBolaFuegoAzul;
        private boolean fase2Activada = false;
        private Texture texturaCura;
        private Texture texturaEscudo;
        private Texture texturaVelocidad;

        public ControladorProyectiles(Texture texturaMonedaRecibida, Texture texturaBolaFuegoRecibida, Texture texturaBolaFuegoAzulRecibida, Texture texturaCuraRecibida, Texture texturaEscudoRecibida, Texture texturaVelocidadRecibida, Sound ss, Music mm) {
            musicaFondo = mm;
            sonidoMoneda = ss;
            this.texturaMoneda = texturaMonedaRecibida;
            this.texturaBolaFuego = texturaBolaFuegoRecibida;
            this.texturaBolaFuegoAzul = texturaBolaFuegoAzulRecibida;
            this.texturaCura = texturaCuraRecibida;	
            this.texturaEscudo = texturaEscudoRecibida;
            this.texturaVelocidad = texturaVelocidadRecibida;
        }
	
	public void crear() {
        proyectiles = new Array<EntidadJuego>();
		crearProyectil(null);
	      musicaFondo.setLooping(true);
	      musicaFondo.play();
	}
	
		private void crearProyectil(Heroe heroe) {
		    float xPos = MathUtils.random(0, 800 - 64);
		    EntidadJuego nuevoProyectil = null;
	
		    if (fase2Activada) {
		        int tipo = MathUtils.random(1, 20); 

		        if (tipo <= 9) { 
		            nuevoProyectil = new Moneda(texturaMoneda, xPos, 480);
		        } else if (tipo <= 12) { 
		            nuevoProyectil = new BolaFuego(texturaBolaFuego, xPos, 480);
		        } else if (tipo <= 15) { 
		            nuevoProyectil = new BolaFuegoAzul(texturaBolaFuegoAzul, xPos, 480);
		        } else if (tipo == 16 || tipo == 17) { 
		            if (heroe != null && heroe.getVidas() < 5) {
		                nuevoProyectil = new PowerUpVida(texturaCura, xPos, 480);
		            } else {
		                nuevoProyectil = new Moneda(texturaMoneda, xPos, 480);
		            }
		        } else if (tipo == 18 || tipo == 19) { 
		            
		            if (heroe != null && heroe.esInmune()) {
		                nuevoProyectil = new Moneda(texturaMoneda, xPos, 480); 
		            } else {
		                int escudosActuales = 0;
		                for (EntidadJuego p : proyectiles) {
		                    if (p instanceof PowerUpInmunidad) escudosActuales++;
		                }
		                
		                if (escudosActuales < 2) {
		                    nuevoProyectil = new PowerUpInmunidad(texturaEscudo, xPos, 480); 
		                } else {
		                    nuevoProyectil = new Moneda(texturaMoneda, xPos, 480); 
		                }
		            }

		        } else { 
		        	if (heroe != null && !heroe.estaEnSlowMo()) {
		                nuevoProyectil = new PowerUpVelocidad(texturaVelocidad, xPos, 480);
		            } else {
		                nuevoProyectil = new Moneda(texturaMoneda, xPos, 480); 
		            }
		        }
		        
		    } else {
		        if (MathUtils.random(1, 10) < 5) { 
		            nuevoProyectil = new BolaFuego(texturaBolaFuego, xPos, 480);
		        } else { 
		            nuevoProyectil = new Moneda(texturaMoneda, xPos, 480);
		        }
		    }
		    
		    if (nuevoProyectil != null) {
		        proyectiles.add(nuevoProyectil);
		    }
		    ultimoProyectilTiempo = TimeUtils.nanoTime();
		}
	
   public boolean actualizarMovimiento(Heroe heroe) { 
	    if (TimeUtils.nanoTime() - ultimoProyectilTiempo > 100000000) crearProyectil(heroe);

	    float delta = Gdx.graphics.getDeltaTime(); 

	    if (heroe.estaEnSlowMo()) {
	        delta = delta * 0.5f; 
	    }

	    for (int i = 0; i < proyectiles.size; i++) {
	        EntidadJuego proyectil = proyectiles.get(i);
	        
            /**
             * (GM2.2) Invocación del "Paso Variable" (Template Method).
             * Se llama al método abstracto 'actualizar', y Java ejecuta
             * la implementación concreta (Moneda, BolaFuego, etc.)
             * gracias al Polimorfismo.
             */
	        proyectil.actualizar(delta); 

	        if (proyectil.getHitbox().y + proyectil.getHitbox().height < 0) {
	            proyectiles.removeIndex(i);
	            i--; 
	            continue;
	        }

	        if (proyectil.getHitbox().overlaps(heroe.getHitbox())) {

	            if (proyectil instanceof BolaFuego) { 
	                if (!heroe.esInmune()) { 
	                    heroe.restarVida(1); 
	                    if (heroe.getVidas() <= 0) return false;
	                }
	            
	            } else if (proyectil instanceof BolaFuegoAzul) { 
	                if (!heroe.esInmune()) {
	                    heroe.restarVida(2); 
	                    if (heroe.getVidas() <= 0) return false;
	                }
	            
	            } else if (proyectil instanceof Moneda) { 
	                heroe.sumarPuntos(10); 
	                sonidoMoneda.play();
	            
	            } else if (proyectil instanceof PowerUpVida) {
	                heroe.sumarVida(1); 
	            
	            } else if (proyectil instanceof PowerUpInmunidad) {
	                heroe.activarInmunidad(); 
	            
	            } else if (proyectil instanceof PowerUpVelocidad) {
	                heroe.activarSlowMo();
	            }
	            
	            proyectiles.removeIndex(i);
	            i--; 
	        }
	    }
	    return true;
	}
   
   public void dibujarProyectiles(SpriteBatch batch) { 
	    for (EntidadJuego proyectil : proyectiles) {
            /**
             * (GM2.2) Invocación del "Paso Fijo" (Template Method).
             * Todas las entidades usan el mismo método 'dibujar' heredado
             * de la plantilla EntidadJuego.
             */
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
	public void activarFase2() {
	    this.fase2Activada = true;
	}

	public boolean isFase2() {
	    return this.fase2Activada;
	}
	public void limpiarProyectiles() {
	    proyectiles.clear();
	}
   
}