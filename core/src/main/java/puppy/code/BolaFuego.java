package puppy.code;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;

/**
 * REQUISITO GM1.4: extends EntidadJuego (Implementación de Template Method GM2.2)
 * REQUISITO GM1.5: implements Colisionable
 * REQUISITO GM2.2: Aplicación del Patrón de Diseño Template Method (Clase Concreta).
 * * * * Esta clase implementa el método abstracto 'actualizar' de la plantilla EntidadJuego.
 */
public class BolaFuego extends EntidadJuego implements Colisionable {
    
    private static final float VELOCIDAD_CAIDA = 350; 
    
    public BolaFuego(Texture img, float x, float y) {
        super(img, x, y);
    }

    /**
     * (GM1.4 / GM2.2) Implementación del método abstracto 'actualizar'.
     * Define la lógica de movimiento específica para esta entidad.
     */
    @Override
    public void actualizar(float delta) {
        hitbox.y -= VELOCIDAD_CAIDA * delta;
    }

    /**
     * (GM1.5) Implementación del método de la interfaz Colisionable.
     */
    @Override
    public boolean colisionaCon(Rectangle otro) {
        return this.hitbox.overlaps(otro);
    }
}