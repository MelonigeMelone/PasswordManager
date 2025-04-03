package de.tobiaseberle.passwordmanager.console.command.model.argument;

import java.util.List;
import java.util.stream.Stream;

public enum ArgumentType {
    STRING(".*") {
        @Override
        public StringArgumentValue parse(String value) {
            return new StringArgumentValue(value);
        }
    },
    INTEGER("\\d+") {
        @Override
        public IntegerArgumentValue parse(String value) {
            return new IntegerArgumentValue(value);
        }
    },
    BOOLEAN("(?i:true|false|yes|no)") {
        @Override
        public BooleanArgumentValue parse(String value) {
            return new BooleanArgumentValue(value);
        }
    },
    OPTION(".*") {
        @Override
        public OptionArgumentValue parse(String value) {
            return new OptionArgumentValue(value);
        }
    };

    private final String pattern;

    ArgumentType(String pattern) {
        this.pattern = pattern;
    }

    public abstract AbstractArgumentValue<?> parse(String value);

    public String getPattern() {
        return pattern;
    }

    public static ArgumentType fromString(String value) {

        List<ArgumentType> nonDefaultArgumentTypes = Stream.of(ArgumentType.values())
                .filter(argumentType -> argumentType != ArgumentType.STRING).toList();

        for (ArgumentType argumentType : nonDefaultArgumentTypes) {
            if (value.matches(argumentType.getPattern())) {
                return argumentType;
            }
        }

        return ArgumentType.STRING;
    }

}
