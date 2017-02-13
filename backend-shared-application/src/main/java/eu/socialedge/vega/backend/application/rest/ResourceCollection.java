package eu.socialedge.vega.backend.application.rest;

import org.springframework.hateoas.ResourceSupport;

import java.util.List;

public class ResourceCollection<R extends ResourceSupport> extends ResourceSupport {
    private List<R> _embedded;

    public ResourceCollection(List<R> children) {
        this._embedded = children;
    }
}
