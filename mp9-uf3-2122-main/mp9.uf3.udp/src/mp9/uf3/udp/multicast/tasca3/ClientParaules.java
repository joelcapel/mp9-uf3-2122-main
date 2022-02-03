package mp9.uf3.udp.multicast.tasca3;

import java.io.IOException;
import java.net.*;
import java.nio.ByteBuffer;
import java.util.*;

public class ClientParaules {
/* Client afegit al grup multicast SrvVelocitats.java que representa un velocímetre */

	private boolean continueRunning = true;
    private MulticastSocket socket;
    private InetAddress multicastIP;
    private int port;
    private NetworkInterface netIf;
    private InetSocketAddress group;
    private Map<String,Integer> mapParaules;
    private List<String> escollides;


	public ClientParaules(int portValue, String strIp) throws IOException {
		multicastIP = InetAddress.getByName(strIp);
        port = portValue;
        socket = new MulticastSocket(port);
        netIf = socket.getNetworkInterface();
        group = new InetSocketAddress(strIp,portValue);
        mapParaules = new HashMap<>();
        escollides = new ArrayList<>();
	}

	public void runClient() throws IOException{
        DatagramPacket packet;
        byte [] receivedData = new byte[1024];
        
        socket.joinGroup(group,netIf);
        System.out.printf("Connectat a %s:%d%n",group.getAddress(),group.getPort());
        
        while(continueRunning){
           packet = new DatagramPacket(receivedData, 1024);
           socket.setSoTimeout(5000);
           try{
                socket.receive(packet);
                continueRunning = getData(packet.getData(), packet.getLength());
            }catch(SocketTimeoutException e){
                System.out.println("S'ha perdut la connexió amb el servidor.");
                continueRunning = false;
            }
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        socket.leaveGroup(group,netIf);
        socket.close();
    }
	
	protected boolean getData(byte[] data, int length) {
		String p = new String(data,0, length);
        if(!escollides.contains(p)) {
            mapParaules.computeIfPresent(p, (k, v) -> v + 1);
            mapParaules.putIfAbsent(p, 1);
            mapParaules.forEach((k,v) -> System.out.printf("%s:%d ",k,v));
            System.out.println();

        }
		return true;
    }
	
	public static void main(String[] args) throws IOException {
		ClientParaules cvel = new ClientParaules(5557, "224.0.10.10");
		cvel.runClient();
		System.out.println("Parat!");

	}

}
