/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.intiro.itr.common;

import java.util.*;
//import java.net.*;
import javax.mail.*;
import javax.mail.internet.*;
//import javax.servlet.jsp.*;
import javax.servlet.jsp.tagext.*;

/**
 *
 * @author Martin
 */
public class JavaMail extends BodyTagSupport {

    public int SendSimpleMail(String sFrom, String sTo, String sSubject, String sMessage) throws MessagingException
    {
    Session mailSession =  Session.getDefaultInstance(new Properties(), null); 

try
{
      
MimeMessage mimeMessage = new MimeMessage(mailSession); 
mimeMessage.setFrom(new InternetAddress(sFrom)); 
InternetAddress[] address = 
            InternetAddress.parse(sTo, false); 
mimeMessage.setRecipients(Message.RecipientType.TO, address); 
mimeMessage.setSubject(sSubject); 


MimeBodyPart mimeBodyPart = new MimeBodyPart(); 
mimeBodyPart.setText(sMessage); 


Multipart multiPart = new MimeMultipart(); 
multiPart.addBodyPart(mimeBodyPart); 


mimeMessage.setContent(multiPart); 
mimeMessage.setSentDate(new Date()); 


Transport transport = mailSession.getTransport("smtp"); 
transport.connect("localhost", null, null); 
transport.sendMessage(mimeMessage, 
            mimeMessage.getAllRecipients()); 

return 1;
} 
catch (Exception e)
{
    return -1;
}


    }
}
