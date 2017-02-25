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
package eu.socialedge.vega.backend.infrastructure.persistence.convert;

import eu.socialedge.vega.backend.payment.domain.ExpirationDate;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.Objects;

import static org.apache.commons.lang3.StringUtils.isBlank;

@Converter(autoApply = true)
public class ExpirationDateConverter implements AttributeConverter<ExpirationDate, String> {

    @Override
    public String convertToDatabaseColumn(ExpirationDate expDate) {
        return Objects.toString(expDate, null);
    }

    @Override
    public ExpirationDate convertToEntityAttribute(String rawExpDate) {
        return isBlank(rawExpDate) ? null : ExpirationDate.parse(rawExpDate);
    }
}
