package puppy.code;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * REQUISITO GM1.4 -> extends EntidadJuego (Esta es la Hija 1)
 * REQUISITO GM1.5 -> implements Colisionable (Esta es la Implementa 1)
 */
public class Heroe extends EntidadJuego implements Colisionable {

    private Sound sonidoHerido;
    private int vidas;
    private int puntos;
    private boolean herido;
    private boolean puedeMoverseVertical = false; 

    public Heroe(Texture imagen, Sound sonidoHerido) {
        super(imagen, Gdx.graphics.getWidth() / 2 - imagen.getWidth() / 2, 20); 
        this.sonidoHerido = sonidoHerido;
        this.vidas = 5;
        this.puntos = 0;
        this.herido = false;
    }
    
    /**
     * REGLA GM1.4 CUMPLIDA: Método 'abstract' implementado.
     */
    @Override
    public void actualizar(float delta) {
        // La lógica de movimiento está en actualizarMovimiento()
    }

    /**
     * REGLA GM1.5 CUMPLIDA: Método 'interface' implementado.
     */
    @Override
    public boolean colisionaCon(Rectangle otro) {
        return this.hitbox.overlaps(otro);
    }
    
    public void actualizarMovimiento() {
        // Movimiento Horizontal
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            hitbox.x -= 400 * Gdx.graphics.getDeltaTime();
        }
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            hitbox.x += 400 * Gdx.graphics.getDeltaTime();
        }

        // Movimiento Vertical (solo si puedeMoverseVertical es true)
        if (puedeMoverseVertical) {
            if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
                hitbox.y += 400 * Gdx.graphics.getDeltaTime();
            }
            if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
                hitbox.y -= 400 * Gdx.graphics.getDeltaTime();
            }
        }

        // Límites de pantalla
        if (hitbox.x < 0) hitbox.x = 0;
        if (hitbox.x > 800 - hitbox.width) hitbox.x = 800 - hitbox.width;
        // (Puedes añadir límites Y aquí si quieres)
    }

    public void permitirMovimientoVertical() {
        this.puedeMoverseVertical = true;
    }
    
    public int getVidas() { return vidas; }
    public void restarVida() { 
        this.vidas--; 
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
    
    public void destruir() {
        sonidoHerido.dispose();
    }
}

