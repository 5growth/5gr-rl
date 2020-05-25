##5Growth CTTC RL implementation

This is the CTTC implementation for 5Growth RL.

## Installation

First, install python 3.6 and virtualenv in your host.

In "main" folder (where is located this README):

* create virtual environment:
```
virtualenv -p python3.6 venv
source venv/bin/activate
pip3 install -r requirements.txt 
```
* in the "main" directory, create "body_input.dat" and "body_output.dat" files:
```
touch body_input.dat body_output.dat
```

* in the "conf" directory, copy the "rl.cfg" and "ra.cfg" files (that define the parameters of RL and the RA):
```
cp rl.cfg.ini rl.cfg
cp ra.cfg.ini ra.cfg 
``` 

In "db" folder, copy rl_db.db.ini to rl_db.db
```
cp rl_db.db.ini rl_db.db 
```

## Usage
To run the code in "main" folder:

Activate the virtualenv if not:

```
source venv/bin/activate 
```
Then:
```
export PYTHONPATH=$PYTHONPATH:/current-directory/ (pwd)
python nbi/nbi_server.py
```
Once the code is running, you can enter in the GUI putting in the browser:
http://your_ip_address:8090

Default User: admin (Password: admin) 

## Change RL parameters
If you want to customize RL and RA parameters, please edit the 'rl.cfg' and 'ra.cfg' files in 'conf' directory.
