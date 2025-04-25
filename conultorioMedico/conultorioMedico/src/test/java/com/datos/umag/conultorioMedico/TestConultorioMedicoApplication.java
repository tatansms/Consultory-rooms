package com.datos.umag.conultorioMedico;

import org.springframework.boot.SpringApplication;

public class TestConultorioMedicoApplication {

	public static void main(String[] args) {
		SpringApplication.from(ConultorioMedicoApplication::main).with(TestcontainersConfiguration.class).run(args);
	}

}
