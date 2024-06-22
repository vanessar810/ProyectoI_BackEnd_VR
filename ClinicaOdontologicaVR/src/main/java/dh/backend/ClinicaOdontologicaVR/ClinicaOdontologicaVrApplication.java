package dh.backend.ClinicaOdontologicaVR;

import dh.backend.ClinicaOdontologicaVR.db.H2Connection;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ClinicaOdontologicaVrApplication {

	public static void main(String[] args) {
		SpringApplication.run(ClinicaOdontologicaVrApplication.class, args);
		H2Connection.crearTablas();
	}
}
