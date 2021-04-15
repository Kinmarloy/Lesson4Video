package tests.pageobjects.steps;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.$x;

public class StudentRegistrationPage {
    private SelenideElement pageHeader = $x("//div[text()='Practice Form']"),
                    firstNameInput = $x("//div[text()='Practice Form']");

    public void  checkPageHeader (String value) {
        pageHeader.shouldHave(text(value));
    }

    public void setFirstName (String value){
        firstNameInput.setValue(value);
    }

    public void setBirthDate (String year,String month, String day) {

        $x("//input[@id='dateOfBirthInput']").click();
        $x("//select[@class='react-datepicker__year-select']").selectOption(year);
        $x("//select[@class='react-datepicker__month-select']").selectOption(month);
        if (day.length() == 1) day = "0" + day;
        $x("//div[contains(@class,'react-datepicker__day--0"+day+"')]").click();

    }



}
