package si.fri.tpo.team7.api.servlet.exceptionmappers;

import si.fri.tpo.team7.api.servlet.dtos.RestError;

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
@ApplicationScoped
public class NotFoundMapper implements ExceptionMapper<NotFoundException> {

    @Override
    public Response toResponse(NotFoundException e) {

        RestError restError = new RestError();
        restError.setStatus(400);
        restError.setCode("bad.request");
        restError.setMessage(e.getMessage());

        return Response
                .status(Response.Status.BAD_REQUEST)
                .entity(restError)
                .build();
    }
}
