package ua.net.vmarchenko.v1.pages;
import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.WebDriverRunner.url;


public class Givens {

    private static String appURL = "https://todomvc4tasj.herokuapp.com/";


    public static void given(Task... tasks) {
        ensureUrlCorrect();
        String js = buildJsString(tasks);
        executeJavaScript(js);
        refresh();
    }

    public static void given(TaskType taskType, String... taskTexts) {
        given(aTasks(taskType, taskTexts));
    }


    public static void givenAtActive(TaskType taskType, String... taskTexts) {
        givenAtActive(aTasks(taskType, taskTexts));
    }

    public static void givenAtActive(Task...tasks) {
        given(tasks);
        open(appURL + "#/active");
    }

    public static void givenAtCompleted(TaskType taskType, String... taskTexts) {
        givenAtCompleted(aTasks(taskType, taskTexts));
    }

    public static void givenAtCompleted(Task... tasks) {
        given(tasks);
        open(appURL + "#/completed");
    }


    private static String buildJsString(Task... tasks) {
        StringBuilder sb = new StringBuilder("localStorage.setItem('todos-troopjs', '[");
        for (int i = 0; i < tasks.length; i++) {
            sb.append(tasks[i].toString());
            if (i != tasks.length - 1) {
                sb.append(" , ");
            }
        }
        sb.append("]')");
        return sb.toString();
    }

    public static void ensureUrlCorrect() {
        if (!url().equals(appURL)) {
            open(appURL);
        }
    }

    public static Task aTask(TaskType taskType, String taskText) {
        return new Task(taskType, taskText);
    }

    public static Task[] aTasks(TaskType taskType, String... taskTexts) {
        Task[] tasks = new Task[taskTexts.length];
        for (int i = 0; i < tasks.length; i++) {
            tasks[i] = aTask(taskType, taskTexts[i]);
        }
        return tasks;
    }

    public static class Task {
        public String taskText;
        public TaskType taskType;

        public Task(TaskType taskType, String taskText) {
            this.taskText = taskText;
            this.taskType = taskType;
        }

        @Override
        public String toString() {
            return "{\\\"completed\\\":" + taskType + ", \\\"title\\\":\\\"" + taskText + "\\\"}";
        }
    }

    public enum TaskType {

        ACTIVE("false"), COMPLETED("true");

        private String flag;

        TaskType(String flag) {
            this.flag = flag;
        }

        public String toString() {
            return flag;
        }
    }
}
