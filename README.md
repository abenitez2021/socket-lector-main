
## Proyecto EnterClient
Cliente para leer archivos e incentivar accion.

### Pasos para levantar el proyecto

1- Clonar el proyecto
2- Mudarse al branch Develop
3- Asegurarse que se está conectado a internet full o se tiene permisos para descarga de librerías necesarias.


### Preparar VSCode

- Abrir VSCode e Instalar Plugins: 
- 1. Extension Pack for Java de Microsoft, contiene 6 plugins.
- 2. Spring Boot Extension Pack de VMware. contiene 3 plugins.
- Verificar que JAVA_HOME esté correctamente en las Variables de Entorno.


### Abrir proyecto con VSCode

- Abrir carpeta de proyecto en VSCode, dejar que se descarguen las librerías, tarda la primera vez. Se crea automáticamente la carpeta .m2 dentro de tu directorio de usuario, verificar si aumenta de tamaño.


### Configuración de application.properties
- Copiar el archivo application.properties.template como application.properties en el mismo directorio.


### Ejecutar proyecto
- Probar si el servidor responde con: http://127.0.0.1:8080/server/status




### Volver a descargar librerías
En la vista de "Maven Projects", expande la carpeta de tu proyecto y luego expande la carpeta "Lifecycle".
Haz clic derecho en el objetivo "clean" y selecciona "Run Clean" para limpiar el proyecto.
Luego, haz clic derecho en el objetivo "install" y selecciona "Run Install" para instalar las dependencias del proyecto.



