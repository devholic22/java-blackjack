package model.name;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static util.Keyword.NAME_SPLITTER;

public class Names {

    private final List<Name> names;

    private Names(final List<Name> names) {
        this.names = names;
    }

    public static Names from(final List<Name> names) {
        return new Names(names);
    }

    public static List<Name> createScoreNameCards(final int score, final List<String> names) {
        return names.stream()
                .map(name -> Name.withScore(score, name))
                .collect(Collectors.toList());
    }

    public static List<Name> createSpecialNameCards(final String special, final List<String> names) {
        return names.stream()
                .map(name -> Name.from(special + name))
                .collect(Collectors.toList());
    }

    public static List<String> createSplitNameValues(final String input) {
        return Arrays.stream(input.split(NAME_SPLITTER.getValue()))
                .map(String::trim)
                .distinct()
                .collect(Collectors.toList());
    }
}
