package puppy.code;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

/**
 * REQUISITO GM1.4: Clase abstracta (Padre de Heroe, Moneda, etc.)
 * REQUISITO GM1.6: Encapsulamiento (protected)
 * REQUISITO GM2.2: Aplicación del Patrón de Diseño Template Method.
 * * * * Esta clase abstracta define la "plantilla" (Template Method) para todas
 * * las entidades del juego. Define el esqueleto del algoritmo (actualizar y dibujar)
 * * y deja los pasos variables (actualizar) como abstractos.
 */
public abstract class EntidadJuego {

    /**
     * (GM1.6) Atributos comunes (protected) para que las clases hijas
     * puedan acceder a ellos directamente.
     */
    protected Rectangle hitbox;
    protected Texture imagen;

    /**
     * Constructor base (plantilla) para todas las entidades.
     */
    public EntidadJuego(Texture img, float x, float y) {
        this.imagen = img;
        this.hitbox = new Rectangle(x, y, img.getWidth(), img.getHeight());
    }

    /**
     * REGLA (GM1.4 y GM2.2): Este es el "Paso Variable" del Patrón Template Method.
     * *
     * * Como método abstracto, obliga a las subclases (Heroe, Moneda, BolaFuego)
     * * a implementar su propia lógica de movimiento y comportamiento.
     */
    public abstract void actualizar(float delta);

    /**
     * REGLA (GM2.2): Este es el "Paso Fijo" (o método concreto) del Patrón Template Method.
     * *
     * * Todas las clases hijas heredan este método de dibujo, asegurando
     * * que todas se dibujen de la misma manera sin repetir código.
     */
    public void dibujar(SpriteBatch batch) {
        batch.draw(imagen, hitbox.x, hitbox.y);
    }
    
    /**
     * Getter público para que otras clases (Contexto) puedan
     * revisar la colisión respetando el encapsulamiento.
     */
    public Rectangle getHitbox() { 
        return hitbox; 
    }
    
    public Texture getImagen() { 
        return imagen; 
    }
}