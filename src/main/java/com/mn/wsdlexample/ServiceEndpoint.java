package com.mn.wsdlexample;

import com.mn.wsdlexample.exceptions.EntityNotFoundException;
import com.mn.wsdlexample.exceptions.RequestArgumentException;
import example.com.mn.wsdl.IINRequest;
import example.com.mn.wsdl.IINResponse;
import example.com.mn.wsdl.Person;
import example.com.mn.wsdl.ResponceStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import java.util.UUID;

@Endpoint
public class ServiceEndpoint {


    private PersonRepository personRepository;

    @Autowired
    public ServiceEndpoint(PersonRepository personRepository){
        this.personRepository = personRepository;
    }

    @PayloadRoot(localPart = "getByIIN")
    @ResponsePayload
    public IINResponse getByIin(@RequestPayload IINRequest request) {
        IINResponse response = new IINResponse();
        UUID uuid = UUID.randomUUID();
        Person person = new Person();
        ResponceStatus status = new ResponceStatus();
        try {
            person = personRepository.findPersonByIin(request.getIin());
            status.setCode(200);
            status.setDescription("OK");
        } catch (EntityNotFoundException e) {
            status.setCode(404);
            status.setDescription(e.getMessage());
        } catch (RequestArgumentException e) {
            status.setCode(400);
            status.setDescription(e.getMessage());
        }

        response.setId(uuid.toString());
        response.setResponceStatus(status);
        response.setIINRequest(request);
        response.setPerson(person);
        return response;
    }

}
