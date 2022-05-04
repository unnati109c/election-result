## Domain Analysis
1. Election night
   1. Input 
      - file input containing election results given by data supplier
      - input data contains constituency, party codes and votes per party
      - repeating set of pairs with the party code and the votes cast
        - There has to be at least 2 parties
2. Output   
    - Winning party details - Constituency, Winning Party, Vote Share

3. There are total 6 parties at maximum : BJP INC NCP CPI BSP IND
4. If two parties have same votes which is maximum for the constituency (TIE case)
    - Assuming the winner will be the party which appears first in input
5. Fail Fast Approach to be used if -
    - Invalid vote count
    - Constituency not found  
    - Invalid party code
    - Invalid Input format
    - Redundant Constituency in input
    - Input File Not Found 
    - Redundant PartyCode For Constituency 
6. Votes can never be negative or decimal number
7. Winner party is the party having maximum number of votes
8. Vote share of winning party is votes a party has secured as a percentage of the total number of votes polled in a constituency
