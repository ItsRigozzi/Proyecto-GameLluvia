# ⚔️ Dungeon Knight

**Dungeon Knight** es un videojuego de supervivencia y recolección desarrollado en **Java** con la biblioteca **LibGDX**. El proyecto fue diseñado priorizando una arquitectura limpia y escalable utilizando patrones de diseño de POO (Singleton, Template Method y Strategy).

## 🕹️ El Juego: Desafío y Controles

El objetivo es obtener la puntuación más alta recolectando **Monedas de Oro** y sobreviviendo el mayor tiempo posible. El juego evoluciona a una **Fase 2** (con movimiento libre) al alcanzar los 50 puntos, introduciendo nuevos enemigos y *power-ups*.

| Elemento | Acción |
| :--- | :--- |
| **Monedas de Oro** | Recompensas: Suma 10 puntos. |
| **Bola de Fuego** | Peligro (Fase 1 y 2): Resta 1 vida. |
| **Bola de Fuego Azul** | Peligro (Fase 2): Resta 2 vidas. |
| **Power-Up: Cura** | Recompensa (Fase 2): Recupera 1 vida. |
| **Power-Up: Escudo** | Recompensa (Fase 2): Inmunidad temporal (5 seg). |
| **Power-Up: Velocidad** | Recompensa (Fase 2): Ralentiza todos los proyectiles (7 seg). |

**Controles:**
* **Movimiento:** Configurable en **Ajustes** (Flechas $\leftarrow \rightarrow \uparrow \downarrow$ o Teclas `W` `A` `S` `D`).
* **Pausa:** Tecla `ESC` durante el juego.
* **Interacción:** Clic en los botones de la UI.

## ⚠️ Requisitos Técnicos y Ejecución

Para trabajar con este proyecto se requiere el siguiente entorno.

1.  **IDE:** NetBeans (17+) o Eclipse (2023-09+).
2.  **Java Development Kit (JDK):** **JDK 11** (Obligatorio).

> **IMPORTANTE sobre JDK:** El proyecto está configurado y ha sido probado para ser 100% compatible con **JDK 11**, según los requisitos de evaluación. Asegúrate de que tu IDE esté configurado para usar un JDK 11.

## ▶️ Cómo Abrir y Ejecutar el Proyecto

### Opción 1: NetBeans

1.  **Descargar:** Clona o descarga el repositorio y descomprime la carpeta.
2.  **Abrir Proyecto:** Ve a **File** $\rightarrow$ **Open Project...**
3.  **Seleccionar Carpeta:** Navega dentro de la carpeta descargada y selecciona la carpeta **interna** llamada `DungeonKnight-master` (la que contiene el archivo `settings.gradle`).
4.  **Ejecutar:** En el panel "Projects", haz clic derecho en el submódulo **`DungeonKnight:lwjgl3`** y selecciona **Run**.

### Opción 2: Eclipse (Flujo Recomendado)

1.  **Importar:** Ve a **File** $\rightarrow$ **Import...**
2.  **Seleccionar Tipo:** Escribe "Gradle" en el filtro y selecciona **"Existing Gradle Project"**.
3.  **Seleccionar Carpeta:** En "Project root directory", haz clic en **Browse** y selecciona la carpeta **interna** `DungeonKnight-master` (la que contiene el archivo `settings.gradle`).
4.  **Finalizar:** Haz clic en **Finish**.
5.  **Ejecutar (Usando Gradle Tasks):**
    * Abre la pestaña **"Gradle Tasks"** (si no está visible, ve a **Window** $\rightarrow$ **Show View** $\rightarrow$ **Other...** $\rightarrow$ **Gradle** $\rightarrow$ **Gradle Tasks**).
    * Expande el proyecto raíz **`DungeonKnight-master`**.
    * Expande el módulo **`lwjgl3`** $\rightarrow$ carpeta **`application`**.
    * Haz doble clic en la tarea **`run`**.
