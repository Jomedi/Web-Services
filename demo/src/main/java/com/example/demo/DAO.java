package com.example.demo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DAO {

	String url = "jdbc:postgresql://localhost:5432/nombre_basedatos";
	String usuario = "tu_usuario";
	String contraseña = "tu_contraseña";

	public DAO() {
		try {
			Connection connection = DriverManager.getConnection(url, usuario, contraseña);
			Statement statement = connection.createStatement();

			String query = "CREATE DATABASE nombre_basedatos";
			statement.executeUpdate(query);

			System.out.println("Base de datos creada correctamente.");
			connection.close(); // Cierra la conexión cuando hayas terminado
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}


}
