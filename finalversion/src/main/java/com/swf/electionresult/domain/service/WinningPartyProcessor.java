package com.swf.electionresult.domain.service;

import com.swf.electionresult.domain.valueobject.PartyCode;
import com.swf.electionresult.domain.valueobject.VoteShare;
import com.swf.electionresult.dto.WinningParty;

public class WinningPartyProcessor {

    public WinningParty getWinner(String constituency, VoteShare winner, int total) {

        PartyCode partyCode = winner.getPartyCode();
        int votes = winner.getVotes();


        double voteShare = ((votes * 1.0) / total) * 100;

        return new WinningParty(constituency, partyCode, voteShare);

    }
}
