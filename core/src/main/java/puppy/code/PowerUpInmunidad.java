package puppy.code;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;

/**
 * Power-up que da inmunidad temporal al héroe.
 */
public class PowerUpInmunidad extends EntidadJuego implements Colisionable {
    
    private static final float VELOCIDAD_CAIDA = 150; 
    
    public PowerUpInmunidad(Texture img, float x, float y) {
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