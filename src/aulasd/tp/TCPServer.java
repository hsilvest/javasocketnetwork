package aulasd.tp;

//Aguarda pedido de conex�o de algum cliente
//L� uma linha do cliente
//Transforma as min�sculas em mai�sculas
//Envia ao cliente
//Volta para o in�cio
import java.io.*;
import java.net.*;

public class TCPServer {

	public static void main(String[] args) throws Exception {
		String clientSentence;
		String capitalizedSentence;
		// cria socket de comunica��o com os clientes na porta 6789
		ServerSocket welcomeSocket = new ServerSocket(6788);
		// espera msg de algum cliente e trata
		while (true) {
			// espera conex�o de algum cliente
			Socket connectionSocket = welcomeSocket.accept();
			// cria streams de entrada e saida com o cliente que chegou
			BufferedReader inFromClient = new BufferedReader(new InputStreamReader(connectionSocket.getInputStream()));
			DataOutputStream outToClient = new DataOutputStream(connectionSocket.getOutputStream());
			// l� uma linha do cliente
			clientSentence = inFromClient.readLine();
			// transforma a linha em mai�sculas
			capitalizedSentence = clientSentence.toUpperCase() + '\n';
			// envia a linha mai�scula para o cliente
			outToClient.writeBytes(capitalizedSentence);

		}
	}
}
