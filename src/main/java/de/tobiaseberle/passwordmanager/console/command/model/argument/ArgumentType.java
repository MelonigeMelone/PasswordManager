package de.tobiaseberle.passwordmanager.console.command.model.argument;

import java.util.List;
import java.util.stream.Stream;

public enum ArgumentType {
    STRING(".*") {
        @Override
        public StringArgument parse(String value) {
            return new StringArgument(value);
        }
    },
    INTEGER("\\d+") {
        @Override
        public IntegerArgument parse(String value) {
            return new IntegerArgument(value);
        }
    },
    BOOLEAN("(?i:true|false|1|0|yes|no)") {
        @Override
        public BooleanArgument parse(String value) {
            return new BooleanArgument(value);
        }
    };

   private final String pattern;

    private ArgumentType(String pattern) {
         this.pattern = pattern;
    }

    public abstract Argument<?> parse(String value);

    public String getPattern() {
        return pattern;
    }

    public static ArgumentType fromString(String value) {

        List<ArgumentType> nonDefaultArgumentTypes = Stream.of(ArgumentType.values())
                .filter(argumentType -> argumentType != ArgumentType.STRING).toList();

        for (ArgumentType argumentType : nonDefaultArgumentTypes) {
            if (argumentType.getPattern().matches(value)) {
                return argumentType;
            }
        }

        return ArgumentType.STRING;
    }

}
