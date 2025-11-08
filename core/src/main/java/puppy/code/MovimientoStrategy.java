package puppy.code;

/**
 * REQUISITO GM2.3: Aplicación del Patrón de Diseño Strategy (Interfaz Strategy).
 * * * * Esta interfaz define el "contrato" o la operación común (el método mover)
 * * que todas las estrategias de movimiento concretas (MoverConFlechas, MoverConWASD)
 * * deben implementar.
 */
public interface MovimientoStrategy {
    
    /**
     * (GM2.3) Este es el método Strategy.
     * Define la firma del algoritmo que será implementado por
     * las clases concretas.
     *
     * @param heroe El Contexto (Heroe) que se va a mover.
     * @param delta El tiempo transcurrido desde el último frame.
     */
    void mover(Heroe heroe, float delta);
    
}