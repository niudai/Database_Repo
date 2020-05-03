package com.mycompany.myapp.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.mycompany.myapp.web.rest.TestUtil;

public class ExceptionTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Exception.class);
        Exception exception1 = new Exception();
        exception1.setId(1L);
        Exception exception2 = new Exception();
        exception2.setId(exception1.getId());
        assertThat(exception1).isEqualTo(exception2);
        exception2.setId(2L);
        assertThat(exception1).isNotEqualTo(exception2);
        exception1.setId(null);
        assertThat(exception1).isNotEqualTo(exception2);
    }
}
