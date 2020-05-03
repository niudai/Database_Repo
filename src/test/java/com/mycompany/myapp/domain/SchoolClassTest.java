package com.mycompany.myapp.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.mycompany.myapp.web.rest.TestUtil;

public class SchoolClassTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(SchoolClass.class);
        SchoolClass schoolClass1 = new SchoolClass();
        schoolClass1.setId(1L);
        SchoolClass schoolClass2 = new SchoolClass();
        schoolClass2.setId(schoolClass1.getId());
        assertThat(schoolClass1).isEqualTo(schoolClass2);
        schoolClass2.setId(2L);
        assertThat(schoolClass1).isNotEqualTo(schoolClass2);
        schoolClass1.setId(null);
        assertThat(schoolClass1).isNotEqualTo(schoolClass2);
    }
}
