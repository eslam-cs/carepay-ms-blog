package com.carepay.blog.exception.mapper;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.carepay.blog.server.dto.ErrorResponseApiDto;
import com.carepay.blog.server.dto.GenericResponseStatusApiDto;



@Provider
@Component
public class FallbackExceptionMapper implements ExceptionMapper<Exception> {

    private static final Logger logger = LoggerFactory.getLogger(FallbackExceptionMapper.class);

    @Override
    public Response toResponse(Exception exception) {
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(prepareErrorMessage(exception))
                .type(MediaType.APPLICATION_JSON).build();
    }

    private ErrorResponseApiDto prepareErrorMessage(Exception exception) {
        return new ErrorResponseApiDto()
                .status(new GenericResponseStatusApiDto()
                        .statusCode(String.valueOf(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode()))
                        .statusMessage("Error while proccessing the request").additionalInfo(exception.getMessage()));
    }

}