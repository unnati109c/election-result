package com.swf.electionresult.formatter.impl;

import com.swf.electionresult.domain.valueobject.PartyCode;
import com.swf.electionresult.dto.WinningParty;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ElectionResultsFormatterTest {

    @Test
    public void shouldBeAbleToFormatElectionResults() {
        ElectionResultsFormatter formatter = new ElectionResultsFormatter();
        List<WinningParty> winningParties = List.of(
                new WinningParty("Pune", PartyCode.BJP, 40.0),
                new WinningParty("Mumbai", PartyCode.BJP, 40.0));
        List<String> expectedResults = List.of(
                "Constituency,Winning Party,Votes Share",
                "Pune, Bhartiya Janta Party, 40.0",
                "Mumbai, Bhartiya Janta Party, 40.0");

        List<String> results = formatter.format(winningParties);

        assertEquals(expectedResults, results);
    }

}