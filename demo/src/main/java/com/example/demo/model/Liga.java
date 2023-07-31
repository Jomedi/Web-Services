package com.example.demo.model;

import java.util.HashMap;
import java.util.Map;

public class Liga {
	public Map<String, Equipo> equipos = new HashMap<>();
	public Map<String, Jugador> jugadores = new HashMap<>();
	
	public Liga() {
		Jugador pepe = new Jugador("J01", "Pepe", "Gómez");
		jugadores.put(pepe.getId(), pepe);
		Jugador luis = new Jugador("J02", "Luis", "López");
		jugadores.put(luis.getId(), luis);
		Jugador lara = new Jugador("J03", "Lara", "Pérez");
		jugadores.put(lara.getId(), lara);
		Jugador manu = new Jugador("J04", "Manu", "Ponce");
		jugadores.put(manu.getId(), manu);
		Jugador pili = new Jugador("J05", "Pili", "Salas");
		jugadores.put(pili.getId(), pili);

		Map<String, Jugador> jugadores1 = new HashMap<>();
		jugadores1.put(pepe.getId(), pepe);
		jugadores1.put(luis.getId(), luis);
		Equipo eq1 = new Equipo("E1", "Rojo", jugadores1);
		equipos.put(eq1.getId(), eq1);

		Map<String, Jugador> jugadores2 = new HashMap<>();
		jugadores2.put(lara.getId(), lara);
		jugadores2.put(manu.getId(), manu);
		Equipo eq2 = new Equipo("E2", "Verde", jugadores2);
		equipos.put(eq2.getId(), eq2);
	}

	public Map<String, Equipo> getEquipos() {
		return equipos;
	}

	public void setEquipos(Map<String, Equipo> equipos) {
		this.equipos = equipos;
	}

	public Map<String, Jugador> getJugadores() {
		return jugadores;
	}

	public void setJugadores(Map<String, Jugador> jugadores) {
		this.jugadores = jugadores;
	}
}
