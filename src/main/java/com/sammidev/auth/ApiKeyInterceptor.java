package com.sammidev.auth;

import com.sammidev.exception.UnauthorizedException;
import com.sammidev.repository.ApiKeyRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.ui.ModelMap;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.context.request.WebRequestInterceptor;

@Component
@AllArgsConstructor
public class ApiKeyInterceptor implements WebRequestInterceptor {

    ApiKeyRepository apiKeyRepository;

    @Override
    public void preHandle(WebRequest request) throws Exception {
        var apiKey = request.getHeader("X-Api-Key");
        if (apiKey == null) {
            throw new UnauthorizedException();
        }

        if (!apiKeyRepository.existsById(apiKey)) {
            throw new UnauthorizedException();
        }
    }

    @Override
    public void postHandle(WebRequest request, ModelMap modelMap) throws Exception {}

    @Override
    public void afterCompletion(WebRequest request, Exception e) throws Exception {}
}
