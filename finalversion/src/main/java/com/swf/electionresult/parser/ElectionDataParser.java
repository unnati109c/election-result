package com.swf.electionresult.parser;

import com.swf.electionresult.dto.ElectionDataDto;
import com.swf.electionresult.dto.VotesShareDto;
import com.swf.electionresult.error.InvalidInputException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class ElectionDataParser {

    public List<ElectionDataDto> parse(List<String> electionResults) throws InvalidInputException {
        List<ElectionDataDto> electionDataTokens = new ArrayList<>();

        Pattern p = Pattern.compile(" *([a-zA-Z]+)+ *- *((?:[a-zA-Z]+ *= *\\d+ *,+ *)+ *[a-zA-Z]+ *= *\\d+ *)");
        for (String data : electionResults) {
            if (data.isEmpty()) continue;
            Matcher m = p.matcher(data);
            if (m.matches()) {
                String votes = m.group(2);
                List<String> tokens = Arrays.stream(votes.trim().split(","))
                        .dropWhile(String::isBlank).collect(Collectors.toList());
                List<VotesShareDto> voteShares = new ArrayList<>();
                for (String token : tokens) {
                    List<String> tokenData = Arrays.stream(token.trim().split("="))
                            .dropWhile(String::isBlank).toList();
                    voteShares.add(new VotesShareDto(tokenData.get(0).trim(), Integer.parseInt(tokenData.get(1).trim())));
                }
                electionDataTokens.add(
                        new ElectionDataDto(m.group(1), voteShares));
            } else
                throw new InvalidInputException(data);
        }
        return electionDataTokens;
    }
}
