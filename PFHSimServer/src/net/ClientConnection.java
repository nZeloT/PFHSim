package net;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import net.shared.ClientMessage;
import net.shared.ServerMessage;

public class ClientConnection extends Thread{
	
	private Socket s;
	private ObjectInputStream in;
	private ObjectOutputStream out;
	
	private boolean closed = true;
	
	private ServerMessage msg;
	private ClientMessage ans;
	
	public ClientConnection(Socket s, ServerMessage setup) {
		try {
			in = new ObjectInputStream(s.getInputStream());
			out = new ObjectOutputStream(s.getOutputStream());
			closed = false;
			msg = setup;
			ans = null;
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void run() {
		super.run();

		try{
			while(!closed){
				ServerMessage serv = null;
				synchronized (msg) {
					serv = msg;
					msg = null;
				}
				
				if(serv != null){
					ClientMessage clnt = doRoundTrip(serv);
					synchronized (ans) {
						ans = clnt;
					}
				}
				
				//wait for the simulation to finish and place a new message
				Thread.sleep(500);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	
	}
	
	private ClientMessage doRoundTrip(ServerMessage clnt){
		ClientMessage serv = null;
		if(!closed){
			try {
				out.writeObject(clnt);
				out.flush();
				serv = (ClientMessage)in.readObject();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return serv;
	}

	public void close(){
		try {
			closed = true;
			in.close();
			out.close();
			s.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void placeMessasge(ServerMessage msg){
		synchronized (this.msg) {
			this.msg = msg;
		}
	}
	
	public ClientMessage retrieveAnswer(){
		ClientMessage ans = null;
		synchronized (this.ans) {
			ans = this.ans;
		}
		return ans;
	}
	
	public boolean hasAnswered(){
		return ans != null;
	}
	
}
