package com.mycompany.myapp.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.mycompany.myapp.web.rest.TestUtil;

public class CampusTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Campus.class);
        Campus campus1 = new Campus();
        campus1.setId(1L);
        Campus campus2 = new Campus();
        campus2.setId(campus1.getId());
        assertThat(campus1).isEqualTo(campus2);
        campus2.setId(2L);
        assertThat(campus1).isNotEqualTo(campus2);
        campus1.setId(null);
        assertThat(campus1).isNotEqualTo(campus2);
    }
}
