package main;

import java.util.concurrent.Callable;

public class OplThread implements Callable<Oplossing>{
	
	//Oplossing o;
	Algoritme algo;
	int tijd;
	
	public OplThread(Algoritme algo, int tijd) {
		this.algo = algo;
		this.tijd = tijd;
	}
	
	@Override
	public Oplossing call() throws Exception {
		Oplossing o = this.algo.lokaalZoeken(this.tijd);
		//Hier code die moet uitgevoerd worden door thread
		// -> Auto verplaatsen
		// -> Oplossing valideren
		return o;
	}

}
