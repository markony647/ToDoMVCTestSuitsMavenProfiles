package ua.net.vmarchenko.v1.features;

import org.junit.Test;
import org.junit.experimental.categories.Category;
import ua.net.vmarchenko.v1.categories.Smoke;

import static ua.net.vmarchenko.v1.pages.Givens.given;
import static ua.net.vmarchenko.v1.pages.ToDoMVC.*;


public class TodosE2ETest extends BaseTest {

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
}
