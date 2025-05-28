# Reto-Técnico-Java-Springboot-TCS-Ecuador

Este proyecto fue desarrollado como parte del reto técnico para la vacante de Desarrollador Backend Junior en Tata Consultancy Services (TCS) Ecuador. La aplicación permite gestionar clientes, cuentas bancarias y movimientos financieros, cumpliendo con los requerimientos especificados en el documento técnico entregado.

# Descripción General

La API REST implementa operaciones CRUD sobre las entidades principales del sistema y permite registrar movimientos de tipo depósito o retiro, manteniendo actualizados los saldos de las cuentas y validando reglas de negocio como la disponibilidad de fondos.

# Funcionalidades

- Gestión de clientes (crear, listar, actualizar, eliminar)

- Gestión de cuentas (asociadas a clientes)

- Registro y consulta de movimientos financieros

- Cálculo automático de saldo disponible por cuenta

- Validación de saldo insuficiente en retiros

- Consulta de movimientos por cliente y rango de fechas

- Generación del script de base de datos (BaseDatos.sql)

- Pruebas unitarias en la capa de servicios

- Despliegue completo mediante Docker y Docker Compose

# Tecnologías y Herramientas

- Java 17
- Spring Boot 3.5.0
- Spring Data JPA
- PostgreSQL
- Docker y Docker Compose
- Maven
- JUnit y Mockito

#Estructura del Proyecto

<pre lang="markdown"> ```bash 📁 reto_tcs_ecuador ├── 📁 controller │ ├── ClienteController.java │ ├── CuentaController.java │ └── MovimientoController.java ├── 📁 dto │ ├── ClienteRequestDTO.java │ ├── ClienteResponseDTO.java │ ├── CuentaDTO.java │ ├── DetalleMovimientoDTO.java │ └── MovimientoDTO.java ├── 📁 exception │ ├── ClienteNotFoundException.java │ ├── CuentaNotFoundException.java │ ├── GlobalExceptionHandler.java │ └── MovimientoNotFoundException.java ├── 📁 mapper │ ├── ClienteMapper.java │ ├── CuentaMapper.java │ └── MovimientoMapper.java ├── 📁 model │ ├── Cliente.java │ ├── Cuenta.java │ ├── Movimiento.java │ └── Persona.java ├── 📁 repository │ ├── ClienteRepository.java │ ├── CuentaRepository.java │ └── MovimientoRepository.java ├── RetoTcsEcuadorApplication.java ├── 📁 service │ ├── ClienteService.java │ ├── CuentaService.java │ ├── MovimientoService.java │ └── 📁 impl │ ├── ClienteServiceImpl.java │ ├── CuentaServiceImpl.java │ └── MovimientoServiceImpl.java └── 📁 utils ├── Genero.java └── TipoCuenta.java ``` </pre>

# Configuración y Ejecución
- Compilar el proyecto
- Desde la raíz del proyecto: mvn clean package -DskipTests

- Ejecutar con Docker docker compose up --build
  Esto levantará:
    - La aplicación Spring Boot expuesta en http://localhost:8060
    - Una base de datos PostgreSQL en localhost:5432

Autor
Santiago Cabrera Arias
Desarrollador Backend.
