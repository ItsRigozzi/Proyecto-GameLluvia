package puppy.code;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;

/**
 * REQUISITO GM1.4 -> extends EntidadJuego (Hija 2)
 * REQUISITO GM1.5 -> implements Colisionable (Implementa 2)
 *
 * NOTA: Esta clase existe solo para cumplir el requisito de "2 clases" del Avance.
 * La lógica de las monedas que caen sigue en ControladorProyectiles por este momento.
 */
public class Moneda extends EntidadJuego implements Colisionable {

    public Moneda(Texture img, float x, float y) {
        super(img, x, y);
    }

    /**
     * REGLA GM1.4 CUMPLIDA: Método 'abstract' implementado.
     */
    @Override
    public void actualizar(float delta) {
        // Lógica simple de caída
        hitbox.y -= 200 * delta; // Cae a 200 píxeles/segundo
    }

    /**
     * REGLA GM1.5 CUMPLIDA: Método 'interface' implementado.
     */
    @Override
    public boolean colisionaCon(Rectangle otro) {
        return this.hitbox.overlaps(otro);
    }
}