rio de prueba Técnica CCE

### Análisis
Tras el análisis del requerimiento se observan 4 entidades.
Producto: Corresponde a la entidad que manejará la información básica de 1. nombre, valor y stock del mismo.
2. Usuario: Corresponde a la entidad que manejará información de la persona y a la vez de su cuenta de usuario.
3. Item: Corresponde a cada petición de producto agregado en un marketPlace, puede ser un producto y una cantidad x y en otro item el mismo producto y una cantidad y, y en otro item, un producto diferente y la cantidad z. En sí un ítem en un carrito de compras se define por la combinación entre Producto y cantidad.
4. Carrito: Es la entidad que agrupa los items en un momento determinado entre el cual el usuario ingresa items, los elimina o les ajusta las cantidades. El carro permanece vigente hasta que se realiza la compra y allí se inhabilita, sin perderse la información de la compra.  Mientras no se hacen las compras, se puede vaciar.

En la parte dinámica del proceso se identifican los siguientes 3 servicios:

####Productos 

1. Consulta
2. Creacion
3. Eliminacion
4. Actualizacion

####Usuarios
1. Consulta
2. Creación
3. Eliminación
4. Actualizacion
5. Autenticación

####Carrito
1. Creación de un único carrito activo por usuario
2. Vacíar carrito
3. Deshabilitar carrito
4. Creación de Item(pareja Producto-Cantidad)
5. Eliminación de Item
6. Modificación de Item


### Preparación

Lenguaje: Java (jdk1.8)
Framework: SpringBoot (Arquetipos para la estructura mediante SpringInitializr)
Database: Postgres14 (Por agilizar aunque se me facilita mucho más Oracle)
Tipo de API: API-REST.
IDE: Eclipse
Contenedor Web: Tomcat incorporado
Gestión del Proyecto: Maven
Otras herramientas o plugins utilizados: Postman, Spring Tools 4, MapStruct, JWT, lombok

La elección de los patrones prácticamente se ceden a los proporcionados por el framework, entre ellos el Repository, DTO, fachada, ....


