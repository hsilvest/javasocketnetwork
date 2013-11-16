package aulasd.tp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Enumeration;
import java.util.Vector;



public class TCPServer extends Thread {

	private Socket conexao;
	private static Vector clientes;
	private String nomeCliente;

	public TCPServer(Socket socket) {
		this.conexao = socket;
	}

	public static void main(String[] args) throws Exception {
		clientes = new Vector<>();
		try {
			ServerSocket welcomeSocket = new ServerSocket(6787);
			System.out.println("Servidor rodando na porta 6787");
			while (true) {
				Socket connectionSocket = welcomeSocket.accept();
				Thread thread = new TCPServer(connectionSocket);
				thread.start();
			}
		} catch (IOException ex) {
			System.out.println("Erro: " + ex);
		}
	}

	@Override
	public void run() {
		try {
			BufferedReader inFromClient = new BufferedReader(
					new InputStreamReader(this.conexao.getInputStream()));

			PrintStream outToClient = new PrintStream(
					this.conexao.getOutputStream());

			this.nomeCliente = inFromClient.readLine();
			System.out.println(this.nomeCliente + " Conectou ao servidor");
			
			clientes.add(outToClient);
			
			String msg = inFromClient.readLine();
			while (!msg.toUpperCase().equals("EXIT")) {
				broadCast(outToClient, " escreveu ", msg);
				msg = inFromClient.readLine();
			}
			System.out.println(this.nomeCliente + " Desconectou do servidor");
			broadCast(outToClient, " Desconectou ", "do servidor");
			clientes.remove(outToClient);
			this.conexao.close();
		} catch (IOException ex) {
			System.out.println("Erro: " + ex);
		}
	}

	public void broadCast(PrintStream out, String action, String msg)
			throws IOException {
		Enumeration elementos = (Enumeration) clientes.elements();
		while (elementos.hasMoreElements()) {
			PrintStream outToClient = (PrintStream) elementos.nextElement();
			if (outToClient != out) {
				outToClient.println(this.nomeCliente + action + msg);
			}

		}
	}

}
