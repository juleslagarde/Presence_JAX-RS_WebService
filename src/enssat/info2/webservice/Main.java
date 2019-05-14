package enssat.info2.webservice;

import com.google.gson.Gson;
import enssat.info2.webservice.user.*;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/main")
public class Main {
    private Gson gson = new Gson();
    private UserService service = UserServiceImpl.getInstance();

    public Main(){
        System.out.println("const Main ========================================");
    }

	// This method is called if HTML is request
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public String getJson() {
		return gson.toJson(new MainReturn(service.getAllUsers()));
	}

    @POST
    @Path("/{login}")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public Response getUser(@PathParam("login") String login, MultivaluedMap<String, String> formParams) {
        System.out.println("formParams"+formParams.toString());
        service.updateUser(new User(login, formParams.getFirst("status")));
        return Response.ok().build();
    }


	private class MainReturn {
        List<User> user;

        MainReturn(List<User> user) {
            this.user = user;
        }
    }

}
