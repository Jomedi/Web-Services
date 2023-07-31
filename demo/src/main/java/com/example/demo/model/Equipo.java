package com.example.demo.model;

import java.util.HashMap;
import java.util.Map;

public class Equipo {
	private String id;
	private String nombre;
	private Map<String, Jugador> jugadores;

	public Equipo() {
		this.jugadores = new HashMap<>();
	}

	public Equipo(String id, String nombre) {
		this.id = id;
		this.nombre = nombre;
		this.jugadores = new HashMap<>();
	}

	public Equipo(String id, String nombre, Map<String, Jugador> jugadores) {
		this.id = id;
		this.nombre = nombre;
		this.jugadores = jugadores;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public Map<String, Jugador> getJugadores() {
		return jugadores;
	}

	public void setJugadores(Map<String, Jugador> jugadores) {
		this.jugadores = jugadores;
	}
	
	public void addJugador(Jugador jugador) {
//		if (jugadores == null) {
//			jugadores = new HashMap<>();
//		}
		jugadores.put(jugador.getId(), jugador);
	}
}
