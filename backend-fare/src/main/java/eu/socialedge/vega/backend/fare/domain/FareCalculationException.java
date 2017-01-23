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
package eu.socialedge.vega.backend.fare.domain;

public class FareCalculationException extends RuntimeException {
    public FareCalculationException(String message) {
        super(message);
    }

    public FareCalculationException(String message, Throwable cause) {
        super(message, cause);
    }

    public FareCalculationException(Throwable cause) {
        super(cause);
    }
}
