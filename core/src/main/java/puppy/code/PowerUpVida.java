package puppy.code;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;

/**
 * Power-up que da vida al héroe.
 */
public class PowerUpVida extends EntidadJuego implements Colisionable {
    
    // Cae un poco más lento que las monedas
    private static final float VELOCIDAD_CAIDA = 150; 
    
    public PowerUpVida(Texture img, float x, float y) {
        super(img, x, y);
    }

    @Override
    public void actualizar(float delta) {
        hitbox.y -= VELOCIDAD_CAIDA * delta;
    }

    @Override
    public boolean colisionaCon(Rectangle otro) {
        return this.hitbox.overlaps(otro);
    }
}
