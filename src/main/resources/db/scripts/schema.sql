
CREATE TABLE IF NOT EXISTS rol
(
    id              INTEGER PRIMARY KEY AUTOINCREMENT,
    descripcion_rol TEXT,
    nombre          TEXT NOT NULL
);

CREATE TABLE IF NOT EXISTS empleado
(
    id       INTEGER PRIMARY KEY AUTOINCREMENT,
    apellido TEXT    NOT NULL,
    nombre   TEXT    NOT NULL,
    mail     TEXT,
    telefono TEXT,
    rol_id   INTEGER NOT NULL,
    FOREIGN KEY (rol_id) REFERENCES rol (id)
);

CREATE TABLE IF NOT EXISTS estado
(
    id            INTEGER PRIMARY KEY AUTOINCREMENT,
    ambito        TEXT NOT NULL,
    nombre_estado TEXT NOT NULL
);

CREATE TABLE IF NOT EXISTS tipo_motivo
(
    id     INTEGER PRIMARY KEY AUTOINCREMENT,
    nombre TEXT NOT NULL
);

CREATE TABLE IF NOT EXISTS estacion_sismologica
(
    id                               INTEGER PRIMARY KEY AUTOINCREMENT,
    codigo_estacion                  INTEGER NOT NULL UNIQUE,
    documento_certificacion_adq      TEXT,
    fecha_solicitud_adquisicion      DATETIME,
    latitud                          REAL    NOT NULL,
    longitud                         REAL    NOT NULL,
    nombre                           TEXT    NOT NULL,
    numero_certificacion_adquisicion INTEGER
);

CREATE TABLE IF NOT EXISTS orden_inspeccion
(
    id                      INTEGER PRIMARY KEY AUTOINCREMENT,
    numero_orden            INTEGER NOT NULL UNIQUE,
    fecha_hora_finalizacion DATETIME,
    fecha_hora_cierre       DATETIME,
    fecha_hora_inicio       DATETIME,
    observacion_cierre      TEXT,
    tarea_asignada          TEXT,
    estacion_sismologica_id INTEGER NOT NULL,
    estado_id               INTEGER NOT NULL,
    empleado_id             INTEGER NOT NULL,
    FOREIGN KEY (estacion_sismologica_id) REFERENCES estacion_sismologica (id),
    FOREIGN KEY (estado_id) REFERENCES estado (id),
    FOREIGN KEY (empleado_id) REFERENCES empleado (id)
);

CREATE TABLE IF NOT EXISTS sismografo
(
    id                       INTEGER PRIMARY KEY AUTOINCREMENT,
    fecha_adquisicion        DATETIME,
    identificador_sismografo INTEGER NOT NULL UNIQUE,
    numero_serie             TEXT,
    serie_temporal           TEXT,
    modelo                   TEXT,
    reparacion               INTEGER DEFAULT 0,
    estacion_sismologica_id  INTEGER NOT NULL,
    estado_actual_id         INTEGER NOT NULL,
    FOREIGN KEY (estacion_sismologica_id) REFERENCES estacion_sismologica (id),
    FOREIGN KEY (estado_actual_id) REFERENCES estado (id)
);

CREATE TABLE IF NOT EXISTS cambio_estado
(
    id                INTEGER PRIMARY KEY AUTOINCREMENT,
    fecha_hora_fin    DATETIME,
    fecha_hora_inicio DATETIME NOT NULL,
    estado_id         INTEGER  NOT NULL,
    responsable_id    INTEGER  NOT NULL,
    sismografo_id     INTEGER  NOT NULL,
    FOREIGN KEY (estado_id) REFERENCES estado (id),
    FOREIGN KEY (responsable_id) REFERENCES empleado (id),
    FOREIGN KEY (sismografo_id) REFERENCES sismografo (id),
    CHECK (fecha_hora_fin IS NULL OR fecha_hora_fin >= fecha_hora_inicio)
);

CREATE TABLE IF NOT EXISTS motivo_fuera_servicio
(
    id               INTEGER PRIMARY KEY AUTOINCREMENT,
    descripcion      TEXT,
    tipo_motivo_id   INTEGER NOT NULL,
    cambio_estado_id INTEGER NOT NULL,
    FOREIGN KEY (tipo_motivo_id) REFERENCES tipo_motivo (id),
    FOREIGN KEY (cambio_estado_id) REFERENCES cambio_estado (id)
);

CREATE TABLE IF NOT EXISTS usuario
(
    id             INTEGER PRIMARY KEY AUTOINCREMENT,
    password       TEXT    NOT NULL,
    nombre_usuario TEXT    NOT NULL UNIQUE,
    ultimo_acceso  TEXT,
    habilitado     INTEGER DEFAULT 1,
    empleado_id    INTEGER NOT NULL,
    FOREIGN KEY (empleado_id) REFERENCES empleado (id)
);

CREATE TABLE IF NOT EXISTS sesion
(
    id                INTEGER PRIMARY KEY AUTOINCREMENT,
    fecha_hora_inicio DATETIME NOT NULL,
    fecha_hora_fin    DATETIME,
    usuario_id        INTEGER  NOT NULL,
    FOREIGN KEY (usuario_id) REFERENCES usuario (id)
);

CREATE INDEX idx_sismografo_estacion ON sismografo (estacion_sismologica_id);
CREATE INDEX idx_orden_empleado ON orden_inspeccion (empleado_id);
CREATE INDEX idx_cambio_estado_sismografo ON cambio_estado (sismografo_id);