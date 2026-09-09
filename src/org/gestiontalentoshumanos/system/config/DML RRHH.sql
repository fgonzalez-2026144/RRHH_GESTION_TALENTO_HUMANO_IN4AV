use RRHH_gestion_talento_humano_in4av;

-- =========================================================
-- 1) ROLES
-- =========================================================
call sp_create_roles('Administrador');
call sp_create_roles('Nomina');
call sp_create_roles('Recursos Humanos');
call sp_create_roles('Temporal');

call sp_read_roles();
call sp_search_roles(1);

call sp_update_roles(4, 'Supervisor');
call sp_read_roles();

call sp_delete_roles(4);
call sp_read_roles();


-- =========================================================
-- 2) DEPARTAMENTOS
-- =========================================================
call sp_create_departamentos('Recursos Humanos');
call sp_create_departamentos('Tecnología');
call sp_create_departamentos('Ventas');

call sp_read_departamentos();
call sp_search_departamentos(1);

call sp_update_departamentos(3, 'Ventas y Marketing');
call sp_read_departamentos();

call sp_delete_departamentos(3);
call sp_read_departamentos();


-- =========================================================
-- 3) PUESTOS
-- =========================================================
call sp_create_puestos('Analista de RRHH');
call sp_create_puestos('Desarrollador de Software');
call sp_create_puestos('Ejecutivo de Ventas');

call sp_read_puestos();
call sp_search_puestos(1);

call sp_update_puestos(2, 'Desarrollador Senior');
call sp_read_puestos();

call sp_delete_puestos(3);
call sp_read_puestos();


-- =========================================================
-- 4) EMPLEADOS
-- (usa puesto_id=1 y departamento_id=1, que sí existen)
-- =========================================================
call sp_create_empleados('Ana López', 1, 1, '2023-01-15', 8500.00);
call sp_create_empleados('Carlos Méndez', 2, 2, '2022-06-01', 12000.00);
call sp_create_empleados('Beatriz Ruiz', 1, 1, '2024-03-10', 7800.00);

call sp_read_empleados();

-- El empleado_id es un UUID generado dentro del procedimiento.
-- Copia los IDs que salgan aquí y pégalos donde dice 'PEGAR-ID-...-AQUI' abajo.
select * from empleados;

call sp_search_empleados('PEGAR-ID-DE-ANA-AQUI');

-- update completo (Administrador)
call sp_update_empleados('PEGAR-ID-DE-ANA-AQUI', 'Ana López Ramírez', 1, 1, '2023-01-15', 9000.00);
call sp_read_empleados();

-- update sin salario (Recursos Humanos)
call sp_update_datos_empleados('PEGAR-ID-DE-BEATRIZ-AQUI', 'Beatriz Ruiz Gómez', 2, 2, '2024-03-10');
call sp_read_empleados();

-- update solo salario (Nomina)
call sp_update_salario_empleados('PEGAR-ID-DE-BEATRIZ-AQUI', 8200.00);
call sp_read_empleados();

call sp_delete_empleados('PEGAR-ID-DE-CARLOS-AQUI');
call sp_read_empleados();


-- =========================================================
-- 5) USUARIOS
-- (usa rol_id=1,2,3 que existen, y el empleado_id real de Ana/Beatriz)
-- =========================================================
call sp_create_usuarios('admin1', 'hash_ficticio_admin', 1, null);
call sp_create_usuarios('nomina1', 'hash_ficticio_nomina', 2, null);
call sp_create_usuarios('rrhh.ana', 'hash_ficticio_rrhh', 3, 'PEGAR-ID-DE-ANA-AQUI');

call sp_read_usuarios();
call sp_search_usuarios('admin1');

call sp_update_usuarios(1, 'admin.principal', 'hash_ficticio_789', 1, null);
call sp_read_usuarios();

call sp_delete_usuarios(2);
call sp_read_usuarios();


-- =========================================================
-- 6) NOMINAS
-- (usa el empleado_id real de Ana/Beatriz)
-- =========================================================
call sp_create_nominas('PEGAR-ID-DE-ANA-AQUI', '2024-01-31', 9000.00, 'Pagado');
call sp_create_nominas('PEGAR-ID-DE-BEATRIZ-AQUI', '2024-01-31', 8200.00, 'Pendiente');

call sp_read_nominas();
call sp_search_nominas(1);

call sp_update_nominas(1, 'PEGAR-ID-DE-ANA-AQUI', '2024-02-01', 9200.00, 'Pagado');
call sp_read_nominas();

call sp_delete_nominas(1);
call sp_read_nominas();

-- =========================================================
-- FIN DEL SCRIPT DE PRUEBAS
-- =========================================================