package puppy.code;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;

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
    public boolean puedeMoverseVertical = false; 
    private Preferences prefs;
    private boolean estaBrillando = false;
    private float tiempoBrillo = 0;
    private boolean esInmune = false;
    private float tiempoInmunidad = 0f;
    private boolean estaEnSlowMo = false;
    private float tiempoSlowMo = 0f;
    
    private enum Estado {QUIETO_FRENTE, QUIETO_LADO, CAMINANDO_FRENTE, CAMINANDO_LADO}
    private Animation<TextureRegion> animCaminarLado;
    private Animation<TextureRegion> animCaminarFrente;
    private TextureRegion paradoLado;
    private TextureRegion paradoFrente;
    
    private Estado estadoActual = Estado.QUIETO_LADO;
    private float tiempoEstado; 
    private boolean mirandoDerecha = true;


    /**
     * (GM2.3) Almacena las instancias de las Estrategias Concretas
     * que el Héroe puede utilizar.
     */
    private final MovimientoStrategy stratFlechas = new MoverConFlechas();
    private final MovimientoStrategy stratWASD = new MoverConWASD();

    public Heroe(Texture paradoLadoTex, Texture paradoFrenteTex,Texture caminarLado1Tex, Texture caminarLado2Tex, Texture caminarFrente1Tex, Texture caminarFrente2Tex, Sound sonidoHerido) {
    	super(paradoLadoTex, Gdx.graphics.getWidth() / 2 - paradoLadoTex.getWidth() / 2, 20); 
        this.sonidoHerido = sonidoHerido;
        this.vidas = 5;
        this.puntos = 0;
        this.herido = false;
        this.prefs = Gdx.app.getPreferences("dungeonknight_settings");
        
        this.paradoLado = new TextureRegion(paradoLadoTex);
        this.paradoFrente = new TextureRegion(paradoFrenteTex);
        
        Array<TextureRegion> framesLado = new Array<TextureRegion>();
        framesLado.add(new TextureRegion(caminarLado1Tex));
        framesLado.add(new TextureRegion(caminarLado2Tex));
        animCaminarLado = new Animation<TextureRegion>(0.25f, framesLado, Animation.PlayMode.LOOP);

        Array<TextureRegion> framesFrente = new Array<TextureRegion>();
        framesFrente.add(new TextureRegion(caminarFrente1Tex));
        framesFrente.add(new TextureRegion(caminarFrente2Tex));
        animCaminarFrente = new Animation<TextureRegion>(0.25f, framesFrente, Animation.PlayMode.LOOP);

        
        this.tiempoEstado = 0f;
        
       }
    
    /**
     * (GM1.4 / GM2.2) Implementación del método abstracto de la plantilla.
     * En este caso, la lógica principal de 'actualizar' se delega
     * al método 'actualizarMovimiento'.
     */
    @Override
    public void actualizar(float delta) {
    }

    /**
     * (GM1.5) Implementación del método de la interfaz.
     */
    @Override
    public boolean colisionaCon(Rectangle otro) {
        return this.hitbox.overlaps(otro);
    }
    
    public void actualizarMovimiento() {
        
        int controlMode = prefs.getInteger("controlMode", 0);
        float delta = Gdx.graphics.getDeltaTime(); 

        /**
         * (GM2.3) Delegación de la Estrategia.
         * El Contexto (Heroe) no sabe "cómo" moverse, solo le pide
         * a la estrategia actual (stratFlechas o stratWASD) que ejecute
         * el algoritmo de movimiento correspondiente.
         */
        boolean estaMoviendoVertical = false;
        boolean estaMoviendoHorizontal = false;
        
        if (controlMode == 0) { 
            if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
                estaMoviendoHorizontal = true;
                mirandoDerecha = false;
            }
            if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
                estaMoviendoHorizontal = true;
                mirandoDerecha = true;
            }
            if (puedeMoverseVertical && (Gdx.input.isKeyPressed(Input.Keys.UP) || Gdx.input.isKeyPressed(Input.Keys.DOWN))) {
                estaMoviendoVertical = true;
            }
            
            stratFlechas.mover(this, delta); 
            
        } else { 
            if (Gdx.input.isKeyPressed(Input.Keys.A)) {
                estaMoviendoHorizontal = true;
                mirandoDerecha = false;
            }
            if (Gdx.input.isKeyPressed(Input.Keys.D)) {
                estaMoviendoHorizontal = true;
                mirandoDerecha = true;
            }
            if (puedeMoverseVertical && (Gdx.input.isKeyPressed(Input.Keys.W) || Gdx.input.isKeyPressed(Input.Keys.S))) {
                estaMoviendoVertical = true;
            }
            
            stratWASD.mover(this, delta); 
        }

        if (estaMoviendoVertical) {
            estadoActual = Estado.CAMINANDO_FRENTE;
            
        } else if (estaMoviendoHorizontal) {
            estadoActual = Estado.CAMINANDO_LADO;
            
        } else {
        	
            if (estadoActual == Estado.CAMINANDO_FRENTE || estadoActual == Estado.QUIETO_FRENTE) {
                estadoActual = Estado.QUIETO_FRENTE;
            } else {
                estadoActual = Estado.QUIETO_LADO;
            }
        }
        
        tiempoEstado += delta;
        

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

        TextureRegion currentFrame = null; 
        
        switch (estadoActual) {
            case QUIETO_FRENTE:
                currentFrame = paradoFrente;
                break;
            case QUIETO_LADO:
                currentFrame = paradoLado;
                break;
            case CAMINANDO_FRENTE:
                currentFrame = animCaminarFrente.getKeyFrame(tiempoEstado, true);
                break;
            case CAMINANDO_LADO:
                currentFrame = animCaminarLado.getKeyFrame(tiempoEstado, true);
                break;
        }

        if (mirandoDerecha && !currentFrame.isFlipX()) {
            currentFrame.flip(true, false);
        }
        if (!mirandoDerecha && currentFrame.isFlipX()) {
            currentFrame.flip(true, false);
        }
        
        
        
        if (estaBrillando) {
            batch.setColor(com.badlogic.gdx.graphics.Color.PINK);
            tiempoBrillo -= Gdx.graphics.getDeltaTime();
            if (tiempoBrillo <= 0) estaBrillando = false;
        
        } else if (esInmune) {
            float offset = 4.0f; 
            batch.setColor(1.0f, 1.0f, 0.0f, 1.0f); 

            batch.draw(currentFrame, hitbox.x - offset, hitbox.y, hitbox.width, hitbox.height); 
            batch.draw(currentFrame, hitbox.x + offset, hitbox.y, hitbox.width, hitbox.height); 
            batch.draw(currentFrame, hitbox.x, hitbox.y - offset, hitbox.width, hitbox.height); 
            batch.draw(currentFrame, hitbox.x, hitbox.y + offset, hitbox.width, hitbox.height); 

            batch.setColor(com.badlogic.gdx.graphics.Color.WHITE); 
            
            tiempoInmunidad -= Gdx.graphics.getDeltaTime();
            if (tiempoInmunidad <= 0) esInmune = false;
        }
        
        if (estaEnSlowMo) {
            tiempoSlowMo -= Gdx.graphics.getDeltaTime();
            if (tiempoSlowMo <= 0) estaEnSlowMo = false;
        }

        
        batch.draw(currentFrame, hitbox.x, hitbox.y, hitbox.width, hitbox.height);

        batch.setColor(com.badlogic.gdx.graphics.Color.WHITE);
    }
    
    public void activarInmunidad() {
        this.esInmune = true;
        this.tiempoInmunidad = 5.0f;
    }
    
    public boolean esInmune() {
        return this.esInmune;
    }
    
    public boolean isPuedeMoverseVertical() {
        return this.puedeMoverseVertical;
    }
    
    public void destruir() {
        sonidoHerido.dispose();
    }
    
    public void activarSlowMo() {
        this.estaEnSlowMo = true;
        this.tiempoSlowMo = 7.0f;
    }

    public boolean estaEnSlowMo() {
        return this.estaEnSlowMo;
    }

    public float getTiempoSlowMo() {
        return this.tiempoSlowMo;
    }

    public float getTiempoInmunidad() {
        return this.tiempoInmunidad;
    }
}