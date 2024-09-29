Proyecto realizado por: Patrik Paul Sirbu, Jacob Altenburger Villar y Pedro Alonso Tapia Lobo
-------------------
Fecha de entrega: 29/09/2024
-------------------
Link al repositorio: https://github.com/jacobgalactico/prueba.git

Sistema de Seguridad Stark Industries

Descripción:
Stark Industries está desarrollando un sistema de seguridad avanzado para su nueva sede en Nueva York. El sistema debe gestionar múltiples sensores en tiempo real y garantizar la máxima eficiencia y capacidad de respuesta ante cualquier amenaza. Este proyecto, basado en Spring Framework, implementa un sistema concurrente que integra y gestiona diferentes tipos de sensores (movimiento, temperatura, acceso) con alta eficiencia y fiabilidad en la detección y respuesta a eventos.
Funcionalidades
1.	Gestión de Sensores:
o	Permite gestionar sensores de diferentes tipos: acceso, movimiento y temperatura.
o	Los sensores pueden ser añadidos, eliminados y actualizados a través de una interfaz web.
o	Cada sensor tiene un estado: activo, desactivado, o en error.
2.	Control de Acceso:
o	La aplicación implementa un sistema de autenticación para el acceso de usuarios a las diferentes funcionalidades.
o	Basado en Spring Security, se define una capa de autorización para gestionar roles y permisos de acceso.
3.	Notificaciones:
o	Permite la creación y gestión de notificaciones en el sistema, las cuales son visibles en la interfaz web.
4.	Eventos:
o	El sistema registra eventos relacionados con los sensores (ej. intrusiones, cambios de temperatura o accesos no autorizados).
o	Los eventos pueden ser creados manualmente desde la interfaz de administración.
5.	Concurrencia:
o	Se usa @Async y ExecutorService para manejar el procesamiento concurrente de datos de los sensores y eventos.
6.	Monitorización y Logs:
o	A través de Spring Actuator, se permite monitorizar el estado del sistema.
o	Se implementa un sistema de logging para rastrear eventos y posibles errores en tiempo real. Hemos configurado dos usuarios ( admin y contraseña admin) y (user y contraseña user)
