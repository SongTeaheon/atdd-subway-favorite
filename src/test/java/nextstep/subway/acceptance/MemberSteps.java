package nextstep.subway.acceptance;

import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class MemberSteps {

    public static ExtractableResponse<Response> 회원_생성_요청(String email, String password, Integer age) {
        Map<String, String> params = new HashMap<>();
        params.put("email", email);
        params.put("password", password);
        params.put("age", age + "");

        return RestAssured
                .given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(params)
                .when().post("/members")
                .then().log().all()
                .statusCode(HttpStatus.CREATED.value())
                .extract();
    }

    public static void 회원_정보_일치함(String location, String email, int age) {
        var response = 회원_정보_조회_요청(location);

        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.jsonPath().getString("id")).isNotNull();
        assertThat(response.jsonPath().getString("email")).isEqualTo(email);
        assertThat(response.jsonPath().getInt("age")).isEqualTo(age);
    }

    public static ExtractableResponse<Response> 회원_정보_조회_요청(String location) {
        return RestAssured.given().log().all()
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .when().get(location)
                .then().log().all()
                .extract();
    }

    public static ExtractableResponse<Response> 회원_정보_수정_요청(String location, String email, String password, Integer age) {
        Map<String, String> params = new HashMap<>();
        params.put("email", email);
        params.put("password", password);
        params.put("age", age + "");

        return RestAssured
                .given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(params)
                .when().put(location)
                .then().log().all()
                .statusCode(HttpStatus.OK.value())
                .extract();
    }

    public static ExtractableResponse<Response> 회원_삭제_요청(String location) {
        return RestAssured
                .given().log().all()
                .when().delete(location)
                .then().log().all()
                .statusCode(HttpStatus.NO_CONTENT.value())
                .extract();
    }
}