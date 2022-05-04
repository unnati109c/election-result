package com.swf.electionresult.factory;

import com.swf.electionresult.domain.valueobject.PartyCode;
import com.swf.electionresult.error.InvalidPartyCodeException;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class PartyCodeFactoryTest {

    private static Stream<Arguments> validPartyCodes() {
        return Stream.of(
                Arguments.of("BJP", PartyCode.BJP),
                Arguments.of("INC", PartyCode.INC),
                Arguments.of("BSP", PartyCode.BSP),
                Arguments.of("CPI", PartyCode.CPI),
                Arguments.of("NCP", PartyCode.NCP),
                Arguments.of("IND", PartyCode.IND)
        );
    }

    private static Stream<Arguments> invalidPartyCodes() {
        return Stream.of(
                Arguments.of("BJrq"),
                Arguments.of("???"),
                Arguments.of(""),
                Arguments.of("                     "),
                Arguments.of("@@!$#%^*"),
                Arguments.of("12345")
        );
    }

    @ParameterizedTest
    @MethodSource("validPartyCodes")
    public void shouldCreatePartCode(String rawPartyCode, PartyCode expectedPartyCode) throws InvalidPartyCodeException {
        PartyCodeFactory factory = new PartyCodeFactory();
        assertEquals(expectedPartyCode, factory.createFrom(rawPartyCode));
    }

    @ParameterizedTest
    @MethodSource("invalidPartyCodes")
    public void shouldThrowExceptionForInvalidPartyCode(String rawPartyCode) throws InvalidPartyCodeException {
        PartyCodeFactory factory = new PartyCodeFactory();
        Exception exception = assertThrows(InvalidPartyCodeException.class, () -> factory.createFrom(rawPartyCode));
        assertEquals(rawPartyCode, exception.getMessage());
    }

}