package com.swf.electionresult.runner;

import com.swf.electionresult.dto.ElectionDataDto;
import com.swf.electionresult.dto.WinningParty;
import com.swf.electionresult.error.*;
import com.swf.electionresult.formatter.ResultsFormatter;
import com.swf.electionresult.parser.ElectionDataParser;
import com.swf.electionresult.reader.InputFileReader;
import com.swf.electionresult.service.ElectionService;

import java.util.List;

public class ElectionResultRunner {
    private final InputFileReader fileReader;
    private final ResultsFormatter electionResultsFormatter;
    private final ElectionDataParser parser;
    private final ElectionService electionService;

    public ElectionResultRunner(InputFileReader fileReader,
                                ResultsFormatter electionResultsFormatter,
                                ElectionDataParser parser,
                                ElectionService electionService) {
        this.fileReader = fileReader;
        this.electionResultsFormatter = electionResultsFormatter;
        this.parser = parser;
        this.electionService = electionService;
    }

    public String run(String inputFilePath) throws InputFileNotFoundException, InvalidInputException,
            RedundantElectionResultsForConstituencyException, RedundantPartyCodeForConstituencyException,
            InvalidPartyCodeException, ConstituencyNotFoundException {
        List<String> electionResults = fileReader.read(inputFilePath);
        List<ElectionDataDto> electionDataTokens = parser.parse(electionResults);
        electionService.saveElectionResults(electionDataTokens);
        List<WinningParty> winners = electionService.getWinners();
        return String.join("\n", electionResultsFormatter.format(winners));
    }
}
