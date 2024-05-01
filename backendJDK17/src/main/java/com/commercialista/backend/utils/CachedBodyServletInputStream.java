package com.commercialista.backend.utils;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import jakarta.servlet.ReadListener;
import jakarta.servlet.ServletInputStream;

//FONTE: https://www.baeldung.com/spring-reading-httpservletrequest-multiple-times

//Let’s create a class – CachedBodyServletInputStream – which will implement ServletInputStream. In this class, we’ll create a new constructor as well as override the isFinished(), isReady() and read() methods.

public class CachedBodyServletInputStream extends ServletInputStream {

    private InputStream cachedBodyInputStream;

    public CachedBodyServletInputStream(byte[] cachedBody) {
        this.cachedBodyInputStream = new ByteArrayInputStream(cachedBody);
    }
    
    @Override
    public int read() throws IOException {
        return cachedBodyInputStream.read();
    }
    
    @Override
    public boolean isFinished() {
        try {
			return cachedBodyInputStream.available() == 0;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return true;
		}
		
    }
    
    @Override
    public boolean isReady() {
        return true;
    }

	@Override
	public void setReadListener(ReadListener listener) {
		// TODO Auto-generated method stub
		
	}
}
