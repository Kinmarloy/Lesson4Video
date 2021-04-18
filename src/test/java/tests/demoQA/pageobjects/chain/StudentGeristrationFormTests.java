package tests.pageobjects.chain;

import org.junit.jupiter.api.Test;
import tests.TestBase;

public class StudentGeristrationFormTests extends TestBase {

    StudentRegistrationPage studentRegistrationPage;

    @Test
    void RegistrastionForm(){

        studentRegistrationPage = new StudentRegistrationPage();

        studentRegistrationPage.openPage()
            .fillForm()
            .checkData();
    }



}
