package org.bytedesk.rest.handler;

import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;

import java.util.ArrayList;
import java.util.List;

public class IpMappingJackson2HttpMessageConverter extends MappingJackson2HttpMessageConverter {

    public IpMappingJackson2HttpMessageConverter(){

        List<MediaType> mediaTypes = new ArrayList<>();
        mediaTypes.add(MediaType.TEXT_PLAIN);
        mediaTypes.add(MediaType.TEXT_HTML);
        setSupportedMediaTypes(mediaTypes);
    }
}

