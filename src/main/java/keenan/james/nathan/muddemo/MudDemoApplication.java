package keenan.james.nathan.muddemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class MudDemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(MudDemoApplication.class, args);
	}

}
