# Domain Modelling

## domain.valueobject
1. **PartyCode**  
   - State 
     - abbreviation
   - Behaviour 
   - Implementations -
     - BJP - Bhartiya Janta Party
     - INC - Indian National Congress
     - BSP - Bahujan Samaj Party
     - CPI - Communist Party of India
     - NCP - Nationalist Congress Party
     - IND - Independent
2. **VoteShare**
    - State
        - partyCode
        - vote
    - Behaviour

## domain.service
3. **WinningPartyProcessor**
    - State
    - Behaviour
        - getWinner
## factory
4. **PartyCodeFactory**
    - State
    - Behaviour 
        - createFromRawPartyCode
        
## parser
5. **ElectionDataParser**
    - State
    - Behaviour
        - parse
## service
6. **ElectionService**
    - State
        - repository
        - factory
        - processor
    - Behaviour
        - saveElectionResults
        - getWinner
      
## repository
7. **ElectionResultRepository**
    - State
    - Behaviour
      - add
      - getMaxVotedParty
      - getTotalVotes
      - getConstituencies
    - implementations
      - InMemoryElectionResultRepository

## database
8. **ElectionResultDatabase**
    - State
        - election results per constituency data
    - Behaviour
        - add
        - getMaxVotedParty
        - getTotalVotes
        - getConstituencies
    - implementations
        - InMemoryElectionResultDatabase

## formatter
9. **ResultsFormatter**
    - State 
    - Behaviour
      - print
    - implementations
        - ElectionResultsFormatter
## reader
10. **InputFileReader**
    - State
    - Behaviour
        - read
## runner
11. **ElectionResultsRunner**
    - State
    - Behaviour
        - run