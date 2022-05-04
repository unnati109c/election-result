package com.swf.electionresult.reader;

import com.swf.electionresult.error.InputFileNotFoundException;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class InputFileReader {

    public List<String> read(String filePath) throws InputFileNotFoundException {
        try {
            Path path = Paths.get(filePath);
            return Files.readAllLines(path);
        } catch (Exception e) {
            throw new InputFileNotFoundException();
        }
    }
}
