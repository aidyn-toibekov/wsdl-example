package com.mn.wsdlexample;

import com.mn.wsdlexample.exceptions.EntityNotFoundException;
import com.mn.wsdlexample.exceptions.RequestArgumentException;
import example.com.mn.wsdl.Person;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.HashMap;

@Component
public class PersonRepository {
    private HashMap<String, Person> personByIinMap;

    @PostConstruct
    public void initData() {
        Person mzs = new Person();
        mzs.setFirstName("Zhasulan");
        mzs.setLastName("Makhatov");
        mzs.setPatronymic("Samatovich");
        personByIinMap.put("011220550046", mzs);
    }

    public Person findPersonByIin(String iin) throws EntityNotFoundException, RequestArgumentException {
        if (iin.matches("\\d{12}")) {
            if (personByIinMap.containsKey(iin)) {
                return personByIinMap.get(iin);
            }
            throw new EntityNotFoundException("Person not found by IIN");
        }
        throw new RequestArgumentException("IIN is invalid");
    }
}
