package dh.backend.ClinicaOdontologicaVR;

import dh.backend.ClinicaOdontologicaVR.db.H2Connection;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ClinicaVrApplication {

	public static void main(String[] args) {
		SpringApplication.run(ClinicaVrApplication.class, args);
		H2Connection.crearTablas();
	}
}
