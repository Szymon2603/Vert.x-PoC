package pl.beardeddev.service

import io.vertx.core.Vertx
import io.vertx.core.eventbus.DeliveryOptions
import io.vertx.core.json.JsonObject
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import pl.beardeddev.configuration.Consts
import pl.beardeddev.configuration.Methods
import spock.lang.Specification
import spock.lang.Stepwise
import spock.util.concurrent.AsyncConditions

@Stepwise
class JobOfferServiceVerticalSpec extends Specification {

    Vertx vertx;
    AsyncConditions conditions
    Logger log = LoggerFactory.getLogger(JobOfferServiceVerticalSpec.class)

    void setup() {
        vertx = Vertx.vertx()
        vertx.deployVerticle(new JobOfferServiceVertical(Consts.JOB_OFFER_SERVICE_QUEUE.name))
    }

    void cleanup() {
        vertx.close()
    }

    def "Retrieve one job offer"() {
        setup:
            conditions = new AsyncConditions(1)
            JsonObject request = new JsonObject()
            request.put(Consts.ID_PARAM.name, 1)
            DeliveryOptions options = new DeliveryOptions()
            options.addHeader(Consts.ACTION_HEADER.name, Methods.GET_BY_ID.name)

        expect:
            vertx.eventBus().send(Consts.JOB_OFFER_SERVICE_QUEUE.name, request, options, { handler ->
                conditions.evaluate {
                    log.info "Result ${handler.result().body()}"
                    assert handler.succeeded()
                    assert handler.result().body() != null
                }
            })
        conditions.await()
    }
}
