package com.wd.liandidemo.RF;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class PBOC_TL {

    //eg. tlv="9F36020B889F270180" return {9F27=80, 9F36=0B88}
    public static Map decodingTLV(List list) {
        Map map = new HashMap();
        for(int i = 0; i < list.size(); i++) {
            String[] tlv = (String[]) list.get(i);
            map.put(tlv[0], tlv[1]);
        }
        return map;
    }

    //eg. tlv="9F36020B889F270180" return {[9F36,02,0B88], [9F27,01,80]}
    public static List decodingTLV(String str) {
        if (str == null || str.length() % 2 != 0) {
            throw new RuntimeException("Invalid tlv, null or odd length");
        }
        List ls = new ArrayList();
        for (int i = 0; i < str.length();) {
            try {
                String tag = str.substring(i, i = i + 2);
                // extra byte for TAG field
                if ((Integer.parseInt(tag, 16) & 0x1F) == 0x1F) {
                    tag += str.substring(i, i = i + 2);
                }
                String len = str.substring(i, i = i + 2);
                int length = Integer.parseInt(len, 16);
                // more than 1 byte for length
                if (length > 128) {//临界值，当是128即10000000时，长度还是一位，而不是两位
                    int bytesLength = length - 128;
                    len = str.substring(i, i = i + (bytesLength * 2));
                    length = Integer.parseInt(len, 16);
                }
                length *= 2;
//                String value = str.substring(i, i = i + length);
//                System.out.println("tag:" + tag + " len:" + len + " value:" + value);
//                System.out.println("tag:" + tag + " len:" + len );
                ls.add(new String[] {tag, len});
            } catch (NumberFormatException e) {
                throw new RuntimeException("Error parsing number", e);
            } catch (IndexOutOfBoundsException e) {
                throw new RuntimeException("Error processing field", e);
            }
        }
        return ls;
    }

    public static String encodingTLV(List tlvList) {
        String str = "";
        for(int i = 0; i < tlvList.size(); i++) {
            String[] tlv = (String[]) tlvList.get(i);
            str += tlv[0] + tlv[1] ;
        }
        return str;
    }

    public static void main(String[] args) {
        String ss = "9F66049F02069F03069F1A0295055F2A029A039C019F3704";
        List tlvList = PBOC_TL.decodingTLV(ss);
        String[] result = new String[tlvList.size()];
        for(int i = 0; i < tlvList.size(); i++) {
            String[] tlv = (String[]) tlvList.get(i);
            result[i] = tlv[0] + "=" + tlv[1] ;
            System.out.println(result[i]);
        }
    }
}