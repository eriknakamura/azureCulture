import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

public class checkIP implements Runnable {

	@Override
	public void run() {
		upm_i2clcd.Jhd1313m1 lcd = new upm_i2clcd.Jhd1313m1(2, 0x3E, 0x62);
		lcd.setColor((short) 0, (short) 255, (short) 0);
		String ipAddr = "";
		while (true) {
			try {
				Enumeration<NetworkInterface> eni;
				eni = NetworkInterface.getNetworkInterfaces();
				while (eni.hasMoreElements()) {
					NetworkInterface ni = eni.nextElement();
					Enumeration<InetAddress> inetAddresses = ni
							.getInetAddresses();

					while (inetAddresses.hasMoreElements()) {
						InetAddress ia = inetAddresses.nextElement();
						if (!ia.isLinkLocalAddress()) {
							if (ni.getName().equalsIgnoreCase("wlan0")) {
								ipAddr = ia.getHostAddress();
								lcd.write(0, 0, "IP Address: ");
								lcd.write(1, 0, ipAddr);
							}
						}
					}
				}
			} catch (SocketException e) {
				e.printStackTrace();
			}

		}
	}

}
