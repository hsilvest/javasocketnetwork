package aulasd.tp;

import java.io.*;
import java.net.*;

public class TPClient extends Thread {

	private String sentence;
	private String modifiedSentence;
	private Socket conexao;

	public TPClient(Socket clientSocket) {
		this.conexao = clientSocket;
	}

	public static void main(String[] args) throws Exception {
		try {
			while (true) {
				Socket clientSocket = new Socket("127.0.0.1", 6787);

				DataOutputStream outToServer = new DataOutputStream(
						clientSocket.getOutputStream());
				BufferedReader inFromUser = new BufferedReader(
						new InputStreamReader(System.in));
				Thread thread = new TPClient(clientSocket);
				thread.start();
				String msg;
				while (true) {
					System.out.println("Mensagem: ");
					msg = inFromUser.readLine();
					outToServer.writeBytes(msg);
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
				if(msg.toUpperCase().equals("EXIT")){
					this.conexao.close();
				}
				System.out.println();
				System.out.println(msg);
				System.out.println("Mensagem: ");
			}
		} catch (IOException ex) {
			System.out.println("Erro: " + ex);
		}

	}

}
