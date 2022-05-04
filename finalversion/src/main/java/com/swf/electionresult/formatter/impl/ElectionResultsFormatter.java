package com.swf.electionresult.formatter.impl;

import com.swf.electionresult.dto.WinningParty;
import com.swf.electionresult.formatter.ResultsFormatter;

import java.util.ArrayList;
import java.util.List;

public class ElectionResultsFormatter implements ResultsFormatter {

    @Override
    public List<String> format(List<WinningParty> winningParties) {

        List<String> winners = new ArrayList<>();
        winners.add("Constituency,Winning Party,Votes Share");

        for (WinningParty winningParty : winningParties) {
            String winner = winningParty.getConstituency() + ", " +
                    winningParty.getPartyCode().getAbbreviation() + ", " +
                    String.format("%.1f", winningParty.getVote());
            winners.add(winner);
        }

        return winners;
    }
}
