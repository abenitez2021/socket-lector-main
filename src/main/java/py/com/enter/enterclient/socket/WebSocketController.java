package py.com.enter.enterclient.socket;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Controller
public class WebSocketController {




    @MessageMapping("/ping")
    @SendTo("/topic/pong")
    public String handlePing() {
        return "pong";
    }

    

    @MessageMapping("/hello")
    @SendTo("/topic/controlAccesoVisita")
    public Greeting controlAccesoVisita(HelloMessage message) throws Exception {
        
        System.out.println("topic/controlAccesoVisitas IN");
        Thread.sleep(1000); // Simula un poco de retraso en el servidor
        System.out.println("topic/controlAccesoVisita OUT");

        return new Greeting("Hola, " + message.getName() + "!");
    }
}



@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
class HelloMessage {

    private String name;
    

    // Getters y setters
}

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
class Greeting {

    private String content;

    // Constructor, getters y setters
}
