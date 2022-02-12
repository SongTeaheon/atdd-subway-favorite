package nextstep.subway.unit;

import nextstep.auth.authentication.AuthenticationToken;
import nextstep.auth.authentication.session.SessionAuthenticationConverter;
import nextstep.subway.support.MockRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpServletRequest;

import static org.assertj.core.api.Assertions.assertThat;

public class SessionAuthenticationConverterTest {
    private static final String EMAIL = "email@email.com";
    private static final String PASSWORD = "password";

    @Test
    @DisplayName("HttpRequest로 AuthenticationToken 생성")
    void convert() {
        SessionAuthenticationConverter converter = new SessionAuthenticationConverter();
        MockHttpServletRequest mockHttpServletRequest = MockRequest.crateSessionRequest(EMAIL, PASSWORD);

        AuthenticationToken token = converter.convert(mockHttpServletRequest);

        AuthenticationToken_생성_검증(token);
    }

    private void AuthenticationToken_생성_검증(AuthenticationToken token) {
        assertThat(token.getPrincipal()).isEqualTo(EMAIL);
        assertThat(token.getCredentials()).isEqualTo(PASSWORD);
    }
}
