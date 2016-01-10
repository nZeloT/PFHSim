package net;

import java.net.Socket;

import net.shared.ClientMessage;
import net.shared.Connection;
import net.shared.ServerMessage;

public class ClientConnection extends Connection<ServerMessage, ClientMessage>{
	
	private String name;

	public ClientConnection(Socket s, ServerMessage setup) {
		super(s);
		msg = setup;
		ans = null;
		name = "Unset";
	}

	@Override
	public void run() {
		try{
			while(!closed){

				ServerMessage serv = null;
				synchronized (msgLock) {
					serv = msg;
					msg = null;
				}

				if(serv != null){
					ClientMessage clnt = null;
					try {
						out.writeObject(serv);
						out.flush();

						//wait for the client to answer
						clnt = (ClientMessage)in.readObject();
						name = clnt.getName();
					}catch (Exception e) {
						System.err.println(name);
						closed = true;
						e.printStackTrace();
					}
					
					synchronized (ansLock) {
						ans = clnt;
					}
				}

				//wait for the simulation to continue and place a new message
				Thread.sleep(500);
			}
		}catch(InterruptedException e){
			System.err.println(name);
			e.printStackTrace();
		}finally{
			closed = true;
		}
		
		System.err.println("Thread " + name + " ist Tod");
	}

}
