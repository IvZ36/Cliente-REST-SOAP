import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

import javax.ws.rs.core.MultivaluedMap;

public class ClienteRest {
    public ClienteRest ()
    {

    }
    public String listarurls (String usuario) {
        Client cliente = Client.create();
        WebResource recursos = cliente.resource("http://localhost:4567/rest/listaurl/" + usuario);

        ClientResponse respuesta = recursos.accept("application/json").get(ClientResponse.class);

        String salida = respuesta.getEntity(String.class);
        return salida;
    }

    public String crearurl (MultivaluedMap formData)
    {
        Client cliente = Client.create();
        WebResource recursos = cliente.resource("http://localhost:4567/rest/acortar");
        ClientResponse respuesta = recursos.type("application/x-www-form-urlencoded").post(ClientResponse.class, formData);
        String salida = respuesta.getEntity(String.class);
        return salida;
    }

}
