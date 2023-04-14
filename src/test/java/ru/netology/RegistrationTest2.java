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
        Configuration.headless = true;
        Configuration.holdBrowserOpen = true;
        Configuration.browserSize = "1800x1100";
        Configuration.timeout = 15000;
        open("http://localhost:9999/");
    }

    public String generateDate(int addDays, int addMonth, int addYears, String pattern) {
        return LocalDate.now().plusDays(addDays).plusMonths(addMonth).plusYears(addYears).format(DateTimeFormatter.ofPattern(pattern));
    }

    @Test
    void shouldRegisterByAccountWithCityPopUpTest() {
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
    void shouldRegisterByAccountWithCalendarPopUpForOneWeekTest() {
        $("[placeholder='Город']").setValue("Ка");
        $(byText("Калининград")).click();
        String planningDate = generateDate(7, 0, 0, "dd.MM.yyyy");
        $(".icon_name_calendar").click();
        $x("//div//tr[5]/td[5]").click();
        $("[name='name']").setValue("Иванов Иван");
        $("[name='phone']").setValue("+71234567890");
        $("[data-test-id='agreement']").click();
        $x("//div/button").click();
        $x("//div[contains(text(), 'Встреча успешно забронирована')]").shouldBe(visible);
    }

    @Test
    void shouldCalendarMoveToTheNextMonthTest() {
        $(".icon_name_calendar").click();
        $x("/html/body/div[2]/div//div[4]").click();
        $(byText("Май 2023")).shouldBe(Condition.visible);
    }

    @Test
    void shouldCalendarMoveToTheNextYearTest() {
        $(".icon_name_calendar").click();
        $x("/html/body/div[2]/div//div[3]").click();
        $(byText("Апрель 2024")).shouldBe(Condition.visible);
    }

    @Test
    void shouldCalendarMoveToTheBackTest() {
        $(".icon_name_calendar").click();
        $x("/html/body/div[2]/div//div[4]").click();
        $x("/html/body/div[2]/div//div[2]").click();
        $(byText("Апрель 2023")).shouldBe(Condition.visible);
    }
}
