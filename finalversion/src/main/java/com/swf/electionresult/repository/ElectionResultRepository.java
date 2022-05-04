package com.swf.electionresult.repository;

import com.swf.electionresult.domain.valueobject.VoteShare;
import com.swf.electionresult.dto.ElectionResult;
import com.swf.electionresult.error.ConstituencyNotFoundException;

import java.util.List;

public interface ElectionResultRepository {
    void add(String constituency, List<VoteShare> voteShares);

    List<ElectionResult> getResults();

    VoteShare maxVotedParty(String constituency) throws ConstituencyNotFoundException;

    int totalVotes(String constituency) throws ConstituencyNotFoundException;

    List<String> getConstituencies();


}
