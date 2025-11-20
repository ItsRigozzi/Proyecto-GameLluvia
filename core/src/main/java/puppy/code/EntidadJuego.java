package puppy.code;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

/**
 * REQUISITO GM2.2: Aplicación del Patrón de Diseño Template Method (Clase Abstracta).
 * * * * Esta clase define la "plantilla" (Template) para todas las entidades del juego.
 * * Establece la estructura del algoritmo con métodos concretos (pasos fijos)
 * * y métodos abstractos (pasos variables) que las subclases deben implementar.
 */
public abstract class EntidadJuego {

    /**
     * Atributos protegidos para que las clases concretas (subclases)
     * puedan acceder a ellos directamente al implementar los pasos variables.
     */
    protected Rectangle hitbox;
    protected Texture imagen;

    
    public EntidadJuego(Texture img, float x, float y) {
        this.imagen = img;
        this.hitbox = new Rectangle(x, y, img.getWidth(), img.getHeight());
    }

    /**
     * (GM2.2) "Paso Variable" (Primitive Operation) del Template Method.
     * *
     * * Como método abstracto, obliga a las subclases (Heroe, Moneda, BolaFuego)
     * * a implementar su propia lógica específica de movimiento y comportamiento.
     */
    public abstract void actualizar(float delta);

    /**
     * (GM2.2) "Paso Fijo" (Concrete Operation) del Template Method.
     * *
     * * Este comportamiento es común y heredado por todas las subclases, asegurando
     * * que la lógica de dibujado sea consistente y no se repita código.
     */
    public void dibujar(SpriteBatch batch) {
        batch.draw(imagen, hitbox.x, hitbox.y);
    }
    
    
    public Rectangle getHitbox() { 
        return hitbox; 
    }
    
    public Texture getImagen() { 
        return imagen; 
    }
}