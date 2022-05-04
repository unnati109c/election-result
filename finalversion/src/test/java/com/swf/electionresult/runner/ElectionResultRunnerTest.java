package com.swf.electionresult.runner;

import com.swf.electionresult.domain.service.WinningPartyProcessor;
import com.swf.electionresult.error.*;
import com.swf.electionresult.factory.PartyCodeFactory;
import com.swf.electionresult.formatter.impl.ElectionResultsFormatter;
import com.swf.electionresult.parser.ElectionDataParser;
import com.swf.electionresult.reader.InputFileReader;
import com.swf.electionresult.repository.impl.InMemoryElectionResultRepository;
import com.swf.electionresult.service.ElectionService;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ElectionResultRunnerTest {

    @Test
    public void shouldRunTheElectionResultsApplicationAndGenerateResult()
            throws RedundantElectionResultsForConstituencyException, InvalidInputException,
            InputFileNotFoundException, ConstituencyNotFoundException,
            RedundantPartyCodeForConstituencyException, InvalidPartyCodeException {
        ElectionResultRunner electionResultRunner = new ElectionResultRunner(
                new InputFileReader(),
                new ElectionResultsFormatter(),
                new ElectionDataParser(),
                new ElectionService(
                        InMemoryElectionResultRepository.getInstance(),
                        new WinningPartyProcessor(),
                        new PartyCodeFactory()));
        String expectedElectionResults =
                "Constituency,Winning Party,Votes Share\n" +
                        "Jaipur, Bhartiya Janta Party, 48.8\n" +
                        "Bangalore, Indian National Congress, 49.7\n" +
                        "Pune, Bhartiya Janta Party, 44.1";

        String actualElectionResults = electionResultRunner.run("src\\test\\resources\\input.txt");

        assertEquals(expectedElectionResults, actualElectionResults);
    }
}