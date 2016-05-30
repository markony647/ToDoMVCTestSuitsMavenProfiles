package ua.net.vmarchenko.v1;


import org.junit.Test;
import org.junit.experimental.categories.Category;
import ua.net.vmarchenko.v1.categories.Buggy;

import static ua.net.vmarchenko.v1.pages.Givens.*;
import static ua.net.vmarchenko.v1.pages.Givens.TaskType.*;
import static ua.net.vmarchenko.v1.pages.ToDoMVC.*;


public class TodosOperationsAtAllFilterTest extends BaseTest {

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
}
