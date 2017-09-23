package pl.beardeddev.configuration;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public enum Consts {
    JOB_OFFER_SERVICE_QUEUE("jobOfferServiceQueue"),
    ACTION_HEADER("action"),
    ID_PARAM("id");

    private final String name;

}
