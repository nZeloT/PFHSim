package net;

import java.net.Socket;

import net.shared.ClientMessage;
import net.shared.Connection;
import net.shared.ServerMessage;

public class ClientConnection extends Connection<ServerMessage, ClientMessage>{

	public ClientConnection(Socket s, ServerMessage setup) {
		super(s);
		msg = setup;
		ans = null;
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
					} catch (Exception e) {
						e.printStackTrace();
					}
					synchronized (ansLock) {
						ans = clnt;
					}
				}

				//wait for the simulation to continue and place a new message
				Thread.sleep(500);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}

}
