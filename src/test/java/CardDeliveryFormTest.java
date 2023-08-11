import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

class CardDeliveryFormTest {

    private String generateDate(int addDays, String pattern) {
        return LocalDate.now().plusDays(addDays).format(DateTimeFormatter.ofPattern(pattern));
    }

    @BeforeEach
    void setup() {
        open("http://localhost:9999");
    }

    @Test
    void shouldTest() {
        $("[data-test-id='city'] input").setValue("Ижевск");
        String currentDate = generateDate(4, "dd.MM.yyyy");
        $("[data-test-id='date'] input").sendKeys(Keys.chord(Keys.LEFT_SHIFT, Keys.HOME), Keys.DELETE);
        $("[data-test-id='date'] input").setValue(currentDate);
        $("[data-test-id='name'] input").setValue("Корецкий Владимир");
        $("[data-test-id='phone'] input").setValue("+79333333333");
        $("[data-test-id='agreement']").click();
        $("button.button").click();
        $(".notification__content")
                .shouldBe(visible, Duration.ofSeconds(15))
                .shouldHave(exactText("Встреча успешно забронирована на " + currentDate));
    }
}
