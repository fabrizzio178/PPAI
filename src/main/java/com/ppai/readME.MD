## EL CODIGO QUE ESTA EN GESTOR YA ESTA LISTO PARA DESPLEGAR Y PARA SER CONSUMIDO POR JAVAFX
## SE DEBE CREAR DENTRO DEL DIRECTORIO VIEW UNA CLASE MONITOR Y UN MONITOR.FXML

| Método `GestorResultados`              | Qué hace                                                | Qué mostrarías en JavaFX                   |
| -------------------------------------- | ------------------------------------------------------- | ------------------------------------------ |
| `mostrarOrdenesDisponiblesComoTexto()` | Devuelve `List<String>` con info de órdenes disponibles | Mostrarlo en un `ComboBox` o `ListView`    |
| `buscarMotivosComoTexto()`             | Devuelve `List<String>` con los motivos                 | Mostrarlo en un `ListView` o `ChoiceBox`   |
| `tomarTipoMotivo(List<Integer>)`       | Guarda motivos por índices seleccionados                | Llamalo al presionar un botón              |
| `tomarComentario(List<String>)`        | Guarda comentarios (en el mismo orden)                  | Llamalo luego de ingresar comentarios      |
| `validarObservacionExistente(...)`     | Retorna `true/false`                                    | Muestra alertas o validaciones en pantalla |
| `getFechaHoraActual()`                 | Devuelve la fecha formateada actual                     | Mostrar en un `Label`                      |
| `buscarFueraServicioComoTexto()`       | Devuelve lista de sismógrafos fuera de servicio         | Mostrar en `ListView` o sección resumen    |
| `cerrarOrdenInspeccion()`              | Cierra la orden seleccionada                            | Sin parámetros, usar tras validación       |
| `ponerSismografoFueraDeServicio()`     | Marca el sismógrafo como fuera de servicio              | Llamado tras el cierre                     |

## Voy a dejar un codigo sugerido por IA en la clase monitor. No se si estara bien o mal. Tambien, voy a dejar un monitor.fxml
