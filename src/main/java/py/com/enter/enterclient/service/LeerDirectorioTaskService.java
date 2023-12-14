package py.com.enter.enterclient.service;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import lombok.extern.slf4j.Slf4j;
import py.com.enter.enterclient.util.JeBoot;

@Slf4j
@Component
@Configuration
@EnableScheduling
public class LeerDirectorioTaskService {

    @Value("${parametro.directorio.leer}")
    private String directorioLeer;

    // private File directorioScan;
    // private File archivoReferencia;

    @Value("${parametro.idPuesto}")
    private String idPuesto;

    @Value("${parametro.intervalo.milisegundos}")
    private long intervaloMilisegundos;

    @Value("${parametro.api.server}")
    private String apiServer;

    @Value("${parametro.api.token}")
    private String apiToken;
    @Value("${parametro.enviarCargaSocket}")
    private String enviarCargaSocket;

    @Value("${parametro.eliminarContenidoDirectorio}")
    private String eliminarContenidoDirectorio;

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    public LeerDirectorioTaskService() {
        // directorioScan = new File(directorioLeer.replace("/", File.separator));
        // archivoReferencia = new File(
        // directorioLeer.replace("/", File.separator) + File.separator +
        // "escaneadoPorSpring.txt");
    }

    public boolean obtenerDatosArchivo() throws JSONException {

        // localhost:7001/api/visitas/sdk-archivo
        String url = apiServer + "/api/visitas/sdk-archivo";

        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(List.of(MediaType.APPLICATION_JSON));
        headers.set("Authorization", "Bearer " + apiToken);

         // Verificar si idPuesto es una cadena vacía y asignar null en su lugar
        if (idPuesto != null && idPuesto.isEmpty()) {
            idPuesto = null;
        }

        String requestBody = String.format("{\"idPuesto\": %s}", idPuesto);

        HttpEntity<String> requestEntity = new HttpEntity<>(requestBody, headers);

        ResponseEntity<String> response = restTemplate.exchange(
                url,
                HttpMethod.POST, // Método de la petición (en este caso POST)
                requestEntity, // Cuerpo de la petición
                String.class // Tipo de respuesta esperada
        );

        HttpStatus statusCode = (HttpStatus) response.getStatusCode();
        String responseBody = response.getBody();
        System.out.println("responseBody: " + responseBody);
        // Boolean isTokenValido = response.getBody();

        if (statusCode == HttpStatus.OK) {

            JSONObject jsonSendSocket = new JSONObject();
            jsonSendSocket.put("conPayload", enviarCargaSocket);
            jsonSendSocket.put("idPuesto", idPuesto);
            jsonSendSocket.put("fechaHora", JeBoot.getFechaStringHHMMSS(new Date()));
            jsonSendSocket.put("correcto", "N");
            jsonSendSocket.put("payload", JSONObject.NULL);

            try {
                JSONObject responseJson = new JSONObject(responseBody);
                // {"ok":true,"message":"archivo json leido
                // correctamente.","result":{"nombre":"ARNALDO ALBE","apellido":"RIQUELME
                // RIVEROS","documento":"4994624","tipoDocumento":"ID","foto":"http://127.0.0.1:7001/api/visitas/documento-foto/puesto/1/archivo/Photo.png","imagenFrente":"http://127.0.0.1:7001/api/visitas/documento-frente/puesto/1/archivo/WHITE.png","imagenDorso":"http://127.0.0.1:7001/api/visitas/documento-dorso/puesto/1/archivo/WHITE.png","codigoNacionalidad":"PRY","Nacionalidad":"Paraguay","fechaNacimiento":"1/12/1991","fechaExpiracionDocumento":"19/6/2025","fechaEmision":null,"sexo":"M","estadoCivil":null,"identityCardNumber":null,"idPuestoEnviado":1,"info":{"TransactionID":"24202f7b-f031-4e1c-8c19-8c0266dcc163","DateTime":"2023-09-14T00:07:06.329","ComputerName":"DESKTOP-LDCO92T","UserName":"JOSUE","SDKVersion":"6.8.0.6084","FileVersion":"6.8","DeviceType":"7027
                // (OV 5Mp)","DeviceNumber":"0x089FC582","DeviceLabelNumber":"338J2283"}}}
                if (responseJson.getBoolean("ok")) {
                    jsonSendSocket.put("correcto", "S");
                    if (enviarCargaSocket.equalsIgnoreCase("S")) {
                        jsonSendSocket.put("payload", responseJson);
                    }

                }
                ;

            } catch (Exception e) {
                log.error("error al procesar responseBody a JSON", e);
            }

            messagingTemplate.convertAndSend("/topic/controlAccesoVisita", jsonSendSocket.toString());

            // JSONObject json = new JSONObject(responseBody);
            // Boolean jsonError = json.getBoolean("error");
            // if (jsonError) {
            // return false;
            // }

            return true;
        }

        return false;
    }

    // @Scheduled(fixedRate = 2000 * 60, initialDelay = 1000 * 60)
    // @Scheduled(fixedRate = 1000 * 3, initialDelay = 1000 * 3)
    @Scheduled(fixedRateString = "${parametro.intervalo.milisegundos}", initialDelay = 3000)
    public void leerDirectorioIntervalo() {
        System.out.println("\n\n\n\n\n[" + JeBoot.getFechaStringHHMMSS(new Date()) + "] Leer Directorio: "
                + directorioLeer + ", puesto: " + idPuesto);

        try {
            File directorioScan = new File(directorioLeer.replace("/", File.separator));

            if (directorioScan != null && directorioScan.isDirectory()) {
                if (directorioScan.list().length == 0) {
                    System.out.println("--- vacio ---");
                } else {

                    // Page1
                    File page1 = new File(
                            directorioLeer.replace("/", File.separator) + File.separator + "Page1");
                    if (!page1.exists()) {

                        System.out.println("--- vacio sin Page1---");

                    } else {// si existe el Page1
                        File archivoReferencia = new File(
                                directorioLeer.replace("/", File.separator) + File.separator
                                        + "escaneadoPorSpring.txt");
                        if (archivoReferencia.exists()) {// ya fue escaneado
                            // eliminar

                            if (eliminarContenidoDirectorio.equalsIgnoreCase("S")) {
                                try {
                                    eliminarContenidoDirectorio(directorioScan);
                                    System.out.println("Directorio eliminado");
                                } catch (Exception e) {
                                    log.warn("Error al eliminar directorio scan", e);
                                }
                            }

                        } else { // no fue escaneado

                            if (eliminarContenidoDirectorio.equalsIgnoreCase("S")) {
                                try {
                                    // crear archivo referencia
                                    if (archivoReferencia.createNewFile()) {
                                        System.out.println("Crear archivo referencia: escaneadoPorSpring.txt");
                                    } else {
                                        System.out
                                                .println("No se pudo crear archivo referencia: escaneadoPorSpring.txt");
                                    }
                                } catch (IOException e) {
                                    log.warn("Error al crear archivo referencia", e);
                                }
                            }

                            System.out.println("\nObtener datos de API\n");
                            obtenerDatosArchivo();
                            System.out.println("\n Fin de Obtener datos de API\n\n\n\n\n\n\n\n\n");

                        }
                    }

                }
            } else {
                System.out.println("La ruta no apunta a un directorio");
            }

        } catch (Exception e) {

            log.error("Error al leerDirectorioIntervalo", e);
        }

    }

    private static void eliminarContenidoDirectorio(File directorio) {
        File[] archivosEnDirectorio = directorio.listFiles();
        if (archivosEnDirectorio != null) {
            for (File archivoEnDirectorio : archivosEnDirectorio) {
                if (archivoEnDirectorio.isDirectory()) {
                    eliminarContenidoDirectorio(archivoEnDirectorio);
                }
                archivoEnDirectorio.delete();
            }
        }
    }

}
