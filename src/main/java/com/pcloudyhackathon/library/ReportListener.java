package com.pcloudyhackathon.library;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.codeborne.selenide.WebDriverRunner;
import com.pcloudyhackathon.utils.CustomLogger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.testng.*;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.apache.commons.io.FileUtils;

/**
 * 
 * This class helps in tracing report and screenshot with all test conditions of
 * pass, fail and skipped
 *
 */
public class ReportListener implements ISuiteListener, ITestListener {

	/**
	 * Extent Report Declarations
	 */
	private static ExtentReports extent = ExtentManager.createInstance();
	public static ThreadLocal<ExtentTest> test = new ThreadLocal<ExtentTest>();
	protected static String report = "";
	protected static String screenshot = "";
	protected static String type = "";
	static CustomLogger log = new CustomLogger(ReportListener.class);

	/**
	 * This method checks the parameters before reports starts
	 * 
	 * @param suite : To get parameters
	 */
	public synchronized void onStart(ISuite suite) {
		log.info("Test Suite started!");
		screenshot = suite.getParameter("screenShot");
		if (screenshot == null)
			screenshot = "default";

		report = suite.getParameter("reportName");
		if (report == null)
			report = "TestReport";

		type = suite.getParameter("type");
		if (type == null)
			type = "";

	}

	/**
	 * Method overloading for previous method
	 */
	public synchronized void onStart(ITestContext context) {
	}

	/**
	 * Method to handle the methods, parameter, and description once the test starts
	 * 
	 * @param result
	 */
	public synchronized void onTestStart(ITestResult result) {
		log.info((result.getMethod().getMethodName() + " started!"));
		ExtentTest extentTest = null;
		if (type.equalsIgnoreCase("excel")) {
			extentTest = extent.createTest(result.getMethod().getMethodName() + " | " + result.getParameters()[0],
					result.getMethod().getDescription());
		} else {
			extentTest = extent.createTest(result.getMethod().getMethodName());
		}
		log.info("Test Name:- ------ " + result.getTestClass().getXmlTest().getName());
		extentTest.assignCategory(result.getTestClass().getXmlTest().getName());
		test.set(extentTest);
		if (type.equalsIgnoreCase("excel"))
			test.get()
					.info(result.getMethod().getMethodName() + " | " + result.getParameters()[0] + " test is started");
		else
			test.get().info(result.getMethod().getMethodName() + " test is started");
	}

	/**
	 * Method to handle the methods, parameter, and description once the test passes
	 * 
	 * @param result
	 */
	public synchronized void onTestSuccess(ITestResult result) {
		log.info((result.getMethod().getMethodName() + " passed!"));
		if (type.equalsIgnoreCase("excel"))
			test.get().pass(result.getMethod().getMethodName() + " | " + result.getParameters()[0] + " test pass");
		else
			test.get().pass(result.getMethod().getMethodName() + " test pass");
		if (screenshot.equalsIgnoreCase("pass") || screenshot.equalsIgnoreCase("default")
				|| screenshot.equalsIgnoreCase("yes")) {
			String file = takeScreenShot(result);
			if (!file.equals("")) {
				try {
					test.get().addScreenCaptureFromPath(new File(file).getName(), result.getMethod().getMethodName());
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * Method to handle the methods, parameter, and description once the test failes
	 * 
	 * @param result
	 */
	public synchronized void onTestFailure(ITestResult result) {
		log.info((result.getMethod().getMethodName() + " failed!"));
		if (type.equalsIgnoreCase("excel"))
			test.get().fail(result.getMethod().getMethodName() + " | " + result.getParameters()[0] + " test failed");
		else
			test.get().fail(result.getMethod().getMethodName() + " test failed");
		test.get().fail(result.getThrowable().getMessage());
		if (screenshot.equalsIgnoreCase("default") || screenshot.equalsIgnoreCase("fail")
				|| screenshot.equalsIgnoreCase("yes")) {
			String file = takeScreenShot(result);
			if (!file.equals("")) {
				try {
					test.get().addScreenCaptureFromPath(new File(file).getName(), result.getMethod().getMethodName());
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * Method to handle the methods, parameter, and description once the test skips
	 *
	 */
	public synchronized void onTestSkipped(ITestResult result) {
		log.info((result.getMethod().getMethodName() + " skipped!"));
		if (type.equalsIgnoreCase("excel"))
			test.get().skip(result.getMethod().getMethodName() + " | " + result.getParameters()[0] + " is skipped!");
		else
			test.get().skip(result.getMethod().getMethodName() + " is skipped!");
	}

	public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
		log.info(("onTestFailedButWithinSuccessPercentage for " + result.getMethod().getMethodName()));
	}

	/**
	 * Method to handle screenshot
	 * 
	 * @param result
	 */
	public String takeScreenShot(ITestResult result) {
		String file = "";
		try {
			if (!(WebDriverRunner.getWebDriver() == null)) {
				Thread.sleep(1000);
				File src = ((TakesScreenshot) WebDriverRunner.getWebDriver()).getScreenshotAs(OutputType.FILE);
				String timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss")
						.format(new Date(System.currentTimeMillis()));
				String filePath = null;
				if (System.getProperty("os.name").toLowerCase().contains("win"))
					filePath = System.getProperty("user.dir") + "\\target\\surefire-reports\\"
							+ result.getMethod().getMethodName() + " " + timeStamp + ".png";
				else
					filePath = System.getProperty("user.dir") + "/target/surefire-reports/"
							+ result.getMethod().getMethodName() + " " + timeStamp + ".png";
				FileUtils.copyFile(src, new File(filePath));
				file = filePath;
				log.info("Screenshot path:- " + filePath);
			}

		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return file;
	}

	/**
	 * Method to collect and clear the end results for reports
	 * 
	 * @param suite : To get parameters
	 */
	public void onFinish(ISuite suite) {
		log.info(("Test Suite is ending!"));
		extent.flush();
	}

	/**
	 * Method overloading for previous method
	 */
	public synchronized void onFinish(ITestContext context) {

	}

}
