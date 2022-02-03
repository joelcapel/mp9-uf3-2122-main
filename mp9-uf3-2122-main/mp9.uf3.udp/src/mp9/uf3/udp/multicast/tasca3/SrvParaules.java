package mp9.uf3.udp.multicast.tasca3;

import mp9.uf3.udp.multicast.exemple.Velocitat;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.util.Arrays;
import java.util.List;

public class SrvParaules {
/* Servidor Multicast que ens comunica la velocitat que porta d'un objecte */

	private MulticastSocket socket;
	private InetAddress multicastIP;
	private int port;
	private boolean continueRunning = true;
	private Velocitat simulator;
	private List<String> llistaParaules;

	public SrvParaules(List<String> paraules, int portValue, String strIp) throws IOException {
		 socket = new MulticastSocket(portValue);
		 multicastIP = InetAddress.getByName(strIp);
		 port = portValue;
		 simulator = new Velocitat(200);
		 llistaParaules = paraules;
	}

	public void runServer() throws IOException{
		DatagramPacket packet;
		byte [] sendingData;
		 
		while(continueRunning){
			sendingData = getParaula();
			packet = new DatagramPacket(sendingData, sendingData.length,multicastIP, port);
			socket.send(packet);
		 	try {
				Thread.sleep(1000);
			} catch (InterruptedException ex) {
				ex.getMessage();
			}
		}
		socket.close();
	}

	private byte[] getParaula() {
		String p = llistaParaules.get((int)(Math.random()*llistaParaules.size()));
		System.out.println(p);
		return p.getBytes();
	}

	public static void main(String[] args) throws IOException {
		List<String> paraules = Arrays.asList("Interface","Class","Integer","Implements","extends","Double");
		SrvParaules srvVel = new SrvParaules(paraules, 5557, "224.0.10.10");
		srvVel.runServer();
		System.out.println("Parat!");
	}

}
