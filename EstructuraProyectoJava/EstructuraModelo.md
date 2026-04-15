# Manual de Skill - Estructura del Modelo CRUD

## Overview

Este manual describe la estructura estándar para implementar modelos CRUD en el proyecto. Todos los modelos siguen un patrón consistente con manejo de errores y herencia de la clase `Conexion`.

## Estructura Base

### Clase Conexion

Todos los modelos deben extender la clase `Conexion` ubicada en `src/Modelo/Conexion.java`. Esta clase proporciona:

- Conexión a la base de datos MySQL
- Manejo básico de errores de conexión
- Configuración de base de datos (db_decoraciones, puerto 3306)

## Plantilla CRUD Estándar

### Estructura del Modelo

```java
package Modelo;

import java.sql.*;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

public class NombreModelo extends Conexion {

    PreparedStatement preparedStatement = null;
    Connection connection;
    String sentenciaSQL;
    ResultSet resultSet;
    TipoType tipo;

    // MÉTODOS CRUD ESTÁNDAR

    /**
     * Crea un nuevo registro
     * @param objeto - Objeto con los datos a crear
     * @return true si la creación fue exitosa, false en caso contrario
     */
    public boolean create(TipoType objeto) {
        connection = getConxion();
        sentenciaSQL = "INSERT INTO nombre_tabla (campo1, campo2, campo3, estado) VALUES (?,?,?,?)";

        try {
            preparedStatement = connection.prepareStatement(sentenciaSQL);
            preparedStatement.setString(1, objeto.getCampo1());
            preparedStatement.setString(2, objeto.getCampo2());
            preparedStatement.setString(3, objeto.getCampo3());
            preparedStatement.setString(4, objeto.getEstado());
            preparedStatement.execute();
            JOptionPane.showMessageDialog(null, "Registro ingresado correctamente");
            return true;
        } catch (SQLException e) {
            Logger.getLogger(NombreModelo.class.getName()).log(Level.SEVERE, null, e);
            System.out.print(e.getMessage());
            return false;

        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                System.out.print(e.getMessage());
            }
        }
    } // Fin de crear

    /**
     * Obtiene un registro específico por su ID
     * @param id - Identificador del registro
     * @return Objeto del tipo correspondiente o null si no existe
     */
    public TipoType get(int id) {
        connection = getConxion();
        sentenciaSQL = "SELECT * FROM nombre_tabla WHERE id = ?";

        try {
            preparedStatement = connection.prepareStatement(sentenciaSQL);
            preparedStatement.setInt(1, id);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                int id_tipo = resultSet.getInt(1);
                String campo1 = resultSet.getString(2);
                String campo2 = resultSet.getString(3);
                String campo3 = resultSet.getString(4);
                String estado = resultSet.getString(5);

                TipoType tipoBuscado = new TipoType();

                tipoBuscado.setId_tipo(id_tipo);
                tipoBuscado.setCampo1(campo1);
                tipoBuscado.setCampo2(campo2);
                tipoBuscado.setCampo3(campo3);
                tipoBuscado.setEstado(estado);
                return tipoBuscado;
            }

            return null;

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al leer" + e.getMessage());
            Logger.getLogger(NombreModelo.class.getName()).log(Level.SEVERE, null, e);
            System.out.print(e.getMessage());
            return null;

        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                System.out.print(e.getMessage());
            }
        }
    } // Fin de get

    /**
     * Obtiene todos los registros de la tabla
     * @return ArrayList con todos los objetos
     */
    public ArrayList<TipoType> getAll() {
        ArrayList<TipoType> lista = new ArrayList<>();
        connection = getConxion();
        sentenciaSQL = "SELECT * FROM nombre_tabla";

        try {
            preparedStatement = connection.prepareStatement(sentenciaSQL);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int id_tipo = resultSet.getInt(1);
                String campo1 = resultSet.getString(2);
                String campo2 = resultSet.getString(3);
                String campo3 = resultSet.getString(4);
                String estado = resultSet.getString(5);

                TipoType tipo = new TipoType();

                tipo.setId_tipo(id_tipo);
                tipo.setCampo1(campo1);
                tipo.setCampo2(campo2);
                tipo.setCampo3(campo3);
                tipo.setEstado(estado);
                lista.add(tipo);

            }
            connection.close();

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "No se pudo leer datos" + e.getMessage());
        }
        return lista;
    }// Fin de getAll

    /**
     * Actualiza un registro existente
     * @param objeto - Objeto con los datos actualizados
     * @return true si la actualización fue exitosa, false en caso contrario
     */
    public boolean update(TipoType objeto) {
        connection = getConxion();
        sentenciaSQL = "UPDATE nombre_tabla SET campo1=?, campo2=?, campo3=?, estado=? WHERE id=?";

        try {
            preparedStatement = connection.prepareStatement(sentenciaSQL);
            preparedStatement.setString(1, objeto.getCampo1());
            preparedStatement.setString(2, objeto.getCampo2());
            preparedStatement.setString(3, objeto.getCampo3());
            preparedStatement.setString(4, objeto.getEstado());
            preparedStatement.setInt(5, objeto.getId_tipo());
            preparedStatement.execute();
            JOptionPane.showMessageDialog(null, "Registro actualizado correctamente");
            return true;
        } catch (SQLException e) {
            Logger.getLogger(NombreModelo.class.getName()).log(Level.SEVERE, null, e);
            System.out.print(e.getMessage());
            return false;

        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                System.out.print(e.getMessage());
            }
        }
    } // Fin de update

    /**
     * Elimina un registro por su ID
     * @param id - Identificador del registro a eliminar
     * @return true si la eliminación fue exitosa, false en caso contrario
     */
    public boolean delete(int id) {
        connection = getConxion();
        sentenciaSQL = "UPDATE nombre_tabla SET estado='inactivo' WHERE id=?";

        try {
            preparedStatement = connection.prepareStatement(sentenciaSQL);
            preparedStatement.setInt(1, id);
            preparedStatement.execute();
            JOptionPane.showMessageDialog(null, "Registro eliminado correctamente");
            return true;
        } catch (SQLException e) {
            Logger.getLogger(NombreModelo.class.getName()).log(Level.SEVERE, null, e);
            System.out.print(e.getMessage());
            return false;

        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                System.out.print(e.getMessage());
            }
        }
    } // Fin de delete
}
```

## Convenciones y Buenas Prácticas

### 1. Variables de Instancia

Todos los modelos deben declarar estas variables a nivel de clase:

```java
PreparedStatement preparedStatement = null;
Connection connection;
String sentenciaSQL;
ResultSet resultSet;
TipoType tipo;
```

### 2. Manejo de Errores

- Usar Logger para registrar errores: `Logger.getLogger(NombreModelo.class.getName()).log(Level.SEVERE, null, e)`
- Mostrar mensajes al usuario con JOptionPane: `JOptionPane.showMessageDialog(null, "mensaje")`
- Imprimir errores en consola: `System.out.print(e.getMessage())`
- Nunca lanzar excepciones, manejarlas internamente

### 3. Gestión de Recursos

- Cerrar la conexión en el bloque finally: `connection.close()`
- No es necesario cerrar preparedStatement y resultSet individualmente

### 4. Nomenclatura

- Métodos CRUD: `create()`, `getById()`, `getAll()`, `update()`, `delete()`
- Los nombres de tablas en minúsculas
- Los nombres de columnas como están en la base de datos
- Usar índices numéricos para acceder a columnas (1, 2, 3...)

### 5. Retornos

- `create()`: retorna boolean (true si se creó, false si no)
- `getById()`: retorna el objeto específico o null si no existe
- `getAll()`: retorna ArrayList con todos los registros
- `update()`: retorna boolean (true si se actualizó, false si no)
- `delete()`: retorna boolean (true si se eliminó, false si no)

### 6. Eliminación Lógica

- El método `delete()` debe hacer eliminación lógica: `UPDATE tabla SET estado='inactivo' WHERE id=?`
- No eliminar físicamente los registros

### 7. Autenticación de Usuarios

- El modelo de usuarios debe incluir un método `auth()` que reciba usuario y contraseña
- Método para autenticación: `public Usuario auth(String usuario, String password)`
- Debe verificar las credenciales contra la base de datos
- Retornar el objeto Usuario si las credenciales son correctas, null si no

### 8. Consultas SQL y Base de Datos

- **REGLA OBLIGATORIA**: Siempre revisar el archivo `src/Modelo/init.sql` antes de escribir consultas SQL
- Verificar nombres exactos de tablas, columnas y tipos de datos
- Las consultas deben coincidir exactamente con la estructura definida en init.sql
- No asumir nombres de columnas, verificarlos en el script SQL

### 9. Manejo de Vistas

- Cuando existan vistas en la base de datos (ej: `vista_ventas`), crear métodos específicos
- Nomenclatura para vistas: `getAll_vista_[nombre_vista]`
- Ejemplo: `getAll_vista_ventas()` para la vista vista_ventas

### 10. Métodos de Filtrado

- Para filtros complejos usar nomenclatura: `getAll_vista_[nombre]_for_[campo]`
- Filtros comunes disponibles:
  - Por fechas: `_for_fecha_inicio_fin`
  - Por rangos de fechas: `_for_rango_fechas`
  - Por nombres de productos: `_for_nombre_producto`
  - Por categorías: `_for_categoria`
  - Por clientes: `_for_cliente`
  - Por vendedores: `_for_vendedor`
- Ejemplos:
  - `getAll_vista_ventas_for_fecha_inicio_fin(String inicio, String fin)`
  - `getAll_vista_ventas_for_cliente(String nombreCliente)`
  - `getAll_vista_ventas_for_categoria(String categoria)`

## Ejemplo Completo

### Modelo de Usuario

```java
package Modelo.usuarios;

import Modelo.Conexion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

public class UsuarioModel extends Conexion {

    PreparedStatement preparedStatement = null;
    Connection connection;
    String sentenciaSQL;
    ResultSet resultSet;

    public boolean create(Usuario usuario) {
        connection = getConxion();
        sentenciaSQL = "INSERT INTO usuarios (id_usuario, nombre, email, password, estado) VALUES (?,?,?,?,?)";

        try {
            preparedStatement = connection.prepareStatement(sentenciaSQL);
            preparedStatement.setInt(1, usuario.getId_usuario());
            preparedStatement.setString(2, usuario.getNombre());
            preparedStatement.setString(3, usuario.getEmail());
            preparedStatement.setString(4, usuario.getPassword());
            preparedStatement.setString(5, usuario.getEstado());
            preparedStatement.execute();
            JOptionPane.showMessageDialog(null, "Usuario ingresado correctamente");
            return true;
        } catch (SQLException e) {
            Logger.getLogger(UsuarioModel.class.getName()).log(Level.SEVERE, null, e);
            System.out.print(e.getMessage());
            return false;

        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                System.out.print(e.getMessage());
            }
        }
    } // Fin de crear

    public ArrayList<Usuario> getAll() {
        ArrayList<Usuario> usuariosList = new ArrayList<Usuario>();
        connection = getConxion();
        sentenciaSQL = "SELECT * FROM usuarios";

        try {
            preparedStatement = connection.prepareStatement(sentenciaSQL);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Usuario usuario = new Usuario();
                int id_usuario = resultSet.getInt(1);
                String nombre = resultSet.getString(2);
                String email = resultSet.getString(3);
                String password = resultSet.getString(4);
                String estado = resultSet.getString(5);

                usuario.setId_usuario(id_usuario);
                usuario.setNombre(nombre);
                usuario.setEmail(email);
                usuario.setPassword(password);
                usuario.setEstado(estado);
                usuariosList.add(usuario);

            }
            connection.close();

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "No se pudo leer datos" + e.getMessage());
        }
        return usuariosList;
    }// Fin de getAll

    public Usuario getById(Usuario usuario) {
        connection = getConxion();
        sentenciaSQL = "SELECT * FROM usuarios WHERE id_usuario=?";

        try {
            preparedStatement = connection.prepareStatement(sentenciaSQL);
            preparedStatement.setInt(1, usuario.getId_usuario());
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                int id_usuario = resultSet.getInt(1);
                String nombre = resultSet.getString(2);
                String email = resultSet.getString(3);
                String password = resultSet.getString(4);
                String estado = resultSet.getString(5);

                Usuario usuarioBuscado = new Usuario();

                usuarioBuscado.setId_usuario(id_usuario);
                usuarioBuscado.setNombre(nombre);
                usuarioBuscado.setEmail(email);
                usuarioBuscado.setPassword(password);
                usuarioBuscado.setEstado(estado);
                return usuarioBuscado;
            }

            return null;

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al leer" + e.getMessage());
            Logger.getLogger(UsuarioModel.class.getName()).log(Level.SEVERE, null, e);
            System.out.print(e.getMessage());
            return null;

        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                System.out.print(e.getMessage());
            }
        }
    } // Fin de getById

    public boolean update(Usuario usuario) {
        connection = getConxion();
        sentenciaSQL = "UPDATE usuarios SET nombre=?, email=?, password=?, estado=? WHERE id_usuario=?";

        try {
            preparedStatement = connection.prepareStatement(sentenciaSQL);
            preparedStatement.setString(1, usuario.getNombre());
            preparedStatement.setString(2, usuario.getEmail());
            preparedStatement.setString(3, usuario.getPassword());
            preparedStatement.setString(4, usuario.getEstado());
            preparedStatement.setInt(5, usuario.getId_usuario());
            preparedStatement.execute();
            JOptionPane.showMessageDialog(null, "Usuario actualizado correctamente");
            return true;
        } catch (SQLException e) {
            Logger.getLogger(UsuarioModel.class.getName()).log(Level.SEVERE, null, e);
            System.out.print(e.getMessage());
            return false;

        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                System.out.print(e.getMessage());
            }
        }
    } // Fin de update

    public boolean delete(Usuario usuario) {
        connection = getConxion();
        sentenciaSQL = "UPDATE usuarios SET estado='inactivo' WHERE id_usuario=?";

        try {
            preparedStatement = connection.prepareStatement(sentenciaSQL);
            preparedStatement.setInt(1, usuario.getId_usuario());
            preparedStatement.execute();
            JOptionPane.showMessageDialog(null, "Usuario eliminado correctamente");
            return true;
        } catch (SQLException e) {
            Logger.getLogger(UsuarioModel.class.getName()).log(Level.SEVERE, null, e);
            System.out.print(e.getMessage());
            return false;

        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                System.out.print(e.getMessage());
            }
        }
    } // Fin de delete

    public Usuario auth(String usuario, String password) {
        connection = getConxion();
        sentenciaSQL = "SELECT * FROM usuarios WHERE usuario = ? AND password = ? AND estado = 'activo'";

        try {
            preparedStatement = connection.prepareStatement(sentenciaSQL);
            preparedStatement.setString(1, usuario);
            preparedStatement.setString(2, password);
            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                int id_usuario = resultSet.getInt(1);
                String nombre_usuario = resultSet.getString(2);
                String email = resultSet.getString(3);
                String pass = resultSet.getString(4);
                String estado = resultSet.getString(5);
                String nombre_completo = resultSet.getString(6);

                Usuario usuarioAutenticado = new Usuario();
                usuarioAutenticado.setId_usuario(id_usuario);
                usuarioAutenticado.setUsuario(nombre_usuario);
                usuarioAutenticado.setEmail(email);
                usuarioAutenticado.setPassword(pass);
                usuarioAutenticado.setEstado(estado);
                usuarioAutenticado.setNombre_completo(nombre_completo);
                return usuarioAutenticado;
            }

            return null;

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error en autenticación" + e.getMessage());
            Logger.getLogger(UsuarioModel.class.getName()).log(Level.SEVERE, null, e);
            System.out.print(e.getMessage());
            return null;

        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                System.out.print(e.getMessage());
            }
        }
    } // Fin de auth
}
```

## Checklist de Implementación

### Requisitos Básicos

- [ ] Extender clase `Conexion`
- [ ] Declarar variables de instancia (preparedStatement, connection, sentenciaSQL, resultSet)
- [ ] Implementar método `create(objeto)` que retorna boolean
- [ ] Implementar método `getById(objeto)` que retorna objeto o null
- [ ] Implementar método `getAll()` que retorna ArrayList
- [ ] Implementar método `update(objeto)` que retorna boolean
- [ ] Implementar método `delete(objeto)` con eliminación lógica

### Manejo de Errores y Recursos

- [ ] Todos los métodos con try-catch SQLException
- [ ] Usar Logger para registrar errores
- [ ] Usar JOptionPane para mensajes al usuario
- [ ] Cerrar conexión en bloque finally
- [ ] Usar índices numéricos para columnas del ResultSet
- [ ] Usar preparedStatement.execute() en lugar de executeUpdate()

### Requisitos Especiales

- [ ] **PARA USUARIOS**: Implementar método `auth(String usuario, String password)`
- [ ] **OBLIGATORIO**: Revisar `src/Modelo/init.sql` antes de escribir consultas
- [ ] **SI EXISTEN VISTAS**: Crear métodos `getAll_vista_[nombre]`
- [ ] **PARA FILTRADOS**: Crear métodos `getAll_vista_[nombre]_for_[campo]`

### Filtros Comunes (cuando aplique)

- [ ] `_for_fecha_inicio_fin(String inicio, String fin)`
- [ ] `_for_rango_fechas(String inicio, String fin)`
- [ ] `_for_nombre_producto(String nombre)`
- [ ] `_for_categoria(String categoria)`
- [ ] `_for_cliente(String nombreCliente)`
- [ ] `_for_vendedor(String nombreVendedor)`

## Notas Adicionales

- Esta estructura garantiza consistencia en todo el proyecto
- Facilita el mantenimiento y depuración
- Proporciona manejo robusto de errores
- Previene fugas de memoria al cerrar recursos correctamente
