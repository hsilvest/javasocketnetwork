package aulasd.tp;

// Abre conex�o com o servidor
// L� uma linha do teclado
// Envia a linha ao servidor
// L� uma linha do servidor e mostra no v�deo
// Fecha conex�o com o servidor
import java.io.*; // classes para input e output streams
import java.net.*; // classes para socket, serversocket e clientsocket

public class TPClient {

	public static void main(String[] args) throws Exception {
		String sentence;
		String modifiedSentence;
		// cria o stream do teclado
		BufferedReader inFromUser = new BufferedReader(new InputStreamReader(System.in));
		// cria o socket de acesso ao server hostname na porta 6789
		Socket clientSocket = new Socket("127.0.0.1", 6997);
		// cria os streams (encadeamentos) de entrada e saida com o servidor
		DataOutputStream outToServer = new DataOutputStream(clientSocket.getOutputStream());
		BufferedReader inFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
		// le uma linha do teclado e coloca em sentence
		sentence = inFromUser.readLine();
		// envia a linha para o server
		outToServer.writeBytes(sentence + '\n');
		// l� uma linha do server
		modifiedSentence = inFromServer.readLine();
		// apresenta a linha do server no v�deo
		System.out.println("FROM SERVER " + modifiedSentence);
		// fecha o cliente
		clientSocket.close();
	}

}
