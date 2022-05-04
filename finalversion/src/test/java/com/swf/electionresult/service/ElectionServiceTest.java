package com.swf.electionresult.service;

import com.swf.electionresult.domain.service.WinningPartyProcessor;
import com.swf.electionresult.domain.valueobject.PartyCode;
import com.swf.electionresult.domain.valueobject.VoteShare;
import com.swf.electionresult.dto.ElectionDataDto;
import com.swf.electionresult.dto.VotesShareDto;
import com.swf.electionresult.dto.WinningParty;
import com.swf.electionresult.error.ConstituencyNotFoundException;
import com.swf.electionresult.error.InvalidPartyCodeException;
import com.swf.electionresult.error.RedundantElectionResultsForConstituencyException;
import com.swf.electionresult.error.RedundantPartyCodeForConstituencyException;
import com.swf.electionresult.factory.PartyCodeFactory;
import com.swf.electionresult.repository.ElectionResultRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ElectionServiceTest {
    @Mock
    ElectionResultRepository electionResultRepository;
    @Mock
    WinningPartyProcessor winningPartyProcessor;
    @Mock
    PartyCodeFactory partyCodeFactory;
    @InjectMocks
    ElectionService electionService;

    @Test
    public void shouldBeAbleToSaveElectionResults()
            throws RedundantElectionResultsForConstituencyException, InvalidPartyCodeException, RedundantPartyCodeForConstituencyException {
        List<ElectionDataDto> electionDataTokens = List.of(
                new ElectionDataDto("Pune",
                        List.of(
                                new VotesShareDto("BJP", 30),
                                new VotesShareDto("INC", 40),
                                new VotesShareDto("IND", 22)
                        )),
                new ElectionDataDto("Mumbai",
                        List.of(
                                new VotesShareDto("BJP", 30),
                                new VotesShareDto("INC", 40),
                                new VotesShareDto("IND", 22)
                        ))
        );
        when(partyCodeFactory.createFrom("BJP")).thenReturn(PartyCode.BJP);
        when(partyCodeFactory.createFrom("INC")).thenReturn(PartyCode.INC);
        when(partyCodeFactory.createFrom("IND")).thenReturn(PartyCode.IND);
        when(electionResultRepository.getConstituencies())
                .thenReturn(List.of()).thenReturn(List.of("Pune")).thenReturn(List.of("Pune", "Mumbai"));

        electionService.saveElectionResults(electionDataTokens);

        verify(electionResultRepository, times(2)).getConstituencies();
        verify(electionResultRepository, times(2)).add(any(), any());
    }

    @Test
    public void saveElectionResults_shouldThrowRedundantPartyCodeForConstituencyException()
            throws InvalidPartyCodeException {
        List<ElectionDataDto> electionDataTokens = List.of(
                new ElectionDataDto("Pune",
                        List.of(
                                new VotesShareDto("BJP", 30),
                                new VotesShareDto("BJP", 40),
                                new VotesShareDto("IND", 22)
                        ))
        );
        when(partyCodeFactory.createFrom("BJP")).thenReturn(PartyCode.BJP);

        Exception exception = assertThrows(RedundantPartyCodeForConstituencyException.class,
                () -> electionService.saveElectionResults(electionDataTokens));
        assertEquals("Pune-BJP", exception.getMessage());
    }


    @Test
    public void saveElectionResults_shouldThrowRedundantElectionResultsForConstituencyException()
            throws InvalidPartyCodeException {
        List<ElectionDataDto> electionDataTokens = List.of(
                new ElectionDataDto("Pune",
                        List.of(
                                new VotesShareDto("BJP", 30),
                                new VotesShareDto("INC", 40),
                                new VotesShareDto("IND", 22)
                        )),
                new ElectionDataDto("Pune",
                        List.of(
                                new VotesShareDto("BJP", 30),
                                new VotesShareDto("INC", 40),
                                new VotesShareDto("IND", 22)
                        ))
        );
        when(partyCodeFactory.createFrom("BJP")).thenReturn(PartyCode.BJP);
        when(partyCodeFactory.createFrom("INC")).thenReturn(PartyCode.INC);
        when(partyCodeFactory.createFrom("IND")).thenReturn(PartyCode.IND);
        when(electionResultRepository.getConstituencies()).thenReturn(List.of()).thenReturn(List.of("Pune"));


        Exception exception = assertThrows(RedundantElectionResultsForConstituencyException.class,
                () -> electionService.saveElectionResults(electionDataTokens));
        assertEquals("Pune", exception.getMessage());
    }

    @Test
    public void shouldBeAbleToGetWinners() throws ConstituencyNotFoundException {
        when(electionResultRepository.getConstituencies()).thenReturn(List.of("Pune", "Mumbai"));
        when(electionResultRepository.maxVotedParty("Pune"))
                .thenReturn(new VoteShare(PartyCode.BJP, 40));
        when(electionResultRepository.maxVotedParty("Mumbai"))
                .thenReturn(new VoteShare(PartyCode.BJP, 40));
        when(electionResultRepository.totalVotes("Pune"))
                .thenReturn(100);
        when(electionResultRepository.totalVotes("Mumbai"))
                .thenReturn(100);
        when(winningPartyProcessor.getWinner("Pune", new VoteShare(PartyCode.BJP, 40), 100))
                .thenReturn(new WinningParty("Pune", PartyCode.BJP, 40.0));
        when(winningPartyProcessor.getWinner("Mumbai", new VoteShare(PartyCode.BJP, 40), 100))
                .thenReturn(new WinningParty("Mumbai", PartyCode.BJP, 40.0));
        List<WinningParty> winningParties = List.of(
                new WinningParty("Pune", PartyCode.BJP, 40.0),
                new WinningParty("Mumbai", PartyCode.BJP, 40.0));

        assertEquals(winningParties, electionService.getWinners());
    }

}