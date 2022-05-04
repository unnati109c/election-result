package com.swf.electionresult.repository.impl;

import com.swf.electionresult.database.InMemoryElectionResultDatabase;
import com.swf.electionresult.domain.valueobject.VoteShare;
import com.swf.electionresult.dto.ElectionResult;
import com.swf.electionresult.error.ConstituencyNotFoundException;
import com.swf.electionresult.repository.ElectionResultRepository;

import java.util.List;

public class InMemoryElectionResultRepository implements ElectionResultRepository {
    private static InMemoryElectionResultRepository repository;
    private final InMemoryElectionResultDatabase database;

    private InMemoryElectionResultRepository(InMemoryElectionResultDatabase database) {
        this.database = database;
    }

    public static InMemoryElectionResultRepository getInstance() {
        if (repository == null)
            repository = new InMemoryElectionResultRepository(InMemoryElectionResultDatabase.getInstance());
        return repository;
    }

    @Override
    public List<ElectionResult> getResults() {
        return database.getResults();
    }

    @Override
    public void add(String constituency, List<VoteShare> voteShares) {
        database.add(constituency, voteShares);
    }

    @Override
    public VoteShare maxVotedParty(String constituency) throws ConstituencyNotFoundException {
        return database.maxVotedParty(constituency);
    }

    @Override
    public int totalVotes(String constituency) throws ConstituencyNotFoundException {
        return database.totalVotes(constituency);
    }

    @Override
    public List<String> getConstituencies() {
        return database.getConstituencies();
    }
}
