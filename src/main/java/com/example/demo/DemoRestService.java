package com.example.demo;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.Equipo;
import com.example.demo.model.Liga;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@Tag(name = "DemoRestService", description = "API de la Liga")
public class DemoRestService {
	@Autowired
	private Liga liga;

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
			@Parameter(description = "id del equipo", required = true, example = "E1", in = ParameterIn.PATH)
			@PathVariable(value = "id") String id) {
		Equipo equipo = liga.getEquipos().get(id);
		if (equipo == null) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(equipo);
	}
}