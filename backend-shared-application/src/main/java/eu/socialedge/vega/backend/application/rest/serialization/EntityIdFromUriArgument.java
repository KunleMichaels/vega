package eu.socialedge.vega.backend.application.rest.serialization;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.val;
import org.apache.catalina.connector.RequestFacade;
import org.springframework.core.MethodParameter;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import java.net.URI;
import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

class EntityIdFromUriArgument implements HandlerMethodArgumentResolver {

    private final AntPathMatcher pathMatcher = new AntPathMatcher();
    private final ObjectMapper mapper = new ObjectMapper();

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(EntityIdsFromUri.class);
    }

    @Override
    public Set<String> resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest,
                                       WebDataBinderFactory binderFactory) throws Exception {

        val requestFacade = (RequestFacade) webRequest.getNativeRequest();
        val requestString = requestFacade.getReader().lines().collect(Collectors.joining());
        val links = mapper.readValue(requestString, String[].class);

        //TODO extract it from parameter
        String pattern = "/**/tags/{id}/**";
        return Arrays.stream(links).map(link -> mapToId(link, pattern)).collect(Collectors.toSet());
    }

    private String mapToId(String link, String pattern) {
        val uri = URI.create(link).normalize();
        val variables = pathMatcher.extractUriTemplateVariables(pattern, uri.getPath());

        return variables.get("id");
    }
}
