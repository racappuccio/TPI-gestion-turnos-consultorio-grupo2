-- Tabla doctor
CREATE TABLE doctor (
	id_doctor SERIAL PRIMARY KEY,
	nombre VARCHAR(50) NOT NULL,
	apellido VARCHAR(50) NOT NULL,
	dni VARCHAR(15) UNIQUE NOT NULL,
	cuil VARCHAR(15) UNIQUE NOT NULL,
	telefono VARCHAR(20),
	especialidad VARCHAR(50),
	email VARCHAR(100)
);

-- Tabla paciente
CREATE TABLE paciente (
	id_paciente SERIAL PRIMARY KEY,
	nombre VARCHAR(50) NOT NULL,
	apellido VARCHAR(50) NOT NULL,
	dni VARCHAR(15) UNIQUE NOT NULL,
	cuil VARCHAR(15) UNIQUE NOT NULL,
	telefono VARCHAR(20),
	email VARCHAR(100),
	obra_social VARCHAR(100),
	fecha_nacimiento DATE
);

--Tabla usuario
CREATE TABLE usuario (
    id_usuario SERIAL PRIMARY KEY,
    nombre VARCHAR(50) NOT NULL,
    tipo_rol VARCHAR(20) NOT NULL CHECK (tipo_rol IN ('secretaria', 'doctor', 'admin')),
    usuario VARCHAR(50) UNIQUE NOT NULL,
    contrasena VARCHAR(100) NOT NULL
);

-- Tabla turno
CREATE TABLE turno (
	id_turno SERIAL PRIMARY KEY,
	fecha DATE NOT NULL,
	hora TIME NOT NULL,
	motivo VARCHAR(200),
	estado VARCHAR(20) CHECK (estado IN ('pendiente', 'confirmado', 'cancelado', 'realizado')),
	fecha_creacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
	id_doctor INT NOT NULL,
	id_paciente INT NOT NULL,
	FOREIGN KEY (id_doctor) REFERENCES doctor(id_doctor),
	FOREIGN KEY (id_paciente) REFERENCES paciente(id_paciente)
);

-- Tabla historial de turnos
CREATE TABLE historial_turnos (
	id_historial SERIAL PRIMARY KEY,
	id_turno INT NOT NULL,
	id_usuario INT NOT NULL,
	fecha_accion TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
	observacion TEXT,
	accion VARCHAR(50),
	usuario_responsable VARCHAR(100),
	estado_anterior VARCHAR(20),
	estado_nuevo VARCHAR(20),
	motivo_cambio TEXT,
	FOREIGN KEY (id_turno) REFERENCES turno(id_turno),
	FOREIGN KEY (id_usuario) REFERENCES usuario(id_usuario)
);



-- Restricciones de fechas de nacimiento y turnos
alter table paciente
add constraint chk_fecha_nacimiento check (fecha_nacimiento < current_date);

alter table turno
add constraint chk_hora check (hora between '08:00' and '20:00');
alter table turno
add constraint chk_fecha_turno check (fecha >= current_date);

-- Para ver los turnos pendientes
create view turnos_pendientes as
select t.id_turno, d.nombre as doctor, p.nombre as paciente, t.fecha, t.hora
from turno t
join doctor d on t.id_doctor = d.id_doctor
join paciente p on t.id_paciente = p.id_paciente
where t.estado = 'pendiente';

-- Para registrar un nuevo turno automaticamente
create or replace function registrar_turno(
    id_doc INT,
    id_pac INT,
    f DATE,
    h TIME,
    mot TEXT
) returns void as $$
begin
    if exists (
        select 1 from turno 
        where id_doctor = id_doc and fecha = f and hora = h
    ) then
        raise exception 'El turno ya está ocupado en esa fecha y hora';
    else
        insert into turno (id_doctor, id_paciente, fecha, hora, motivo, estado)
        values (id_doc, id_pac, f, h, mot, 'pendiente');
    end if;
end;
$$ language plpgsql;
