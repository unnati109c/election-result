package com.swf.electionresult;

import com.swf.electionresult.domain.service.WinningPartyProcessor;
import com.swf.electionresult.error.*;
import com.swf.electionresult.factory.PartyCodeFactory;
import com.swf.electionresult.formatter.ResultsFormatter;
import com.swf.electionresult.formatter.impl.ElectionResultsFormatter;
import com.swf.electionresult.parser.ElectionDataParser;
import com.swf.electionresult.reader.InputFileReader;
import com.swf.electionresult.repository.ElectionResultRepository;
import com.swf.electionresult.repository.impl.InMemoryElectionResultRepository;
import com.swf.electionresult.runner.ElectionResultRunner;
import com.swf.electionresult.service.ElectionService;

public class Main {
    public static void main(String[] args) throws RedundantElectionResultsForConstituencyException,
            InvalidInputException, InputFileNotFoundException, ConstituencyNotFoundException,
            RedundantPartyCodeForConstituencyException, InvalidPartyCodeException {

        InputFileReader fileReader = new InputFileReader();
        ElectionResultRepository electionResultRepository =
                InMemoryElectionResultRepository.getInstance();
        WinningPartyProcessor winningPartyProcessor = new WinningPartyProcessor();
        ResultsFormatter electionResultsFormatter = new ElectionResultsFormatter();
        PartyCodeFactory partyCodeFactory = new PartyCodeFactory();
        ElectionDataParser parser = new ElectionDataParser();
        ElectionService electionService = new ElectionService(electionResultRepository,
                winningPartyProcessor, partyCodeFactory);

        ElectionResultRunner electionResultRunner = new ElectionResultRunner(
                fileReader, electionResultsFormatter, parser, electionService);

        System.out.println(electionResultRunner.run(args[0]));
    }
}
