package com.swf.electionresult.formatter;

import com.swf.electionresult.dto.WinningParty;

import java.util.List;

public interface ResultsFormatter {
    public List<String> format(List<WinningParty> winningParties);

}
