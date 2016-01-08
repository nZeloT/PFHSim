package core;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import net.ClientConnection;
import net.shared.ClientMessage;
import net.shared.ServerMessage;

public class Server {

	///////////////////////////////////////////////////////////////////////////
	public static void main(String[] args) {
		Server s = new Server(); 
		s.gameLoop();
	}
	///////////////////////////////////////////////////////////////////////////

	private BufferedReader userIn;
	private List<ClientConnection> clients;

	private ServerSimulation sim;

	public Server() {
		userIn = new BufferedReader(new InputStreamReader(System.in));
		sim = new ServerSimulation();
	}

	public void gameLoop(){
		setup();

		do{
			System.out.println("Waiting for Client Input ...");
			List<ClientMessage> clntMsg = new ArrayList<>();
			//1. Receive the round details from the clients
			for (int i = 0; i < clients.size(); i++) {
				//wait for every client to send the answer
				while(!clients.get(i).hasAnswered() && !clients.get(i).isClosed()){
					try {
						Thread.sleep(750);
					} catch (InterruptedException e) {
						//wouldn't be nice but ok
						e.printStackTrace();
					}

				}

				//he has answered. save the answer and wait for the next one
				clntMsg.add(clients.get(i).retrieveAnswer());
				System.out.println("Message received");
			}

			//2. check if still all clients are in game and no client has send the limit reached flag
			for (int i = 0; i < clntMsg.size(); i++) {
				if(clntMsg.get(i) == null || clntMsg.get(i).isCashOnLimit() || clients.get(i).isClosed()){
					clntMsg.remove(i);
					try{ clients.get(i).join(); }catch(Exception e){e.printStackTrace();}
					clients.remove(i--);
					System.out.println("Client disconnected.");
				}
			}

			if(clntMsg.size() > 0){
				//3. pass the data to the simulation
				System.out.println("Processing input data ...");
				ServerMessage[] servMsg = sim.simulationStep(clntMsg.toArray(new ClientMessage[0]));

				//4. Get the respective message for each client
				System.out.println("Sending Server Info ...");
				for (int i = 0; i < clients.size(); i++) {
					clients.get(i).placeMessasge(servMsg[i]);
				}
			}else
				System.out.println("No more clients connected ...");

		}while(!sim.isFinished() && clients.size() > 0);

		shutdown();
	}

	private void setup(){
		System.out.println("Hi there. This is PFHSimServer talking to you. Welcome to a new round.");
		System.out.println("======================================================================");
		System.out.println();
		System.out.println("Let's begin with the setup.");
		System.out.println("How many of you are going to attend this session?");

		int clientCount = readInt("I'm sorry, but I didn't get it right. Could you please repeat?", 1, Integer.MAX_VALUE);
		clients = new ArrayList<>();

		ServerMessage setupMsg = sim.getSetupMessage();

		ServerSocket ss = null;
		try {
			ss = new ServerSocket(44444);
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(-1);
		}

		System.out.println("");
		System.out.println("Thanks for your input. Waiting for " + clientCount + " happy Players");
		System.out.println("to connect to Adress " + ss.getInetAddress().getHostAddress());
		System.out.println();

		//waiting for players to connect ...
		int count = 0;
		try{
			while(count < clientCount){
				Socket newClient = ss.accept();
				ClientConnection conn = new ClientConnection(newClient, setupMsg);
				conn.start();
				clients.add(conn);
				count++;
				System.out.println(count + " of " + clientCount + " successfully connected. Still waiting.");
			}

			ss.close();
		}catch(IOException e){
			//some error with the server socket; this should not happen => terminate
			e.printStackTrace();
			System.exit(-1);
		}

		System.out.println();
		System.out.println("======================================================================");
		System.out.println("Oh great. Everyone is connected and ready to go. Have fun :)");
	}

	private void shutdown(){
		System.out.println("======================================================================");
		System.out.println("It was a great time playing with you. Be sure to return soon.");

		for (int i = 0; i < clients.size(); i++) {
			clients.get(i).close();
			try {
				clients.get(i).join();
			} catch (InterruptedException e) {
				//we really don't care much here. as long as it is dead in the end :D
				e.printStackTrace();
			}
			clients.remove(i--);
		}

		try {
			userIn.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		System.out.println("Cheers ;)");
	}

	private int readInt(String errorMsg, int min, int max){
		boolean repeat = false;
		int n = 0;
		do{
			repeat = false;
			try {
				String input = userIn.readLine();
				n = Integer.parseInt(input);

				if(n < min || n > max){
					repeat = true;
					System.out.println(errorMsg);
				}

			} catch (NumberFormatException e) {
				//error to go another round
				repeat = true;
				System.out.println(errorMsg);
			} catch (IOException e) {
				//error to terminate with
				e.printStackTrace();
				System.exit(-1);
			}
		}while(repeat);
		return n;
	}

}
