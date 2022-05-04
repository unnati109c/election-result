package com.swf.electionresult.dto;

import java.util.List;
import java.util.Objects;

public class ElectionDataDto {
    private final String constituency;
    private final List<VotesShareDto> voteShares;

    public ElectionDataDto(String constituency, List<VotesShareDto> votesShares) {
        this.constituency = constituency;
        this.voteShares = votesShares;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ElectionDataDto that = (ElectionDataDto) o;
        return Objects.equals(constituency, that.constituency) && Objects.equals(voteShares, that.voteShares);
    }

    @Override
    public int hashCode() {
        return Objects.hash(constituency, voteShares);
    }

    public String getConstituency() {
        return constituency;
    }

    public List<VotesShareDto> getVoteShares() {
        return voteShares;
    }
}
