package com.pcloudyhackathon.library;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.configuration.ChartLocation;
import com.aventstack.extentreports.reporter.configuration.Theme;

import com.pcloudyhackathon.utils.CustomLogger;
import org.openqa.selenium.Platform;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 
 * This class satisfies the pre and post conditions of extent reports to start
 * and end with generation of test execution.
 */
public class ExtentManager {


	/**
	 * Extent Manager variable declarations and initializations
	 */
	private static ExtentReports extent;
	private static Platform platform;
	public static String reportFileName = "Automation-Report" + "_"
			+ new SimpleDateFormat("yyyy-MM-dd hh-mm-ss-ms").format(new Date()) + ".html";
	private static String macPath = System.getProperty("user.dir") + "/target" + "/surefire-reports";
	private static String windowsPath = System.getProperty("user.dir") + "\\target" + "\\surefire-reports";
	private static String macReportFileLoc = macPath + "/" + reportFileName;
	private static String winReportFileLoc = windowsPath + "\\" + reportFileName;
	static CustomLogger log = new CustomLogger(ExtentManager.class);

	/**
	 * This method is to handle an instance of extent report if not created.
	 * 
	 * @return extent (Reference of extent report)
	 */
	public static ExtentReports getInstance() {
		if (extent == null)
			createInstance();
		return extent;
	}

	/**
	 * This method is to create an instance of Extent Report.
	 * 
	 * @return extent (Reference of extent report)
	 */
	public static ExtentReports createInstance() {
		platform = getCurrentPlatform();
		String fileName = getReportFileLocation(platform);
		ExtentHtmlReporter htmlReporter = new ExtentHtmlReporter(fileName);
		htmlReporter.config().setTestViewChartLocation(ChartLocation.TOP);
		htmlReporter.config().setChartVisibilityOnOpen(true);
		htmlReporter.config().setTheme(Theme.STANDARD);
		htmlReporter.config().setDocumentTitle(fileName);
		htmlReporter.config().setEncoding("utf-8");
		htmlReporter.config().setReportName(fileName);

		extent = new ExtentReports();
		extent.attachReporter(htmlReporter);

		return extent;
	}

	/**
	 * This method is to select the extent report file location based on platform.
	 * 
	 * @param platform : OS name
	 * @return Path of report file based on OS
	 */
	private static String getReportFileLocation(Platform platform) {
		String reportFileLocation = null;
		switch (platform) {
		case MAC:
			reportFileLocation = macReportFileLoc;
			createReportPath(macPath);
			log.info("ExtentReport Path for MAC: " + macPath + "\n");
			break;
		case WINDOWS:
			reportFileLocation = winReportFileLoc;
			createReportPath(windowsPath);
			log.info("ExtentReport Path for WINDOWS: " + windowsPath + "\n");
			break;
		case LINUX:
			reportFileLocation = macReportFileLoc;
			createReportPath(macPath);
			log.info("ExtentReport Path for LINUX: " + macPath + "\n");
			break;
		case UNIX:
			reportFileLocation = macReportFileLoc;
			createReportPath(macPath);
			log.info("ExtentReport Path for UNIX: " + macPath + "\n");
			break;

		default:
			log.info("ExtentReport path has not been set! There is a problem!\n");
			log.info("Platform is " + platform.toString());
			break;
		}
		return reportFileLocation;
	}

	/**
	 * This method is to create the report path if it does not exist
	 * 
	 * @param path : path to create directory for reports
	 */
	private static void createReportPath(String path) {
		File testDirectory = new File(path);
		if (!testDirectory.exists()) {
			if (testDirectory.mkdir()) {
				log.info("Directory: " + path + " is created!");
			} else {
				log.info("Failed to create directory: " + path);
			}
		} else {
			log.info("Directory already exists: " + path);
		}
	}

	/**
	 * This method is to get the current platform
	 * 
	 * @return platform : current OS name.
	 */
	private static Platform getCurrentPlatform() {
		if (platform == null) {
			String operSys = System.getProperty("os.name").toLowerCase();
			if (operSys.contains("win")) {
				platform = Platform.WINDOWS;
			} else if (operSys.contains("nix") || operSys.contains("nux") || operSys.contains("aix")) {
				platform = Platform.LINUX;
			} else if (operSys.contains("mac")) {
				platform = Platform.MAC;
			}
		}
		return platform;
	}

}
