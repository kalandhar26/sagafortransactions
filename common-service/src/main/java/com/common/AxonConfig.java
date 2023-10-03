package com.common;

import com.thoughtworks.xstream.XStream;
import org.axonframework.serialization.Serializer;
import org.axonframework.serialization.xml.XStreamSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
public class AxonConfig {

    @Bean
    @Primary
    public XStreamSerializer xStreamSerializer() {
        XStream xStream = new XStream();
        xStream.allowTypes(new Class[]{
                com.common.queries.GetUserPaymentDetailsQuery.class
        });
        return XStreamSerializer.builder().xStream(xStream).build();
    }


}
