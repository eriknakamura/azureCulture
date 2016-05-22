import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Enumeration;


public class AgrowBot {
  public static void main(String[] args) throws InterruptedException, UnknownHostException, IOException {
	  
	  
	  
	  upm_grove.GroveTemp tempSensor = new upm_grove.GroveTemp(0);
	  
	  (new Thread(new checkIP())).start();
	  (new Thread(new arm())).start();
	  
	  
	  ServerSocket temp = new ServerSocket(83);
	  while(true){
		  
		  Socket getTemp = temp.accept();
		  OutputStream tempStream = getTemp.getOutputStream();
		  DataOutputStream tempDos = new DataOutputStream(tempStream);
		  tempDos.writeDouble((((tempSensor.value()* 9) / 5) + 32));
		  
	  }
	  
  }

}