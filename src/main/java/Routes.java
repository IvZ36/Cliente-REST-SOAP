import com.sun.jersey.core.util.MultivaluedMapImpl;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.Version;
import servicios.ServicioSOAP;
import servicios.ServicioSOAPService;
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

public class Enrutamiento {
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

            get("/urls-rest/:usuario", (req, res) -> {
                String usuario = req.params("user");
                Template template = configuration.getTemplate("spark.template.freemarker/crear-rest.ftl");
                StringWriter writer = new StringWriter();
                Map<String, Object> atributos = new HashMap<>();

                ClienteRest rest = new ClienteRest();

                String salida = rest.listarurls(usuario);

                if (!salida.replaceAll("\"", "").equals("")) {
                    salida = salida.replace('[', ' ');
                    salida = salida.replace(']', ' ');
                    String[] salidasAux = salida.split("\"");

                    List<String> listaSalidasAux = new ArrayList<>();

                    for (int i = 0; i < salidasAux.length; i++) {
                        if (!salidasAux[i].equals(" ") && !salidasAux[i].equals(",")) {
                            listaSalidasAux.add(salidasAux[i]);
                        }
                    }

                    List<String []> salidas = new ArrayList<>();

                    for (String x : listaSalidasAux) {
                        salidas.add(x.split(","));
                    }

                    atributos.put("salidas", salidas);
                    template.process(atributos, writer);

                    return writer;
                } else {
                    setFlashMessage(req, "No se pudo consultar posts. El usuario no existe.");
                    res.redirect("/");
                    return null;
                }
            });

            get("/crear-post", (req, res) -> {
                StringWriter writer = new StringWriter();
                Map<String, Object> atributos = new HashMap<>();
                Template template = configuration.getTemplate("spark.template.freemarker/crear-rest.ftl");

                template.process(atributos, writer);

                return writer;
            });

            post("/crear-post", (req, res) -> {
                StringWriter writer = new StringWriter();
                Map<String, Object> atributos = new HashMap<>();
                Template template = configuration.getTemplate("spark.template.freemarker/crear-rest.ftl");

                java.sql.Date tiempoAhora = new Date(System.currentTimeMillis());

                Path tempFile = Files.createTempFile(uploadDir.toPath(), "", "");

                req.attribute("org.eclipse.jetty.multipartConfig", new MultipartConfigElement("/temp"));

                String texto = req.queryParams("texto");
                String usuario = req.queryParams("usuario");

                try (InputStream input = req.raw().getPart("imagen").getInputStream()) {
                    Files.copy(input, tempFile, StandardCopyOption.REPLACE_EXISTING);
                }

                MultivaluedMap<String, String> formulario = new MultivaluedMapImpl();
                formulario.add("usuario", usuario);
                formulario.add("texto", texto);
                formulario.add("imagen", tempFile.getFileName().toString());

                ClienteRest rest = new ClienteRest();
                String salida = rest.crearurl(formulario);

                if(!salida.replaceAll("\"", "").equals("")) {
                    salida = salida.replaceAll("\"", "");
                    String[] salidas = salida.split(",");

                    atributos.put("texto", salidas[0]);
                    atributos.put("imagen", salidas[1]);
                    atributos.put("usuario", salidas[2]);
                    atributos.put("fecha", salidas[3]);
                    template.process(atributos, writer);

                    return writer;
                } else {
                    setFlashMessage(req, "No se pudo crear el post. El usuario no existe.");
                    res.redirect("/");
                    return null;
                }
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

            get("/posts/:usuario", (req, res) -> {
                String usuario = req.params("usuario");
                Template template = configuration.getTemplate("spark.template.freemarker/crear-soap.ftl");
                StringWriter writer = new StringWriter();
                Map<String, Object> atributos = new HashMap<>();

                ServicioSOAPService soap = new ServicioSOAPService();
                ServicioSOAP port = soap.getServicioSOAPPort();

                List<String> salida = port.getListadoPostPorUsuario(usuario);

                if (!salida.isEmpty()) {
                    List<String []> salidas = new ArrayList<>();

                    for (String x : salida) {
                        salidas.add(x.split(","));
                    }

                    atributos.put("salidas", salidas);
                    template.process(atributos, writer);

                    return writer;
                } else {
                    setFlashMessage(req, "No se pudo consultar posts. El usuario no existe.");
                    res.redirect("/");
                    return null;
                }
            });

            get("/crear-post", (req, res) -> {
                StringWriter writer = new StringWriter();
                Map<String, Object> atributos = new HashMap<>();
                Template template = configuration.getTemplate("spark.template.freemarker/crear-soap.ftl");

                template.process(atributos, writer);

                return writer;
            });


            post("/crear-post", (req, res) -> {
                StringWriter writer = new StringWriter();
                Map<String, Object> atributos = new HashMap<>();
                Template template = configuration.getTemplate("spark.template.freemarker/post-creado-soap.ftl");

                java.sql.Date tiempoAhora = new Date(System.currentTimeMillis());

                Path tempFile = Files.createTempFile(uploadDir.toPath(), "", "");

                req.attribute("org.eclipse.jetty.multipartConfig", new MultipartConfigElement("/temp"));

                String texto = req.queryParams("texto");
                String usuario = req.queryParams("usuario");

                try (InputStream input = req.raw().getPart("imagen").getInputStream()) {
                    Files.copy(input, tempFile, StandardCopyOption.REPLACE_EXISTING);
                }

                ServicioSOAPService soap = new ServicioSOAPService();
                ServicioSOAP port = soap.getServicioSOAPPort();
                String salida = port.crearPost(texto, usuario, tempFile.getFileName().toString());

                if (!salida.isEmpty()) {
                    salida = salida.replaceAll("\"", "");

                    String[] salidas = salida.split(",");

                    atributos.put("texto", salidas[0]);
                    atributos.put("imagen", salidas[1]);
                    atributos.put("usuario", salidas[2]);
                    atributos.put("fecha", salidas[3]);
                    template.process(atributos, writer);

                    return writer;
                } else {
                    setFlashMessage(req, "No se pudo crear el post. El usuario no existe.");
                    res.redirect("/");
                    return null;
                }
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
