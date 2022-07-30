# pCloudy-hackathon-automation-framework
Java based project for test automation covering functional automation testing. The framework uses **Selenide** with **TestNG** and **Extent reporter** as a reporting tool which adheres to **Page object model** design pattern.

### Dependencies and tools
- Java 18
- Maven 3.8.5 
- Any IDE that supports JAVA (eclipse/IntelliJ IDEA)

### Below tools are required for mobile
- HomeBrew (for mac): Support tool that helps in installing the below tools via CLI
- Android Studio (latest version)
- Set up an android emulator only if want to execute testcases on emulator
- Appium (1.21)
- Node (latest version)
- Appium-doctor (to check if the required setup for running android / ios testcase is complete or not)

### Setup and execute testcases

1. Clone this repo using git bash or git cli
2. Execute a suite using cmd line:
    - **ONLY FOR MOBILE EXECUTION ON LOCAL Devices**: on a new terminal execute the following command ``appium --allow-insecure chromedriver_autodownload`` **NOTE**: do not close this terminal.
    - Connect a real device to your machine or start a emulator
        - Open new Terminal/cmd at root folder of the project
        - Execute the below command
          - pCloudy android
            ``mvn clean test -DsuiteXmlFile=testng.xml -Dtimeout=20000 -DlogLevel=DEBUG -DplatformName=Android -DappPackage=appPackage -DappActivity=appActivty -DappName=appName -DexecuteOnPCloudy=true -DpCloudyEmail=email -DpCloudyApiKey=apikey -DdeviceName=Google  -DdeviceVersion=12``
          - On local machine android
            ``mvn clean test -DsuiteXmlFile=testng.xml -Dtimeout=20000 -DlogLevel=DEBUG -DplatformName=Android -DappPackage=appPackage -DappActivity=appActivty -DappName=appName -DexecuteOnPCloudy=true -DdeviceName=Google  -DdeviceVersion=12``
          - [^1]To run on ios change ``-DplatformName=ios -DdeviceName=apple  -DdeviceVersion=13.1`` remove ``-DappPackage=appPackage -DappActivity=appActivty`` instead use ``-DbundleId=ios_bundleID``, this holds good for both pCloudy and local execution
       - Understanding each parameter
         - DsuiteXmlFile=testng.xml: Executes a specific a testng.xml
         - DlogLevel={DEBUG/INFO}: Prints log while executing test case
         - Dtimeout=20000: Timeout in milliseconds to fail the test, if conditions still not met
         - DplatformName={ANDROID/ios}: on which device does the application has to run
         - DappName={app name/full location in case of local execution}: location of the app on the system e.g. "app.apk" or "app.ipa"
         - DdeviceVersion=12.0: The actual device android version on which the application has to be run
         - DappPackage={Android app package}
         - DappActivity={Android app activity}
         - DexecuteOnPCloudy={true/false} to execute on pCloudy true for local execution pass false
         - DpCloudyEmail=pCloudy email
         - DpCloudyApiKey=pCloudy api key
         - DdeviceName=device name on which the execution has to happen
3. Execute a suite using IDE:
     - Open the IDE, on root folder you will find a xml named testng.xml.
     - Right click -> Run as -> TestNG suite: If this option is not available that means you have to install testNG on to your IDE(google for solution).
     - The testng.xml also contains the same set of parameter to control language, environment, timeouts and loglevel these can be changed accordingly
     - The tests within the xml can be run in parallel by increasing **thread-count** parameter and adding parallel="tests". e.g. `thread-count="2" parallel="tests"`, this means 2 testcases will be run on 2 browsers in parallel 
4. After the execution, the reports will be created under target -> surefire-reports -> Automation-report_{timestamp}.html. Open this on any browser to view it.

### NOTE:
config.properties file on root folder contains the env properties/default variables for any testcase to run.
1. Update that property file on local and run the script
2. Update the properties on testNg.xml to ignore the config property
3. Update the properties during the run time via mvn command using step 3

### Reports and screenshot locations

Scenario 1: 
- report link: https://device.pcloudy.com/execution_report/399929?key=jqsfg4f2pgqk9d3y88hd4n8w
- local report path: 
    - target/selenide/2022-07-30 16:25:44.124 
    - target/surefire-reports/Automation-Report_2022-07-30 04-25-43-2543.html

Scenario 3:
- report link: https://device.pcloudy.com/execution_report/399937?key=jqsfg4f2pgqk9d3y88hd4n8w
- local report path:
    - target/selenide/2022-07-30 16:28:58.881
    - target/surefire-reports/Automation-Report_2022-07-30 04-28-58-2858.html

Scenario 4:
- report link: https://device.pcloudy.com/execution_report/399942?key=jqsfg4f2pgqk9d3y88hd4n8w
- local report path:
    - target/selenide/2022-07-30 16:31:39.312
    - target/surefire-reports/Automation-Report_2022-07-30 04-31-39-3139.html


#### Sample diff image after comparing 2 images
![diff](https://user-images.githubusercontent.com/49331044/181908403-2df79e03-83a8-447c-8417-d7bbfd4f205a.png)



While execution the priority will always be given to **mvn command > testNg.xml > config.properties** 
i.e. if apikey and email is present passed via mvn command while execution it checked the testng.xml if not present then check the config.properties before failing the execution

[^1]: \* indicates that though the framework is built to support ios execution it has not been tested on ios application and devices

