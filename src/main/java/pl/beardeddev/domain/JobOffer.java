package pl.beardeddev.domain;

import lombok.*;

import java.net.URL;

@Getter
@Setter
@ToString
@EqualsAndHashCode(of = {"id"})
@Builder
public class JobOffer {

    private Long id;
    private String title;
    private URL urlAddress;
    private String description;

}
