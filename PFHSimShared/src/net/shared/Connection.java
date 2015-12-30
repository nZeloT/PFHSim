package net.shared;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public abstract class Connection<M extends Message, A extends Message> extends Thread {

	private Socket s;
	
	protected ObjectInputStream in;
	protected ObjectOutputStream out;
	
	protected boolean closed = true;
	
	protected M msg;
	protected A ans;
	
	protected Object msgLock = new Object();
	protected Object ansLock = new Object();
	
	public Connection(Socket s) {
		try {
			this.s = s;
			out = new ObjectOutputStream(s.getOutputStream());
			out.flush(); // omg
			in = new ObjectInputStream(s.getInputStream());
			closed = false;
			msg = null;
			ans = null;
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public abstract void run();
	
	public final void close(){
		try {
			closed = true;
			in.close();
			out.close();
			s.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public final void placeMessasge(M msg){
		synchronized (msgLock) {
			this.msg = msg;
		}
	}
	
	public final A retrieveAnswer(){
		A ans = null;
		synchronized (ansLock) {
			ans = this.ans;
			this.ans = null;
		}
		return ans;
	}
	
	public final boolean hasAnswered(){
		return ans != null;
	}
}
