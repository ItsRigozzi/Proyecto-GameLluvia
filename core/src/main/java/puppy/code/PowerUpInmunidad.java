package puppy.code;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;

/**
 * REQUISITO GM2.2: Aplicación del Patrón de Diseño Template Method (Clase Concreta).
 * * * * Esta clase es una "Clase Concreta" que hereda de la plantilla abstracta EntidadJuego.
 * * Implementa los pasos variables del algoritmo definidos en la clase padre.
 */
public class PowerUpInmunidad extends EntidadJuego implements Colisionable {
    
    private static final float VELOCIDAD_CAIDA = 150; 
    
    public PowerUpInmunidad(Texture img, float x, float y) {
        super(img, x, y);
    }

    /**
     * (GM2.2) Implementación del "Paso Variable" del Template Method.
     * *
     * * Define la lógica específica de movimiento para el PowerUp de Inmunidad (caída lenta),
     * * completando así el algoritmo de actualización definido en la plantilla.
     */
    @Override
    public void actualizar(float delta) {
        hitbox.y -= VELOCIDAD_CAIDA * delta;
    }

    @Override
    public boolean colisionaCon(Rectangle otro) {
        return this.hitbox.overlaps(otro);
    }
}