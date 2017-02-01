/**
 * SocialEdge Vega - An Electronic Transit Fare Payment System
 * Copyright (c) 2016 SocialEdge
 * <p>
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * <p>
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 */
package eu.socialedge.vega.backend.infrastructure.persistence.jpa.convert;

import javax.persistence.AttributeConverter;
import java.time.Period;
import java.util.Objects;

public class PeriodSerializer implements AttributeConverter<Period, String> {

    @Override
    public String convertToDatabaseColumn(Period attribute) {
        return Objects.toString(attribute, null);
    }

    @Override
    public Period convertToEntityAttribute(String dbData) {
        if (dbData == null || dbData.length() == 0)
            return null;

        return Period.parse(dbData);
    }
}
