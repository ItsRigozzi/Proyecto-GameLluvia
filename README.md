# ⚔️ Dungeon Knight

![GitHub Language Count](https://img.shields.io/github/languages/count/TU_USUARIO/DungeonKnight)
![GitHub Top Language](https://img.shields.io/github/languages/top/TU_USUARIO/DungeonKnight)
![GitHub Last Commit](https://img.shields.com/github/last-commit/TU_USUARIO/DungeonKnight)

**Dungeon Knight** es un videojuego de supervivencia y recolección desarrollado en **Java** con la biblioteca **LibGDX** y el sistema de construcción **Gradle**. El proyecto fue diseñado priorizando una arquitectura limpia y escalable.

## 🕹️ El Juego: Desafío y Controles

El objetivo es obtener la puntuación más alta recolectando **Monedas de Oro** que caen del techo de una mazmorra, mientras se esquivan las peligrosas **Bolas de Fuego**. El Caballero comienza con 5 vidas.

| Elemento | Acción |
| :--- | :--- |
| **Monedas de Oro** | Recompensas: Suma puntos. |
| **Bolas de Fuego** | Peligro: Resta una vida. |

**Controles:**
* **Movimiento:** Usa las flechas $\leftarrow$ y $\rightarrow$ para mover al Caballero horizontalmente.
* **Interacción:** Clic o toque en la pantalla para iniciar o reiniciar el juego.

## ⚠️ Requisitos Técnicos y Ejecución

Para trabajar con este proyecto se requiere el siguiente entorno. **Es fundamental cumplir con la versión de Java:**

1.  **IDE:** NetBeans o Eclipse (con soporte para Gradle).
2.  **Java Development Kit (JDK) 17 o superior.**

> **IMPORTANTE sobre JDK:** La herramienta de construcción de dependencias de este proyecto exige explícitamente una **JVM de Java 17 o superior** para compilar. Asegúrate de que el IDE esté configurado para usar JDK 17.

### ▶️ Cómo Abrir y Ejecutar el Proyecto (Flujo de IDE)

Para ejecutar el juego en el entorno de desarrollo (NetBeans o Eclipse):

1.  **Descargar:** Clona o descarga el repositorio y descomprime la carpeta `DungeonKnight`.
2.  **Abrir Proyecto:** En el IDE, selecciona **File** (Archivo) $\rightarrow$ **Open Project** (Abrir Proyecto).
3.  **Seleccionar Carpeta:** Navega y selecciona la carpeta raíz del proyecto (`DungeonKnight`). El IDE reconocerá automáticamente el proyecto Gradle.
4.  **Ejecutar:** Navega en el árbol del proyecto hasta el módulo **`DungeonKnight:lwjgl3`**.
    * Clic derecho sobre el módulo **`DungeonKnight:lwjgl3`**
    * Selecciona **Run** (Ejecutar) $\rightarrow$ **Run Project** (Ejecutar Proyecto).
