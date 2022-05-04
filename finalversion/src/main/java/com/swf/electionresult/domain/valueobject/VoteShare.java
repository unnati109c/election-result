package com.swf.electionresult.domain.valueobject;

import java.util.Objects;

public class VoteShare {
    private final PartyCode partyCode;
    private final int votes;

    public VoteShare(PartyCode partyCode, int votes) {
        this.partyCode = partyCode;
        this.votes = votes;
    }

    public PartyCode getPartyCode() {
        return partyCode;
    }

    public int getVotes() {
        return votes;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        VoteShare voteShare = (VoteShare) o;
        return votes == voteShare.votes && partyCode == voteShare.partyCode;
    }

    @Override
    public int hashCode() {
        return Objects.hash(partyCode, votes);
    }
}
