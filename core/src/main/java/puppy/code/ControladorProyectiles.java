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
        private Texture texturaBolaFuegoAzul;
        private boolean fase2Activada = false;
        private Texture texturaCura;
        private Texture texturaEscudo;
        private Texture texturaVelocidad;
        private boolean estaEnSlowMo = false;
        private float tiempoSlowMo = 0f;
	   
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
	      // start the playback of the background music immediately
	      musicaFondo.setLooping(true);
	      musicaFondo.play();
	}
	
		private void crearProyectil(Heroe heroe) {
		    float xPos = MathUtils.random(0, 800 - 64);
		    EntidadJuego nuevoProyectil = null;
	
		    if (fase2Activada) {
		        int tipo = MathUtils.random(1, 20); // Total 20 (100%)

		        if (tipo <= 9) { // 45% Moneda
		            nuevoProyectil = new Moneda(texturaMoneda, xPos, 480);
		        } else if (tipo <= 12) { // 15% Fuego Normal
		            nuevoProyectil = new BolaFuego(texturaBolaFuego, xPos, 480);
		        } else if (tipo <= 15) { // 15% Fuego Azul
		            nuevoProyectil = new BolaFuegoAzul(texturaBolaFuegoAzul, xPos, 480);
		        } else if (tipo == 16 || tipo == 17) { // 10% Cura 
		            if (heroe != null && heroe.getVidas() < 5) {
		                nuevoProyectil = new PowerUpVida(texturaCura, xPos, 480);
		            } else {
		                nuevoProyectil = new Moneda(texturaMoneda, xPos, 480);
		            }
		        } else if (tipo == 18 || tipo == 19) { // 10% Escudo
		            
		            
		            // Regla 1: ¿El héroe NO es nulo Y YA es inmune?
		            if (heroe != null && heroe.esInmune()) {
		                nuevoProyectil = new Moneda(texturaMoneda, xPos, 480); // Dale una moneda
		            } else {
		                // Regla 2: ¿Hay demasiados escudos YA en pantalla? (La regla anterior)
		                int escudosActuales = 0;
		                for (EntidadJuego p : proyectiles) {
		                    if (p instanceof PowerUpInmunidad) escudosActuales++;
		                }
		                
		                if (escudosActuales < 2) {
		                    nuevoProyectil = new PowerUpInmunidad(texturaEscudo, xPos, 480); // Genera el escudo
		                } else {
		                    nuevoProyectil = new Moneda(texturaMoneda, xPos, 480); // Dale una moneda
		                }
		            }

		        } else { // 5% Velocidad (tipo == 20)
		            // (Esta ya tenía la restricción correcta)
		            if (!estaEnSlowMo) {
		                nuevoProyectil = new PowerUpVelocidad(texturaVelocidad, xPos, 480);
		            } else {
		                nuevoProyectil = new Moneda(texturaMoneda, xPos, 480); 
		            }
		        }
		        
		    } else {
		    	// Lógica de FASE 1 (sin cambios)
		        if (MathUtils.random(1, 10) < 5) { // 40% Fuego
		            nuevoProyectil = new BolaFuego(texturaBolaFuego, xPos, 480);
		        } else { // 60% Moneda
		            nuevoProyectil = new Moneda(texturaMoneda, xPos, 480);
		        }
		    }
		    
		    if (nuevoProyectil != null) {
		        proyectiles.add(nuevoProyectil);
		    }
		    ultimoProyectilTiempo = TimeUtils.nanoTime();
		}
	
   public boolean actualizarMovimiento(Heroe heroe) { 
	// Generar proyectiles
	    if (TimeUtils.nanoTime() - ultimoProyectilTiempo > 100000000) crearProyectil(heroe);

	    // --- INICIO LÓGICA SLOW-MO ---
	    float delta = Gdx.graphics.getDeltaTime(); // Tiempo normal

	    if (estaEnSlowMo) {
	        delta = delta * 0.5f; // ¡Ralentiza el tiempo a la mitad!
	        
	        tiempoSlowMo -= Gdx.graphics.getDeltaTime(); // El temporizador usa tiempo real
	        if (tiempoSlowMo <= 0) {
	            estaEnSlowMo = false;
	        }
	    }

	    // Revisar si los proyectiles cayeron o chocaron
	    for (int i = 0; i < proyectiles.size; i++) {
	        EntidadJuego proyectil = proyectiles.get(i);
	        
	        // 1. Mueve el proyectil (usando el 'delta' modificado)
	        proyectil.actualizar(delta); // <-- PASAMOS EL DELTA LENTO

	        // 2. Cae al suelo y se elimina
	        if (proyectil.getHitbox().y + proyectil.getHitbox().height < 0) {
	            proyectiles.removeIndex(i);
	            i--; 
	            continue;
	        }

	        // 3. Revisa colisión con el héroe
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
	                // (Decidiremos si sumar 10 o 20 en la Fase de "Doble Puntos")
	                heroe.sumarPuntos(10); 
	                sonidoMoneda.play();
	            
	            } else if (proyectil instanceof PowerUpVida) {
	                heroe.sumarVida(1); 
	            
	            } else if (proyectil instanceof PowerUpInmunidad) {
	                heroe.activarInmunidad(); 
	            
	            } else if (proyectil instanceof PowerUpVelocidad) {
	                estaEnSlowMo = true;
	                tiempoSlowMo = 7.0f; // 7 segundos de slow-mo
	                // (Añadir sonido de "slow-mo" aquí si quieres)
	            }
	            

	            proyectiles.removeIndex(i);
	            i--; 
	        }
	    }
	    return true;
	}
   
   public void dibujarProyectiles(SpriteBatch batch) { 
         //Dibujo polimorfico: Ya no necesitamos saber si es Moneda o Bola de Fuego,
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
	// Método para que GameScreen nos avise que la Fase 2 comenzó
	public void activarFase2() {
	    this.fase2Activada = true;
	}

	// Método para que GameScreen sepa si estamos en Fase 2
	public boolean isFase2() {
	    return this.fase2Activada;
	}
	public void limpiarProyectiles() {
	    proyectiles.clear();
	}
   
}
