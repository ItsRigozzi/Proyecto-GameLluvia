package puppy.code;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;

/**
 * Power-up que ralentiza el tiempo (proyectiles) temporalmente.
 */
public class PowerUpVelocidad extends EntidadJuego implements Colisionable {
    
    // Cae a una velocidad media
    private static final float VELOCIDAD_CAIDA = 180; 
    
    public PowerUpVelocidad(Texture img, float x, float y) {
        super(img, x, y);
    }

    @Override
    public void actualizar(float delta) {
        // Se mueve usando el 'delta' (que será modificado por el controlador)
        hitbox.y -= VELOCIDAD_CAIDA * delta;
    }

    @Override
    public boolean colisionaCon(Rectangle otro) {
        return this.hitbox.overlaps(otro);
    }
}