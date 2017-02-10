/**
 * Copyright (c) 2016. Arbuz All rights reserved.  http://www.arbuz.io.
 */

package com.arbuz.platform.demo.template.resource;

import com.arbuz.platform.demo.template.dto.AuthorizationRequest;
import com.arbuz.platform.demo.template.dto.AuthorizationResponse;
import com.arbuz.platform.demo.template.dto.ErrorResponse;
import com.arbuz.platform.demo.template.dto.RetrieveTransactionsResponse;
import com.arbuz.platform.demo.template.dto.UpdateTransactionRequest;
import com.arbuz.platform.demo.template.model.Transaction;
import com.arbuz.platform.demo.template.service.PaymentService;
import com.arbuz.platform.demo.template.service.exception.PaymentException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.stereotype.Service;

import javax.annotation.security.RolesAllowed;
import javax.validation.Valid;
import javax.validation.executable.ValidateOnExecution;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.core.Variant;
import java.util.List;
import java.util.Locale;

@Path("/payment")
@Api(value = "/payment", description = "Processes payments")
@Produces(MediaType.APPLICATION_JSON)
@Service("paymentResource")
public class PaymentResource
{
    private final Logger logger = LoggerFactory.getLogger(PaymentResource.class);

    @Autowired
    private PaymentService paymentService;

    @Context
    private Request request;

    @Context
    private UriInfo uriInfo;

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "Authorize", notes = "Authorizes payment transaction.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "SUCCESS", response = AuthorizationResponse.class),
            @ApiResponse(code = 402, message = "FAILURE", response = ErrorResponse.class),
            @ApiResponse(code = 406, message = "UNSUPPORTED LOCALE"),
            @ApiResponse(code = 500, message = "RUNTIME ERROR", response = ErrorResponse.class)
    })
    @ValidateOnExecution
    public Response authorize(@ApiParam(value = "Accept Language", allowableValues = "en, fr")
                              @HeaderParam("Accept-Language") Locale locale,
                              @ApiParam(value = "Authorization request object", required = true)
                              @Valid AuthorizationRequest authorizationRequest) throws PaymentException
    {
        Variant variant = request.selectVariant(ApiConstants.LANGUAGE_VARIANTS);

        logger.debug("Selected language: " + variant.getLanguageString());

        if (variant == null)
        {
            return Response.notAcceptable(ApiConstants.LANGUAGE_VARIANTS).build();
        }
        else
        {
            logger.debug("Authorization request: " + authorizationRequest);

            String transactionId = paymentService.authorize(authorizationRequest, locale);

            AuthorizationResponse response = new AuthorizationResponse();
            response.setTransactionId(transactionId);

            response.add(new Link(uriInfo.getAbsolutePathBuilder().path(PaymentResource.class, "retrieveTransactions")
                    .resolveTemplate("transactionId", response.getTransactionId())
                    .build().toString()).withRel(Link.REL_NEXT));

            logger.debug("Authorization response: " + authorizationRequest);

            return Response.ok().entity(response).build();
        }
    }

    @GET
    @Path("transactions")
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "Retrieve Transactions by status", notes = "Retrieves posts by status")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "SUCCESS"),
            @ApiResponse(code = 402, message = "FAILURE", response = ErrorResponse.class),
            @ApiResponse(code = 406, message = "UNSUPPORTED LOCALE"),
            @ApiResponse(code = 500, message = "RUNTIME ERROR", response = ErrorResponse.class)
    })
    @RolesAllowed({"ROLE_USER"})
    public Response retrieveTransactions(@ApiParam(value = "Accept Language", allowableValues = "en, fr")
                                         @HeaderParam("Accept-Language") Locale locale,
                                         @ApiParam(value = "Status", allowableValues = "PENDING, AUTHORIZED, FAILED")
                                         @QueryParam("status") String status,
                                         @Context Request request,
                                         @Context UriInfo uriInfo) throws PaymentException
    {
        Variant variant = request.selectVariant(ApiConstants.LANGUAGE_VARIANTS);

        logger.debug("Selected language: " + variant.getLanguageString());

        if (variant == null)
        {
            return Response.notAcceptable(ApiConstants.LANGUAGE_VARIANTS).build();
        }
        else
        {
            List<Transaction> transactions = paymentService.retrieveTransactions(status, locale);

            RetrieveTransactionsResponse response = new RetrieveTransactionsResponse();
            response.setTransactions(transactions);

            response.add(new Link(uriInfo.getBaseUriBuilder().path(PaymentResource.class)
                    .build().toString()).withRel(Link.REL_SELF));

            logger.debug("RetrieveTransactionsResponse response: " + response);

            return Response.ok().entity(response).build();
        }
    }

    @DELETE
    @Path("/{transactionId}")
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "Delete Transaction", notes = "Delete transaction by transactionId")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "SUCCESS"),
            @ApiResponse(code = 402, message = "FAILURE", response = ErrorResponse.class),
            @ApiResponse(code = 404, message = "TRANSACTION NOT FOUND"),
            @ApiResponse(code = 406, message = "UNSUPPORTED LOCALE"),
            @ApiResponse(code = 500, message = "RUNTIME ERROR", response = ErrorResponse.class)
    })
    public Response removeTransaction(@ApiParam(value = "Accept Language", allowableValues = "en, fr")
                               @HeaderParam("Accept-Language") Locale locale,
                               @ApiParam(value = "transactionId to remove") @PathParam("transactionId") String transactionId) throws PaymentException
    {
        Variant variant = request.selectVariant(ApiConstants.LANGUAGE_VARIANTS);

        logger.debug("Selected language: " + variant.getLanguageString());

        if (variant == null)
        {
            return Response.notAcceptable(ApiConstants.LANGUAGE_VARIANTS).build();
        }
        else
        {
            return paymentService.removeTransaction(transactionId, locale) ? Response.ok().build() : Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    @PUT
    @Path("/{transactionId}")
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "Update Transaction", notes = "Update details of transaction")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "SUCCESS"),
            @ApiResponse(code = 402, message = "FAILURE", response = ErrorResponse.class),
            @ApiResponse(code = 404, message = "TRANSACTION NOT FOUND"),
            @ApiResponse(code = 406, message = "UNSUPPORTED LOCALE"),
            @ApiResponse(code = 500, message = "RUNTIME ERROR", response = ErrorResponse.class)
    })
    @ValidateOnExecution
    public Response updateTransaction(@ApiParam(value = "Accept Language", allowableValues = "en, fr")
                                      @HeaderParam("Accept-Language") Locale locale,
                                      @ApiParam(value = "transactionId to update") @PathParam("transactionId") String transactionId,
                                      @ApiParam(value = "Update transaction request", required = true)
                                      @Valid UpdateTransactionRequest updateTransactionRequest) throws PaymentException
    {
        Variant variant = request.selectVariant(ApiConstants.LANGUAGE_VARIANTS);

        logger.info("Selected language: " + variant.getLanguageString());

        if (variant == null)
        {
            return Response.notAcceptable(ApiConstants.LANGUAGE_VARIANTS).build();
        }
        else
        {
            return paymentService.updateTransaction(transactionId, updateTransactionRequest, locale) ? Response.ok().build() : Response.status(Response.Status.NOT_FOUND).build();
        }
    }
}