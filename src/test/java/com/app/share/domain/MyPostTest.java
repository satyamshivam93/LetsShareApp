package com.app.share.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.app.share.web.rest.TestUtil;

public class MyPostTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(MyPost.class);
        MyPost myPost1 = new MyPost();
        myPost1.setId(1L);
        MyPost myPost2 = new MyPost();
        myPost2.setId(myPost1.getId());
        assertThat(myPost1).isEqualTo(myPost2);
        myPost2.setId(2L);
        assertThat(myPost1).isNotEqualTo(myPost2);
        myPost1.setId(null);
        assertThat(myPost1).isNotEqualTo(myPost2);
    }
}
