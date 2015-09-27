Project in beta development
===

Utility used to download astronomy images from the user's search result.  
Used Jsoup and an sqlite3 database.


***

How to run on Windows (with Eclipse):  

If the *EGit* plug-in is already installed on your version of Eclipse, go to step 3
1.  Use Eclipse to install *EGit* from the *Eclipse Marketplace*  (Help => Eclipse Marketplace...)  
2.  Restart Eclipse, unless already performed during the plug-in installation  
3.  Right click inside Package Explorer  (Window => Package Explorer)  
4.  Choose "Import..."  
5.  Expand the "Git" folder  
6.  Choose "Projects from Git"
7.  Choose "Clone URI"
8.  Enter the HTTPS address from the GitHub site ([https://github.com/droidjst/AstronomyDownloader.git](https://github.com/droidjst/AstronomyDownloader.git))
9.  The form should automatically populate with information from the URL  
10.  If you'd like to contribute to the project you can enter your GitHub username and password.  Anonymous commits are not accepted
11.  Choose "Next"
12.  Add a tick mark to the "master" branch
13.  Choose "Next"
14.  Store a remote copy of the master branch on the local machine by entering a new directory from the file system (preferably the Git folder found in the /Users/{Name}/ directory).
15.  Keep the initial branch set to "master".
16.  Traditionally the remote name should be "origin", but it can be anything  
17.  Choose "Finish"

Troubleshooting:    
If there is an error with Referenced Libraries then make sure the Project Build Paths (Right-click the project in Project Explorer => Build Path => Configure Build Path...) have both Jsoup and sqlite3 referenced and set the tick marks under "Order and Export".  For all other inquiries send me an email droidjst@gmail.com.  

***

How to run on Windows (with Command Prompt):

The Java 1.7 JDK (or above) must be installed on the computer and the /bin folder must listed in the System Environment Variables (run "java -version" in Command Prompt).  

Since this project was developed in Eclipse there is a package structure which is difficult to work with on the command line.  Copy the following batch of commands to Notepad and save as "AstronomyCompile.bat".

***

Copyright 2015 Joseph Tranquillo <droidjst@gmail.com>  

