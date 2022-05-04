package com.swf.electionresult.database;

import com.swf.electionresult.domain.valueobject.VoteShare;
import com.swf.electionresult.dto.ElectionResult;
import com.swf.electionresult.error.ConstituencyNotFoundException;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class InMemoryElectionResultDatabase {
    private static InMemoryElectionResultDatabase database;
    private final List<ElectionResult> results;

    private InMemoryElectionResultDatabase() {
        this.results = new ArrayList<>();
    }

    public static InMemoryElectionResultDatabase getInstance() {
        if (database == null)
            database = new InMemoryElectionResultDatabase();
        return database;
    }

    public List<ElectionResult> getResults() {
        return results;
    }

    public void add(String constituency, List<VoteShare> voteShares) {

        results.add(new ElectionResult(constituency, voteShares));
    }


    public VoteShare maxVotedParty(String constituency) throws ConstituencyNotFoundException {
        for (ElectionResult result : results)
            if (result.getConstituency().equalsIgnoreCase(constituency))
                return result.getVoteShares().stream().max(Comparator.comparingInt(VoteShare::getVotes)).get();
        throw new ConstituencyNotFoundException(constituency);
    }

    public int totalVotes(String constituency) throws ConstituencyNotFoundException {
        for (ElectionResult result : results)
            if (result.getConstituency().equalsIgnoreCase(constituency))
                return result.getVoteShares().stream().mapToInt(VoteShare::getVotes).sum();
        throw new ConstituencyNotFoundException(constituency);
    }

    public List<String> getConstituencies() {
        List<String> constituencies = new ArrayList<>();
        for (ElectionResult result : results) constituencies.add(result.getConstituency());
        return constituencies;
    }
}
