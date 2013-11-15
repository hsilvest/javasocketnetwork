package aulasd.tp;

//Aguarda pedido de conexão de algum cliente
//Lê uma linha do cliente
//Transforma as minúsculas em maiúsculas
//Envia ao cliente
//Volta para o início
import java.io.*;
import java.net.*;

public class TCPServer {

	public static void main(String[] args) throws Exception {
		String clientSentence;
		String capitalizedSentence;
		// cria socket de comunicação com os clientes na porta 6789
		ServerSocket welcomeSocket = new ServerSocket(6788);
		// espera msg de algum cliente e trata
		while (true) {
			// espera conexão de algum cliente
			Socket connectionSocket = welcomeSocket.accept();
			// cria streams de entrada e saida com o cliente que chegou
			BufferedReader inFromClient = new BufferedReader(new InputStreamReader(connectionSocket.getInputStream()));
			DataOutputStream outToClient = new DataOutputStream(connectionSocket.getOutputStream());
			// lê uma linha do cliente
			clientSentence = inFromClient.readLine();
			// transforma a linha em maiúsculas
			capitalizedSentence = clientSentence.toUpperCase() + '\n';
			// envia a linha maiúscula para o cliente
			outToClient.writeBytes(capitalizedSentence);

		}
	}
}
