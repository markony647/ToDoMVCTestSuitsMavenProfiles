package ua.net.vmarchenko.v1.features;


import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.openqa.selenium.By;
import ru.yandex.qatools.allure.annotations.Step;
import ua.net.vmarchenko.v1.categories.All;
import ua.net.vmarchenko.v1.categories.Buggy;
import ua.net.vmarchenko.v1.categories.Smoke;

import static com.codeborne.selenide.CollectionCondition.empty;
import static com.codeborne.selenide.CollectionCondition.exactTexts;
import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;
import static ua.net.vmarchenko.helpers.GivenHelpers.TaskType.ACTIVE;
import static ua.net.vmarchenko.helpers.GivenHelpers.TaskType.COMPLETED;
import static ua.net.vmarchenko.helpers.GivenHelpers.aTask;
import static ua.net.vmarchenko.helpers.GivenHelpers.given;

@Category(All.class)
public class ToDoMVCTest extends BaseTest {

    public static ElementsCollection tasks = $$("#todo-list>li");
    public static SelenideElement newTaskField = $("#new-todo");

    @Category(Smoke.class)
    @Test
    public void testTasksCommonFlow() {

        given();

        add("a");

        //Complete
        toggle("a");
        assertTasks("a");

        switchToActive();
        assertNoVisibleTasks();

        switchToCompleted();
        assertVisibleTasks("a");

        //Reopen
        toggle("a");
        assertNoVisibleTasks();

        switchToAll();

        delete("a");
        assertNoTasks();
    }

    @Test
    public void testEditAtAll() {
        given(ACTIVE, "a");

        edit("a", "a edited");
        assertTasks("a edited");
        assertItemsLeft(1);
    }

    @Test
    @Category(Buggy.class)
    public void testReopenAtAll() {
        given(COMPLETED, "a", "b");

        toggle("b");
        assertTasks("a");
        assertItemsLeft(1);
    }

    @Test
    public void testCompleteAllAtAll() {
        given(
                aTask(ACTIVE, "a"),
                aTask(ACTIVE, "b"),
                aTask(COMPLETED, "c")
        );

        toggleAll();
        assertTasks("a", "b", "c");
        assertItemsLeft(0);
    }

    @Test
    public void testReopenAllAtAll() {
        given(COMPLETED, "a", "b");

        toggleAll();
        assertTasks("a", "b");
        assertItemsLeft(2);
    }

    @Test
    public void testClearCompletedAtAll() {
        given(
                aTask(ACTIVE, "a"),
                aTask(COMPLETED, "b")
        );

        clearCompleted();
        assertTasks("a");
        assertItemsLeft(1);
    }

    @Test
    public void testCancelEditAtAll() {
        given(ACTIVE, "a", "b");

        cancelEdit("a", "a edit cancelled");
        assertTasks("a", "b");
        assertItemsLeft(2);
    }

    @Test
    public void testDeleteByRemovingTextAtAll() {
        given(ACTIVE, "a", "b");

        edit("b", "");
        assertTasks("a");
        assertItemsLeft(1);
    }

    @Test
    public void testSwitchFromAllToCompleted() {
        given(
                aTask(ACTIVE, "a"),
                aTask(COMPLETED, "b")
        );

        switchToCompleted();
        assertVisibleTasks("b");
        assertItemsLeft(1);
    }

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

    
    public static void assertNoTasks() {
        tasks.shouldBe(empty);
    }

    @Step
    public static void assertVisibleTasks(String... taskTexts) {
        tasks.filter(visible).shouldHave(exactTexts(taskTexts));
    }

    
    public static void assertNoVisibleTasks() {
        tasks.filter(visible).shouldBe(empty);
    }

    
    public static void assertItemsLeft(int count) {
        $("#todo-count>strong").shouldHave(exactText(Integer.toString(count)));
    }
}
