/*
 * Copyright 2012-2014 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package eu.socialedge.vega.backend.application.api.convert;

import lombok.val;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class UriListHttpMessageConverter implements HttpMessageConverter<Collection<Link>> {

    private static final List<MediaType> MEDIA_TYPES = new ArrayList<MediaType>() {{
        add(MediaType.parseMediaType("text/uri-list"));
    }};

    @Override
    public boolean canWrite(Class<?> clazz, MediaType mediaType) {
        if (null == mediaType)
            return false;

        val isCollection = Collection.class.isAssignableFrom(clazz);
        val isUriList = mediaType.getSubtype().contains("uri-list");

        return isCollection && isUriList;
    }

    @Override
    public boolean canRead(Class<?> clazz, MediaType mediaType) {
        return false;
    }

    @Override
    public List<MediaType> getSupportedMediaTypes() {
        return MEDIA_TYPES;
    }

    @Override
    public Collection<Link> read(Class<? extends Collection<Link>> clazz, HttpInputMessage inputMessage)
            throws IOException, HttpMessageNotReadableException {
        throw new UnsupportedOperationException();
    }

    @Override
    public void write(Collection<Link> links, MediaType contentType, HttpOutputMessage outputMessage)
            throws IOException, HttpMessageNotWritableException {
        val resBodyStream = outputMessage.getBody();
        val writer = new BufferedWriter(new OutputStreamWriter(resBodyStream));

        for (Link link : links) {
            writer.write(link.getHref());
            writer.newLine();
        }

        writer.flush();
    }
}
