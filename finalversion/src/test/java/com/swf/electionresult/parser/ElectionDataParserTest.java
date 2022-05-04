package com.swf.electionresult.parser;

import com.swf.electionresult.dto.ElectionDataDto;
import com.swf.electionresult.dto.VotesShareDto;
import com.swf.electionresult.error.InvalidInputException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ElectionDataParserTest {


    private static Stream<Arguments> invalidElectionData() {
        return Stream.of(
                Arguments.of(List.of(" ")),
                Arguments.of(List.of("-")),
                Arguments.of(List.of("Jaipur-BJP=4XXX0,INC=25Z")),
                Arguments.of(List.of("Jaipur-BJP=40")),
                Arguments.of(List.of("Jaip12ur-B33JP=4XXX0,INC=25Z"))
        );
    }

    @Test
    public void shouldParseElectionData() throws InvalidInputException {
        ElectionDataParser electionDataParser = new ElectionDataParser();
        List<String> electionData = List.of("Jaipur-BJP=400,INC=250", "");
        List<ElectionDataDto> expected = List.of(new ElectionDataDto("Jaipur",
                List.of(new VotesShareDto("BJP", 400),
                        new VotesShareDto("INC", 250))));

        List<ElectionDataDto> electionDataTokens = electionDataParser.parse(electionData);

        assertEquals(expected, electionDataTokens);
    }

    @ParameterizedTest
    @MethodSource("invalidElectionData")
    public void shouldThrowExceptionIfInvalidInput(List<String> invalidInput) {
        ElectionDataParser electionDataParser = new ElectionDataParser();

        for (String input : invalidInput) {
            Exception exception = assertThrows(InvalidInputException.class, () -> electionDataParser.parse(invalidInput));
            assertEquals(input, exception.getMessage());
        }
    }

}