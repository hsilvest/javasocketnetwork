package aulasd.tp;

import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.Enumeration;

public class TCPServer extends Thread {

	private Socket conexao;
	private static ArrayList<DataOutputStream> clientes = new ArrayList<>();

	public TCPServer(Socket socket) {
		this.conexao = socket;
	}

	public static void main(String[] args) throws Exception {
		try {
			ServerSocket welcomeSocket = new ServerSocket(6787);
			System.out.println("Servidor rodando na porta 6788");
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
			DataOutputStream outToClient = new DataOutputStream(
					this.conexao.getOutputStream());
			clientes.add(outToClient);
			String mensagem = inFromClient.readLine();
			while (!mensagem.toUpperCase().equals("exit")) {
				broadCast(outToClient, "escreveu", mensagem);
				mensagem = inFromClient.readLine();
			}
			clientes.remove(outToClient);
			this.conexao.close();
		} catch (IOException ex) {
			System.out.println("Erro: " + ex);
		}
	}

	public void broadCast(DataOutputStream out, String action, String msg) throws IOException {
		Enumeration elementos = (Enumeration) clientes;
		while (elementos.hasMoreElements()) {
			DataOutputStream envia = (DataOutputStream) elementos.nextElement();
			if(envia != out){
				envia.writeBytes(msg);
			}
			
		}
	}

}
