package fr.harington.parser;

import fr.harington.exception.MowerParserException;

public interface LineParserStrategy<T> {
    T parseLine(String line) throws MowerParserException;
}

