package puppy.code;

import com.badlogic.gdx.math.Rectangle;

/**
 * REQUISITO GM1.5: Interfaz (Contrato de colisión)
 */
public interface Colisionable {
    boolean colisionaCon(Rectangle otro);
}
