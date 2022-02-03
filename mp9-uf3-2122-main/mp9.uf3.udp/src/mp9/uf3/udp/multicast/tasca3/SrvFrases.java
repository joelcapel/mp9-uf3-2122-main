package mp9.uf3.udp.multicast.tasca3;

import mp9.uf3.udp.multicast.exemple.Velocitat;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.util.Arrays;
import java.util.List;

public class SrvFrases{
    /* Servidor Multicast que ens comunica la velocitat que porta d'un objecte */

    private MulticastSocket socket;
    private InetAddress multicastIP;
    private int port;
    private boolean continueRunning = true;
    private Velocitat simulator;
    private List<String> llistaFrases;

    public SrvFrases(List<String> frases, int portValue, String strIp) throws IOException {
        socket = new MulticastSocket(portValue);
        multicastIP = InetAddress.getByName(strIp);
        port = portValue;
        simulator = new Velocitat(200);
        llistaFrases = frases;
    }

    public void runServer() throws IOException{
        DatagramPacket packet;
        byte [] sendingData;

        while(continueRunning){
            if (getFrase().length >8){
                sendingData = getFrase();
                packet = new DatagramPacket(sendingData, sendingData.length,multicastIP, port);
                socket.send(packet);
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException ex) {
                    ex.getMessage();
                }
            } else if (getFrase().length <8){
                System.out.println("NOOOOOOOOOOOO");
            }
        }
        socket.close();
    }

    private byte[] getFrase() {
        String p = llistaFrases.get((int)(Math.random()*llistaFrases.size()));
        System.out.println(p);
        return p.getBytes();
    }

    public static void main(String[] args) throws IOException {
        List<String> frases = Arrays.asList(
                "Hola soy Joel, que tal te encuentras en el dia de hoy?",
                "Adios Joel",
                "Hola soy Manel, como estas?",
                "Adios Manel",
                "Hola me llamo Joel, encantado de conocerte, tengo 24 años",
                "Hasta la vista Joel, ha sido un placer tenerte aqui");
        SrvFrases srvVel = new SrvFrases(frases, 5557, "224.0.10.10");
        srvVel.runServer();
        System.out.println("Parat!");
    }

}

