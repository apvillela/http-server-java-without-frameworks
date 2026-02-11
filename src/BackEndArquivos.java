import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.URI;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

class MeuArquivosHandler implements HttpHandler {

    @Override
    public void handle(HttpExchange httpExchange) throws IOException {

        URI uri = httpExchange.getRequestURI();
        String path = uri.getPath();
        OutputStream outputStream = httpExchange.getResponseBody();

        System.out.println("Recebemos uma chamada no path: " + path);
        if (path.equals("/index.html")){
            String htmlResponse = "index.html";
            httpExchange.getResponseHeaders().set("Content-Type","text/html");
            httpExchange.sendResponseHeaders(200, htmlResponse.length());
            outputStream.write(htmlResponse.getBytes());
            outputStream.flush();
            outputStream.close();
        } else if (path.equals("/pics/logo.png")) {

            return;
        } else if (path.equals("/")) {

            return;
            
        }

    }

}

public class BackEndArquivos {
    public static void main(String[]args) throws Exception {
        // Cria um servidor HTTP, que recebe requisições em localhost no port 8080 (e com backlog 0)
        HttpServer srv = HttpServer.create(new InetSocketAddress("localhost", 8080), 0);

        // Cria um contexto para processar requisições em URIs iniciadas com "/"
        // O tratador de requisições é um objeto MeuHandler
        srv.createContext("/", new MeuArquivosHandler());
        // Executa o servidor HTTP
        srv.start();
    }
}
