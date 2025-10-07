package co.spribe.qaa.listeners;

import co.spribe.qaa.core.config.Config;
import org.testng.IAlterSuiteListener;
import org.testng.xml.XmlSuite;

import java.util.List;

public class SuiteThreadsListener implements IAlterSuiteListener {
    @Override
    public void alter(List<XmlSuite> suites) {
        int threads = Config.threads();
        for (XmlSuite suite : suites) {
            suite.setParallel(XmlSuite.ParallelMode.CLASSES);
            suite.setThreadCount(threads);
        }
    }
}