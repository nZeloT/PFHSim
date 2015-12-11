package net;

import java.net.Socket;

import net.shared.ClientMessage;
import net.shared.Connection;
import net.shared.ServerMessage;

//------------------for no having the UI freeze
public class ServerConnection extends Connection<ClientMessage, ServerMessage>{

	public ServerConnection(Socket s) {
		super(s);
	}

	@Override
	public void run(){
		try{
			while(!closed){
				//wait for the server to send info
				ServerMessage serv = (ServerMessage)in.readObject();

				synchronized (ansLock) {
					ans = serv;
				}

				ClientMessage clnt = null;
				do{
					synchronized (msgLock) {
						clnt = msg;
						msg = null;
					}
					
					Thread.sleep(500);
				}while(clnt == null && !closed);

				if(clnt != null){
					out.writeObject(clnt);
					out.flush();
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}

}
