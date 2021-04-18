package tests.demoQA.pageobjects;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.javafaker.Faker;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import tests.TestBase;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static utils.FileUtils.readStringFromFile;

public class Lesson4WithExecuteJavascript extends TestBase {
    static Faker faker = new Faker();
    static String firstName = faker.name().firstName(),
            lastName = faker.name().lastName(),
            userEmail = faker.internet().emailAddress(),
            gender = "Female", //Male , Other
            userNumber = faker.phoneNumber().subscriberNumber(10),
            dateOfBirthDay = "10",
            dateOfBirthMonth = "September",
            dateOfBirthYear = "1990",
            subjects1 = "Maths",
            subjects2 = "Arts",
            subjects3 = "English",
            hobbies1 = "Sports",
            hobbies2 = "Reading",
            hobbies3 = "Music",
            picture = "1.jpg",
            currentAddress = faker.address().fullAddress(),
            state = "Haryana",
            city = "Panipat";

    static Map<String, String> expectedData  = new HashMap<>() {{
        put("Student Name", firstName + " " + lastName);
        put("Student Email", userEmail);
        put("Gender", gender);
        put("Mobile", userNumber);
        put("Date of Birth", dateOfBirthDay + " " + dateOfBirthMonth + "," + dateOfBirthYear);
        put("Subjects", subjects1 + ", " + subjects2 + ", " + subjects3);
        put("Hobbies", hobbies1 + ", " + hobbies2 + ", " + hobbies3);
        put("Picture", picture);
        put("CurrentAdress", currentAddress);
        put("City", state + " " + city);
    }};


    @BeforeAll
    static void RegistrastionForm() {


        open("https://demoqa.com/automation-practice-form");

        $x("//div[text()='Practice Form']").shouldHave(text("Practice Form"));

        $x("//input[@id='firstName']").setValue(firstName);
        $x("//input[@id='lastName']").setValue(lastName);
        $x("//input[@id='userEmail']").setValue(userEmail);
        $x("//input[@id='gender-radio-1']/following::label[text()='" + gender + "']").click();
        $x("//input[@id='userNumber']").setValue(userNumber);

        $x("//input[@id='dateOfBirthInput']").click();
        $x("//select[@class='react-datepicker__year-select']").selectOption(dateOfBirthYear);
        $x("//select[@class='react-datepicker__month-select']").selectOption(dateOfBirthMonth);
        if (dateOfBirthDay.length() == 1) dateOfBirthDay = "0" + dateOfBirthDay;
        $x("//div[contains(@class,'react-datepicker__day--0" + dateOfBirthDay + "')]").click();

        $x("//input[@id='subjectsInput']").setValue(subjects1).pressEnter();
        $x("//input[@id='subjectsInput']").setValue(subjects2).pressEnter();
        $x("//input[@id='subjectsInput']").setValue(subjects3).pressEnter();

        $x("//div[@id='hobbiesWrapper']//label[text()='" + hobbies1 + "']").click();
        $x("//div[@id='hobbiesWrapper']//label[text()='" + hobbies2 + "']").click();
        $x("//div[@id='hobbiesWrapper']//label[text()='" + hobbies3 + "']").click();

        $x("//input[@id='uploadPicture']").uploadFromClasspath("img/" + picture);

        $x("//textarea[@id='currentAddress']").setValue(currentAddress);

        $x("//div[@id='state']").click();
        $x("//div[text()='" + state + "']").click();
        $x("//div[@id='city']").click();
        $x("//div[text()='" + city + "']").click();

        $x("//button[@id='submit']").click();
        $x("//div[@id='example-modal-sizes-title-lg']").shouldHave(text("Thanks for submitting the form"));

        getBodyContentWithExecuteScript();

    }

    @ParameterizedTest
    @MethodSource("getTableDataAsStream")
    void checkTableData(String key, String actualValue) {
        System.out.println(key + " " + actualValue);

        assertThat(expectedData.get(key), is(actualValue));
    }

    public static Stream<Arguments> getTableDataAsStream() {
        return createList(getBodyContentWithExecuteScript()).stream();
    }

    private static List<Arguments> createList(Map<String, String> data) {
        return data.entrySet()
                .stream()
                .map(e -> Arguments.of(e.getKey(), e.getValue()))
                .collect(Collectors.toList());
    }

    public static Map<String, String> getBodyContentWithExecuteScript() {
        String jsCode = readStringFromFile("./src/test/resources/javascript/get_table_data.js");
        String browserResponse = executeJavaScript(jsCode);
        System.out.println(browserResponse);

        ObjectMapper mapper = new ObjectMapper();
        Map<String, String> data = null;
        try {
            data = mapper.readValue(browserResponse,
                    new TypeReference<Map<String, String>>(){});
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        System.out.println(data);

        return data;
    }


}
