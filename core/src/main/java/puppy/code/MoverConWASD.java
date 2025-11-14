package puppy.code;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;

/**
 * REQUISITO GM2.3: Aplicación del Patrón de Diseño Strategy (Estrategia Concreta).
 * * * * Esta clase es la segunda "Estrategia Concreta".
 * * Define el algoritmo específico para mover al Héroe usando las teclas WASD.
 */
public class MoverConWASD implements MovimientoStrategy {

    /**
     * (GM2.3) Implementación del método de la interfaz Strategy.
     * Contiene la lógica exacta para el movimiento con WASD.
     * @param heroe El Contexto (Heroe) que se va a mover.
     * @param delta El tiempo transcurrido.
     */
    @Override
    public void mover(Heroe heroe, float delta) {
        
        if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            heroe.hitbox.x -= 400 * delta;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.D)) {
            heroe.hitbox.x += 400 * delta;
        }

        if (heroe.isPuedeMoverseVertical()) {
            if (Gdx.input.isKeyPressed(Input.Keys.W)) {
                heroe.hitbox.y += 400 * delta;
            }
            if (Gdx.input.isKeyPressed(Input.Keys.S)) {
                heroe.hitbox.y -= 400 * delta;
            }
        }
    }
}