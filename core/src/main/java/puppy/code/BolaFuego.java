package puppy.code;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;

/**
 * REQUISITO GM1.4 -> extends EntidadJuego (Hija 3)
 * REQUISITO GM1.5 -> implements Colisionable (Implementa 3)
 */
public class BolaFuego extends EntidadJuego implements Colisionable {
    
    // La velocidad de caída es más rápida que la moneda (350 vs 200)
    private static final float VELOCIDAD_CAIDA = 350; 
    
    public BolaFuego(Texture img, float x, float y) {
        super(img, x, y);
    }

    /**
     * Define cómo se mueve la Bola de Fuego (cae rápidamente)
     */
    @Override
    public void actualizar(float delta) {
        // Mueve la Bola de Fuego hacia abajo
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