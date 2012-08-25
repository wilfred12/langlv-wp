package org.langlv.homeprj.MessageUtils;

import org.jpos.iso.AsciiInterpreter;
import org.jpos.iso.ISOException;
import org.jpos.iso.ISOMsg;
import org.jpos.iso.packager.ISO87APackager;
import org.jpos.iso.packager.XML2003Packager;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author langlv
 */
public class MessageUtils {
    
    public MessageUtils(){
        
    }
    
    public static int decodeHeader(byte[] header) {
        if (header == null || header.length < 4) {
            throw new RuntimeException("header length is invalid");
        }

        String s = AsciiInterpreter.INSTANCE.uninterpret(header, 0, 4);
        return Integer.parseInt(s);
    }

    /**
     * decode a byte buffer and return in xml string
     *
     * @param msgData
     * @return
     */
    public static String decodeMessage(byte[] msgData) throws ISOException {
        ISOMsg msg = new ISOMsg();
        msg.setPackager(new ISO87APackager());

        int unpacked = msg.unpack(msgData);

        if (unpacked > 0) {
            msg.setPackager(new XML2003Packager());
            byte[] xmlbuf = msg.pack();
            return AsciiInterpreter.INSTANCE.uninterpret(xmlbuf, 0, xmlbuf.length);
        } else{
            throw new RuntimeException("Error while trying to decode iso message");
        }
    }
}
