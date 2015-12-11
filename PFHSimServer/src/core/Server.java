package core;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

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
	private ClientConnection[] clients;
	
	private ServerSimulation sim;

	public Server() {
		userIn = new BufferedReader(new InputStreamReader(System.in));
		sim = new ServerSimulation();
	}
	
	public void gameLoop(){
		setup();
		
		do{
			System.out.println("Waiting for Client Input ...");
			ClientMessage clntMsg[] = new ClientMessage[clients.length];
			//1. Receive the round details from the clients
			for (int i = 0; i < clients.length; i++) {
				//wait for every client to send the answer
				while(!clients[i].hasAnswered()){
					try {
						Thread.sleep(750);
					} catch (InterruptedException e) {
						//wouldn't be nice but ok
						e.printStackTrace();
					}
				}
				
				//he has answered. save the answer and wait for the next one
				clntMsg[i] = clients[i].retrieveAnswer();
				System.out.println("\t" + (i+1) + "/" + clntMsg.length);
			}
			
			//2. pass the data to the simulation
			System.out.println("Processing input data ...");
			ServerMessage[] servMsg = sim.simulationStep(clntMsg);
			
			//3. Get the respective message for each client
			System.out.println("Sending Server Info ...");
			for (int i = 0; i < clients.length; i++) {
				clients[i].placeMessasge(servMsg[i]);
			}
			
		}while(!sim.isFinished());

		shutdown();
	}
	
	private void setup(){
		System.out.println("Hi there. This is PFHSimServer talking to you. Welcome to a new round.");
		System.out.println("======================================================================");
		System.out.println();
		System.out.println("Let's begin with the setup.");
		System.out.println("How many of you are going to attend this session?");

		int clientCount = readInt("I'm sorry, but I didn't get it right. Could you please repeat?", 1, Integer.MAX_VALUE);
		clients = new ClientConnection[clientCount];

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
				clients[count] = new ClientConnection(newClient, setupMsg);
				clients[count++].start();
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
		
		for (int i = 0; i < clients.length; i++) {
			clients[i].close();
			try {
				clients[i].join();
			} catch (InterruptedException e) {
				//we really don't care much here. as long as it is dead in the end :D
				e.printStackTrace();
			}
			clients[i] = null;
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
