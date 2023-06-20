package ASL.is.boundary;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import javax.imageio.ImageIO;

import com.google.zxing.BinaryBitmap;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatReader;

import com.google.zxing.NotFoundException;
import com.google.zxing.Result;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;


public class BLettoreQR {
	
	protected String leggiQR (String path) throws FileNotFoundException, IOException, NotFoundException {
		
		
		Map<EncodeHintType, ErrorCorrectionLevel> hashMap
		= new HashMap<EncodeHintType,
					ErrorCorrectionLevel>();

		hashMap.put(EncodeHintType.ERROR_CORRECTION,
				ErrorCorrectionLevel.L);
		
		
		
		BinaryBitmap binaryBitmap
		= new BinaryBitmap(new HybridBinarizer(
			new BufferedImageLuminanceSource(
				ImageIO.read(
					new FileInputStream(path)))));

		Result result
		= new MultiFormatReader().decode(binaryBitmap);

		
		return result.getText();
		
	}
	
	
	
}
	
	




