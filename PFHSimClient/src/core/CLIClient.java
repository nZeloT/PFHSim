package core;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

import bsh.EvalError;
import bsh.Interpreter;
import bsh.NameSpace;
import net.ServerConnection;
import net.shared.ClientMessage;
import net.shared.ServerMessage;
import sim.Enterprise;
import sim.procurement.ResourceMarket;

public class CLIClient {
	private static final int SERVER_PORT = 44444;

	//this is a command line client for testing purposes
	public static void main(String[] args) throws Exception{
		CLIClient clnt = new CLIClient();
		clnt.gameLoop();
	}

	private BufferedReader userIn;
	private ServerConnection server;

	private ResourceMarket market;
	private Enterprise e;
	
	private Interpreter inter;

	public CLIClient() {
		userIn = new BufferedReader(new InputStreamReader(System.in));
		inter = new Interpreter();
		NameSpace space = inter.getNameSpace();
		space.importPackage("sim");
		space.importPackage("sim.hr");
		space.importPackage("sim.procurement");
		space.importPackage("sim.production");
		space.importPackage("sim.research.dev");
		space.importPackage("sim.warehouse");
		space.importPackage("sim.simulation.sales");
		space.importPackage("core");
	}

	public void gameLoop() throws Exception{
		setup();
		boolean finish = false;
		do{

			//1. wait for the server to send info
			System.out.println("Waiting for Server input ...");
			while(!server.hasAnswered()){
				Thread.sleep(500);
			}

			ServerMessage msg = server.retrieveAnswer();

			if(market == null){
				//first message we received setup market and enterprise
				market = new ResourceMarket(msg.getNewResourcePrices());
				e = new Enterprise(market);

				inter.set("ent", e);
				inter.set("market", market);
			}else{
				//2. process the simulation
				System.out.println("Processing Input ...");
				finish = msg.hasGameEnded();
				if(!finish){
					market.setNewResourcePrices(msg.getNewResourcePrices());
					e.doSimulationStep(msg.getSoldOfferAmounts());
				}
			}

			if(!finish){
				//3. process user action
				processUserInput();

				//4. collect info to send to server
				System.out.println("Sending Client Info ...");
				ClientMessage clnt = new ClientMessage(market.getSoldResources(), e.getOffers());
				server.placeMessasge(clnt);
			}
		}while(!finish);

		shutdown();
	}

	private void processUserInput(){
		boolean running = true;
		do{
			try {
				System.out.println("Enter command:");
				String input = userIn.readLine();
				if(input.equals("finish"))
					running = false;
				else
					System.out.println(inter.eval(input));
			} catch (IOException e) {
				e.printStackTrace();
			} catch (EvalError e) {
				e.printStackTrace();
			}
		}while(running);
	}

	private void setup() throws Exception{
		System.out.println("Hi. This is the CLI Client for Testing. Enjoy.");
		System.out.println("Enter the Server IP: ");

		String serverIp = userIn.readLine();

		Socket s = new Socket(serverIp, SERVER_PORT);

		server = new ServerConnection(s);
		server.start();

		System.out.println("Alright. Setup is finished.");
	}

	private void shutdown() throws Exception{
		server.close();
		server.join();
		server = null;

		System.out.println("Goodbye!");
	}

}
