package org.jabref.model.entry;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Year;
import java.time.YearMonth;
import java.util.Optional;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class DateTest {

    public final Date date = new Date(2022, 03, 17);

    @Test
    void parseCorrectlyYearRangeDate() throws Exception {
        Date expectedDataRange = new Date(Year.of(2014), Year.of(2017));
        assertEquals(Optional.of(expectedDataRange), Date.parse("2014/2017"));
    }

    @Test
    void parseCorrectlyDayMonthYearDate() throws Exception {
        Date expected = new Date(LocalDate.of(2014, 6, 19));
        assertEquals(Optional.of(expected), Date.parse("19-06-2014"));
    }

    @Test
    void parseCorrectlyMonthYearDate() throws Exception {
        Date expected = new Date(YearMonth.of(2014, 6));
        assertEquals(Optional.of(expected), Date.parse("06-2014"));
    }

    @Test
    void parseCorrectlyYearMonthDate() throws Exception {
        Date expected = new Date(YearMonth.of(2014, 6));
        assertEquals(Optional.of(expected), Date.parse("2014-06"));
    }

    @Test
    void parseCorrectlyYearDate() throws Exception {
        Date expected = new Date(Year.of(2014));
        assertEquals(Optional.of(expected), Date.parse("2014"));
    }

    @Test
    void parseDateNull() {
        assertThrows(NullPointerException.class, () -> Date.parse(null));
    }

    @Test
    void testEquals1() throws Exception {
        assertEquals(false, date.equals(new Date(2020, 1, 21)));
    }

    @Test
    void testEquals2() throws Exception {
        assertEquals(true, date.equals(date));
    }

    @Test
    void testEquals3() throws Exception {
        assertEquals(false, date.equals(null));
    }

    @Test
    void testEquals4() throws Exception {
        assertEquals(false, date.equals(new Date(2020, 02, 02)));
    }

    @Test
    void testEquals5() throws Exception {
        Date date = new Date(2021, 03, 17);
        assertEquals(false, date.equals(LocalDateTime.now()));
    }

    @Test
    void testEquals6() throws Exception {
        assertEquals(false, date.equals(new Date(2022, 03, 16)));
    }

    @Test
    void testEquals7() throws Exception {
        assertEquals(false, date.equals(new Date(2022, 04, 17)));
    }

    @Test
    void testEquals8() throws Exception {
        assertEquals(false, date.equals(new Date(2021, 03, 17)));
    }

    @Test
    void testEquals9() throws Exception {
        Object date2 = LocalDate.of(2022, 03, 17);
        assertEquals(false, date.equals(date2));
    }

    @ParameterizedTest
    @MethodSource("provideInvalidCornerCaseArguments")
    public void nonExistentDates(String invalidDate, String errorMessage) {
        assertEquals(Optional.empty(), Date.parse(invalidDate), errorMessage);

    }

    private static Stream<Arguments> provideInvalidCornerCaseArguments() {
        return Stream.of(
                Arguments.of("", "input value not empty"),
                Arguments.of("32-06-2014", "day of month exists [1]"),
                Arguments.of("00-06-2014", "day of month exists [2]"),
                Arguments.of("30-13-2014", "month exists [1]"),
                Arguments.of("30-00-2014", "month exists [2]")
        );
    }
}
