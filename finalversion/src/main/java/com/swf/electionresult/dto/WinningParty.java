package com.swf.electionresult.dto;

import com.swf.electionresult.domain.valueobject.PartyCode;

import java.util.Objects;

public class WinningParty {
    private final String constituency;
    private final PartyCode partyCode;
    private final double vote;

    public WinningParty(String constituency, PartyCode partyCode, double vote) {
        this.constituency = constituency;
        this.partyCode = partyCode;
        this.vote = vote;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        WinningParty that = (WinningParty) o;
        return Double.compare(that.vote, vote) == 0 && Objects.equals(constituency, that.constituency) && partyCode == that.partyCode;
    }

    @Override
    public int hashCode() {
        return Objects.hash(constituency, partyCode, vote);
    }

    public String getConstituency() {
        return constituency;
    }

    public PartyCode getPartyCode() {
        return partyCode;
    }

    public double getVote() {
        return vote;
    }
}
