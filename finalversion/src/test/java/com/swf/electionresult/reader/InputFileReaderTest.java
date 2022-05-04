package com.swf.electionresult.reader;

import com.swf.electionresult.error.InputFileNotFoundException;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class InputFileReaderTest {

    @Test
    public void shouldBeAbleToReadInputFile() throws InputFileNotFoundException {
        InputFileReader reader = new InputFileReader();
        List<String> expected = List.of("Hello", "The Election Results Are Out!!", "", "Bye");

        List<String> inputs = reader.read("src\\test\\resources\\testInput.txt");

        assertEquals(expected, inputs);
    }

    @Test
    public void shouldThrowExceptionIfInputFileNotFound() {
        InputFileReader reader = new InputFileReader();

        assertThrows(InputFileNotFoundException.class, () -> reader.read("xyz"));
    }

}