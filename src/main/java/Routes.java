import com.sun.jersey.core.util.MultivaluedMapImpl;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.Version;
import spark.Request;

import javax.servlet.MultipartConfigElement;
import javax.ws.rs.core.MultivaluedMap;
import java.io.File;
import java.io.InputStream;
import java.io.StringWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.sql.Date;
import java.util.*;

import static spark.Spark.*;
import static spark.debug.DebugScreen.*;

public class Routes {
    private static final String FLASH_MESSAGE_KEY = "flash_message";

    public static void crearRutas() {
        port(getHerokuAssignedPort());

        final Configuration configuration = new Configuration(new Version(2, 3, 23));
        configuration.setClassForTemplateLoading(Main.class, "/");

        File uploadDir = new File("upload");
        uploadDir.mkdir();

        staticFiles.externalLocation("upload");
        staticFiles.location("/publico");

        get("/", (req, res) -> {
            StringWriter writer = new StringWriter();
            Map<String, Object> atributos = new HashMap<>();
            Template template = configuration.getTemplate("spark.template.freemarker/inicio.ftl");

            atributos.put("flashMessage", captureFlashMessage(req));

            template.process(atributos, writer);

            return writer;
        });

        path("/rest", () -> {
            get("/", (req, res) -> {
                StringWriter writer = new StringWriter();
                Map<String, Object> atributos = new HashMap<>();
                Template template = configuration.getTemplate("spark.template.freemarker/listar-rest.ftl");

                template.process(atributos, writer);

                return writer;
            });

            post("/listaurl", (req, res) -> {
                String usuario = req.queryParams("user");
                res.redirect("/urls-rest" + usuario);
                return null;
            });

        });

        path("/soap", () -> {
            get("/", (req, res) -> {
                StringWriter writer = new StringWriter();
                Map<String, Object> atributos = new HashMap<>();
                Template template = configuration.getTemplate("spark.template.freemarker/listar-soap.ftl");

                template.process(atributos, writer);

                return writer;
            });

            post("/listaurl", (req, res) -> {
                String usuario = req.queryParams("usuario");
                res.redirect("/soap/urls/" + usuario);
                return null;
            });

        });
    }

    private static int getHerokuAssignedPort() {
        ProcessBuilder processBuilder = new ProcessBuilder();
        if (processBuilder.environment().get("PORT") != null) {
            return Integer.parseInt(processBuilder.environment().get("PORT"));
        }

        return 8080;
    }

    private static void setFlashMessage(Request req, String message) {
        req.session().attribute(FLASH_MESSAGE_KEY, message);
    }

    private static String getFlashMessage(Request req) {
        if (req.session(false) == null) {
            return null;
        }

        if (!req.session().attributes().contains(FLASH_MESSAGE_KEY)) {
            return null;
        }

        return (String) req.session().attribute(FLASH_MESSAGE_KEY);
    }

    private static String captureFlashMessage(Request req) {
        String message = getFlashMessage(req);
        if (message != null) {
            req.session().removeAttribute(FLASH_MESSAGE_KEY);
        }
        return message;
    }
}
