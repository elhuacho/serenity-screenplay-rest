import models.users.Datum;
import net.serenitybdd.junit.runners.SerenityRunner;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.rest.abilities.CallAnApi;
import org.junit.Test;
import org.junit.runner.RunWith;
import questions.GetUsersQuestion;
import questions.ResponseCode;
import tasks.GetUsers;

import static net.serenitybdd.screenplay.GivenWhenThen.seeThat;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;

@RunWith(SerenityRunner.class)
public class SerenityInitialTests {

    private static final String restApiUrl = "https://reqres.in/api";

    @Test
    public void initialTest() {
        Actor walter = Actor.named("Walter").whoCan(CallAnApi.at(restApiUrl));
        walter.attemptsTo(
                GetUsers.fromPage(1)
        );
        walter.should(
               seeThat("el codigo de respuesta", ResponseCode.was(), equalTo(200))
        );

        Datum user = new GetUsersQuestion().answeredBy(walter)
                .getData().stream().filter(x -> x.getId() == 1).findFirst().orElse(null);

        walter.should(
                seeThat("usuario no es nulo", act -> user, notNullValue())
        );
        walter.should(
                seeThat("el email del usuario", act -> user.getEmail(), equalTo("george.bluth@reqres.in")),
                seeThat("el primer nombre del usuario", act -> user.getFirstName(), equalTo("George")),
                seeThat("el apellido del usuario", act -> user.getLastName(), equalTo("Bluth")),
                seeThat("el avantar del usuario", act -> user.getAvatar(), equalTo("https://s3.amazonaws.com/uifaces/faces/twitter/calebogden/128.jpg"))
        );
    }
}
