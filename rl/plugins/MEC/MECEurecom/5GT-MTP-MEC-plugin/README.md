# MTP MEC plugin

## About
The MEC plugin exposes an interface (corresponds to the ETSI MEC Mm3 reference point) to the MTP for the application of specific MEC platform configurations for MEC application instances, such as traffic and DNS rule management. It communicates with the MEC platform (MEP) using the latter's REST API (corresponds to the ETSI MEC Mm5 reference point) in order to apply specific configurations for MEC applications, such as traffic and DNS rules as prescribed by the application's descriptor. It plays the role of a MEP Manager (MEPM). A single instance of the MEC plugin may manage multiple MEPs, one per region. Each region is identified by an ID and represents a geographical area defined as a circle of a specific radius around a geographical location (GPS coordinates).

The API it exposes to the MTP offers the following functions:

* Request to install MEC application rules. The body of the request includes information such as DNS rules, services required or produced, traffic rules, etc.
* Removal of installed MEC application rules.
* List of covered regions, including the geographical location information of the latter.
* List of MEC application rules installed and details per request.

## Contents
* swagger.yaml: API specification using OpenApi 2.0.
* python-flask-server: MEC plugin implementation
* examples: Sample request bodies to install MEC application rules.

## Installing and running
The MEG plugin is written in python. The suggested way to execute it is via a virtual environment.

* Install virtualenv: pip install virtualenv
* Create a folder for your virtual environment: mkdir /path/to/some/folder
* Create a virtual environment for a component: virtualenv -p python3 /path/to/some/folder
* Activate it: . /path/to/some/folder/bin/activate
* Install the required packages in the virtual environment (found in python_flask_server/requirements.txt): pip install -r requirements.txt
* Execute the service (inside python_flask_server): python -m swagger_server

Sample requests are found in the "examples" folder.

