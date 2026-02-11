import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

// Desafio: Criar um endpoint no localhost:8080 que retorna a data de agora
class MeuDataHandler implements HttpHandler {

    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        OutputStream outputStream = httpExchange.getResponseBody();
        LocalDate hoje = LocalDate.now();
        var formato = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String dataFormatada = formato.format(hoje);
        String jsonResponse = "{\"data\":\"" + dataFormatada + "\"}";

        httpExchange.getResponseHeaders().set("Content-Type", "application/json; charset=UTF-8");

        httpExchange.sendResponseHeaders(200, jsonResponse.length());

        outputStream.write(jsonResponse.getBytes());
        outputStream.flush();
        outputStream.close();

    }

}


public class Main {

    public static void main(String[] args) throws Exception {
        // Cria um servidor HTTP, que recebe requisições em localhost no port 8080 (e com backlog 0)
        HttpServer srv = HttpServer.create(new InetSocketAddress("localhost", 8080), 0);

        // Cria um contexto para processar requisições em URIs iniciadas com "/"
        // O tratador de requisições é um objeto MeuHandler
        srv.createContext("/data", new MeuDataHandler());
        // Executa o servidor HTTP
        srv.start();

    }
}

class Cliente {
    Socket clientSocket;
    PrintWriter out;
    BufferedReader in;

    public Cliente(String ip, int port) throws IOException {
        // cria um socket para se conectar com o ip e port indicados
        clientSocket = new Socket(ip, port);

        // cria um PrintWriter para facilitar o envio de mensagens
        out = new PrintWriter(clientSocket.getOutputStream(), true);

        // cria um BufferedReader para facilitar a recepção de dados
        in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
    }

    // envia uma mensagem, e espera uma resposta, a qual é retornada como resultado
    public String envia(String msg) throws IOException{
        out.println(msg);
        String resp = in.readLine();
        return resp;
    }
}

