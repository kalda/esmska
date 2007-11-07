/*
 * Vodafone.java
 *
 * Created on 7. červenec 2007, 1:52
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package esmska.operators;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.ImageIcon;
import esmska.data.SMS;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;

/** Vodafone operator
 *
 * @author ripper
 */
public class Vodafone implements Operator {
    private static final String RES = "/esmska/resources/";
    private static final int MAX_CHARS = 760;
    private static final int SMS_LENGTH = 152;
    private static final int MAX_PARTS = 1;
    private static final int SIGNATURE_EXTRA_LENGTH = 5;
    private static final boolean SUPPORTS_SIGNATURE = true;
    private static final ImageIcon ICON =
            new ImageIcon(Vodafone.class.getResource(RES + "operators/Vodafone.png"));
    private String imgid;
    private String ppp;
    /**
     * Creates a new instance of Vodafone
     */
    public Vodafone() {
    }
    
    public ImageIcon getSecurityImage() {
        ImageIcon image = null;
        try {
            URL url = new URL("http://sms.vodafone.cz/");
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            BufferedReader br = new BufferedReader(
                    new InputStreamReader(con.getInputStream(),"UTF-8"));
            
            String content = "";
            String s = "";
            while ((s = br.readLine()) != null) {
                content += s + "\n";
            }
            
            br.close();
            con.disconnect();
            
            //find imgid and image url
            Pattern p = Pattern.compile("<input type=\"hidden\" name=\"imgid\" value=\"(.*)\" />");
            Matcher m = p.matcher(content);
            if (m.find()) {
                imgid = m.group(1);
                url = new URL("http://sms.vodafone.cz/imgcode.php?id=" + imgid);
            }
            
            //find ppp
            p = Pattern.compile("<input type=\"hidden\" name=\"ppp\" value=\"(.*)\" />");
            m = p.matcher(content);
            if (m.find())
                ppp = m.group(1);
            
            con = (HttpURLConnection) url.openConnection();
            InputStream is = con.getInputStream();
            byte[] buffer = new byte[1024];
            ByteArrayOutputStream os = new ByteArrayOutputStream();
            int count = 0;
            while ((count = is.read(buffer)) >= 0)
                os.write(buffer,0,count);
            image = new ImageIcon(os.toByteArray());
            
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return image;
    }
    
    public boolean send(SMS sms) {
        String number = sms.getNumber().replaceFirst("\\+420", "");
        String senderNumber = sms.getSenderNumber();
        if (senderNumber != null)
            senderNumber = senderNumber.replaceFirst("\\+420", "");
        try {
            URL url = new URL("http://sms.vodafone.cz/send.php");
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setDoOutput(true);
            con.setUseCaches(false);
            con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded; charset=utf-8");
            OutputStreamWriter wr = new OutputStreamWriter(con.getOutputStream(), "UTF-8");
            //send POST request
            wr.write("message=" + URLEncoder.encode(sms.getText()!=null?sms.getText():"","UTF-8")
            + "&number=" + URLEncoder.encode(number,"UTF-8")
            + "&mynumber=" + URLEncoder.encode(senderNumber!=null?senderNumber:"","UTF-8")
            + "&sender=" + URLEncoder.encode(sms.getSenderName()!=null?sms.getSenderName():"","UTF-8")
            + "&imgid=" + URLEncoder.encode(imgid!=null?imgid:"","UTF-8")
            + "&ppp=" + URLEncoder.encode(ppp!=null?ppp:"","UTF-8")
            + "&pictogram=" + URLEncoder.encode(sms.getImageCode()!=null?sms.getImageCode():"","UTF-8")
            + "&send=" + URLEncoder.encode("Odeslat!","UTF-8")
            );
            wr.flush();
            wr.close();
            
            //get reply
            BufferedReader br = new BufferedReader(
                    new InputStreamReader(con.getInputStream(), "UTF-8"));
            
            String content = "";
            String s = "";
            while ((s = br.readLine()) != null) {
                content += s + "\n";
            }
            
            br.close();
            con.disconnect();
            
            //find whether sent ok
            Pattern p = Pattern.compile("<div id=\"thanks\">");
            Matcher m = p.matcher(content);
            boolean ok = m.find();
            
            //find errors
            p = Pattern.compile("<div id=\"errmsg\">\n<p>(.*?)</p>");
            m = p.matcher(content);
            if (m.find())
                sms.setErrMsg(m.group(1));
            
            return ok;
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
    
    public String toString() {
        return "Vodafone";
    }
    
    public int getMaxChars() {
        return MAX_CHARS;
    }
    
    public int getSMSLength() {
        return SMS_LENGTH;
    }
    
    public int hashCode() {
        return getClass().getName().hashCode();
    }
    
    public boolean equals(Object obj) {
        return (obj == this || obj instanceof Vodafone);
    }
    
    public int getMaxParts() {
        return MAX_PARTS;
    }
    
    public int getSignatureExtraLength() {
        return SIGNATURE_EXTRA_LENGTH;
    }
    
    public boolean isSignatureSupported() {
        return SUPPORTS_SIGNATURE;
    }
    
    public ImageIcon getIcon() {
        return ICON;
    }
    
}
