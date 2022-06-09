package connection;

import java.io.IOException;
import java.lang.reflect.Array;
import java.net.*;
import java.nio.ByteBuffer;
import java.util.Arrays;

import com.google.gson.stream.JsonToken;
import exceptions.MaxSizeBufferException;
import org.springframework.util.SerializationUtils;
public class CommunicationUDP {
    private final int PACKET_MAX_LENGTH = 2400;
    private DatagramSocket udpSocket;
    private byte[] msgBuffer;
    private int serverPort;
    private InetAddress serverIPAddress;

    public CommunicationUDP(int clientPort, int serverPort, InetAddress serverIPAddress) throws SocketException{
        this.serverPort = serverPort;
        this.serverIPAddress = serverIPAddress;
        udpSocket = new DatagramSocket(clientPort);
    }
    public static byte[][] divideArray(byte[] source, int chunksize) {
//2048

        byte[][] ret = new byte[(int)Math.ceil(source.length / (double)chunksize)][chunksize];

        int start = 0;

        for(int i = 0; i < ret.length; i++) {
            ret[i] = Arrays.copyOfRange(source,start, start + chunksize);
            start += chunksize ;
        }
        return ret;
    }

    public void send(NetPackage netPackage) throws IOException {
        msgBuffer = SerializationUtils.serialize(netPackage);
        byte[][] msgBuf2 = divideArray(msgBuffer,2048);
        System.out.println(msgBuf2.length);
        for (int i = 0; i<msgBuf2.length;i++) {
            BufferPacket bp;
            if (i+1==msgBuf2.length)
                bp = new BufferPacket(msgBuf2[i],true);
            else
                bp = new BufferPacket(msgBuf2[i],false);
            byte[] msgBuf = SerializationUtils.serialize(bp);
            DatagramPacket packet = new DatagramPacket(msgBuf, msgBuf.length);
            packet.setAddress(serverIPAddress);
            packet.setPort(serverPort);
            udpSocket.send(packet);
        }
    }

    public <T> T receive() throws IOException,IllegalArgumentException{
        try{
            msgBuffer = new byte[PACKET_MAX_LENGTH];
            DatagramPacket packet = new DatagramPacket(msgBuffer, PACKET_MAX_LENGTH);
            packet.setAddress(serverIPAddress);
            packet.setPort(serverPort);
            udpSocket.setSoTimeout(5000);
            udpSocket.receive(packet);
            T response = (T)SerializationUtils.deserialize(packet.getData());
            return response;
        }
        catch (SocketTimeoutException ex){
            throw new SocketTimeoutException();
        }
        catch (IOException ex){
            throw new IOException();
        }
        catch (IllegalArgumentException ex){
            ex.printStackTrace();
            System.err.println("Object couldn't be deserialised");
           throw new IllegalArgumentException();
        }
    }
    public void close(){
        udpSocket.close();
    }
}


//public class CommunicationUDP<T> {
//    private static int PACKET_MAX_LENGTH = 1024;
//    public static byte[] joinTwoArrays(byte[] a, byte[] b) {
//        Byte[] aB = ArrayUtils.toObject(a);
//    }
//        return  Stream.concat(Arrays.stream(a), Arrays.stream(b)).toArray(byte[]::new);
//
//
//        public static NetResponse  sendAndReceive(NetPackage netPackage, int serverPort, InetAddress serverIPAddress) throws IOException {
//
//        byte[] msgBuffer = SerializationUtils.serialize(netPackage);
//
//        int length = msgBuffer.length;
//        if (length > PACKET_MAX_LENGTH) {
//            length = PACKET_MAX_LENGTH;
//        }
//        DatagramPacket packet = new DatagramPacket(msgBuffer, length);
//        packet.setAddress(serverIPAddress);
//        packet.setPort(serverPort);
//        try(DatagramSocket udpSocket = new DatagramSocket()){
//        udpSocket.send(packet);
//        udpSocket.setSoTimeout(5000);
//        msgBuffer = new byte[PACKET_MAX_LENGTH];
//        packet = new DatagramPacket(msgBuffer, msgBuffer.length);
//        udpSocket.receive(packet);
//        }
//        catch (SocketTimeoutException e){
//            System.err.println("Время ожидания ответа от сервера истекло");
//            return null;
//        }
//        catch (IOException e) {
//            return null;
//        }
//        return (T) SerializationUtils.deserialize(packet.getData());
//    }

//    public static NetResponse receive(int clientPort) {
//        byte[] msgBuffer = new byte[PACKET_MAX_LENGTH];
//
//        DatagramPacket packet = new DatagramPacket(msgBuffer, msgBuffer.length);
//        try(DatagramSocket udpSocket = new DatagramSocket()){
//            udpSocket.setSoTimeout(5000);
//            udpSocket.receive(packet);}
//        catch (SocketTimeoutException e){
//            System.err.println("Время ожидания ответа от сервера истекло");
//            return null;
//        }
//        catch (IOException e){
//            System.err.println("Ошибка чтения пакета");
//            return null;
//        }
//        return (NetResponse) SerializationUtils.deserialize(packet.getData());
//    }

