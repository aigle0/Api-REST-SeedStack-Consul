package com.ext.psa.interfaces.rest;


import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.seedstack.seed.rest.Rel;

import com.ecwid.consul.v1.ConsulClient;
import com.ecwid.consul.v1.kv.model.GetValue;

@Path("/machines")
@Produces({MediaType.APPLICATION_JSON, "application/hal+json"})
@Consumes({MediaType.APPLICATION_JSON, "application/hal+json"})
public class MachineResource {

	private static final String MACHINE_DOES_NOT_EXIST = "Machine %s doesn't exist";
    MachineService machineService = new MachineService();
    


    @GET
    @Produces({MediaType.APPLICATION_JSON, "application/hal+json"})
    @Consumes({MediaType.APPLICATION_JSON, "application/hal+json"})
    public List< Machine> getMachines() throws Exception {
        List<Machine> machines = new ArrayList<>();
        Machine m = new Machine();
        m.setNameMachine("name");
        m.setStatus("ok");
        machines.add(m);
        return machines;

    }

    @GET
    @Path("/check")
    @Produces({MediaType.APPLICATION_JSON, "application/hal+json"})
    @Consumes({MediaType.APPLICATION_JSON, "application/hal+json"})
    public Machine getMachine() throws Exception {

        ConsulClient client = new ConsulClient("localhost");
        JSONParser parser = new JSONParser();
        FileReader fileReader = new FileReader("C:\\Users\\PC\\Desktop\\file.json");
        JSONObject jsonObject = (JSONObject) parser.parse(fileReader);
        String namemachine = (String) jsonObject.get("nameMachine");
        System.out.println("nameMachine: " + namemachine);
        // get response consul

        com.ecwid.consul.v1.Response<GetValue> keyValueResponse = client.getKVValue(namemachine);
        System.out.println(keyValueResponse.getValue().getKey() + ": " + keyValueResponse.getValue().getDecodedValue());

        JSONArray jarray = new JSONArray();
        JSONObject json = new JSONObject();

        jarray.add(json.put("name", keyValueResponse.getValue().getKey()));
        jarray.add(json.put("maintenance", keyValueResponse.getValue().getDecodedValue()));

        FileWriter file = new FileWriter("C:/Users/PC/Desktop/file1.json");
        try {
            file.write(json.toJSONString());
            System.out.println("Successfully Copied JSON Object to File...");
            System.out.println("\nJSON Object: " + json);

            System.out.println("-----------------");

        } finally {
            file.flush();
            file.close();
        }
        Machine m = new Machine();
        m.setNameMachine(keyValueResponse.getValue().getKey());
        m.setStatus(keyValueResponse.getValue().getDecodedValue());
        return m;

    }
    
    @POST
	@Path("/checkstate")
    @Produces({MediaType.APPLICATION_JSON, "application/hal+json"})
    @Consumes({MediaType.APPLICATION_JSON, "application/hal+json"})
    @Rel(value = "checkstate", home = true)
	public Response getmachineState(String values) throws  ParseException {
    	
		JSONParser parser = new JSONParser();
		JSONObject jsonObject = (JSONObject) parser.parse(values);
		String namemachine = (String) jsonObject.get("node");
		//System.out.println("node: " + namemachine);

		JSONObject jsonResp = new JSONObject();
		JSONObject jsonResp2 = new JSONObject();
		String s2 = null;
				
		ConsulClient client = new ConsulClient("localhost");		
		com.ecwid.consul.v1.Response<List<String>> kvkeys = client.getKVKeysOnly("lagoon"); 
		
		List<String> listkvkeys = kvkeys.getValue();
		for (int i = 0; i < listkvkeys.size(); i++) {   	  
    	  if(listkvkeys.get(i).endsWith(namemachine))
    	  {    	  
        	  jsonResp.put("Machine's name", namemachine);
        	  com.ecwid.consul.v1.Response<GetValue> keyValueResponse2 = client.getKVValue(listkvkeys.get(i));
              jsonResp.put("Machine's status", keyValueResponse2.getValue().getDecodedValue());
              jsonResp2.put("node", jsonResp);
              s2 = jsonResp2.toJSONString();
              //System.out.println(s2);           
    	  }  
    	  /*else{
              throw new NotFoundException(String.format(MACHINE_DOES_NOT_EXIST, namemachine));
          }*/
      }
      return Response.ok(s2, MediaType.APPLICATION_JSON).status(Response.Status.OK).build();   
    } 
}

