package edu.uw.edm.profile.service.impl;

import com.amazonaws.services.s3.model.AmazonS3Exception;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;

import edu.uw.edm.profile.service.ObjectDownloader;

/**
 * @author Maxime Deravet Date: 10/23/17
 */
@Service
public class ResourceLoaderObjectDownloaderImpl implements ObjectDownloader {

    private ResourceLoader resourceLoader;
    private ObjectMapper objectMapper;

    @Autowired
    public ResourceLoaderObjectDownloaderImpl(ResourceLoader resourceLoader, ObjectMapper objectMapper) {
        this.resourceLoader = resourceLoader;
        this.objectMapper = objectMapper;
    }

    @Override
    public <T> T getObjectForURL(String resourceURL, Class<T> valueType) throws IOException {
        //TODO should I check if resourceURL contains doubled '/' ( // )

        Resource resource = this.resourceLoader.getResource(resourceURL);

        InputStream inputStream;
        try {
            inputStream = resource.getInputStream();
        } catch (AmazonS3Exception e) {
            //Hack as AmazonS3Exception is a RuntimeException
            throw new IOException(e);
        }


        return objectMapper.readValue(inputStream, valueType);
    }
}
