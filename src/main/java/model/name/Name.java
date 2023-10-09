package model.name;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static util.Keyword.ACE;
import static util.Keyword.DEALER;

public class Name {

    private final String name;

    private Name(final String name) {
        this.name = name;
    }

    public static Name from(final String name) {
        return new Name(name);
    }

    public static Name withScore(final int score, final String name) {
        return new Name(score + name);
    }

    public static String chainingNames(final List<Name> names) {
        List<String> nameValues = names.stream()
                .map(Name::getName)
                .collect(Collectors.toList());

        return String.join(", ", nameValues);
    }

    public static boolean isAce(final Name value) {
        String valueName = value.getName();
        return valueName.startsWith(ACE.getValue());
    }

    public boolean isNotDealer() {
        return !name.equals(DEALER.getValue());
    }

    public boolean isSameAs(String name) {
        return this.name.equals(name);
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Name other = (Name) o;
        return Objects.equals(name, other.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    public String getName() {
        return name;
    }
}
