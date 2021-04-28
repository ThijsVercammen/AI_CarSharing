package main;

import java.util.concurrent.Callable;

public class OplThread implements Callable<Oplossing>{
	
	Oplossing o;
	
	public OplThread(Oplossing o) {
		this.o = o;
	}
	
	@Override
	public Oplossing call() throws Exception {
		//Hier code die moet uitgevoerd worden door thread
		// -> Auto verplaatsen
		// -> Oplossing valideren
		return o;
	}

}
