import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;


public class arm implements Runnable {
	upm_uln200xa.ULN200XA rotate = new upm_uln200xa.ULN200XA(4096, 1, 2, 3, 4);
	upm_uln200xa.ULN200XA lift = new upm_uln200xa.ULN200XA(4096, 8, 9, 10, 11);
	@Override
	public void run() {
		rotate.setSpeed(7);
		lift.setSpeed(7);
		
		double armRotateAmt = 0;
		double armLiftAmt = 0;
		try {
			
			ServerSocket armControlRotate = new ServerSocket(81);
			ServerSocket armControlLift = new ServerSocket(82);
			while(true){
				
				Socket armRotate = armControlRotate.accept();
				Socket armLift = armControlLift.accept();
				
				InputStream armRotateStream = armRotate.getInputStream();
				DataInputStream armRotateDIS = new DataInputStream(armRotateStream);
				
				InputStream armLiftStream = armLift.getInputStream();
				DataInputStream armLiftDIS = new DataInputStream(armLiftStream);
				
				armRotateAmt = armRotateDIS.readDouble();
				armLiftAmt = armLiftDIS.readDouble();
				
				if(armRotateAmt > 50){
					rotate.release();
					rotate.stepperSteps(4096);
					rotate.setDirection(upm_uln200xa.ULN200XA.ULN200XA_DIRECTION_T.DIR_CW);
				}
				else if(armRotateAmt < -50){
					rotate.release();
					rotate.stepperSteps(4096);
					rotate.setDirection(upm_uln200xa.ULN200XA.ULN200XA_DIRECTION_T.DIR_CCW);
				}
				
				if(armRotateAmt > -50 && armRotateAmt < 50){
					rotate.release();
				}
				
				if(armLiftAmt > 50){
					lift.release();
					lift.stepperSteps(4096);
					lift.setDirection(upm_uln200xa.ULN200XA.ULN200XA_DIRECTION_T.DIR_CW);
				}
				else if(armLiftAmt < -50){
					lift.release();
					lift.stepperSteps(4096);
					lift.setDirection(upm_uln200xa.ULN200XA.ULN200XA_DIRECTION_T.DIR_CCW);
				}
				
				if(armLiftAmt > -50 && armLiftAmt < 50){
					lift.release();
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}


}
