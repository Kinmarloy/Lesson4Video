package tests.pageobjects.chain;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.$x;
import static com.codeborne.selenide.Selenide.open;

public class StudentRegistrationPage {

    String firstName = "Petrov",
            lastName = "Petr",
            userEmail = "petr@prtr.tu",
            gender = "Female", //Male , Other
            userNumber = "0123456789",
            dateOfBirthDay = "7",
            dateOfBirthMonth = "September",
            dateOfBirthYear = "1990",
            subjects1 = "Maths",
            subjects2 = "Arts",
            subjects3 = "English",
            hobbies1 = "Sports",
            hobbies2 = "Reading",
            hobbies3 = "Music",
            picture = "1.jpg",
            currentAddress = "Gomel",
            state = "Haryana",
            city = "Panipat";

    public StudentRegistrationPage  openPage (){
        open("https://demoqa.com/automation-practice-form");
        $x("//div[text()='Practice Form']").shouldHave(text("Practice Form"));

        return this;
    }


    public StudentRegistrationPage  fillForm () {

        $x("//div[text()='Practice Form']").shouldHave(text("Practice Form"));

        $x("//input[@id='firstName']").setValue(firstName);
        $x("//input[@id='lastName']").setValue(lastName);
        $x("//input[@id='userEmail']").setValue(userEmail);
        $x("//input[@id='gender-radio-1']/following::label[text()='"+gender+"']").click();
        $x("//input[@id='userNumber']").setValue(userNumber);

        setBirthDate(dateOfBirthYear, dateOfBirthMonth, dateOfBirthDay);

        $x("//input[@id='subjectsInput']").setValue(subjects1).pressEnter();
        $x("//input[@id='subjectsInput']").setValue(subjects2).pressEnter();
        $x("//input[@id='subjectsInput']").setValue(subjects3).pressEnter();

        $x("//div[@id='hobbiesWrapper']//label[text()='"+hobbies1+"']").click();
        $x("//div[@id='hobbiesWrapper']//label[text()='"+hobbies2+"']").click();
        $x("//div[@id='hobbiesWrapper']//label[text()='"+hobbies3+"']").click();

        $x("//input[@id='uploadPicture']").uploadFromClasspath("img/"+picture);

        $x("//textarea[@id='currentAddress']").setValue(currentAddress);

        $x("//div[@id='state']").click();
        $x("//div[text()='" + state + "']").click();
        $x("//div[@id='city']").click();
        $x("//div[text()='" + city + "']").click();

        $x("//button[@id='submit']").click();

        $x("//div[@id='example-modal-sizes-title-lg']").shouldHave(text("Thanks for submitting the form"));

        return this;
    }

    public void setBirthDate (String year,String month, String day) {

        $x("//input[@id='dateOfBirthInput']").click();
        $x("//select[@class='react-datepicker__year-select']").selectOption(year);
        $x("//select[@class='react-datepicker__month-select']").selectOption(month);
        if (day.length() == 1) day = "0" + day;
        $x("//div[contains(@class,'react-datepicker__day--0"+day+"')]").click();

    }


    public void checkData() {
        $x("//td[text()='Student Name']").parent().shouldHave(text(firstName + " " + lastName));
        $x("//td[text()='Student Email']").parent().shouldHave(text(userEmail));
        $x("//td[text()='Gender']").parent().shouldHave(text(gender));
        $x("//td[text()='Mobile']").parent().shouldHave(text(userNumber));
        $x("//td[text()='Date of Birth']").parent().shouldHave(text(dateOfBirthDay + " " + dateOfBirthMonth + "," + dateOfBirthYear));
        $x("//td[text()='Subjects']").parent().shouldHave(text(subjects1 + ", " + subjects2 + ", " + subjects3));
        $x("//td[text()='Hobbies']").parent().shouldHave(text(hobbies1 + ", " + hobbies2 + ", " + hobbies3));
        $x("//td[text()='Picture']").parent().shouldHave(text(picture));
        $x("//td[text()='Address']").parent().shouldHave(text(currentAddress));
        $x("//td[text()='State and City']").parent().shouldHave(text(state + " " + city));
    }
}
