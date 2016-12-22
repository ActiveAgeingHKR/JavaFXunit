package aajavafx;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.security.MessageDigest;
import java.util.EnumMap;
import java.util.Map;
import java.util.Properties;
import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.imageio.ImageIO;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

/**
 *
 * @author Rolandas
 */
public class QrCodeHandler {
    /*
    For qr code to work you need ZXing library.
    For sending mail to work you need JavaMail API. 
    */
    
    // Get hash of some input data.
    public String hashHandler(int customerId, String visitorId, String startDate, String startTime, String endTime) throws Exception {
        //Seperating variables with #
        String str = customerId +"#"+ visitorId +"#" + startDate +"#"+ startTime +"#" + endTime;

        MessageDigest md = MessageDigest.getInstance("SHA-512");
        md.update(str.getBytes());
     
        byte byteData[] = md.digest();
        //convert the byte to hex format
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < byteData.length; i++) {
         sb.append(Integer.toString((byteData[i] & 0xff) + 0x100, 16).substring(1));
        }
        
        return(sb.toString());
    }
    
    // generate qr code with given parameters.
    public void qrCodeGenerator(String url, String hash, String fileName) {       
        String urlandHash = url+hash;
        
        String filePath = "src/resources/qrCode/" + fileName + ".jpg";
        int size = 250;
        String fileType = "jpg";
        File myFile = new File(filePath);
        try {
            
            Map<EncodeHintType, Object> hintMap = new EnumMap<EncodeHintType,Object>(EncodeHintType.class);
            hintMap.put(EncodeHintType.CHARACTER_SET, "UTF-8");
            
            // Now with zxing version 3.2.1 you could change border size (white border size to just 1)
            hintMap.put(EncodeHintType.MARGIN, 1); /* default = 4 */
            hintMap.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.L);
            
            QRCodeWriter qrCodeWriter = new QRCodeWriter();
            BitMatrix byteMatrix = qrCodeWriter.encode(urlandHash, BarcodeFormat.QR_CODE, size,
                    size, hintMap);
            int CrunchifyWidth = byteMatrix.getWidth();
            BufferedImage image = new BufferedImage(CrunchifyWidth, CrunchifyWidth,
                    BufferedImage.TYPE_INT_RGB);
            image.createGraphics();
            
            Graphics2D graphics = (Graphics2D) image.getGraphics();
            graphics.setColor(Color.WHITE);
            graphics.fillRect(0, 0, CrunchifyWidth, CrunchifyWidth);
            graphics.setColor(Color.BLACK);
            
            for (int i = 0; i < CrunchifyWidth; i++) {
                for (int j = 0; j < CrunchifyWidth; j++) {
                    if (byteMatrix.get(i, j)) {
                        graphics.fillRect(i, j, 1, 1);
                    }
                }
            }
            ImageIO.write(image, fileType, myFile);
        } catch (WriterException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("\n\nYou have successfully created QR Code.");
    }
    
    //Send email from Gmail account to any other account.
    public void sendMail(String toEmail, String fileName) throws AddressException, MessagingException, IOException {
        // enter your gmail account
        final String fromEmail = "hkr.soft.engineering@gmail.com";
        // enter your gmail account password
        final String fromEmailPassword = "hkrsoftwareengineering";
        
        // Contents of the message
        String emailBody = "Use this qr code during your visit. " + "<br><br> Regards, <br>Adjutus team";
        
        System.out.println("\n 1st ===> setup Mail Server Properties..");
        Properties props = System.getProperties();
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        System.out.println("Mail Server Properties have been setup successfully..");
        
        System.out.println("\n\n 2nd ===> get Mail Session..");
        Session getMailSession = Session.getDefaultInstance(props, null);
       
        try {
            // Create a default MimeMessage object.
            Message message = new MimeMessage(getMailSession);
            // Set To: header field of the header.
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail));
            // Set Subject: header field
            message.setSubject("Visit qr code");
            
            // This mail has 2 part, the BODY and the embedded image
            MimeMultipart multipart = new MimeMultipart("related");
            // first part (the html)
            BodyPart messageBodyPart = new MimeBodyPart();
            messageBodyPart.setContent(emailBody, "text/html");
            multipart.addBodyPart(messageBodyPart);

            // second part (the image)
            messageBodyPart = new MimeBodyPart();
            DataSource source = new FileDataSource("src/resources/qrCode/" + fileName + ".jpg");
            /* 
            set file name  
            ********************** by doing that some random thing appears above picture in mail.
            */   
            messageBodyPart.setFileName("visitation qr code");
            messageBodyPart.setDataHandler(new DataHandler(source));
            messageBodyPart.setHeader("Content-ID", "<image>");
            // add image to the multipart
            multipart.addBodyPart(messageBodyPart);
            // put everything together
            message.setContent(multipart);
            System.out.println("Mail session has been created");
            // Send message
            System.out.println("\n\n 3rd ===> Send mail");
            Transport transport = getMailSession.getTransport("smtp");
            // Enter your correct gmail UserID and Password
            // if you have 2FA enabled then provide App Specific Password
            transport.connect("smtp.gmail.com", fromEmail, fromEmailPassword);
            transport.sendMessage(message, message.getAllRecipients());
            System.out.println("Mail sent successfully");
            transport.close();

        } catch (MessagingException e) {
         throw new RuntimeException(e);
      }
    }
    
    
}
