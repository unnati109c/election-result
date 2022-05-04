package com.swf.electionresult.dto;

import com.swf.electionresult.domain.valueobject.VoteShare;

import java.util.List;
import java.util.Objects;

public class ElectionResult {
    private final String constituency;
    private final List<VoteShare> voteShares;

    public ElectionResult(String constituency, List<VoteShare> voteShares) {
        this.constituency = constituency;
        this.voteShares = voteShares;
    }

    public String getConstituency() {
        return constituency;
    }

    public List<VoteShare> getVoteShares() {
        return voteShares;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ElectionResult result = (ElectionResult) o;
        return Objects.equals(constituency, result.constituency) && Objects.equals(voteShares, result.voteShares);
    }

    @Override
    public int hashCode() {
        return Objects.hash(constituency, voteShares);
    }
}
