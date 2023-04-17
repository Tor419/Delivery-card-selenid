package ru.netology;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.ElementsCollection;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.*;

public class RegistrationTest2 {

    @BeforeEach
    void setUp() {
        Configuration.holdBrowserOpen = true;
        Configuration.browserSize = "1800x1100";
        Configuration.timeout = 15000;
        open("http://localhost:9999/");
    }

    private String generateDate(int addDays, String pattern) {
        return LocalDate.now().plusDays(addDays).format(DateTimeFormatter.ofPattern(pattern));
    }

    @Test
    void shouldRegisterByAccountWithCalendarPopUpForOneWeekAndCheckPopUpCityTest() {
        $("[placeholder='Город']").setValue("Ка");
        $(byText("Калининград")).click();
        String planningDate = generateDate(7, "dd.MM.yyyy");
        if (!generateDate(3, "MM").equals(generateDate(7, "MM"))) {
            $(".icon_name_calendar").click();
            $$("[data-disabled]").filter(visible).get(1).click();
        }
        $(".icon_name_calendar").click();
        $$("[data-day]").find(Condition.text(generateDate(7, "d"))).click();
        $("[name='name']").setValue("Иванов Иван");
        $("[name='phone']").setValue("+71234567890");
        $("[data-test-id='agreement']").click();
        $x("//div/button").click();
        $(".notification__content")
                .shouldHave(Condition.text("Встреча успешно забронирована на " + planningDate))
                .shouldBe(Condition.visible);
    }

    @Test
    void shouldCalendarMoveToTheNextMonthTest() {
        $("[placeholder='Город']").setValue("Ка");
        $(byText("Калининград")).click();
        String planningDate = generateDate(15, "dd.MM.yyyy");
        if (!generateDate(3, "MM").equals(generateDate(15, "MM"))) {
            $(".icon_name_calendar").click();
            $$("[data-disabled]").filter(visible).get(1).click();
        }
        $(".icon_name_calendar").click();
        $$("[data-day]").find(Condition.text(generateDate(15, "d"))).click();
        $("[name='name']").setValue("Иванов Иван");
        $("[name='phone']").setValue("+71234567890");
        $("[data-test-id='agreement']").click();
        $x("//div/button").click();
        $(".notification__content")
                .shouldHave(Condition.text("Встреча успешно забронирована на " + planningDate))
                .shouldBe(Condition.visible);
    }

    @Test
    void shouldCalendarMoveToTheNextYearTest() {
        $("[placeholder='Город']").setValue("Ка");
        $(byText("Калининград")).click();
        String planningDate = generateDate(365, "dd.MM.yyyy");
        if (!generateDate(3, "yyyy").equals(generateDate(365, "yyyy"))) {
            $(".icon_name_calendar").click();
            $$("[data-disabled]").filter(visible).get(0).click();
        }
        $(".icon_name_calendar").click();
        $$("[data-day]").find(Condition.text(generateDate(365, "d"))).click();
        $("[name='name']").setValue("Иванов Иван");
        $("[name='phone']").setValue("+71234567890");
        $("[data-test-id='agreement']").click();
        $x("//div/button").click();
        $(".notification__content")
                .shouldHave(Condition.text("Встреча успешно забронирована на " + planningDate))
                .shouldBe(Condition.visible);
    }
}
