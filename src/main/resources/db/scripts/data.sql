-- Estados para Órdenes de Inspección
DELETE FROM cambio_estado;

INSERT INTO cambio_estado (fecha_hora_fin, fecha_hora_inicio, estado_id, responsable_id, sismografo_id)  VALUES
    (NULL, '2000-1-1 00:10:00.00', 5, 1, 1),
    (NULL, '2000-1-2 00:20:00.00', 5, 2, 2),
    (NULL, '2000-1-3 00:30:00.00', 5, 3, 3)
    ;

UPDATE estacion_sismologica
SET fecha_solicitud_adquisicion = CASE
                  WHEN fecha_solicitud_adquisicion = '1/1/2000' THEN '2000/1/1'
                  WHEN fecha_solicitud_adquisicion = '1/2/2000' THEN '2000/1/2'
                  WHEN fecha_solicitud_adquisicion = '1/3/2000' THEN '2000/1/3'
                  ELSE fecha_solicitud_adquisicion
    END;

UPDATE estacion_sismologica
SET fecha_solicitud_adquisicion = strftime('%Y-%m-%d', fecha_solicitud_adquisicion);

DELETE FROM estacion_sismologica;

INSERT INTO estacion_sismologica (codigo_estacion, documento_certificacion_adq, fecha_solicitud_adquisicion, latitud, longitud, nombre, numero_certificacion_adquisicion)  VALUES
      (1, 'no se 1', '2000/1/1', 100.0, 150,'Estacion 1', 10),
      (2, 'no se 2', '2000/1/2', 200.0, 250,'Estacion 2', 20),
      (3, 'no se 3', '2000/1/3', 300.0, 350,'Estacion 3', 30)
      ;






