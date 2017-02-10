/**
 * Copyright (c) 2016. Arbuz All rights reserved.  http://www.arbuz.io.
 */

package com.arbuz.platform.demo.template.resource.validation;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.inject.Provider;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.ElementKind;
import javax.validation.Path;
import javax.validation.ValidationException;
import javax.ws.rs.core.Configuration;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Variant;
import javax.ws.rs.ext.ExceptionMapper;

import org.glassfish.jersey.server.validation.ValidationError;
import org.glassfish.jersey.server.validation.internal.LocalizationMessages;

@javax.ws.rs.ext.Provider
public class ValidationExceptionMapper implements ExceptionMapper<ValidationException>
{

    private static final Logger logger = Logger.getLogger(ValidationExceptionMapper.class.getName());

    @Context
    private Configuration config;

    @Context
    private Provider<Request> request;

    @Override
    public Response toResponse(final ValidationException exception)
    {
        if (exception instanceof ConstraintViolationException)
        {
            logger.log(Level.FINER, LocalizationMessages.CONSTRAINT_VIOLATIONS_ENCOUNTERED(), exception);

            final ConstraintViolationException cve = (ConstraintViolationException) exception;
            final Response.ResponseBuilder response = Response.status(getStatus(cve));

            final List<Variant> variants = Variant.mediaTypes(
                    MediaType.APPLICATION_XML_TYPE,
                    MediaType.APPLICATION_JSON_TYPE).build();
            final Variant variant = request.get().selectVariant(variants);
            if (variant != null)
            {
                response.type(variant.getMediaType());
            }
            else
            {
                response.type(MediaType.TEXT_PLAIN_TYPE);
            }
            response.entity(
                    new GenericEntity<>(getEntity(cve.getConstraintViolations()),
                                        new GenericType<List<ValidationError>>()
                                        { } .getType()
                    )
            );

            return response.build();
        }
        else
        {
            logger.log(Level.WARNING, LocalizationMessages.VALIDATION_EXCEPTION_RAISED(), exception);

            return Response.serverError().entity(exception.getMessage()).build();
        }
    }

    private List<ValidationError> getEntity(final Set<ConstraintViolation<?>> violations)
    {
        final List<ValidationError> errors = new ArrayList<>();

        for (final ConstraintViolation<?> violation : violations)
        {
            errors.add(new ValidationError(violation.getMessage(), violation.getMessageTemplate(), getPath(violation),
                    getInvalidValue(violation.getInvalidValue())));
        }

        return errors;
    }

    private String getInvalidValue(final Object invalidValue)
    {
        if (invalidValue == null)
        {
            return null;
        }

        if (invalidValue.getClass().isArray())
        {
            return Arrays.toString((Object[]) invalidValue);
        }

        return invalidValue.toString();
    }

    private Response.Status getStatus(final ConstraintViolationException exception)
    {
        return getResponseStatus(exception.getConstraintViolations());
    }

    private Response.Status getResponseStatus(final Set<ConstraintViolation<?>> constraintViolations)
    {
        final Iterator<ConstraintViolation<?>> iterator = constraintViolations.iterator();

        if (iterator.hasNext())
        {
            return getResponseStatus(iterator.next());
        }
        else
        {
            return Response.Status.BAD_REQUEST;
        }
    }

    private Response.Status getResponseStatus(final ConstraintViolation<?> constraintViolation)
    {
        for (final Path.Node node : constraintViolation.getPropertyPath())
        {
            final ElementKind kind = node.getKind();

            if (ElementKind.RETURN_VALUE.equals(kind))
            {
                return Response.Status.INTERNAL_SERVER_ERROR;
            }
        }

        return Response.Status.BAD_REQUEST;
    }

    private String getPath(final ConstraintViolation<?> violation)
    {
        Path.Node lastNode = null;

        for (Iterator<Path.Node> nodes = violation.getPropertyPath().iterator(); nodes.hasNext();)
        {
            Path.Node currentNode = nodes.next();

            if (currentNode != null && currentNode.toString() != null && !currentNode.toString().isEmpty())
            {
                lastNode = currentNode;
            }
        }

        return lastNode.toString();
    }
}
