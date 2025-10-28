package puppy.code;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

/**
 * REQUISITO GM1.4: Clase abstracta (Padre de Heroe y Moneda)
 * REQUISITO GM1.6: Encapsulamiento (protected)
 *
 * esta es la "plantilla padre para nuestros objetos (Heroe, Moneda, etc).
 * Aquí ponemos todo lo que tienen en común (imagen, hitbox) para no repetir código.
 */
public abstract class EntidadJuego {

    // Atributos comunes que Heroe y Moneda van a heredar.
    // 'protected' para que solo las clases hijas puedan usarlos. (Requisito GM1.6)
    protected Rectangle hitbox;
    protected Texture imagen;

    /**
     * Constructor base.
     * Las clases hijas (como Heroe) llaman a este "super()"
     * para crear su imagen y su hitbox al nacer.
     */
    public EntidadJuego(Texture img, float x, float y) {
        this.imagen = img;
        // Creamos el hitbox del tamaño exacto de la imagen
        this.hitbox = new Rectangle(x, y, img.getWidth(), img.getHeight());
    }

    /**
     * REGLA (GM1.4): Método abstracto.
     * Esto obliga a las clases hijas (Heroe, Moneda) a escribir su propio método 'actualizar'. Cada una se mueve distinto.
     * así que cada una define cómo hacerlo.
     */
    public abstract void actualizar(float delta);
    /**
     * Método normal (heredado).
     * Todas las hijas se dibujan igual (por ahora), así que
     * lo definimos aquí una sola vez y listo.
     */
    public void dibujar(SpriteBatch batch) {
        batch.draw(imagen, hitbox.x, hitbox.y);
    }
    
    // Getters.
    // Para que GameScreen pueda "preguntar" dónde está el hitbox
    // y así revisar las colisiones.
    
    public Rectangle getHitbox() { 
        return hitbox; 
    }
    
    public Texture getImagen() { 
        return imagen; 
    }
}