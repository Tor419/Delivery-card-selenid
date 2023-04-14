package ru.netology;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.*;
import org.openqa.selenium.Keys;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Condition.appear;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.*;

public class RegistrationTest {

    @BeforeEach
    void setUp() {
        Configuration.headless = true;
        Configuration.holdBrowserOpen = true;
        Configuration.browserSize = "1800x1100";
        Configuration.timeout = 15000;
        open("http://localhost:9999/");
    }

    public String generateDate(long addDays, String pattern) {
        return LocalDate.now().plusDays(addDays).format(DateTimeFormatter.ofPattern(pattern));
    }

    @Test
    void shouldRegisterByAccountNumberDOMModificationTest() {
        $("[placeholder='Город']").setValue("Йошкар-Ола");
        $("[placeholder='Дата встречи']").doubleClick();
        $("[placeholder='Дата встречи']").sendKeys(Keys.BACK_SPACE);
        String planningDate = generateDate(3, "dd.MM.yyyy");
        $("[placeholder='Дата встречи']").setValue(planningDate);
        $("[name='name']").setValue("Иванов-Борисов Павел");
        $("[name='phone']").setValue("+71234567890");
        $("[data-test-id='agreement']").click();
        $x("//div/button").click();
        $x("//div[contains(text(), 'Встреча успешно забронирована')]").shouldBe(visible);
    }

    @Test
    void shouldRegisterErrorEmptyFieldCityTest() {
        $("[placeholder='Город']").setValue("");
        $("[placeholder='Дата встречи']").doubleClick();
        $("[placeholder='Дата встречи']").sendKeys(Keys.BACK_SPACE);
        String planningDate = generateDate(3, "dd.MM.yyyy");
        $("[placeholder='Дата встречи']").setValue(planningDate);
        $("[name='name']").setValue("Иванов-Борисов Павел");
        $("[name='phone']").setValue("+71234567890");
        $("[data-test-id='agreement']").click();
        $x("//div/button").click();
        $x("//div//span[contains(text(), 'Поле обязательно для заполнения')]").shouldBe(visible);
    }

    @Test
    void shouldRegisterErrorNotFindCityTest() {
        $("[placeholder='Город']").setValue("Енгельс");
        $("[placeholder='Дата встречи']").doubleClick();
        $("[placeholder='Дата встречи']").sendKeys(Keys.BACK_SPACE);
        String planningDate = generateDate(3, "dd.MM.yyyy");
        $("[placeholder='Дата встречи']").setValue(planningDate);
        $("[name='name']").setValue("Иванов-Борисов Павел");
        $("[name='phone']").setValue("+71234567890");
        $("[data-test-id='agreement']").click();
        $x("//div/button").click();
        $x("//div//span[contains(text(), 'Доставка в выбранный город недоступна')]").shouldBe(visible);
    }

    @Test
    void shouldRegisterErrorEmptyFieldDataTest() {
        $("[placeholder='Город']").setValue("Новосибирск");
        $("[placeholder='Дата встречи']").doubleClick();
        $("[placeholder='Дата встречи']").sendKeys(Keys.BACK_SPACE);
        String planningDate = generateDate(3, "dd.MM.yyyy");
        //$("[placeholder='Дата встречи']").setValue(planningDate);
        $("[name='name']").setValue("Иванов-Борисов Павел");
        $("[name='phone']").setValue("+71234567890");
        $("[data-test-id='agreement']").click();
        $x("//div/button").click();
        $x("//div//span[contains(text(), 'Неверно введена дата')]").shouldBe(visible);
    }

    @Test
    void shouldRegisterErrorFieldDataTest() {
        $("[placeholder='Город']").setValue("Новосибирск");
        $("[placeholder='Дата встречи']").doubleClick();
        $("[placeholder='Дата встречи']").sendKeys(Keys.BACK_SPACE);
        String planningDate = generateDate(3, "dd.MM.yyyy");
        $("[placeholder='Дата встречи']").setValue("15.04.2023");
        $("[name='name']").setValue("Иванов-Борисов Павел");
        $("[name='phone']").setValue("+71234567890");
        $("[data-test-id='agreement']").click();
        $x("//div/button").click();
        $x("//div//span[contains(text(), 'Заказ на выбранную дату невозможен')]").shouldBe(visible);
    }

    @Test
    void shouldRegisterErrorEmptyFieldNameTest() {
        $("[placeholder='Город']").setValue("Новосибирск");
        $("[placeholder='Дата встречи']").doubleClick();
        $("[placeholder='Дата встречи']").sendKeys(Keys.BACK_SPACE);
        String planningDate = generateDate(3, "dd.MM.yyyy");
        $("[placeholder='Дата встречи']").setValue(planningDate);
        $("[name='name']").setValue("");
        $("[name='phone']").setValue("+71234567890");
        $("[data-test-id='agreement']").click();
        $x("//div/button").click();
        $x("//div//span[contains(text(), 'Поле обязательно для заполнения')]").shouldBe(visible);
    }

    @Test
    void shouldRegisterErrorFieldNameShouldContainRUSCharacterTest() {
        $("[placeholder='Город']").setValue("Новосибирск");
        $("[placeholder='Дата встречи']").doubleClick();
        $("[placeholder='Дата встречи']").sendKeys(Keys.BACK_SPACE);
        String planningDate = generateDate(3, "dd.MM.yyyy");
        $("[placeholder='Дата встречи']").setValue(planningDate);
        $("[name='name']").setValue("Jack London");
        $("[name='phone']").setValue("+71234567890");
        $("[data-test-id='agreement']").click();
        $x("//div/button").click();
        $x("//div//span[contains(text(), 'Имя и Фамилия указаные неверно.')]").shouldBe(visible);
    }

    @Test
    void shouldRegisterErrorEmptyFieldPhoneTest() {
        $("[placeholder='Город']").setValue("Новосибирск");
        $("[placeholder='Дата встречи']").doubleClick();
        $("[placeholder='Дата встречи']").sendKeys(Keys.BACK_SPACE);
        String planningDate = generateDate(3, "dd.MM.yyyy");
        $("[placeholder='Дата встречи']").setValue(planningDate);
        $("[name='name']").setValue("Петров Николай");
        $("[name='phone']").setValue("");
        $("[data-test-id='agreement']").click();
        $x("//div/button").click();
        $x("//div//span[contains(text(), 'Поле обязательно для заполнения')]").shouldBe(visible);
    }

    @Test
    void shouldRegisterErrorFieldPhoneNoOneCharacterTest() {
        $("[placeholder='Город']").setValue("Новосибирск");
        $("[placeholder='Дата встречи']").doubleClick();
        $("[placeholder='Дата встречи']").sendKeys(Keys.BACK_SPACE);
        String planningDate = generateDate(3, "dd.MM.yyyy");
        $("[placeholder='Дата встречи']").setValue(planningDate);
        $("[name='name']").setValue("Петров Николай");
        $("[name='phone']").setValue("5");
        $("[data-test-id='agreement']").click();
        $x("//div/button").click();
        $x("//div//span[contains(text(), 'Телефон указан неверно.')]").shouldBe(visible);
    }

    @Test
    void shouldRegisterErrorFieldPhoneNoMoreThirteenCharacterTest() {
        $("[placeholder='Город']").setValue("Новосибирск");
        $("[placeholder='Дата встречи']").doubleClick();
        $("[placeholder='Дата встречи']").sendKeys(Keys.BACK_SPACE);
        String planningDate = generateDate(3, "dd.MM.yyyy");
        $("[placeholder='Дата встречи']").setValue(planningDate);
        $("[name='name']").setValue("Петров Николай");
        $("[name='phone']").setValue("+793456789012");
        $("[data-test-id='agreement']").click();
        $x("//div/button").click();
        $x("//div//span[contains(text(), 'Телефон указан неверно.')]").shouldBe(visible);
    }

    @Test
    void shouldRegisterErrorFieldPhoneNoPlusSignTest() {
        $("[placeholder='Город']").setValue("Новосибирск");
        $("[placeholder='Дата встречи']").doubleClick();
        $("[placeholder='Дата встречи']").sendKeys(Keys.BACK_SPACE);
        String planningDate = generateDate(3, "dd.MM.yyyy");
        $("[placeholder='Дата встречи']").setValue(planningDate);
        $("[name='name']").setValue("Петров Николай");
        $("[name='phone']").setValue("79345678901");
        $("[data-test-id='agreement']").click();
        $x("//div/button").click();
        $x("//div//span[contains(text(), 'Телефон указан неверно.')]").shouldBe(visible);
    }

    @Test
    void shouldRegisterErrorCheckboxIsNotSetTest() {
        $("[placeholder='Город']").setValue("Новосибирск");
        $("[placeholder='Дата встречи']").doubleClick();
        $("[placeholder='Дата встречи']").sendKeys(Keys.BACK_SPACE);
        String planningDate = generateDate(3, "dd.MM.yyyy");
        $("[placeholder='Дата встречи']").setValue(planningDate);
        $("[name='name']").setValue("Петров Николай");
        $("[name='phone']").setValue("+79345678901");
        //$("[data-test-id='agreement']").click();
        $x("//div/button").click();
        $x("//div//span[contains(text(), 'Я соглашаюсь с условиями обработки')]").shouldBe(visible);
    }
}
