package com.swf.electionresult.repository.impl;

import com.swf.electionresult.database.InMemoryElectionResultDatabase;
import com.swf.electionresult.domain.valueobject.PartyCode;
import com.swf.electionresult.domain.valueobject.VoteShare;
import com.swf.electionresult.error.ConstituencyNotFoundException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class InMemoryElectionResultRepositoryTest {

    @AfterEach
    public void resetSingleton() throws Exception {
        Field electionResultsDatabase = InMemoryElectionResultDatabase.class.getDeclaredField("database");
        electionResultsDatabase.setAccessible(true);
        electionResultsDatabase.set(null, null);
        Field electionResultsRepository = InMemoryElectionResultRepository.class.getDeclaredField("repository");
        electionResultsRepository.setAccessible(true);
        electionResultsRepository.set(null, null);
    }

    @Test
    public void shouldCreateOnlyOneInstanceOfRepository() {
        InMemoryElectionResultRepository electionResultsRepository1 = InMemoryElectionResultRepository.getInstance();
        InMemoryElectionResultRepository electionResultsRepository2 = InMemoryElectionResultRepository.getInstance();

        assertEquals(electionResultsRepository1.hashCode(), electionResultsRepository2.hashCode());
    }

    @Test
    public void shouldBeAbleToAddElectionResults() {
        InMemoryElectionResultRepository electionResultRepository = InMemoryElectionResultRepository.getInstance();
        List<VoteShare> voteShares = List.of(new VoteShare(PartyCode.BJP, 200),
                new VoteShare(PartyCode.BSP, 20), new VoteShare(PartyCode.INC, 30));

        electionResultRepository.add("Pune", voteShares);

        assertEquals(1, electionResultRepository.getResults().size());
    }

    @Test
    public void shouldBeAbleToReturnMaxVotedParty() throws ConstituencyNotFoundException {
        InMemoryElectionResultRepository electionResultRepository = InMemoryElectionResultRepository.getInstance();
        List<VoteShare> voteShares = List.of(new VoteShare(PartyCode.BJP, 200),
                new VoteShare(PartyCode.BSP, 20), new VoteShare(PartyCode.INC, 30));
        electionResultRepository.add("Pune", voteShares);

        VoteShare maxVotedParty = electionResultRepository.maxVotedParty("Pune");

        assertEquals(PartyCode.BJP, maxVotedParty.getPartyCode());
        assertEquals(200, maxVotedParty.getVotes());
    }

    @Test
    public void shouldThrowExceptionWhileReturningMaxVotedPartyIfConstituencyNotFound() {
        InMemoryElectionResultRepository electionResultRepository = InMemoryElectionResultRepository.getInstance();
        List<VoteShare> voteShares = List.of(new VoteShare(PartyCode.BJP, 200),
                new VoteShare(PartyCode.BSP, 20), new VoteShare(PartyCode.INC, 30));
        electionResultRepository.add("Pune", voteShares);

        Exception exception = assertThrows(ConstituencyNotFoundException.class,
                () -> electionResultRepository.maxVotedParty("Mumbai"));

        assertEquals("Mumbai", exception.getMessage());
    }

    @Test
    public void shouldBeAbleToReturnTotalVotes() throws ConstituencyNotFoundException {
        InMemoryElectionResultRepository electionResultRepository = InMemoryElectionResultRepository.getInstance();
        List<VoteShare> voteShares = List.of(new VoteShare(PartyCode.BJP, 200),
                new VoteShare(PartyCode.BSP, 20), new VoteShare(PartyCode.INC, 30));
        electionResultRepository.add("Pune", voteShares);

        int totalVotes = electionResultRepository.totalVotes("Pune");

        assertEquals(250, totalVotes);

    }

    @Test
    public void shouldThrowExceptionWhileReturningTotalVotesIfConstituencyNotFound() {
        InMemoryElectionResultRepository electionResultRepository = InMemoryElectionResultRepository.getInstance();
        List<VoteShare> voteShares = List.of(new VoteShare(PartyCode.BJP, 200),
                new VoteShare(PartyCode.BSP, 20), new VoteShare(PartyCode.INC, 30));
        electionResultRepository.add("Pune", voteShares);

        Exception exception = assertThrows(ConstituencyNotFoundException.class,
                () -> electionResultRepository.totalVotes("Mumbai"));

        assertEquals("Mumbai", exception.getMessage());
    }

    @Test
    public void shouldGetAllConstituencies() {
        InMemoryElectionResultRepository electionResultRepository = InMemoryElectionResultRepository.getInstance();
        List<VoteShare> voteShares1 = List.of(new VoteShare(PartyCode.BJP, 200),
                new VoteShare(PartyCode.BSP, 20), new VoteShare(PartyCode.INC, 30));
        electionResultRepository.add("Pune", voteShares1);
        List<VoteShare> voteShares2 = List.of(new VoteShare(PartyCode.BJP, 200),
                new VoteShare(PartyCode.BSP, 20), new VoteShare(PartyCode.INC, 30));
        electionResultRepository.add("Mumbai", voteShares2);
        List<VoteShare> voteShares3 = List.of(new VoteShare(PartyCode.BJP, 200),
                new VoteShare(PartyCode.BSP, 20), new VoteShare(PartyCode.INC, 30));
        electionResultRepository.add("Bangalore", voteShares3);
        List<String> constituencies = List.of("Pune", "Mumbai", "Bangalore");

        List<String> actualConstituencies = electionResultRepository.getConstituencies();

        assertEquals(constituencies, actualConstituencies);
    }


}