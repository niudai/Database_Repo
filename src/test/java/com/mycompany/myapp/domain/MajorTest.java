package com.mycompany.myapp.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.mycompany.myapp.web.rest.TestUtil;

public class MajorTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Major.class);
        Major major1 = new Major();
        major1.setId(1L);
        Major major2 = new Major();
        major2.setId(major1.getId());
        assertThat(major1).isEqualTo(major2);
        major2.setId(2L);
        assertThat(major1).isNotEqualTo(major2);
        major1.setId(null);
        assertThat(major1).isNotEqualTo(major2);
    }
}
