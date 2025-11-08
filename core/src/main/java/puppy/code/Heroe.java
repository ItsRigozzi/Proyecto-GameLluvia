package puppy.code;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.Preferences;

/**
 * REQUISITO GM1.4: extends EntidadJuego (Implementación de Template Method GM2.2)
 * REQUISITO GM1.5: implements Colisionable
 * REQUISITO GM2.3: Aplicación del Patrón de Diseño Strategy (Contexto).
 * * * * Esta clase (Heroe) actúa como el "Contexto" del patrón Strategy.
 * * Almacena una referencia a la interfaz (MovimientoStrategy) y
 * * delega la acción de "mover" a una implementación concreta (Flechas o WASD).
 */
public class Heroe extends EntidadJuego implements Colisionable {

    private Sound sonidoHerido;
    private int vidas;
    private int puntos;
    private boolean herido;
    public boolean puedeMoverseVertical = false; // Público para que las Estrategias lo vean
    private Preferences prefs;
    private boolean estaBrillando = false;
    private float tiempoBrillo = 0;
    private Texture texturaInmune;
    private boolean esInmune = false;
    private float tiempoInmunidad = 0f;

    /**
     * (GM2.3) Almacena las instancias de las Estrategias Concretas
     * que el Héroe puede utilizar.
     */
    private final MovimientoStrategy stratFlechas = new MoverConFlechas();
    private final MovimientoStrategy stratWASD = new MoverConWASD();

    public Heroe(Texture imagen, Texture texturaInmune, Sound sonidoHerido) {
        super(imagen, Gdx.graphics.getWidth() / 2 - imagen.getWidth() / 2, 20); 
        this.texturaInmune = texturaInmune;
        this.sonidoHerido = sonidoHerido;
        this.vidas = 5;
        this.puntos = 0;
        this.herido = false;
        this.prefs = Gdx.app.getPreferences("dungeonknight_settings");
    }
    
    /**
     * (GM1.4 / GM2.2) Implementación del método abstracto de la plantilla.
     * En este caso, la lógica principal de 'actualizar' se delega
     * al método 'actualizarMovimiento'.
     */
    @Override
    public void actualizar(float delta) {
        // La lógica de movimiento está en actualizarMovimiento()
    }

    /**
     * (GM1.5) Implementación del método de la interfaz.
     */
    @Override
    public boolean colisionaCon(Rectangle otro) {
        return this.hitbox.overlaps(otro);
    }
    
    public void actualizarMovimiento() {
        
    	// 1. Lee la preferencia guardada (0=Flechas, 1=WASD)
        int controlMode = prefs.getInteger("controlMode", 0);
        float delta = Gdx.graphics.getDeltaTime(); // Obtiene el tiempo

        /**
         * (GM2.3) Delegación de la Estrategia.
         * El Contexto (Heroe) no sabe "cómo" moverse, solo le pide
         * a la estrategia actual (stratFlechas o stratWASD) que ejecute
         * el algoritmo de movimiento correspondiente.
         */
        if (controlMode == 0) {
            stratFlechas.mover(this, delta); // Usa la estrategia de Flechas
        } else {
            stratWASD.mover(this, delta); // Usa la estrategia de WASD
        }

        // Lógica común (límites) que se aplica después de cualquier estrategia
        if (hitbox.x < 0) hitbox.x = 0;
        if (hitbox.x > 800 - hitbox.width) hitbox.x = 800 - hitbox.width;
        
        if (hitbox.y < 0) hitbox.y = 0;
        if (hitbox.y > 480 - hitbox.height) hitbox.y = 480 - hitbox.height;
    }

    public void permitirMovimientoVertical() {
        this.puedeMoverseVertical = true;
    }
    
    public int getVidas() { return vidas; }
    
    public void restarVida(int cantidad) { 
        this.vidas -= cantidad;
        this.herido = true;
        sonidoHerido.play();
    }
    
    public int getPuntos() { return puntos; }
    public void sumarPuntos(int cantidad) {
        this.puntos += cantidad;
    }
    
    public boolean estaHerido() { 
        if(this.herido) {
            this.herido = false; 
            return true;
        }
        return false;
    }
    private void iniciarBrillo(float duracion) {
        this.estaBrillando = true;
        this.tiempoBrillo = duracion;
    }
    
    public void sumarVida(int cantidad) {
    	if (this.vidas < 5) {
            this.vidas += cantidad;
            iniciarBrillo(0.5f);
       }
    }
    
    @Override
    public void dibujar(SpriteBatch batch) {
        if (estaBrillando) {
            batch.setColor(com.badlogic.gdx.graphics.Color.PINK);
            
            tiempoBrillo -= Gdx.graphics.getDeltaTime();
            if (tiempoBrillo <= 0) {
                estaBrillando = false;
            }
        }
        
        if (esInmune) {
            batch.draw(texturaInmune, hitbox.x, hitbox.y); 
            
            tiempoInmunidad -= Gdx.graphics.getDeltaTime();
            if (tiempoInmunidad <= 0) {
                esInmune = false;
            }
        } else {
            batch.draw(imagen, hitbox.x, hitbox.y); 
        }
        
        batch.setColor(com.badlogic.gdx.graphics.Color.WHITE);
    }
    public void activarInmunidad() {
        this.esInmune = true;
        this.tiempoInmunidad = 5.0f;
    }
    
    public boolean esInmune() {
        return this.esInmune;
    }
    
    /**
     * Getter público para que las Estrategias (MoverConFlechas)
     * puedan leer el estado de movimiento vertical.
     */
    public boolean isPuedeMoverseVertical() {
        return this.puedeMoverseVertical;
    }
    
    public void destruir() {
        sonidoHerido.dispose();
    }
}