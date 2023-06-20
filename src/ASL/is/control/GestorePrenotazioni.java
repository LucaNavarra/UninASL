package ASL.is.control;

import java.io.File;
import java.io.IOException;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;


import ASL.is.entity.*;
import ASL.is.DAO.*;


public class GestorePrenotazioni {
	
	
	
	
	private static int istanza = 0;
	
	protected GestorePrenotazioni () {
	super();
	}
	
	
	public static GestorePrenotazioni instance() {
		GestorePrenotazioni gest = null;
		if(istanza == 0) {
			gest = new GestorePrenotazioni();
			istanza  = 1;
		}
		return gest;
	}

	
	public double calcolaPrezzo(Specializzazione spec, int isee, GradoDisabilita disabilita) {
		
		double prezzoFinale = spec.getPrezzo();
		
		//E01
		if (isee > 11362 && isee < 36152) {
		prezzoFinale = prezzoFinale - ((prezzoFinale/100)*10); //-10%
		}
		
		//E02
		else if (isee <= 11362 && disabilita == GradoDisabilita.Nessuna) {
		prezzoFinale = prezzoFinale - ((prezzoFinale/100)*25); //-25%
		}
		
		//E03
		else if (isee <= 11362 && disabilita != GradoDisabilita.Nessuna && disabilita != GradoDisabilita.Grave) {
		prezzoFinale = prezzoFinale - ((prezzoFinale/100)*50); //-50%	
		}
		
		//E04
		else if (isee <= 36152 && disabilita == GradoDisabilita.Grave) {
		prezzoFinale = 0;  //FREE
		}
		
		
		return prezzoFinale;
	}
	
	
	public Prenotazione nuovaPrenotazione (Prenotazione prenotazioneScelta, Utente utente, Operatore operatore) throws WriterException, IOException {
		
		try {
		utente = UtenteDao.ottieniUtenteDaUsername(utente.getUsername());
		
		GradoDisabilita disabilita = utente.getDisabilitaUtente();
		int isee = utente.getIsee();
		
		Medico med = prenotazioneScelta.getOrarioPrenotato().getMedico();
		Specializzazione spec = med.getSpecializzazione();
		
		
		prenotazioneScelta.setUtentePrenotato(utente);
		prenotazioneScelta.setOperatorePrenotante(null);
		

		prenotazioneScelta.setPrezzo(calcolaPrezzo(spec,isee,disabilita));
		
		
		if(operatore != null){
		prenotazioneScelta.setOperatorePrenotante(operatore);
		}
		}catch(DAOException e) {
			System.err.print(e.getMessage() + "\n");
		}
		
		return prenotazioneScelta;

	}
	
	
	public static File generaQR(Prenotazione prenotazione) throws WriterException, IOException {
        String sourcepath = "codici_QR/QRCode(ID" + prenotazione.getIdPrenotazione();
        String path = sourcepath + ").png";
        String data = "Data: " + prenotazione.getData().toString() + "; Ora: " + prenotazione.getOrarioPrenotato().getOraInizio() + ":"
                        + prenotazione.getOrarioPrenotato().getMinutiInizio() + "; Stanza: " + prenotazione.getOrarioPrenotato().getStanza() + "; Medico: "
                        + prenotazione.getOrarioPrenotato().getMedico().getNome() + " " + prenotazione.getOrarioPrenotato().getMedico().getCognome() + "; Visita: "
                        + prenotazione.getOrarioPrenotato().getMedico().getSpecializzazione().toStringa();
        BitMatrix matrix;
        File file = null;;
        try {
        matrix = new MultiFormatWriter().encode(data, BarcodeFormat.QR_CODE, 500, 500);
        file = new File(path);
        MatrixToImageWriter.writeToFile(matrix,"png",file);
        }catch(IOException e) {
        	System.err.print(e.getMessage() + "\n");
        }
        return file;
        //System.out.println("QR code generato!")
    }
	

	public static void invioMailConferma(Prenotazione prenotazione) {

		//authentication info
		final String username = "uninAsl.is2022@gmail.com";
		final String password = "AslBet2022";
				
		String fromEmail = "uninAsl.is2022@gmail.com";
		
		String toEmail = prenotazione.getUtentePrenotato().getEmail();
				
		Properties properties = new Properties();
		properties.put("mail.smtp.auth", "true");
		properties.put("mail.smtp.starttls.enable", "true");
		properties.put("mail.smtp.host", "smtp.gmail.com");
		properties.put("mail.smtp.port", "587");
				
		Session session = Session.getInstance(properties, new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
			return new PasswordAuthentication(username,password);
			}
			});
		
		
		//Messaggio
		MimeMessage msg = new MimeMessage(session);
		
		try {
			
			
		msg.setFrom(new InternetAddress(fromEmail));
		msg.addRecipient(Message.RecipientType.TO, new InternetAddress(toEmail));
		msg.setSubject("Conferma prenotazione ASL");
					
		Multipart emailContent = new MimeMultipart();
					
		//Text body part
		MimeBodyPart textBodyPart = new MimeBodyPart();
		textBodyPart.setText("Hai completato la prenotazione di " +
		prenotazione.getOrarioPrenotato().getMedico().getSpecializzazione().toStringa() + "correttamente. In allegato trovi il QR code da presentare all'ingresso con tutte le informazioni della tua visita ASL.");
		
		//Attachment body part
		MimeBodyPart pdfAttachment = new MimeBodyPart();
		//pdfAttachment.attachFile("codici_QR/QRCode(ID:" + String.valueOf(prenotazione.getIdPrenotazione()) + ").png");
		pdfAttachment.attachFile(prenotazione.getQR().getPath());	
					
		//Attach body parts
		emailContent.addBodyPart(textBodyPart);
		emailContent.addBodyPart(pdfAttachment);
					
		
		//Attach multipart to message
		msg.setContent(emailContent);
		Transport.send(msg);
					
		//System.out.println("Mail inviata correttamente");     
					
					
		} catch (MessagingException e) {
			System.out.println("Invio email nonriuscito");
				} catch (IOException e) {
					// TODO Auto-generated catch block
					//e.printStackTrace();
					System.err.print(e.getMessage() + "\n");
				}

			}

	
	public boolean prenotazioneConfermata(Prenotazione prenotazione) throws WriterException, IOException{
		boolean prenotazioneRiuscita = false;
		try {
		if(PrenotazioneDao.creaPrenotazione(prenotazione)) {
			prenotazioneRiuscita = true;
			File f = generaQR(prenotazione);
			prenotazione.setQRPrenotazione(f);
			invioMailConferma(prenotazione);
		}
		}catch(DAOException e) {
			System.err.print(e.getMessage() + "\n");
		}
		return prenotazioneRiuscita;
	}
	
	
	public void cancellaPrenotazione(Prenotazione prenotazione) {
		prenotazione = null;
	}
	
	
	}


	
