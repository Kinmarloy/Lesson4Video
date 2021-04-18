package tests.pageobjects.scenarios;

import org.junit.jupiter.api.Test;
import tests.TestBase;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.*;

public class StudentGeristrationFormTests extends TestBase {

    StudentRegistrationPage studentRegistrationPage;

    @Test
    void RegistrastionForm(){

        studentRegistrationPage = new StudentRegistrationPage();

        studentRegistrationPage.openPage();
        studentRegistrationPage.fillForm();
        studentRegistrationPage.checkData();
    }



}
