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
        //Configuration.headless = true;
        Configuration.holdBrowserOpen = true;
        Configuration.browserSize = "1800x1100";
        Configuration.timeout = 15000;
        open("http://localhost:9999/");
    }

    public String generateDate(int addDays, int addMonth, int addYears, String pattern) {
        return LocalDate.now().plusDays(addDays).plusMonths(addMonth).plusYears(addYears).format(DateTimeFormatter.ofPattern(pattern));
    }

    @Test
    void shouldRegisterByAccountWithCityPopUp() {
        $("[placeholder='Город']").setValue("Ка");
        $(byText("Калининград")).click();
        String planningDate = generateDate(7, 0, 0, "dd.MM.yyyy");
        $("[data-test-id='date'] input").setValue(planningDate);
        $("[name='name']").setValue("Иванов Иван");
        $("[name='phone']").setValue("+71234567890");
        $("[data-test-id='agreement']").click();
        $x("//div/button").click();
        $x("//div[contains(text(), 'Встреча успешно забронирована')]").shouldBe(visible);
    }

    @Test
    void shouldRegisterByAccountWithCalendarPopUpForOneWeek() {
        $("[placeholder='Город']").setValue("Ка");
        $(byText("Калининград")).click();
        String planningDate = generateDate(7, 0, 0, "dd.MM.yyyy");
        $(".icon_name_calendar").click();
        $x("//div//tr[5]/td[4]").click();
        $("[name='name']").setValue("Иванов Иван");
        $("[name='phone']").setValue("+71234567890");
        $("[data-test-id='agreement']").click();
        $x("//div/button").click();
        $x("//div[contains(text(), 'Встреча успешно забронирована')]").shouldBe(visible);
    }

    @Test
    void shouldCalendarMoveToTheNextMonth() {
        $(".icon_name_calendar").click();
        $$("//div[contains(@class, 'calendar__arrow calendar__arrow_direction_right')]").filter(visible).get(0).click();
        $x("//div[contains(text(), 'Май 2023')]").shouldBe(visible);
    }

    @Test
    void shouldCalendarMoveToTheNextYear() {
        $(".icon_name_calendar").click();
        $$("//div[contains(@class, 'calendar__arrow calendar__arrow_direction_right')]").filter(visible).get(1).click();
        $x("//div[contains(text(), 'Апрель 2024')]").shouldBe(visible);
    }
}
