package com.commercialista.backend.utils;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.springframework.util.StreamUtils;

import jakarta.servlet.ServletInputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletRequestWrapper;

//FONTE : https://www.baeldung.com/spring-reading-httpservletrequest-multiple-times

public class CachedBodyHttpServletRequest extends HttpServletRequestWrapper {

    private byte[] cachedBody;

//    First, let’s create a constructor. Inside it, we’ll read the body from the actual InputStream and store it in a byte[] object:
    public CachedBodyHttpServletRequest(HttpServletRequest request) throws IOException {
        super(request);
        InputStream requestInputStream = request.getInputStream();
        this.cachedBody = StreamUtils.copyToByteArray(requestInputStream);
    }
    
//    As a result, we’ll be able to read the body multiple times.
//    Next, let’s override the getInputStream() method. We’ll use this method to read the raw body and convert it into an object.

//    In this method, we’ll create and return a new object of CachedBodyServletInputStream class (an implementation of ServletInputStream):
    @Override
    public ServletInputStream getInputStream() throws IOException {
        return new CachedBodyServletInputStream(this.cachedBody);
    }
    
//    Then, we’ll override the getReader() method. This method returns a BufferedReader object:
    @Override
    public BufferedReader getReader() throws IOException {
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(this.cachedBody);
        return new BufferedReader(new InputStreamReader(byteArrayInputStream));
    }
}
