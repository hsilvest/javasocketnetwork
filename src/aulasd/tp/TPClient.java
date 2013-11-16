package aulasd.tp;

import java.io.*;
import java.net.*;

public class TPClient extends Thread {

	private Socket conexao;

	public TPClient(Socket clientSocket) {
		this.conexao = clientSocket;
	}
	static long startTime;

	public static void main(String[] args) throws Exception {
		try {
			Socket clientSocket = new Socket("127.0.0.1", 6787);
			
			PrintStream outToServer = new PrintStream(
					clientSocket.getOutputStream());
			
			BufferedReader inFromUser = new BufferedReader(
					new InputStreamReader(System.in));
			
			System.out.print("Cliente: ");
			String nome = inFromUser.readLine();
			outToServer.println(nome);
			
			Thread thread = new TPClient(clientSocket);
			thread.start();
			String msg;
			while (true) {
				while (true) {
					System.out.print("Mensagem: ");
					msg = inFromUser.readLine();
					outToServer.println(msg);
					startTime = System.nanoTime();
				}
			}
		} catch (IOException ex) {
			System.out.println("Erro: " + ex);
		}

	}

	@Override
	public void run() {
		try {
			BufferedReader inFromServer = new BufferedReader(
					new InputStreamReader(this.conexao.getInputStream()));
			String msg;
			while (true) {
				msg = inFromServer.readLine();
				long endTime = System.nanoTime();
				if(msg == null){
					System.out.println("Conexao encerrada!");
					System.exit(0);
				}
				System.out.println();
				System.out.println(msg + " - Tempo de Processo: " + Math.round((endTime - startTime)/1e6) + " Nano segundos");
				System.out.print("Mensagem: ");
			}
		} catch (IOException ex) {
			System.out.println("Erro: " + ex);
		}

	}

}
