package com.mycompany.myapp.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.mycompany.myapp.web.rest.TestUtil;

public class SemasterTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Semaster.class);
        Semaster semaster1 = new Semaster();
        semaster1.setId(1L);
        Semaster semaster2 = new Semaster();
        semaster2.setId(semaster1.getId());
        assertThat(semaster1).isEqualTo(semaster2);
        semaster2.setId(2L);
        assertThat(semaster1).isNotEqualTo(semaster2);
        semaster1.setId(null);
        assertThat(semaster1).isNotEqualTo(semaster2);
    }
}
