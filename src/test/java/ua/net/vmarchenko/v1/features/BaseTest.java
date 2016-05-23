package ua.net.vmarchenko.v1.features;


import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Screenshots;
import com.google.common.io.Files;
import org.junit.After;
import org.junit.BeforeClass;
import ru.yandex.qatools.allure.annotations.Attachment;

import java.io.File;
import java.io.IOException;


public class BaseTest {
    static {
        Configuration.pageLoadStrategy = "normal";
    }

    @BeforeClass
    public static void setUp() {
        Configuration.browser = System.getProperty("driver.browser");
    }

    @Attachment(type = "image/png")
    public byte[] makeScreenshot() throws IOException {
        File screenshot = Screenshots.takeScreenShotAsFile();
        return Files.toByteArray(screenshot);
    }


    @After
    public void screenshot() throws IOException {
        makeScreenshot();
    }
}

