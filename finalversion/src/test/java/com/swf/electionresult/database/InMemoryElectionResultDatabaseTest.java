package com.swf.electionresult.database;

import com.swf.electionresult.domain.valueobject.PartyCode;
import com.swf.electionresult.domain.valueobject.VoteShare;
import com.swf.electionresult.dto.ElectionResult;
import com.swf.electionresult.error.ConstituencyNotFoundException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class InMemoryElectionResultDatabaseTest {

    @AfterEach
    public void resetSingleton() throws Exception {
        Field electionResultsDatabase = InMemoryElectionResultDatabase.class.getDeclaredField("database");
        electionResultsDatabase.setAccessible(true);
        electionResultsDatabase.set(null, null);
    }

    @Test
    public void shouldCreateOnlyOneInstanceOfDatabase() {
        InMemoryElectionResultDatabase electionResultsDatabase1 = InMemoryElectionResultDatabase.getInstance();
        InMemoryElectionResultDatabase electionResultsDatabase2 = InMemoryElectionResultDatabase.getInstance();

        assertEquals(electionResultsDatabase1.hashCode(), electionResultsDatabase2.hashCode());
    }

    @Test
    public void shouldBeAbleToAddElectionResults() {
        InMemoryElectionResultDatabase electionResultDatabase = InMemoryElectionResultDatabase.getInstance();
        List<VoteShare> voteShares = List.of(new VoteShare(PartyCode.BJP, 200),
                new VoteShare(PartyCode.BSP, 20), new VoteShare(PartyCode.INC, 30));

        electionResultDatabase.add("Pune", voteShares);

        //assertEquals(1, electionResultDatabase.getResults().size());
        assertTrue(electionResultDatabase.getResults().contains(new ElectionResult("Pune", voteShares)));

    }

    @Test
    public void shouldBeAbleToReturnMaxVotedParty() throws ConstituencyNotFoundException {
        InMemoryElectionResultDatabase electionResultDatabase = InMemoryElectionResultDatabase.getInstance();
        List<VoteShare> voteShares = List.of(new VoteShare(PartyCode.BJP, 200),
                new VoteShare(PartyCode.BSP, 20), new VoteShare(PartyCode.INC, 30));
        electionResultDatabase.add("Pune", voteShares);

        VoteShare maxVotedParty = electionResultDatabase.maxVotedParty("Pune");

        assertEquals(PartyCode.BJP, maxVotedParty.getPartyCode());
        assertEquals(200, maxVotedParty.getVotes());
    }

    @Test
    public void shouldThrowExceptionWhileReturningMaxVotedPartyIfConstituencyNotFound() {
        InMemoryElectionResultDatabase electionResultDatabase = InMemoryElectionResultDatabase.getInstance();
        List<VoteShare> voteShares = List.of(new VoteShare(PartyCode.BJP, 200),
                new VoteShare(PartyCode.BSP, 20), new VoteShare(PartyCode.INC, 30));
        electionResultDatabase.add("Pune", voteShares);

        Exception exception = assertThrows(ConstituencyNotFoundException.class,
                () -> electionResultDatabase.maxVotedParty("Mumbai"));

        assertEquals("Mumbai", exception.getMessage());
    }

    @Test
    public void shouldBeAbleToReturnTotalVotes() throws ConstituencyNotFoundException {
        InMemoryElectionResultDatabase electionResultDatabase = InMemoryElectionResultDatabase.getInstance();
        List<VoteShare> voteShares = List.of(new VoteShare(PartyCode.BJP, 200),
                new VoteShare(PartyCode.BSP, 20), new VoteShare(PartyCode.INC, 30));
        electionResultDatabase.add("Pune", voteShares);

        int totalVotes = electionResultDatabase.totalVotes("Pune");

        assertEquals(250, totalVotes);

    }

    @Test
    public void shouldThrowExceptionWhileReturningTotalVotesIfConstituencyNotFound() {
        InMemoryElectionResultDatabase electionResultDatabase = InMemoryElectionResultDatabase.getInstance();
        List<VoteShare> voteShares = List.of(new VoteShare(PartyCode.BJP, 200),
                new VoteShare(PartyCode.BSP, 20), new VoteShare(PartyCode.INC, 30));
        electionResultDatabase.add("Pune", voteShares);

        Exception exception = assertThrows(ConstituencyNotFoundException.class,
                () -> electionResultDatabase.totalVotes("Mumbai"));

        assertEquals("Mumbai", exception.getMessage());
    }

    @Test
    public void shouldGetAllConstituencies() {
        InMemoryElectionResultDatabase electionResultDatabase = InMemoryElectionResultDatabase.getInstance();
        List<VoteShare> voteShares1 = List.of(new VoteShare(PartyCode.BJP, 200),
                new VoteShare(PartyCode.BSP, 20), new VoteShare(PartyCode.INC, 30));
        electionResultDatabase.add("Pune", voteShares1);
        List<VoteShare> voteShares2 = List.of(new VoteShare(PartyCode.BJP, 200),
                new VoteShare(PartyCode.BSP, 20), new VoteShare(PartyCode.INC, 30));
        electionResultDatabase.add("Mumbai", voteShares2);
        List<VoteShare> voteShares3 = List.of(new VoteShare(PartyCode.BJP, 200),
                new VoteShare(PartyCode.BSP, 20), new VoteShare(PartyCode.INC, 30));
        electionResultDatabase.add("Bangalore", voteShares3);
        List<String> constituencies = List.of("Pune", "Mumbai", "Bangalore");

        List<String> actualConstituencies = electionResultDatabase.getConstituencies();

        assertEquals(constituencies, actualConstituencies);
    }

}