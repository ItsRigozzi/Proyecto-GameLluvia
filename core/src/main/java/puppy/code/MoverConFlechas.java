package puppy.code;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;

/**
 * REQUISITO GM2.3: Aplicación del Patrón de Diseño Strategy (Estrategia Concreta).
 * * * * Esta clase es una "Estrategia Concreta" que implementa la interfaz MovimientoStrategy.
 * * Define el algoritmo específico para mover al Héroe usando las teclas de flecha.
 */
public class MoverConFlechas implements MovimientoStrategy {

    /**
     * (GM2.3) Implementación del método de la interfaz Strategy.
     * Contiene la lógica exacta para el movimiento con flechas.
     * @param heroe El Contexto (Heroe) que se va a mover.
     * @param delta El tiempo transcurrido.
     */
    @Override
    public void mover(Heroe heroe, float delta) {
        
        // Lógica de movimiento horizontal
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            heroe.hitbox.x -= 400 * delta;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            heroe.hitbox.x += 400 * delta;
        }

        // Lógica de movimiento vertical (revisa el estado del Héroe)
        if (heroe.isPuedeMoverseVertical()) {
            if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
                heroe.hitbox.y += 400 * delta;
            }
            if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
                heroe.hitbox.y -= 400 * delta;
            }
        }
    }
}