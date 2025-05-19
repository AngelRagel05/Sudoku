# Plataforma Web de Sudoku Interactivo

# Descripción General.

Desarrollar una plataforma web donde los usuarios puedan jugar Sudoku de distintos niveles de dificultad, guardar su progreso, acceder a estadísticas personales y compartir retos con otros usuarios. La plataforma deberá contar con generación automática de tableros, validación en tiempo real, pistas y un sistema de clasificación basado en tiempos y aciertos.

---

## 2. Objetivos del Proyecto

| **ID**  | **Objetivo SMART**                                                    | **Tipo**    | **Métrica**                         | **Fecha Límite** | **Responsable**          | **Estado**  |
| ------- | -------------------------------------------------------------------- | ----------- | ---------------------------------- | ---------------- | ------------------------ | ----------- |
| OBJ-01  | Implementar la generación automática de tableros Sudoku en 3 niveles (fácil, medio, difícil) en 2 meses. | Estratégico | % de tableros generados correctamente | 2025-07-31       | Equipo de Desarrollo     | No iniciado |
| OBJ-02  | Incorporar validación en tiempo real de las jugadas para el 100 % de los tableros antes del lanzamiento. | Estratégico | % de validaciones implementadas    | 2025-08-15       | Equipo QA                | En progreso |
| OBJ-03  | Permitir guardar y reanudar partidas para al menos el 80 % de los usuarios activos en 3 meses. | Táctico     | % de usuarios que usan guardado    | 2025-09-30       | Equipo Backend           | No iniciado |
| OBJ-04  | Crear sistema de pistas con máximo 3 pistas por partida para mejorar la experiencia de usuario. | Operativo   | Número de pistas usadas             | 2025-09-15       | Equipo UX/UI             | No iniciado |
| OBJ-05  | Implementar un ranking con tiempos y aciertos para motivar la competitividad. | Táctico     | Nº de partidas con ranking activado | 2025-10-01       | Equipo de Desarrollo     | No iniciado |
| OBJ-06  | Garantizar que el tiempo de carga inicial sea menor a 1 segundo en dispositivos móviles. | Operativo   | Tiempo medio de carga               | 2025-07-15       | Equipo de Infraestructura | En progreso |

---

## 3. Requisitos Funcionales

| **ID**  | **Descripción**                                                                                      | **Prioridad** | **Fuente**          | **Estado** |
| ------- | -------------------------------------------------------------------------------------------------- | ------------- | ------------------- | ---------- |
| RF-01   | Generar tableros Sudoku con solución única para 3 niveles de dificultad.                           | Alta          | Análisis de mercado | Propuesto  |
| RF-02   | Validar las entradas del usuario en tiempo real, mostrando errores y aciertos.                     | Alta          | Feedback usuario    | Propuesto  |
| RF-03   | Permitir guardar el progreso actual y reanudar la partida en cualquier momento.                   | Alta          | Requisitos internos | Propuesto  |
| RF-04   | Ofrecer opción de pedir pista que revele una celda correcta hasta 3 veces por partida.             | Media         | Requisitos internos | Propuesto  |
| RF-05   | Mostrar estadísticas personales de partidas jugadas, tiempo promedio y porcentaje de aciertos.    | Media         | Requisitos internos | Propuesto  |
| RF-06   | Implementar sistema de ranking público con tiempos de resolución y número de pistas usadas.        | Media         | Requisitos internos | Propuesto  |
| RF-07   | Permitir compartir retos personalizados con otros usuarios mediante enlace o código.              | Baja          | Requisitos opcionales | Propuesto  |
| RF-08   | Proveer interfaz intuitiva compatible con dispositivos móviles y escritorio.                       | Alta          | Estándares UX       | Propuesto  |

---

## 4. Requisitos No Funcionales

| **ID**  | **Descripción**                                                          | **Categoría**  | **Métrica**                  | **Nivel Objetivo**    | **Comentarios**                         |
| ------- | ------------------------------------------------------------------------ | -------------- | ---------------------------- | --------------------- | ------------------------------------- |
| RNF-01  | Tiempo de respuesta al validar una celda menor a 100 ms.                 | Rendimiento    | Latencia validación          | < 100 ms              | Evaluación en dispositivos móviles.  |
| RNF-02  | Soportar hasta 10 000 usuarios concurrentes jugando sin degradación.    | Escalabilidad  | Usuarios concurrentes        | ≥ 10 000              | Infraestructura en nube escalable.    |
| RNF-03  | Cargar la interfaz completa en menos de 1 segundo en conexiones 4G.      | Rendimiento    | Tiempo de carga              | ≤ 1 s                 | Optimización front-end y uso de CDN.  |
| RNF-04  | Cumplir con estándares de accesibilidad WCAG 2.1 nivel AA.               | Usabilidad     | Nivel de accesibilidad       | AA                    | Validación con herramientas externas.|
| RNF-05  | Proteger los datos personales y de progreso con cifrado AES-256.         | Seguridad      | Nivel de cifrado             | AES-256               | Protección en tránsito y almacenamiento. |
| RNF-06  | Garantizar disponibilidad del sistema ≥ 99,9 % mensual.                  | Disponibilidad | % uptime                    | ≥ 99,9 %              | Monitorización y alertas automáticas. |
| RNF-07  | Registro de auditoría de acciones críticas (inicio sesión, cambios en perfil). | Seguridad  | Integridad de logs           | 100 % integridad      | Uso de sistemas inmutables de logs.  |
| RNF-08  | Compatibilidad con navegadores modernos (Chrome, Firefox, Edge, Safari). | Usabilidad     | % usuarios con navegador soportado | ≥ 95 %             | Pruebas periódicas de compatibilidad.|
