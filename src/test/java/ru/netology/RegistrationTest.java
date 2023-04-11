package ru.netology;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Selenide.open;

public class RegistrationTest {

    @Test
    void shouldRegisterByAccountNumberDOMModification() {
        Configuration.holdBrowserOpen = true;
        Configuration.browserSize = "1800x1100";
        open("http://localhost:9999/");
    }
}
