package edu.uw.edm.profile.service;

import java.io.IOException;

/**
 * @author Maxime Deravet Date: 10/23/17
 */
public interface ObjectDownloader {
    <T> T getObjectForURL(String url, Class<T> valueType) throws IOException;
}
