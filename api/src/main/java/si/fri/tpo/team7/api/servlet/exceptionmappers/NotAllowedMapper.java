package si.fri.tpo.team7.api.servlet.exceptionmappers;

import si.fri.tpo.team7.api.servlet.dtos.RestError;

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.NotAllowedException;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
@ApplicationScoped
public class NotAllowedMapper implements ExceptionMapper<NotAllowedException> {

    @Override
    public Response toResponse(NotAllowedException e) {

        RestError restError = new RestError();
        restError.setStatus(405);
        restError.setCode("not.allowed");
        restError.setMessage(e.getMessage());

        return Response
                .status(Response.Status.ACCEPTED)
                .entity(restError)
                .build();
    }
}
