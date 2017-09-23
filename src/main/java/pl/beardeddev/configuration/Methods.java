package pl.beardeddev.configuration;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;

import java.util.Arrays;
import java.util.Optional;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public enum Methods {
    GET_BY_ID("getById");

    private final String name;

    @SneakyThrows
    public static Optional<Methods> fromName(String name) {
        return Arrays.stream(values()).filter(v -> v.name.equals(name)).findAny();
    }
}
