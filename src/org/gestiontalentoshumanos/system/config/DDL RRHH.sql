drop database if exists RRHH_gestion_talento_humano_in4av;
create database RRHH_gestion_talento_humano_in4av;
use RRHH_gestion_talento_humano_in4av;

-- =========================================================
-- TABLA ROLES
-- =========================================================
create table roles(
    rol_id int not null auto_increment,
    primary key(rol_id),
    nombre_rol varchar(50) not null
);

-- CREATE
delimiter $$
create procedure sp_create_roles(in p_nombre_rol varchar(50))
begin
    insert into roles(nombre_rol)
    values(p_nombre_rol);
end $$
delimiter ;

-- READ
delimiter $$
create procedure sp_read_roles()
begin
    select
        rol_id as `ID del rol`,
        nombre_rol as `Nombre del rol`
    from roles;
end $$
delimiter ;

-- UPDATE
delimiter $$
create procedure sp_update_roles(in p_rol_id int,
                                  in p_nombre_rol varchar(50))
begin
    update roles set
        nombre_rol = p_nombre_rol
    where rol_id = p_rol_id;
end $$
delimiter ;

-- DELETE
delimiter $$
create procedure sp_delete_roles(in p_rol_id int)
begin
    delete from roles
    where rol_id = p_rol_id;
end $$
delimiter ;

-- SEARCH
delimiter $$
create procedure sp_search_roles(in p_rol_id int)
begin
    select
        rol_id as `ID del rol`,
        nombre_rol as `Nombre del rol`
    from roles
    where rol_id = p_rol_id;
end $$
delimiter ;


-- =========================================================
-- TABLA DEPARTAMENTOS
-- =========================================================
create table departamentos(
    departamento_id int auto_increment primary key,
    nombre_departamento varchar(100) not null
);

-- CREATE
delimiter $$
create procedure sp_create_departamentos(in p_nombre_departamento varchar(100))
begin
    insert into departamentos(nombre_departamento)
    values(p_nombre_departamento);
end $$
delimiter ;

-- READ
delimiter $$
create procedure sp_read_departamentos()
begin
    select
        departamento_id as `ID del departamento`,
        nombre_departamento as `Nombre del departamento`
    from departamentos;
end $$
delimiter ;

-- UPDATE
delimiter $$
create procedure sp_update_departamentos(in p_departamento_id int,
                                  in p_nombre_departamento varchar(100))
begin
    update departamentos set
        nombre_departamento = p_nombre_departamento
    where departamento_id = p_departamento_id;
end $$
delimiter ;

-- DELETE
delimiter $$
create procedure sp_delete_departamentos(in p_departamento_id int)
begin
    delete from departamentos
    where departamento_id = p_departamento_id;
end $$
delimiter ;

-- SEARCH
delimiter $$
create procedure sp_search_departamentos(in p_departamento_id int)
begin
    select
        departamento_id as `ID del departamento`,
        nombre_departamento as `Nombre del departamento`
    from departamentos
    where departamento_id = p_departamento_id;
end $$
delimiter ;


-- =========================================================
-- TABLA PUESTOS
-- =========================================================
create table puestos(
    puesto_id int auto_increment primary key,
    titulo_puesto varchar(100) not null
);

-- CREATE
delimiter $$
create procedure sp_create_puestos(in p_titulo_puesto varchar(100))
begin
    insert into puestos(titulo_puesto)
    values(p_titulo_puesto);
end $$
delimiter ;

-- READ
delimiter $$
create procedure sp_read_puestos()
begin
    select
        puesto_id as `ID del puesto`,
        titulo_puesto as `Puesto`
    from puestos;
end $$
delimiter ;

-- UPDATE
delimiter $$
create procedure sp_update_puestos(in p_puesto_id int,
                                  in p_titulo_puesto varchar(100))
begin
    update puestos set
        titulo_puesto = p_titulo_puesto
    where puesto_id = p_puesto_id;
end $$
delimiter ;

-- DELETE
delimiter $$
create procedure sp_delete_puestos(in p_puesto_id int)
begin
    delete from puestos
    where puesto_id = p_puesto_id;
end $$
delimiter ;

-- SEARCH
delimiter $$
create procedure sp_search_puestos(in p_puesto_id int)
begin
    select
        puesto_id as `ID del puesto`,
        titulo_puesto as `Puesto`
    from puestos
    where puesto_id = p_puesto_id;
end $$
delimiter ;


-- =========================================================
-- TABLA EMPLEADOS
-- =========================================================
create table empleados(
    empleado_id varchar(40) primary key,
    nombre_completo varchar(150) not null,
    puesto_id int not null,
    departamento_id int not null,
    fecha_contratacion date not null,
    salario_base decimal(10, 2) not null,
    foreign key (puesto_id) references puestos(puesto_id),
    foreign key (departamento_id) references departamentos(departamento_id)
);

-- CREATE
delimiter $$
create procedure sp_create_empleados(in p_nombre_completo varchar(150),
                                  in p_puesto_id int,
                                  in p_departamento_id int,
                                  in p_fecha_contratacion date,
                                  in p_salario_base decimal(10, 2))
begin
    insert into empleados(empleado_id, nombre_completo, puesto_id, departamento_id, fecha_contratacion, salario_base)
    values(uuid(), p_nombre_completo, p_puesto_id, p_departamento_id, p_fecha_contratacion, p_salario_base);
end $$
delimiter ;

-- READ (con inner join para mostrar nombre de puesto y departamento)
delimiter $$
create procedure sp_read_empleados()
begin
    select
        e.empleado_id as `ID Empleado`,
        e.nombre_completo as `Nombre Completo`,
        p.titulo_puesto as `Puesto`,
        d.nombre_departamento as `Departamento`,
        e.fecha_contratacion as `Fecha Contratación`,
        e.salario_base as `Salario Base`
    from empleados e
    inner join puestos p on e.puesto_id = p.puesto_id
    inner join departamentos d on e.departamento_id = d.departamento_id;
end $$
delimiter ;

-- UPDATE completo (uso exclusivo de Administrador)
delimiter $$
create procedure sp_update_empleados(in p_empleado_id varchar(40),
                                  in p_nombre_completo varchar(150),
                                  in p_puesto_id int,
                                  in p_departamento_id int,
                                  in p_fecha_contratacion date,
                                  in p_salario_base decimal(10, 2))
begin
    update empleados set
        nombre_completo = p_nombre_completo,
        puesto_id = p_puesto_id,
        departamento_id = p_departamento_id,
        fecha_contratacion = p_fecha_contratacion,
        salario_base = p_salario_base
    where empleado_id = p_empleado_id;
end $$
delimiter ;

-- UPDATE de datos SIN salario (uso exclusivo de Recursos Humanos)
delimiter $$
create procedure sp_update_datos_empleados(in p_empleado_id varchar(40),
                                  in p_nombre_completo varchar(150),
                                  in p_puesto_id int,
                                  in p_departamento_id int,
                                  in p_fecha_contratacion date)
begin
    update empleados set
        nombre_completo = p_nombre_completo,
        puesto_id = p_puesto_id,
        departamento_id = p_departamento_id,
        fecha_contratacion = p_fecha_contratacion
    where empleado_id = p_empleado_id;
end $$
delimiter ;

-- UPDATE SOLO salario (uso exclusivo de Nómina)
delimiter $$
create procedure sp_update_salario_empleados(in p_empleado_id varchar(40),
                                  in p_salario_base decimal(10, 2))
begin
    update empleados set
        salario_base = p_salario_base
    where empleado_id = p_empleado_id;
end $$
delimiter ;

-- DELETE
delimiter $$
create procedure sp_delete_empleados(in p_empleado_id varchar(40))
begin
    delete from empleados
    where empleado_id = p_empleado_id;
end $$
delimiter ;

-- SEARCH (con inner join también)
delimiter $$
create procedure sp_search_empleados(in p_empleado_id varchar(40))
begin
    select
        e.empleado_id as `ID Empleado`,
        e.nombre_completo as `Nombre Completo`,
        p.titulo_puesto as `Puesto`,
        d.nombre_departamento as `Departamento`,
        e.fecha_contratacion as `Fecha Contratación`,
        e.salario_base as `Salario Base`
    from empleados e
    inner join puestos p on e.puesto_id = p.puesto_id
    inner join departamentos d on e.departamento_id = d.departamento_id
    where e.empleado_id = p_empleado_id;
end $$
delimiter ;


-- =========================================================
-- TABLA USUARIOS
-- =========================================================
create table usuarios(
    usuario_id int auto_increment primary key,
    username varchar(50) not null unique,
    password_hash varchar(255) not null,
    rol_id int not null,
    empleado_id varchar(40),
    foreign key (rol_id) references roles(rol_id),
    foreign key (empleado_id) references empleados(empleado_id) on delete set null
);

-- CREATE
delimiter $$
create procedure sp_create_usuarios(in p_username varchar(50),
                                  in p_password_hash varchar(255),
                                  in p_rol_id int,
                                  in p_empleado_id varchar(40))
begin
    insert into usuarios(username, password_hash, rol_id, empleado_id)
    values(p_username, p_password_hash, p_rol_id, p_empleado_id);
end $$
delimiter ;

-- READ (inner join con roles, left join con empleados porque puede ser NULL)
delimiter $$
create procedure sp_read_usuarios()
begin
    select
        u.usuario_id as ID,
        u.username as `Nombre de Usuario`,
        u.password_hash as Hash,
        r.nombre_rol as Rol,
        e.nombre_completo as `Empleado Asignado`
    from usuarios u
    inner join roles r on u.rol_id = r.rol_id
    left join empleados e on u.empleado_id = e.empleado_id;
end $$
delimiter ;

-- UPDATE
delimiter $$
create procedure sp_update_usuarios(in p_usuario_id int,
                                  in p_username varchar(50),
                                  in p_password_hash varchar(255),
                                  in p_rol_id int,
                                  in p_empleado_id varchar(40))
begin
    update usuarios
    set
        username = p_username,
        password_hash = p_password_hash,
        rol_id = p_rol_id,
        empleado_id = p_empleado_id
    where usuario_id = p_usuario_id;
end $$
delimiter ;

-- DELETE
delimiter $$
create procedure sp_delete_usuarios(in p_usuario_id int)
begin
    delete from usuarios
    where usuario_id = p_usuario_id;
end $$
delimiter ;

-- SEARCH (útil para el login: valida username y trae el rol de una vez)
delimiter $$
create procedure sp_search_usuarios(in p_username varchar(50))
begin
    select
        u.usuario_id as ID,
        u.username as `Nombre de Usuario`,
        u.password_hash as Hash,
        r.nombre_rol as Rol,
        e.nombre_completo as `Empleado Asignado`
    from usuarios u
    inner join roles r on u.rol_id = r.rol_id
    left join empleados e on u.empleado_id = e.empleado_id
    where u.username = p_username;
end $$
delimiter ;


-- =========================================================
-- TABLA NOMINAS
-- =========================================================
create table nominas(
    nomina_id int auto_increment primary key,
    empleado_id varchar(40) not null,
    fecha_pago date not null,
    monto_total decimal(10, 2) not null,
    estado varchar(20) default 'Pendiente',
    foreign key (empleado_id) references empleados(empleado_id)
);

-- CREATE
delimiter $$
create procedure sp_create_nominas(in p_empleado_id varchar(40),
                                  in p_fecha_pago date,
                                  in p_monto_total decimal(10, 2),
                                  in p_estado varchar(20))
begin
    insert into nominas(empleado_id, fecha_pago, monto_total, estado)
    values(p_empleado_id, p_fecha_pago, p_monto_total, p_estado);
end $$
delimiter ;

-- READ (inner join para mostrar el nombre del colaborador)
delimiter $$
create procedure sp_read_nominas()
begin
    select
        n.nomina_id as ID,
        e.nombre_completo as Empleado,
        n.fecha_pago as `Fecha de Pago`,
        n.monto_total as `Monto Total`,
        n.estado as Estado
    from nominas n
    inner join empleados e on n.empleado_id = e.empleado_id;
end $$
delimiter ;

-- UPDATE
delimiter $$
create procedure sp_update_nominas(in p_nomina_id int,
                                  in p_empleado_id varchar(40),
                                  in p_fecha_pago date,
                                  in p_monto_total decimal(10, 2),
                                  in p_estado varchar(20))
begin
    update nominas
    set
        empleado_id = p_empleado_id,
        fecha_pago = p_fecha_pago,
        monto_total = p_monto_total,
        estado = p_estado
    where nomina_id = p_nomina_id;
end $$
delimiter ;

-- DELETE
delimiter $$
create procedure sp_delete_nominas(in p_nomina_id int)
begin
    delete from nominas
    where nomina_id = p_nomina_id;
end $$
delimiter ;

-- SEARCH (inner join también)
delimiter $$
create procedure sp_search_nominas(in p_nomina_id int)
begin
    select
        n.nomina_id as ID,
        e.nombre_completo as Empleado,
        n.fecha_pago as `Fecha de Pago`,
        n.monto_total as `Monto Total`,
        n.estado as Estado
    from nominas n
    inner join empleados e on n.empleado_id = e.empleado_id
    where n.nomina_id = p_nomina_id;
end $$
delimiter ;