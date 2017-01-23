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


import eu.socialedge.vega.backend.fare.domain.Deduction;
import org.apache.commons.lang3.SerializationException;

import javax.persistence.AttributeConverter;

public class DeductionSerializer implements AttributeConverter<Deduction, String> {

    @Override
    public String convertToDatabaseColumn(Deduction attribute) {
        if (attribute == null)
            return null;

        return attribute.getClass().getName();
    }

    @Override
    public Deduction convertToEntityAttribute(String dbData) {
        if (dbData == null || dbData.length() == 0)
            return null;

        try {
            return (Deduction) Class.forName(dbData).newInstance();
        } catch (Exception e) {
           throw new SerializationException("Failed to deserialize Deduction strategy " +
                   "from str = " + dbData, e);
        }
    }
}
