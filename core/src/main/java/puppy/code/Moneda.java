package puppy.code;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;

/**
 * REQUISITO GM1.4: extends EntidadJuego (Hija 2)
 * REQUISITO GM1.5: implements Colisionable (Implementa 2)
 * REQUISITO GM2.2: Aplicación del Patrón de Diseño Template Method (Clase Concreta).
 * * * * Esta clase implementa el método abstracto 'actualizar' de la plantilla EntidadJuego.
 */
public class Moneda extends EntidadJuego implements Colisionable {

    public Moneda(Texture img, float x, float y) {
        super(img, x, y);
    }

    /**
     * REGLA (GM1.4 / GM2.2) CUMPLIDA: Método 'abstract' implementado.
     * Esta es la implementación del "Paso Variable" del Template Method.
     */
    @Override
    public void actualizar(float delta) {
        hitbox.y -= 200 * delta; 
    }

    /**
     * REGLA GM1.5 CUMPLIDA: Método 'interface' implementado.
     */
    @Override
    public boolean colisionaCon(Rectangle otro) {
        return this.hitbox.overlaps(otro);
    }
}