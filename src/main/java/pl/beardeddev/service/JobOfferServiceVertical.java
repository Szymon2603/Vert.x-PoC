package pl.beardeddev.service;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.core.eventbus.Message;
import io.vertx.core.json.JsonObject;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import pl.beardeddev.configuration.Consts;
import pl.beardeddev.configuration.Methods;
import pl.beardeddev.domain.JobOffer;

import java.net.URL;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@RequiredArgsConstructor
public class JobOfferServiceVertical extends AbstractVerticle {

    @NonNull
    private String jobOfferServiceQueue;
    private Set<JobOffer> jobOffers = new HashSet<>();

    @Override
    public void start(Future<Void> startFuture) throws Exception {
        jobOffers.add(JobOffer.builder()
                .id(1L)
                .title("Test Stub")
                .description("Test desc")
                .urlAddress(new URL("http://fakeaddress.pl"))
                .build());

        vertx.eventBus().consumer(jobOfferServiceQueue, this::onMessage);
    }

    private void onMessage(Message<JsonObject> request) {
        String methodName = request.headers().get(Consts.ACTION_HEADER.getName());
        Optional<Methods> method = Methods.fromName(methodName);
        method.ifPresent(v -> handleMethod(request, v));

    }

    private void handleMethod(Message<JsonObject> request, Methods method) {
        switch(method) {
            case GET_BY_ID:
                JobOffer jobOffer = jobOffers
                        .stream()
                        .filter(v -> v.getId().equals(request.body().getLong(Consts.ID_PARAM.getName())))
                        .findAny()
                        .get();
                request.reply(JsonObject.mapFrom(jobOffer));
        }
    }
}
