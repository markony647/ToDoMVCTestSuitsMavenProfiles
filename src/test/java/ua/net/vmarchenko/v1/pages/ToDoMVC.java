package ua.net.vmarchenko.v1.pages;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.By;
import ru.yandex.qatools.allure.annotations.Step;

import static com.codeborne.selenide.CollectionCondition.empty;
import static com.codeborne.selenide.CollectionCondition.exactTexts;
import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;


public  class ToDoMVC {

    public static ElementsCollection tasks = $$("#todo-list>li");
    public static SelenideElement newTaskField = $("#new-todo");

    @Step
    public static void add(String... taskTexts) {
        for (String taskText : taskTexts) {
            newTaskField.shouldBe(enabled).setValue(taskText).pressEnter();
        }
    }

    @Step
    public static void edit(String oldTaskText, String newTaskText) {
        startEdit(oldTaskText, newTaskText).pressEnter();
    }

    @Step
    public static void cancelEdit(String oldTaskText, String newTaskText) {
        startEdit(oldTaskText, newTaskText).pressEscape();
    }

    @Step
    public static SelenideElement startEdit(String oldTaskText, String newTaskText) {
        tasks.find(exactText(oldTaskText)).doubleClick();
        return tasks.find(cssClass("editing")).find(".edit").setValue(newTaskText);
    }

    @Step
    public static void toggle(String taskText) {
        tasks.find(exactText(taskText)).find(".toggle").click();
    }

    @Step
    public static void toggleAll() {
        $("#toggle-all").click();
    }

    @Step
    public static void delete(String taskText) {
        tasks.find(exactText(taskText)).hover().find(".destroy").click();
    }

    @Step
    public static void clearCompleted() {
        $("#clear-completed").click();
        $("clear-completed").shouldNotBe(visible);
    }

    @Step
    public static void switchToActive() {
        $(By.linkText("Active")).click();
    }

    @Step
    public static void switchToCompleted() {
        $(By.linkText("Completed")).click();
    }

    @Step
    public static void switchToAll() {
        $(By.linkText("All")).click();
    }

    @Step
    public static void assertTasks(String... taskTexts) {
        tasks.shouldHave(exactTexts(taskTexts));
    }

    @Step
    public static void assertNoTasks() {
        tasks.shouldBe(empty);
    }

    @Step
    public static void assertVisibleTasks(String... taskTexts) {
        tasks.filter(visible).shouldHave(exactTexts(taskTexts));
    }

    @Step
    public static void assertNoVisibleTasks() {
        tasks.filter(visible).shouldBe(empty);
    }

    @Step
    public static void assertItemsLeft(int count) {
        $("#todo-count>strong").shouldHave(exactText(Integer.toString(count)));
    }

    @Step
    public static void confirmEditByClickOutside(String oldTaskText, String newTaskText) {
        startEdit(oldTaskText, newTaskText);
        newTaskField.click();
    }
}
