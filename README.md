Configuration <br>



In this configuration guide you set up our project in IntelliJ and with JavaFX that is needed to show our GUI.
JavaFX SDK 
Make sure you have a JavaFX SDK installed, if you already have an SDK installed of JavaFX of version 19 or later, then you can skip this step. Otherwise use the link below to download one. Also remember where you save it, as we will need the path later on.
https://gluonhq.com/products/javafx/ 
![2023-03-31 00_18_36-TV 3 Projekt - Google Docs](https://user-images.githubusercontent.com/4933675/228976768-93be4eb5-fede-4b43-84f0-2773c547febe.png)

Choose an SDK to that OS and Architecture that matches your hardware and device. You can use the search I did on the picture above to only see SDK’s of the version we are looking for.
Download Project and open in IntelliJ
Download the project from our github or use the project that is included in the submission. You can use this link to download the zim file of the project from GitHub.
https://github.com/Kraminius/g09_02327Application_2023
![2023-03-31 00_19_17-TV 3 Projekt - Google Docs](https://user-images.githubusercontent.com/4933675/228976773-a46e4d16-f57a-4ead-85dd-5e41c7efd29f.png)

Press <>Code and choose Download Zip.
Download and extract file with your preferred extraction tool and open the folder as an intelliJ project.

You can do so by right clicking the folder if you have choose to have this as a setting when you installed intelliJ. If this is not an option, open it in IntelliJ manually.
You can do this by opening intelliJ then in the top left corner press 
File > New > New Project From Existing Source
Then navigate to the project and open. 

On the right side of the screen near the bottom a popup might show up, just press Load. It will load the maven build script..
![2023-03-31 00_19_28-TV 3 Projekt - Google Docs](https://user-images.githubusercontent.com/4933675/228976774-9380509f-1005-40fc-83f9-cee975c1050a.png)


Setup Modules
Go to the top left corner and press File > Project Structure > Modules
Open the folder “src” and “main” so you can see the “Resources” folder. Mark it by pressing it and afterward press the Mark as “Ressources” as shown below.
Then press Apply and ok.

![2023-03-31 00_19_39-TV 3 Projekt - Google Docs](https://user-images.githubusercontent.com/4933675/228976776-4879ccc6-9cac-4db1-8cad-6d43b5d492e1.png)




Setup Configurations
In the top right corner click “Current File” and “Edit Configurations”.
![2023-03-31 00_19_52-TV 3 Projekt - Google Docs](https://user-images.githubusercontent.com/4933675/228976778-8747939e-7d83-4792-9742-a867c41bf818.png)


Press the plus icon in the top left corner of this new screen and press Application.

![2023-03-31 00_19_59-TV 3 Projekt - Google Docs](https://user-images.githubusercontent.com/4933675/228976780-2f56b48c-3f06-4514-b72f-64bb95f97d33.png)


On the top of your screen you can rename the application, in this example i renamed it to “Run”. 
After this press “Modify Options” and “Add VM options”.

![2023-03-31 00_20_08-TV 3 Projekt - Google Docs](https://user-images.githubusercontent.com/4933675/228976784-c1f09630-a64e-4544-b51c-674bfa066946.png)


Copy line and paste into VM options
If you are on a mac or Linux computer then paste this:
--module-path /path/to/javafx-sdk-20/lib --add-modules javafx.controls,javafx.fxml
Or if you are on windows use this:
--module-path "\path\to\javafx-sdk-20\lib" --add-modules javafx.controls,javafx.fxml
Paste and switch out the marked part with the path of your “lib” folder of your javaFX SDK. How to do so is also shown in next step.

![2023-03-31 00_20_16-TV 3 Projekt - Google Docs](https://user-images.githubusercontent.com/4933675/228976786-0cdacf81-2ad6-40c9-9c01-f93a2bdc9682.png)



Find your SDK through your file explorer and open until you reach your “lib” folder, then press your path and copy it. Mine looks like this:

![2023-03-31 00_20_29-TV 3 Projekt - Google Docs](https://user-images.githubusercontent.com/4933675/228976789-cdd70b35-29e7-4c56-8c6a-450dd06184ce.png)


Paste the line into the VM options instead of “\path\to\javafx-sdk-20\lib”.
Choose a module by pressing “-cp no module” and choose “g09_02327Application_2023”

![2023-03-31 00_20_37-TV 3 Projekt - Google Docs](https://user-images.githubusercontent.com/4933675/228976790-5852ea71-2496-4a3a-8803-e2b8bb791833.png)


Press the icon of a document at “Main Class” and choose Main (org.example) and press ok.

![2023-03-31 00_20_46-TV 3 Projekt - Google Docs](https://user-images.githubusercontent.com/4933675/228976791-cf6673d8-445d-44d4-a69c-81ad4b79667a.png)

Now Press Apply and OK.

Setup Libraries
Now go to File > Project Structure > Libraries
And then the plus icon and new Project Library Java.

![2023-03-31 00_20_53-TV 3 Projekt - Google Docs](https://user-images.githubusercontent.com/4933675/228976793-26d77d2e-8612-4489-b2ca-253521a27142.png)


Paste your path of your library folder again here.

![2023-03-31 00_20_59-TV 3 Projekt - Google Docs](https://user-images.githubusercontent.com/4933675/228976794-9258698a-8be7-4477-a313-aa903ce7359d.png)

Choose module and press OK.

![2023-03-31 00_21_26-TV 3 Projekt - Google Docs](https://user-images.githubusercontent.com/4933675/228976795-e9e51df6-1f2f-4508-a4cb-7e2913c2a981.png)

Then press Apply and OK
