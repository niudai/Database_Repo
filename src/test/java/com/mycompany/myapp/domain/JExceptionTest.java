package com.mycompany.myapp.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.mycompany.myapp.web.rest.TestUtil;

public class JExceptionTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(JException.class);
        JException jException1 = new JException();
        jException1.setId(1L);
        JException jException2 = new JException();
        jException2.setId(jException1.getId());
        assertThat(jException1).isEqualTo(jException2);
        jException2.setId(2L);
        assertThat(jException1).isNotEqualTo(jException2);
        jException1.setId(null);
        assertThat(jException1).isNotEqualTo(jException2);
    }
}
