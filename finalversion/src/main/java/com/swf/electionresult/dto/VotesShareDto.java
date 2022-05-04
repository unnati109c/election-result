package com.swf.electionresult.dto;

import java.util.Objects;

public class VotesShareDto {
    private final String partyCode;
    private final int votes;

    public VotesShareDto(String partyCode, int vote) {
        this.partyCode = partyCode;
        this.votes = vote;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        VotesShareDto that = (VotesShareDto) o;
        return votes == that.votes && Objects.equals(partyCode, that.partyCode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(partyCode, votes);
    }

    public String getPartyCode() {
        return partyCode;
    }

    public int getVotes() {
        return votes;
    }
}
