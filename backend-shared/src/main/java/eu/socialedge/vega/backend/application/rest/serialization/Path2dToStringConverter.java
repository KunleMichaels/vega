package eu.socialedge.vega.backend.application.rest.serialization;

import eu.socialedge.vega.backend.util.Path2ds;

import org.springframework.core.convert.converter.Converter;

import java.awt.geom.Path2D;
import java.io.IOException;

public class Path2dToStringConverter implements Converter<String, Path2D> {

    @Override
    public Path2D convert(String source) {
        try {
            return Path2ds.parse(source);
        } catch (IOException e) {
            throw new RuntimeException("Failed to convert string to Path2D", e);
        }
    }
}
