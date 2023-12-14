package py.com.enter.enterclient;

import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Bean;
import org.springframework.context.event.EventListener;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import py.com.enter.enterclient.util.JeBoot;

@SpringBootApplication
public class EnterClientApplication {

	@Value("${server.cors.permitido}")
	private String corsPermitidoString;

	@Value("${parametro.idPuesto}")
	private String idPuesto;
	@Value("${nombre.lindo}")
	private String nombreLindo;

	public static void main(String[] args) {
		SpringApplication.run(EnterClientApplication.class, args);
	}

	@Bean
	public WebMvcConfigurer corsConfigurer() {
		return new WebMvcConfigurer() {
			@Override
			public void addCorsMappings(CorsRegistry registry) {

				String arrayPermitido[] = corsPermitidoString.split(",");

				registry.addMapping("/**").allowedOrigins(arrayPermitido);

			// 	registry.addMapping("/ws/**")
            // .allowedOrigins("*")
            // .allowedMethods("*")
            // .allowedHeaders("*")
            // .allowCredentials(false)
            // .maxAge(3600);

			}
		};
	}



	@EventListener(ApplicationReadyEvent.class)
	public void doSomethingAfterStartup() {

		//Setear estatico
		
		//FileStorageService.FILE_UPLOAD_DIRECTORY=fileUploadDirectory;
		
		//KwfBootConfig.initClean();

		
		Date date = new Date();
		String DD_MM_AAAA=JeBoot.getFechaString(date);
		String HH_MM_SS=JeBoot.getHoraStringHHMMSS(date);

		System.out.println("\n           ▄█████▄");
		System.out.println("        ▄███████████▄");
		System.out.println("    ▄██████       █████▄");
        System.out.println("   ██████    KUAA    █████");
        System.out.println("  ██████    listo     █████");
        System.out.println("  ████                  ████");
        System.out.println("   ████   "+DD_MM_AAAA+"   ████");
        System.out.println("    ████▄  "+HH_MM_SS+"  ▄████");
        System.out.println("       ███         ▄███");
        System.out.println("         ███████████"); 
		System.out.println("            █████ \n"); 


		System.out.println("\n\n\n"+nombreLindo); 
		System.out.println("\n ID PUESTO: "+idPuesto); 
		System.out.println("\n\n\n"); 

	}



	


}
