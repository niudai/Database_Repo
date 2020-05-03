package com.mycompany.myapp.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.mycompany.myapp.web.rest.TestUtil;

public class IntervalTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Interval.class);
        Interval interval1 = new Interval();
        interval1.setId(1L);
        Interval interval2 = new Interval();
        interval2.setId(interval1.getId());
        assertThat(interval1).isEqualTo(interval2);
        interval2.setId(2L);
        assertThat(interval1).isNotEqualTo(interval2);
        interval1.setId(null);
        assertThat(interval1).isNotEqualTo(interval2);
    }
}
