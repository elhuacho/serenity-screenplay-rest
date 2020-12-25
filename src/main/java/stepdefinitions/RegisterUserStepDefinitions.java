package stepdefinitions;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.rest.abilities.CallAnApi;
import questions.ResponseCode;
import tasks.RegisterUser;

import static net.serenitybdd.screenplay.GivenWhenThen.seeThat;
import static org.hamcrest.CoreMatchers.equalTo;

public class RegisterUserStepDefinitions {

    private static final String restApiUrl = "http://localhost:5000/api";
    Actor walter;

    @Given("Julian es un cliente que quiere poder administrar sus productos bancarios")
    public void julianEsUnClienteQueQuierePoderAdministrarSusProductosBancarios() {
        walter = Actor.named("Walter").whoCan(CallAnApi.at(restApiUrl));
    }

    @When("el envia la informacion requerida para el registro")
    public void elEnviaLaInformacionRequeridaParaElRegistro() {
        String registerUserInfo = "{\"name\":\"Angel\",\"job\":\"leader\",\"email\":\"eve.holt@reqres.in\",\"password\":\"pistol\"}";

        walter.attemptsTo(
                RegisterUser.withInfo(registerUserInfo)
        );
    }

    @Then("el debe poder tener una cuenta virtual para poder ingresar cuando lo requiera")
    public void elDebePoderTenerUnaCuentaVirtualParaPoderIngresarCuandoLoRequiera() {
        walter.should(
                seeThat("el código de respuesta", ResponseCode.was(), equalTo(200))
        );
    }
}

