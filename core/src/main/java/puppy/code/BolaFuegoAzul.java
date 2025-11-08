package puppy.code;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;

/**
 * Proyectil de Fase 2. Quita 2 vidas.
 */
public class BolaFuegoAzul extends EntidadJuego implements Colisionable {
    
    // Una velocidad diferente para que sea más amenazante
    private static final float VELOCIDAD_CAIDA = 380; 
    
    public BolaFuegoAzul(Texture img, float x, float y) {
        super(img, x, y);
    }

    /**
     * Define cómo se mueve la Bola de Fuego Azul
     */
    @Override
    public void actualizar(float delta) {
        hitbox.y -= VELOCIDAD_CAIDA * delta;
    }

    /**
     * Define la colisión.
     */
    @Override
    public boolean colisionaCon(Rectangle otro) {
        return this.hitbox.overlaps(otro);
    }
}