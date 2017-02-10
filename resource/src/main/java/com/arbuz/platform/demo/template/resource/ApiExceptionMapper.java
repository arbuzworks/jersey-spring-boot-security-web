/**
 * Copyright (c) 2016. Arbuz All rights reserved.  http://www.arbuz.io.
 */

package com.arbuz.platform.demo.template.resource;

import com.arbuz.platform.demo.template.dto.ErrorResponse;
import com.arbuz.platform.demo.template.service.exception.PaymentException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.hateoas.Link;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Component;

import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
import java.net.URI;
import java.net.URISyntaxException;

@Component
@Provider
public class ApiExceptionMapper implements ExceptionMapper<Exception>
{
    private final Logger logger = LoggerFactory.getLogger(ApiExceptionMapper.class);

    @Autowired
    private MessageSource messageSource;

    @Context
    private Request request;

    @Context
    private UriInfo uriInfo;

    @Value("${documenation.url}")
    private String documenationURL;

    @Override
    public Response toResponse(Exception e)
    {
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setType(e.getClass().getName());

        try
        {
            Link link = new Link(uriInfo.resolve(new URI(documenationURL)).toString()).withRel(Link.REL_NEXT);

            errorResponse.add(link);

            logger.debug("Added link: " + link);
        }
        catch (URISyntaxException urise)
        {
            logger.error(urise.getMessage());

            errorResponse.setType(urise.getClass().getName());
            errorResponse.setDeveloperMessage(urise.getMessage());
            Link link = new Link(uriInfo.getBaseUriBuilder().path(PaymentResource.class).build().toString()).withRel(Link.REL_SELF);
            errorResponse.add();

            logger.debug("Added link: " + link);
        }

        if (e instanceof AccessDeniedException)
        {
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }

        if (e instanceof PaymentException)
        {
            errorResponse.setCode(((PaymentException) e).getCode());
            errorResponse.setMessage(e.getMessage());

            logger.debug("Error response: " + errorResponse);

            return Response.status(
                    Response.Status.PAYMENT_REQUIRED).entity(errorResponse)
                    .type(MediaType.APPLICATION_JSON_TYPE)
                    .build();
        }

        String code = Integer.toString(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode());
        errorResponse.setCode(code);
        errorResponse.setMessage(messageSource.getMessage(code, null, request.selectVariant(ApiConstants.LANGUAGE_VARIANTS).getLanguage()));
        errorResponse.setDeveloperMessage(e.getCause().toString());

        logger.debug("Error response: " + errorResponse);

        return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(
                errorResponse).type(MediaType.APPLICATION_JSON_TYPE)
                .build();
    }
}
