package com.example.service;

import com.example.model.Pattern;
import java.util.List;

public interface PatternService {
    List<Pattern> getAllPatterns();
    Pattern getPatternById(String id);
    List<Pattern> getPatternsByQuadrant(String quadrant);
    List<Pattern> getPatternsByRing(String ring);
    Pattern createPattern(Pattern pattern);
    Pattern updatePattern(String id, Pattern pattern);
    boolean deletePattern(String id);
}
