package co.spribe.qaa.tests;

import co.spribe.qaa.core.config.Config;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.ITestContext;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;

import java.lang.reflect.Method;

import static co.spribe.qaa.core.config.Config.*;

public abstract class BaseTest {
  protected final Logger log = LoggerFactory.getLogger(getClass());

  @BeforeSuite(alwaysRun = true)
  public void beforeSuite() {
    log.info("Run start");
    log.info("Config loaded:\nBase Url = {},\nTimeout Ms = {}\nThreads = {}",
            baseUrl(), timeoutMs(), threads());
  }

  @BeforeClass(alwaysRun = true)
  public void beforeClass() {
    log.info("Class start: {}", getClass().getSimpleName());
  }

  @BeforeMethod(alwaysRun = true)
  public void beforeMethod(Method m) {
    log.info("Test start: {}.{} [{}]",
            m.getDeclaringClass().getSimpleName(), m.getName(),
            Thread.currentThread().getName());
  }
}