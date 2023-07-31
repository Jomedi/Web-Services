package com.example.demo;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.Equipo;
import com.example.demo.model.Jugador;
import com.example.demo.model.Liga;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@Tag(name = "DemoRestService", description = "DemoRestService description: API de la Liga")
public class DemoRestService {
	@Autowired
	private Liga liga;
	
	@Autowired
	private PostgreSQLDatabaseCreator postgreSQLDatabaseCreator;

	@Operation(summary = "Equipos de la liga", description = "Lista de equipos de la liga", tags = {
			"DemoRestService" })
	@GetMapping("/equipos")
	public @ResponseBody ResponseEntity<Collection<Equipo>> getEquipos() {
		Collection<Equipo> equipos = liga.getEquipos().values();
		return ResponseEntity.ok(equipos);
	}

	@Operation(summary = "Info del equipo", description = "Información de Equipo", tags = { "DemoRestService" })
	@GetMapping("/equipos/{id}")
	public @ResponseBody ResponseEntity<Equipo> getEquipo(
			@Parameter(description = "id del equipo", required = true, example = "E1", in = ParameterIn.PATH) @PathVariable(value = "id") String id) {
		Equipo equipo = liga.getEquipos().get(id);
		if (equipo == null) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(equipo);
	}

	@Operation(summary = "Lista de jugadores", description = "Lista de jugadores de la liga", tags = {
			"DemoRestService" })
	@GetMapping("/jugadores")
	public @ResponseBody ResponseEntity<Collection<Jugador>> getJugadores() {
		Collection<Jugador> jugadores = liga.getJugadores().values();
		return ResponseEntity.ok(jugadores);
	}

	@Operation(summary = "Jugador de la liga", description = "Atributos de un jugador de la liga", tags = {
			"DemoRestService" })
	@GetMapping("/jugadores/{id}")
	public @ResponseBody ResponseEntity<Jugador> getJugador(
			@Parameter(description = "id del jugador", required = true, example = "J01", in = ParameterIn.PATH) @PathVariable(value = "id") String id) {
		Jugador jugador = liga.getJugadores().get(id);
		if (jugador == null) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(jugador);
	}

	@Operation(summary = "Actualiza un jugador", description = "Actualiza la información de un jugador", tags = {"DemoRestService" })
	@PutMapping("/jugadores/{id}")
	public @ResponseBody ResponseEntity<Jugador> updateJugador(
			@Parameter(description = "id del jugador", required = true, example = "J01", in = ParameterIn.PATH) @PathVariable(value = "id") String id,
			@RequestBody Jugador jugador) {
		if (liga.getJugadores().get(id) == null) {
			return ResponseEntity.notFound().build();
		} else {
			Jugador jg = liga.getJugadores().put(id, jugador);
			return ResponseEntity.ok(jg);
		}
	}

	@Operation(summary = "Actualiza equipo", description = "Actualiza la información un equipo existente", tags = {
			"DemoRestService" })
	@PutMapping("/equipos/{id}")
	public @ResponseBody ResponseEntity<Equipo> updateEquipo(
			@Parameter(description = "id del equipo", required = true, example = "E1", in = ParameterIn.PATH) @PathVariable(value = "id") String id,
			@RequestBody Equipo equipo) {
		if (liga.getEquipos().get(id) == null) {
			return ResponseEntity.notFound().build();
		}
		Equipo updatedEquipo = liga.getEquipos().put(id, equipo);
		return ResponseEntity.ok(updatedEquipo); // devuelve la versión actualizada del equipo
	}

	// Cambiar el ResponseEntity al tipo Jugador
	@Operation(summary = "Añadir jugador a equipo", description = "Añadir un jugador existente a un equipo", tags = {
			"DemoRestService" })
	@PutMapping("/equipos/{id}/{jugadorId}")
	public @ResponseBody ResponseEntity addJugadorAEquipo(
			@Parameter(description = "id del equipo", required = true, example = "E1", in = ParameterIn.PATH) @PathVariable(value = "id") String equipoId,
			@Parameter(description = "id del jugador", required = true, example = "J01", in = ParameterIn.PATH) @PathVariable(value = "jugadorId") String jugadorId) {
		Equipo eq = liga.getEquipos().get(equipoId);
		if (eq == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("El equipo " + equipoId + " no existe");
		}
		Jugador jg = liga.getJugadores().get(jugadorId);
		if (jg == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("El jugador " + jugadorId + " no existe");
		}
		if (eq.getJugadores() != null && eq.getJugadores().containsKey(jg.getId())) {
			return ResponseEntity.badRequest().body("El jugador " + jugadorId + " ya está en el equipo " + equipoId);
		}
		eq.addJugador(jg);
		return ResponseEntity.ok(eq);
	}

	@Operation(summary = "Crear equipo", description = "Crear un nuevo equipo", tags = { "DemoRestService" })
	@PostMapping("/equipos")
	public @ResponseBody ResponseEntity addEquipo(
			@Parameter(description = "Id del equipo", required = true, example = "E1", in = ParameterIn.QUERY) @RequestParam(name = "id") String id,
			@Parameter(description = "Nombre del equipo", required = true, example = "Rojo", in = ParameterIn.QUERY) @RequestParam(name = "nombre") String nombre) {
		if (liga.getEquipos().get(id) == null) {
			Equipo eq = new Equipo(id, nombre);
			liga.getEquipos().put(eq.getId(), eq);
		} else {
			return ResponseEntity.badRequest().body("El equipo " + id + " ya existe");
		}
		Equipo equipoCreado = liga.getEquipos().get(id);
		if (equipoCreado == null) {
			return ResponseEntity.internalServerError().body("No se ha podido crear el equipo " + id);
		} else {
			return ResponseEntity.ok(equipoCreado);
		}
	}

	@Operation(summary = "Crear nuevo jugador", description = "Crear un jugador nuevo en la liga", tags = {
			"DemoRestService" })
	@PostMapping("/jugadores")
	public @ResponseBody ResponseEntity addJugador(
			@Parameter(description = "id del jugador", required = true, example = "J99", in = ParameterIn.QUERY) @RequestParam(name = "id") String id,
			@Parameter(description = "nombre del jugador", required = true, example = "Ernesto", in = ParameterIn.QUERY) @RequestParam(name = "nombre") String nombre,
			@Parameter(description = "apellido del jugador", required = true, example = "Sevilla", in = ParameterIn.QUERY) @RequestParam(name = "apellido") String apellido) {
		
		PostgreSQLDatabaseCreator sql = new PostgreSQLDatabaseCreator();
		
		Jugador jg = liga.getJugadores().get(id);
		if (jg != null) {
			return ResponseEntity.badRequest().body("El jugador " + id + " ya existe");
		}

		jg = new Jugador(id, nombre, apellido);

		liga.getJugadores().put(id, jg);
		sql.insertUser(id, nombre + " " + apellido);
		Jugador jugadorCreado = liga.getJugadores().get(id);
		if (jugadorCreado == null) {
			ResponseEntity.badRequest().body("El jugador " + id + " no se ha podido crear");
		}

		return ResponseEntity.ok(jugadorCreado);
	}

	@Operation(summary = "Eliminar un equipo", description = "Eliminar un equipo", tags = { "DemoRestService" })
	@DeleteMapping("/equipos/{id}")
	public @ResponseBody ResponseEntity delEquipo(
			@Parameter(description = "id del equipo", required = true, example = "E2", in = ParameterIn.PATH) @PathVariable(value = "id") String id) {
		if (liga.getEquipos().get(id) == null) {
			return ResponseEntity.badRequest().body("No se ha encontrado al equipo " + id);
		} else {
			liga.getEquipos().remove(id);

			Equipo eq = liga.getEquipos().get(id);
			if (eq != null) {
				return ResponseEntity.badRequest().body("No se ha podido eliminar el equipo " + id);
			}
			return ResponseEntity.ok(id);
		}
	}

	@Operation(summary = "Eliminar un jugador de un equipo", description = "Eliminar jugador de un equipo", tags = {
			"DemoRestService" })
	@DeleteMapping("/equipos/{id}/{idJugador}")
	public @ResponseBody ResponseEntity delJugadorEquipo(
			@Parameter(description = "id del jugador", required = true, example = "J01", in = ParameterIn.QUERY) @RequestParam(name = "id") String idJugador,
			@Parameter(description = "id del equipo", required = true, example = "E1", in = ParameterIn.QUERY) @RequestParam(name = "idEquipo") String id) {

		Equipo eq = liga.getEquipos().get(id);
		Jugador jg = eq.getJugadores().get(idJugador);

		if (eq == null) {
			return ResponseEntity.badRequest().body("No existe el equipo " + id);
		} else if (jg == null) {
			return ResponseEntity.badRequest().body("No existe el jugador " + idJugador + " en el equipo " + id);
		}

		liga.getEquipos().get(id).getJugadores().remove(idJugador);

		Jugador existeJugador = liga.getEquipos().get(id).getJugadores().get(idJugador);
		if (existeJugador != null) {
			return ResponseEntity.badRequest().body("");
		}

		return ResponseEntity.ok(idJugador + " " + id);
	}

}
