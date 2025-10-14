import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;


public class TestesApiReqres {

    @BeforeAll
    public static void setup(){
        baseURI = "https://reqres.in";
        requestSpecification = given()
                .header("x-api-key", "reqres-free-v1");
    }

    @Test
    public void TesteListarTodosOsUsuarios(){

        given()
            .when()
                .get("/api/users?page=2")
            .then()
                .log().all()
                .statusCode(200)
                .body("page", is(2));

    }

    @Test
    public void TesteListarApenasUmUsuario(){

        Integer id = 11;

        given()
            .when()
                .get("/api/users/" + id)
            .then()
                .log().all()
                .statusCode(200)
                .body("data.id", is(id));

    }

    @Test
    public void TesteUsuarioInexistente(){

        given()
                .pathParams("id", 23)
        .when()
                .get("/api/users/{id}")
        .then()
                .log().all()
                .statusCode(404);

    }

    @Test
    public void TesteCriandoNovoUsuario(){

        given()
                .contentType("application/json")
                .body("{\"name\": \"Lipe\", \"job\": \"QA\"}")
            .when()
                .post("/api/users")
             .then()
                .log().all()
                .statusCode(201)
                .body("name", is("Lipe"))
                .body("job", is("QA"))
                .body("id", is(notNullValue()))
                .body("createdAt", is(notNullValue()));
    }

    @Test
    public void TesteAtualizarCadastroDoUsuario(){

        given()
                .contentType("application/json")
                .body("{\"name\": \"Lipe\", \"job\": \"Dev\"}")
            .when()
                .put("/api/users/2")
            .then()
                .log().all()
                .statusCode(200)
                .body("name", is("Lipe"))
                .body("job", is("Dev"))
                .body("updatedAt", is(notNullValue()));
    }

    @Test
    public void TesteDeletarUsuario(){

        given()
            .when()
                .delete("/api/users/2")
            .then()
                .log().all()
                .statusCode(204);
    }

    @Test
    public void TesteRegistrarUsuarioComSucesso(){

        given()
                .contentType("application/json")
                .body("{\"email\": \"rachel.howell@reqres.in\", \"password\": \"123qa\"}")
            .when()
                .post("/api/register")
            .then()
                .log().all()
                .statusCode(200)
                .body("id", is(notNullValue()))
                .body("token", is(notNullValue()));

    }

    @Test
    public void TesteRegistrarUsuarioSemSucesso(){

        given()
                .contentType("application/json")
                .body("{\"email\": \"rachel.howell@reqres.in\"}")
            .when()
                .post("/api/register")
            .then()
                .log().all()
                .statusCode(400)
                .body("error", is("Missing password"));

    }

    @Test
    public void TesteLoginComSucesso(){

        given()
                .contentType("application/json")
                .body("{\"email\": \"rachel.howell@reqres.in\", \"password\": \"123qa\"}")
            .when()
                .post("/api/login")
            .then()
                .log().all()
                .statusCode(200)
                .body("token", is(notNullValue()));
    }

    @Test
    public void TesteLoginSemSucesso(){

        given()
                .contentType("application/json")
                .body("{\"email\": \"rachel.howell@reqres.in\"}")
            .when()
                .post("/api/login")
            .then()
                .log().all()
                .statusCode(400)
                .body("error", is("Missing password"));
    }

}
