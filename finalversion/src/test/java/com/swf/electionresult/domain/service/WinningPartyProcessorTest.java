package com.swf.electionresult.domain.service;

import com.swf.electionresult.domain.valueobject.PartyCode;
import com.swf.electionresult.domain.valueobject.VoteShare;
import com.swf.electionresult.dto.WinningParty;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class WinningPartyProcessorTest {

    @Test
    public void shouldGetWinningParty() {
        WinningPartyProcessor processor = new WinningPartyProcessor();
        VoteShare winner = new VoteShare(PartyCode.BJP, 100);
        String constituency = "Pune";
        int total = 200;
        WinningParty winningParty = new WinningParty(constituency, winner.getPartyCode(),
                ((winner.getVotes() * 1.0) / total) * 100);

        WinningParty actual = processor.getWinner(constituency, winner, total);

        assertEquals(winningParty, actual);
    }

}