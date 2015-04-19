package com.Picloud.utils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class ByteUtil {
	public static byte[] inputStreamToByte(InputStream in) throws IOException{
		ByteArrayOutputStream bos = new ByteArrayOutputStream();  
        byte[] bs = new byte[1024];   
        int len = -1;  
        while ((len = in.read(bs)) != -1) {  
            bos.write(bs, 0, len);  
        			}  
        byte b[] = bos.toByteArray();  
        bos.close(); 
        return b;
	}
}
