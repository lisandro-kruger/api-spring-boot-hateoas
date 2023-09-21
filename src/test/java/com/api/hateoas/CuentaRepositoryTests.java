package com.api.hateoas;

import com.api.hateoas.model.Cuenta;
import com.api.hateoas.repository.CuentaRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.jdbc.DataJdbcTest;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.test.annotation.Rollback;

@DataJdbcTest
@Rollback(value = true)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class CuentaRepositoryTests {

    @Autowired
    private CuentaRepository cuentaRepository;
    /*
    @Test
    void testAgregarCuenta(){
        Cuenta cuenta = new Cuenta(1223344, "2445544");
        Cuenta cuentaGuardada = cuentaRepository.save(cuenta);

        Assertions.assertThat(cuentaGuardada).isNotNull();
        Assertions.assertThat(cuentaGuardada.getId()).isGreaterThan(0);
    }
     */
}
