package com.hsbc.exchangerate.config;
/**
 * Created by pseepathi on 23/04/2020.
 */
import java.io.IOException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLException;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocket;

import com.hsbc.exchangerate.connection.HeaderInterceptor;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.conn.ssl.X509HostnameVerifier;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.ssl.SSLContextBuilder;
import org.apache.http.ssl.SSLContexts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.http.MediaType;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;


import lombok.extern.slf4j.Slf4j;

@Slf4j
@Configuration
@PropertySource("classpath:application.properties")
public class RestConfiguration {

    @Autowired
    private Environment env;

    @Bean(name = "ratesApiRestTemplate")
    public RestTemplate ratesApiRestTemplate() {

        RestTemplate restTemplate = new RestTemplate(httpsClientHttpRequestFactory(getPropertyInteger("rates.service.readTimeout"),
                getPropertyInteger("rates.service.connectionTimeout"), getPropertyInteger("rates.service.maxPoolSize"),
                getPropertyInteger("rates.service.maxPerRoute")));

        ClientHttpRequestInterceptor acceptHeader = new HeaderInterceptor();
        restTemplate.setInterceptors(Arrays.asList(acceptHeader));

        MappingJackson2HttpMessageConverter jacksonMessageConverter = new MappingJackson2HttpMessageConverter();
        List<MediaType> mediaTypes = new ArrayList<>();
        mediaTypes.add(new MediaType("application", "json", MappingJackson2HttpMessageConverter.DEFAULT_CHARSET));
        mediaTypes.add(new MediaType("application", "*+json", MappingJackson2HttpMessageConverter.DEFAULT_CHARSET));
        mediaTypes.add(new MediaType("text", "plain", MappingJackson2HttpMessageConverter.DEFAULT_CHARSET));
        mediaTypes.add(new MediaType("text", "javascript", MappingJackson2HttpMessageConverter.DEFAULT_CHARSET));
        jacksonMessageConverter.setSupportedMediaTypes(mediaTypes);
        restTemplate.getMessageConverters().add(jacksonMessageConverter);

        return restTemplate;
    }

    public HttpComponentsClientHttpRequestFactory httpsClientHttpRequestFactory(int readTimeout, int connectionTimeout, int maxPoolSize,
                                                                                int maxPerRoute) {
        final HttpComponentsClientHttpRequestFactory requestFactory;
        HttpClientBuilder builder = HttpClientBuilder.create();

        PoolingHttpClientConnectionManager poolingHttpClientConnectionManager = httpsPoolingHttpClientConnectionManager(maxPoolSize, maxPerRoute);
        builder.setConnectionManager(poolingHttpClientConnectionManager);
        requestFactory = new HttpComponentsClientHttpRequestFactory(builder.build());
        requestFactory.setReadTimeout(readTimeout);
        requestFactory.setConnectTimeout(connectionTimeout);

        return requestFactory;
    }

    public PoolingHttpClientConnectionManager httpsPoolingHttpClientConnectionManager(int maxPoolSize, int maxPerRoute) {
        try {
            SSLContextBuilder builder = SSLContexts.custom();
            builder.loadTrustMaterial(null, new TrustStrategy() {
                @Override
                public boolean isTrusted(X509Certificate[] chain, String authType) throws CertificateException {
                    return true;
                }
            });
            SSLContext sslContext = builder.build();
            SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(sslContext, new X509HostnameVerifier() {
                @Override
                public void verify(String host, SSLSocket ssl) throws IOException {
                }

                @Override
                public void verify(String host, X509Certificate cert) throws SSLException {
                }

                @Override
                public void verify(String host, String[] cns, String[] subjectAlts) throws SSLException {
                }

                @Override
                public boolean verify(String s, SSLSession sslSession) {
                    return true;
                }
            });

            Registry<ConnectionSocketFactory> socketFactoryRegistry = RegistryBuilder.<ConnectionSocketFactory>create().register("https", sslsf)
                    .register("http", PlainConnectionSocketFactory.INSTANCE).build();

            PoolingHttpClientConnectionManager poolingHttpClientConnectionManager = new PoolingHttpClientConnectionManager(socketFactoryRegistry);
            poolingHttpClientConnectionManager.setMaxTotal(maxPoolSize);
            poolingHttpClientConnectionManager.setDefaultMaxPerRoute(maxPerRoute);

            return poolingHttpClientConnectionManager;
        } catch (Exception e) {
            log.error("Could not set up the http connection pool", e);
        }
        return null;
    }

    public String getProperty(String envProperty){
       return env.getProperty(envProperty) ;
    }

    public Integer getPropertyInteger(String envProperty){
        return env.getProperty(envProperty, Integer.class) ;
    }

}
