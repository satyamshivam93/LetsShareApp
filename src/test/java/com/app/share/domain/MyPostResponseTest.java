package com.app.share.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.app.share.web.rest.TestUtil;

public class MyPostResponseTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(MyPostResponse.class);
        MyPostResponse myPostResponse1 = new MyPostResponse();
        myPostResponse1.setId(1L);
        MyPostResponse myPostResponse2 = new MyPostResponse();
        myPostResponse2.setId(myPostResponse1.getId());
        assertThat(myPostResponse1).isEqualTo(myPostResponse2);
        myPostResponse2.setId(2L);
        assertThat(myPostResponse1).isNotEqualTo(myPostResponse2);
        myPostResponse1.setId(null);
        assertThat(myPostResponse1).isNotEqualTo(myPostResponse2);
    }
}
