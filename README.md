# TellsTrack
Simple Android application that shows **Swiss public transportation timetables**.

## About this project
This app has been developed as part of the course “Software engineering for mobile platforms” in the Master of Advanced Studies program Software Engineering at HSR.

HSR (German: Hochschule für Technik Rapperswil) or [University of Applied Sciences Rapperswil](https://www.hsr.ch) is a technical university in Rapperswil, Switzerland.

Due to the fact that this is a school assignment the **project will not be developed further after March 2018.**

## Implementation and facts
* Based on Android mobile operating system, developed by Google.
* Consumes the api of [transport.opendata.ch](http://transport.opendata.ch/), which is powered by [Opendata.ch](https://opendata.ch/).
* Minimum SDK Version: 24 (Android Nougat 7.0 and up)
* App layout with drawer, autocomplete, date & timepicker, connection overview and connection details fragments as well as an about page.

## Installation
Since this app is not listed in the Google Play Store, it has to be installed manually.

### Step 1
Before you can install it on your phone you will need to make sure that third-party apps are allowed on your device. Go to **Menu > Settings > Security >** and check **Unknown Sources** to allow your phone to install apps from sources other than the Google Play Store. Android 8.0 Oreo does things a little differently. Rather than check a global setting to allow installation from unknown sources, you'll be you'll be prompted to allow your browser or file manager to install APKs the first time you attempt to do so.

### Step 2
Download the [APK (Android Package Kit)](https://github.com/philippbruhin/TellsTrack/blob/master/documentation/APK/app-debug.apk) file to your computer. Then copy the APK file into a folder of your choice in your Androud Smartphone.

You will now be able to search for the file location in the My files folder of your device. Find the APK file, tap it, then hit Install. And now you're done, congratulations, you have your TellsTrack 2.0. 



## Screenshots
Home | Drawer | Autocomplete
:-------------------------:|:-------------------------:|:-------------------------:
![Home](https://github.com/philippbruhin/TellsTrack/blob/master/documentation/Screenshots/01_home.png "Home")  |  ![Drawer](https://github.com/philippbruhin/TellsTrack/blob/master/documentation/Screenshots/02_drawer.png "Drawer") | ![Autocomplete](https://github.com/philippbruhin/TellsTrack/blob/master/documentation/Screenshots/03_autocomplete.png "Autocomplete")

Date & Time Picker | Connections | Connection details
:-------------------------:|:-------------------------:|:-------------------------:
![Date & Time Picker](https://github.com/philippbruhin/TellsTrack/blob/master/documentation/Screenshots/04_datetimepicker.png "Date & Time Picker")  |  ![Connection](https://github.com/philippbruhin/TellsTrack/blob/master/documentation/Screenshots/05_connection.png "Connection")  |  ![Connection details](https://github.com/philippbruhin/TellsTrack/blob/master/documentation/Screenshots/06_connection_details.png "Connection details")

## Credits
* [Android Studio](https://developer.android.com/studio/) - The Official IDE for Android
* Thanks to [Opendata.ch](https://opendata.ch/) for providing the API for free.
* Thanks to Mischa Demarmels ([Zühlke](https://www.zuehlke.com/)) and Stefan Schoeb ([Paixon](https://paixon.ch/)) for lead through the course.
