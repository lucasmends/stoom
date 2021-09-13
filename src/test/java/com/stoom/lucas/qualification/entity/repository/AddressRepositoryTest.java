package com.stoom.lucas.qualification.entity.repository;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.math.BigDecimal;

import com.stoom.lucas.qualification.entity.model.Address;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@DataJpaTest
public class AddressRepositoryTest {

    @Autowired
    private AddressRepository repository;

	@Autowired
	private TestEntityManager em;

    @BeforeEach
    public void setUp(){
        Address address = new Address();

        address.setId(Long.valueOf(1));
        address.setStreetName("rua");
        address.setNumber(1);
        address.setComplement("complemento");
        address.setCity("cidade");
        address.setState("ST");
        address.setZipcode("11100000");
        address.setCountry("Brasil");
        address.setLatitude(BigDecimal.valueOf(10.100));
        address.setLongitude(BigDecimal.valueOf(10.100)); 

        em.persist(address);

        address.setId(Long.valueOf(2));

        em.persist(address);
    }

    @Test
    public void it_should_save_address() {
        Address address = new Address();

        address.setId(Long.valueOf(10));
        address.setStreetName("rua");
        address.setNumber(1);
        address.setComplement("complemento");
        address.setCity("cidade");
        address.setState("ST");
        address.setZipcode("11100000");
        address.setCountry("Brasil");
        address.setLatitude(BigDecimal.valueOf(10.100));
        address.setLongitude(BigDecimal.valueOf(10.100));

        repository.save(address);
        
        assertEquals(repository.findById(address.getId()).get(), address);
    }

    @Test
    public void it_should_find_by_id() {

        Address ad = repository.findById(Long.valueOf(1)).get();
        
        assertNotNull(ad);
    }
}
