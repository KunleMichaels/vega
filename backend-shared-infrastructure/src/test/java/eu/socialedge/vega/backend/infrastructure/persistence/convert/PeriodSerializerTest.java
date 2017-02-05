package eu.socialedge.vega.backend.infrastructure.persistence.convert;

import org.junit.Test;

import java.time.Period;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class PeriodSerializerTest {

    private static final Period period = Period.of(1, 1, 1);

    private static final PeriodSerializer converter = new PeriodSerializer();

    @Test
    public void shouldSerializePeriodObjectCorrectly() {
        String serializedPeriod = converter.convertToDatabaseColumn(period);

        assertEquals(period.toString(), serializedPeriod);
    }

    @Test
    public void shouldSerializeNullObjectToNull() {
        assertNull(converter.convertToDatabaseColumn(null));
    }

    @Test
    public void shouldRecreateSerializedPeriodObjectCorrectly() {
        Period serializedPeriod = converter.convertToEntityAttribute(period.toString());

        assertEquals(period, serializedPeriod);
    }

    @Test
    public void shouldRecreateToNullForNullInput() {
        assertNull(converter.convertToEntityAttribute(null));
    }

    @Test
    public void shouldRecreateToNullForEmptyInputString() {
        assertNull(converter.convertToEntityAttribute(""));
    }
}
