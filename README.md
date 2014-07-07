The Rest Server directory has an additional folder. Sorry

To build the server, navigate to RestServer/RestServer and do mvn clean install

Deploy the Photon-Server war to the webserver.

change the RESTURL variable in the Photon.activity class to point to the URL of the REST endpoint on the server.

Install the android app on and android device by opening the project in eclipse and connecting usb. Configure it to run on external device and hit run to transfer the app. Alternatively you google the steps to build the app and copy the .apk file onto the phone to install with "unsafe sources" allowed to install on the device.
