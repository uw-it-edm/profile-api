package edu.uw.edm.profile.config;

import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.DefaultHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;

import edu.uw.edm.profile.properties.SecurityProperties;
import edu.uw.edm.profile.security.KeyManagerCabinet;


@Configuration
public class GeneralConfiguration {

    @Bean
    public CloseableHttpClient httpClient(final PoolingHttpClientConnectionManager connectionManager) throws NoSuchAlgorithmException, KeyManagementException {

        RequestConfig.Builder requestConfigBuilder = RequestConfig.custom().setExpectContinueEnabled(true);

        return HttpClients.custom().setConnectionManager(connectionManager)
                .setDefaultRequestConfig(requestConfigBuilder.build()).build();
    }

    @Bean
    public PoolingHttpClientConnectionManager connectionManager(final KeyManagerCabinet cabinet) throws KeyManagementException, NoSuchAlgorithmException {
        TrustManager[] trustManagers = cabinet.getTrustManagers();


        SSLContext context = SSLContext.getInstance(SSLConnectionSocketFactory.TLS);
        context.init(cabinet.getKeyManagers(), trustManagers, new SecureRandom());

        HostnameVerifier hostnameVerifier = new DefaultHostnameVerifier();

        ConnectionSocketFactory socketFactory = new SSLConnectionSocketFactory(context, hostnameVerifier);

        final Registry<ConnectionSocketFactory> sfr = RegistryBuilder.<ConnectionSocketFactory>create()
                .register("http", PlainConnectionSocketFactory.getSocketFactory())
                .register("https", socketFactory)
                .build();

        return new PoolingHttpClientConnectionManager(sfr);
    }

    @Bean
    public KeyManagerCabinet keyManagerCabinet(SecurityProperties securityProperties) throws Exception {
        return new KeyManagerCabinet.Builder(securityProperties).build();
    }


    @Bean
    @Primary
    public RestTemplate restTemplate(@Qualifier("httpClient") HttpClient httpClient) {
        return new RestTemplate(new HttpComponentsClientHttpRequestFactory(httpClient));
    }


}
