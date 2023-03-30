Configuration
In this configuration guide you set up our project in IntelliJ and with JavaFX that is needed to show our GUI.
JavaFX SDK 
Make sure you have a JavaFX SDK installed, if you already have an SDK installed of JavaFX of version 19 or later, then you can skip this step. Otherwise use the link below to download one. Also remember where you save it, as we will need the path later on.
https://gluonhq.com/products/javafx/ 

Choose an SDK to that OS and Architecture that matches your hardware and device. You can use the search I did on the picture above to only see SDK’s of the version we are looking for.
Download Projekt og Åben i IntelliJ.
Download the project from our github or use the project that is included in the submission. You can use this link to download the zim file of the project from GitHub.
https://github.com/Kraminius/g09_02327Application_2023

Press <>Code and choose Download Zip.
Download and extract file with your preferred extraction tool and open the folder as an intelliJ project.

You can do so by right clicking the folder if you have choose to have this as a setting when you installed intelliJ. If this is not an option, open it in IntelliJ manually.
You can do this by opening intelliJ then in the top left corner press 
File > New > New Project From Existing Source
Then navigate to the project and open. 

On the right side of the screen near the bottom a popup might show up, just press Load. It will load the maven build script..


Setup Modules
Go to the top left corner and press File > Project Structure > Modules
Open the folder “src” and “main” so you can see the “Resources” folder. Mark it by pressing it and afterward press the Mark as “Ressources” as shown below.
Then press Apply and ok.




Setup Configurations
In the top right corner click “Current File” and “Edit Configurations”.


Press the plus icon in the top left corner of this new screen and press Application.








On the top of your screen you can rename the application, in this example i renamed it to “Run”. 
After this press “Modify Options” and “Add VM options”.

Copy line and paste into VM options
If you are on a mac or Linux computer then paste this:
--module-path /path/to/javafx-sdk-20/lib --add-modules javafx.controls,javafx.fxml
Or if you are on windows use this:
--module-path "\path\to\javafx-sdk-20\lib" --add-modules javafx.controls,javafx.fxml
Paste and switch out the marked part with the path of your “lib” folder of your javaFX SDK. How to do so is also shown in next step.



Find your SDK through your file explorer and open until you reach your “lib” folder, then press your path and copy it. Mine looks like this:


Paste the line into the VM options instead of “\path\to\javafx-sdk-20\lib”.
Choose a module by pressing “-cp no module” and choose “g09_02327Application_2023”


Press the icon of a document at “Main Class” and choose Main (org.example) and press ok.
Now Press Apply and OK.
Setup Libraries
Now go to File > Project Structure > Libraries
And then the plus icon and new Project Library Java.


Paste your path of your library folder again here.

Choose module and press OK.

Then press Apply and OK
