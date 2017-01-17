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

import org.javamoney.moneta.Money;

import javax.money.MonetaryAmount;
import javax.persistence.AttributeConverter;

public class MonetaryAmountSerializer implements AttributeConverter<MonetaryAmount, String> {

    @Override
    public String convertToDatabaseColumn(MonetaryAmount attribute) {
        if (attribute == null)
            return null;

        return attribute.toString();
    }

    @Override
    public MonetaryAmount convertToEntityAttribute(String dbData) {
        if (dbData == null || dbData.length() == 0)
            return null;

        return Money.parse(dbData);
    }
}
