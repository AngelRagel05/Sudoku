# Plataforma Web de Sudoku Interactivo

# Descripción General.

Desarrollar una plataforma web donde los usuarios puedan jugar Sudoku en diferentes niveles de dificultad, validar sus jugadas, guardar su progreso, solicitar pistas, ver estadísticas personales y acceder a rankings globales. Habrá un rol de administrador encargado de gestionar usuarios y crear nuevos retos.

---

## 2. Objetivos del Proyecto

| **ID** | **Objetivo SMART**                                                               | **Tipo**    | **Métrica**                     | **Fecha Límite** | **Responsable**     | **Estado**  |
| ------ | -------------------------------------------------------------------------------- | ----------- | ------------------------------ | ---------------- | ------------------- | ----------- |
| OBJ-01 | Implementar tablero Sudoku con validación instantánea en 2 meses.                 | Estratégico | % funcionalidades implementadas | 2025-07-01       | Equipo de Desarrollo | No iniciado |
| OBJ-02 | Permitir guardar y cargar progreso en 100% de partidas antes de 3 meses.         | Operativo   | % partidas con guardado         | 2025-07-15       | Equipo de Desarrollo | No iniciado |
| OBJ-03 | Integrar sistema de pistas con límite de uso y feedback claro para usuario.      | Táctico     | % usuarios que usan pistas      | 2025-07-30       | Equipo de Desarrollo | En progreso |
| OBJ-04 | Desarrollar sistema de rankings con actualización diaria.                        | Estratégico | Frecuencia actualización        | 2025-08-15       | Equipo de Producto   | No iniciado |
| OBJ-05 | Implementar panel de administración para gestión de usuarios y retos en 2 meses. | Operativo   | Funcionalidades panel admin     | 2025-07-30       | Equipo de Desarrollo | En progreso |

## 3. Requisitos Funcionales

| **ID** | **Descripción**                                                                                 | **Prioridad** | **Fuente**          | **Estado** |
| ------ | ----------------------------------------------------------------------------------------------- | ------------- | ------------------- | ---------- |
| RF-01  | Generar tablero Sudoku válido con nivel de dificultad seleccionado.                             | Alta          | Análisis interno    | Propuesto  |
| RF-02  | Validar jugadas en tiempo real y mostrar errores o aciertos.                                   | Alta          | Análisis interno    | Propuesto  |
| RF-03  | Guardar y cargar el progreso de partidas para usuarios autenticados.                           | Alta          | Análisis interno    | Propuesto  |
| RF-04  | Permitir solicitar pistas limitadas por partida.                                              | Media         | Análisis interno    | Propuesto  |
| RF-05  | Mostrar estadísticas personales: partidas jugadas, completadas, tiempos promedio.             | Media         | Análisis interno    | Propuesto  |
| RF-06  | Mostrar rankings globales actualizados diariamente.                                           | Media         | Análisis interno    | Propuesto  |
| RF-07  | Gestión de usuarios: registro, inicio sesión, roles (jugador, administrador).                  | Alta          | Análisis interno    | Propuesto  |
| RF-08  | Panel administrador para crear, modificar y eliminar retos Sudoku.                            | Alta          | Análisis interno    | Propuesto  |
| RF-09  | Sistema de notificaciones para alertar al usuario sobre logros o recordatorios.               | Media         | Análisis interno    | Propuesto  |
| RF-10  | Soporte para múltiples dispositivos (responsive design).                                      | Alta          | Análisis interno    | Propuesto  |

## 4. Requisitos No Funcionales

| **ID** | **Descripción**                                          | **Categoría**  | **Métrica**                      | **Nivel Objetivo**   | **Comentarios**                         |
| ------ | -------------------------------------------------------- | -------------- | -------------------------------- | -------------------- | -------------------------------------- |
| RNF-01 | Tiempo de respuesta al validar jugada < 150 ms          | Rendimiento    | Latencia                         | < 150 ms             | Medición con pruebas de carga.          |
| RNF-02 | Disponibilidad del sistema ≥ 99,5 % mensual              | Disponibilidad | % Uptime                        | ≥ 99,5 %             | Monitorización continua y alertas.      |
| RNF-03 | Cumplir HTTPS/TLS para todas las comunicaciones          | Seguridad      | Cifrado                         | TLS 1.2+ obligatorio | Auditorías de seguridad periódicas.     |
| RNF-04 | Interfaz accesible y compatible con WCAG 2.1 AA          | Usabilidad     | Puntuación auditoría accesible  | Nivel AA             | Auditoría externa antes del lanzamiento.|
| RNF-05 | Soportar al menos 5 000 usuarios concurrentes            | Escalabilidad  | Usuarios concurrentes           | ≥ 5 000              | Autoescalado en la nube.                 |
| RNF-06 | Guardado automático cada 30 segundos                      | Rendimiento    | Frecuencia de guardado          | ≤ 30 seg             | Prevención de pérdida de datos.          |
| RNF-07 | Encriptación de datos sensibles en tránsito y reposo     | Seguridad      | Cifrado                        | AES-256 o equivalente | Protección avanzada de datos.            |
| RNF-08 | Compatibilidad con navegadores modernos y móviles        | Compatibilidad | Soporte                        | Chrome, Firefox, Safari, Edge, iOS, Android | Testing multiplataforma.        |

---

## 5. Diagramas UML

### 5.1 Diagrama de Casos de Uso

```mermaid
flowchart LR

 %% Actores
 JU(("👤 Jugador"))
 AD(("🛠️ Administrador"))

 %% Sistema
 subgraph "Sistema Sudoku"

  C1(("Generar tablero"))
  C2(("Validar jugada"))
  C3(("Guardar progreso"))
  C4(("Solicitar pista"))
  C5(("Ver estadísticas"))
  C6(("Gestionar usuarios"))
  C7(("Gestionar retos"))
  C8(("Ver ranking"))

 end

 JU --> C1
 JU --> C2
 JU --> C3
 JU --> C4
 JU --> C5
 JU --> C7
 JU --> C8

 AD --> C6
 AD --> C7
```

### 5.1 Diagrama de Interacción (Secuencia: Validar jugada y guardar progreso)
```mermaid
sequenceDiagram
 actor Jugador
 participant UI
 participant API
 participant DB

 Jugador->>UI: Ingresa número en celda
 UI->>API: POST /validar-jugada {fila, columna, valor}
 API->>DB: Consulta solución correcta
 DB-->>API: Valor correcto
 API-->>UI: Resultado validación (correcto/incorrecto)
 UI-->>Jugador: Muestra feedback en celda

 Jugador->>UI: Guarda progreso
 UI->>API: POST /guardar-progreso {estado-tablero}
 API->>DB: Guarda estado en base de datos
 DB-->>API: Confirmación guardado
 API-->>UI: Confirmación guardado
 UI-->>Jugador: Mensaje "Progreso guardado"
```

### 5.2 Diagrama de Interacción (Secuencia: Validar jugada y guardar progreso)
```mermaid
sequenceDiagram
 actor Jugador
 participant UI
 participant API
 participant DB

 Jugador->>UI: Ingresa número en celda
 UI->>API: POST /validar-jugada {fila, columna, valor}
 API->>DB: Consulta solución correcta
 DB-->>API: Valor correcto
 API-->>UI: Resultado validación (correcto/incorrecto)
 UI-->>Jugador: Muestra feedback en celda

 Jugador->>UI: Guarda progreso
 UI->>API: POST /guardar-progreso {estado-tablero}
 API->>DB: Guarda estado en base de datos
 DB-->>API: Confirmación guardado
 API-->>UI: Confirmación guardado
 UI-->>Jugador: Mensaje "Progreso guardado"

```

### 5.3 Diagrama de Estado (Ciclo de vida de una partida Sudoku)
``` mermaid
stateDiagram-v2
    [*] --> Nuevo

    Nuevo --> EnCurso : iniciarPartida()
    EnCurso --> Pausado : pausarPartida()
    Pausado --> EnCurso : reanudarPartida()
    EnCurso --> Finalizada : completarTablero()
    Finalizada --> [*]

    EnCurso --> Cancelada : abandonarPartida()
    Pausado --> Cancelada : abandonarPartida()
```

### 5.4 Diagrama de Actividad (Solicitar pista y continuar jugando)
``` mermaid
flowchart TD

 Inicio((●))
 SolicitarPista([Solicitar pista])
 MostrarPista([Mostrar celda correcta])
 ContinuarJugando([Continuar juego])
 Fin((◉))

 Inicio --> SolicitarPista
 SolicitarPista --> MostrarPista
 MostrarPista --> ContinuarJugando
 ContinuarJugando --> Fin
```

### 5.5 Diagrama de Clases UML
``` mermaid
classDiagram
    class Ejecutar {
        +main(String[] args): void
    }

    class JuegoSudoku {
        -sudoku: Sudoku
        -sc: Scanner
        -PrintStream: out

        +Iniciar(): void
        -mostrarTableroPersonalizado(): void
    }

    class Sudoku {
        +tablero: int[][]
        +celdasFijas: boolean[][] 

        +generarTablero(dificultad: String): void
        +esMovimientoValido(fila: int, columna: int, valor: int): boolean
        +colocarNumero(fila: int, columna: int, valor: int): void
        +estaResuelto(): void
        +mostrarTablero(): void
        +resolverInterno(): boolean
    }

    class SudokuCell {
        -numeroDefinitivo: integer
        -fijaoriginal: boolean
        -fijaUsuario: boolean

        +isFijaUsuario(): boolean
        +setceldaFija(fijaOriginal boolean, fijaUsuario boolean): void
        +setNumeroDefinitivo(num Integer): void
        +getNumeroDefinitivo(boolean correcto): Integer
    }

    class SudokuGUI {
        -sudoku: Sudoku
        -celdas: SudokuCell[][]
        -panelTablero: JPanel

        +actualizarTablero(): void
    }

    Ejecutar --> JuegoSudoku
    JuegoSudoku --> Sudoku
    JuegoSudoku --> SudokuGUI
    Sudoku --> SudokuCell
    SudokuGUI --> JuegoSudoku

```

## 6 Matriz de Trazabilidad Ampliada

### Requisitos Funcionales

| **ID Req.** | **Descripción del Requisito**                            | **Objetivo** | **Casos de Uso**         | **Clases / Métodos**                                                                                      | **Casos de Prueba**                                                                 |
|-------------|----------------------------------------------------------|--------------|--------------------------|------------------------------------------------------------------------------------------------------------|-------------------------------------------------------------------------------------|
| RF-01       | Generar tablero Sudoku válido con nivel de dificultad.   | OBJ-01       | Generar tablero          | `Sudoku.generarTablero()`, `SudokuCell`                                                                    | Verifica cantidad de celdas vacías por dificultad. Tablero generado válido.         |
| RF-02       | Validar jugadas en tiempo real y mostrar feedback.       | OBJ-01       | Validar jugada           | `Sudoku.esMovimientoValido()`, `Sudoku.colocarNumero()`                                                   | Validar entrada correcta/incorrecta. Prueba de valores fuera de rango.             |
| RF-03       | Guardar y cargar progreso.                               | OBJ-02       | Guardar progreso         | *[No aparece en las clases actuales — sugerencia de implementar]*                                         | Prueba de persistencia de estado.                                                  |
| RF-04       | Solicitar pistas limitadas.                              | OBJ-03       | Solicitar pista          | `Sudoku.resolverInterno()` (*parcial* uso como backend para pistas)                                       | Solicitud de pista devuelve celda válida.                                          |
| RF-05       | Mostrar estadísticas personales.                         | OBJ-04       | Ver estadísticas         | *[No implementado — se puede añadir una clase `Estadisticas`]*                                            | Número de partidas, tiempos, pistas usadas.                                        |
| RF-06       | Rankings globales.                                       | OBJ-04       | Ver ranking              | *[No implementado]*                                                                                        | Ordenar por puntuación o tiempo.                                                   |
| RF-07       | Gestión de usuarios.                                     | OBJ-05       | Gestionar usuarios       | *[No implementado — se sugiere clase `Usuario`]*                                                           | Registro, login, roles.                                                            |
| RF-08       | Panel administrador para retos.                          | OBJ-05       | Gestionar retos          | *[No implementado — se puede vincular a `SudokuGUI`]*                                                      | Crear, editar, eliminar retos.                                                     |
| RF-09       | Notificaciones de logros o recordatorios.                | OBJ-03       | N/A                      | *[No implementado — se sugiere un sistema de alertas con GUI]*                                             | Activación de logros, alertas visibles.                                            |
| RF-10       | Soporte responsive y multidispositivo.                   | OBJ-01       | N/A                      | `SudokuGUI`, uso de `JPanel`                                                                              | Verifica funcionalidad en pantallas diferentes.                                    |

---

### Requisitos No Funcionales

| **ID RNF** | **Descripción**                                             | **Requisitos Impactados**     | **Clases Relacionadas**                       | **Casos de Prueba**                                                                 |
|------------|-------------------------------------------------------------|-------------------------------|-----------------------------------------------|-------------------------------------------------------------------------------------|
| RNF-01     | Tiempo de respuesta < 150 ms                                | RF-02                         | `Sudoku`, `SudokuGUI`                         | Pruebas de latencia con entradas rápidas.                                          |
| RNF-02     | Disponibilidad ≥ 99,5 %                                     | RF-03, RF-06                  | *(Sugerido: arquitectura cloud / backend)*     | Simulación de interrupciones y recuperación.                                       |
| RNF-03     | Comunicación segura (HTTPS/TLS)                             | RF-07                         | *(Aplicaría en arquitectura cliente-servidor)*| Prueba con certificados y conexiones seguras.                                      |
| RNF-04     | Accesibilidad WCAG 2.1 AA                                   | RF-10                         | `SudokuGUI`                                   | Prueba con lectores de pantalla y navegación por teclado.                         |
| RNF-05     | 5.000 usuarios concurrentes                                 | Todos                         | *(Sugerido: escalar backend y base de datos)* | Test de estrés simultáneo.                                                         |
| RNF-06     | Guardado automático cada 30 seg.                            | RF-03                         | *(No implementado aún — clase persistencia)*  | Verificar guardado periódico del estado del tablero.                              |
| RNF-07     | Encriptación en tránsito y reposo                           | RF-07                         | *(No implementado)*                           | Verificación del cifrado de credenciales.                                          |
| RNF-08     | Compatibilidad navegadores y móviles                        | RF-10                         | `SudokuGUI`                                   | Ejecutar en diferentes navegadores/SO.                                             |
