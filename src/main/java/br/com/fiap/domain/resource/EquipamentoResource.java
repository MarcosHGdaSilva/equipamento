package br.com.fiap.domain.resource;

import br.com.fiap.domain.entity.Equipamento;
import br.com.fiap.domain.repository.EquipamentoRepository;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.*;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Path("/equipamento")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)

public class EquipamentoResource implements Resource<Equipamento, Long>{
    @Context
    UriInfo uriInfo;
    private EquipamentoRepository repo = EquipamentoRepository.build();


    @GET
    public Response findAll() {
        List<Equipamento> equipamentos = repo.findAll();
        return Response.status(Response.Status.OK).entity(equipamentos).build();
    }
    @GET
    @Path("/{id}")
    @Override
    public Response findById(@PathParam("id") Long id) {
        Equipamento equipamento = repo.findById(id);
        if (Objects.isNull(equipamento)) return Response.status(404).build();
        return Response.status(Response.Status.OK).entity(equipamento).build();
    }

    @PUT
    @Path("/{id}")
    @Override
    public Response update(@PathParam("id")Long id, Equipamento equipamento) {
        equipamento.setId(id);
        Equipamento e = repo.update(equipamento);
        if (Objects.isNull(e)) return Response.status(404).build();
        return Response.ok().entity(e).build();
    }

    @DELETE
    @Path("/{id}")
    @Override
    public Response delete(@PathParam("id")Long id) {
        boolean deleted = repo.delete(id);
        if (deleted) return Response.ok().build();
        return Response.status(404).build();
    }

    @POST
    @Override
    public Response persist(Equipamento e) {
        Equipamento equipamento = repo.persist(e);
        UriBuilder ub = uriInfo.getAbsolutePathBuilder();
        URI uri = ub.path(String.valueOf(equipamento.getId())).build();
        return Response.created(uri).entity(equipamento).build();
    }

}
