package com.example.demo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.springframework.stereotype.Service;

@Service
public class PostgreSQLDatabaseCreator {
	String url = "jdbc:postgresql://localhost:5432/postgres";
	String username = "postgres";
	String password = "12345";
	String databaseName = "nombre_basedatos";

	public void createDatabase() {
		// Establecer la conexión a la base de datos
		try (Connection connection = DriverManager.getConnection(url, username, password)) {
			Statement statement = connection.createStatement();

			// Crear la tabla
			String createTableQuery = "CREATE TABLE IF NOT EXISTS mi_tabla (id INT, nombre VARCHAR(100))";
			statement.executeUpdate(createTableQuery);

			// Insertar un registro en la tabla
			String insertQuery = "INSERT INTO mi_tabla (id, nombre) VALUES (2, 'Johans')";
			statement.executeUpdate(insertQuery);

			// Realizar la consulta SELECT
			String selectQuery = "SELECT * FROM mi_tabla";
			ResultSet resultSet = statement.executeQuery(selectQuery);

			// Mostrar el contenido de la tabla
			System.out.println("Contenido de la tabla:");
			while (resultSet.next()) {
				int id = resultSet.getInt("id");
				String nombre = resultSet.getString("nombre");
				System.out.println("ID: " + id + ", Nombre: " + nombre);
			}

			System.out.println("Tabla creada, registro insertado y contenido mostrado correctamente.");
		} catch (SQLException e) {
			System.out
					.println("Error al crear la tabla, insertar el registro o mostrar el contenido: " + e.getMessage());
		}
	}
	
	public void insertUser(String id, String nombre) {
		try (Connection connection = DriverManager.getConnection(url, username, password)) {
			Statement statement = connection.createStatement();

			// Insertar un registro en la tabla
			String insertQuery = "INSERT INTO mi_tabla (id, nombre) VALUES (" + id + "," + nombre + ")";
			statement.executeUpdate(insertQuery);

			// Realizar la consulta SELECT
			String selectQuery = "SELECT * FROM mi_tabla";
			ResultSet resultSet = statement.executeQuery(selectQuery);

			// Mostrar el contenido de la tabla
			System.out.println("Contenido de la tabla:");
			while (resultSet.next()) {
				int idR = resultSet.getInt("id");
				String nombreR = resultSet.getString("nombre");
				System.out.println("ID: " + idR + ", Nombre: " + nombreR);
			}
			
			System.out.println("Registro insertado y contenido mostrado correctamente.");
		} catch (SQLException e) {
			System.out
					.println("Error al crear la tabla, insertar el registro o mostrar el contenido: " + e.getMessage());
		}
	}
}