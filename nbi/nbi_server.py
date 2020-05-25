# Copyright 2020 Centre Tecnològic de Telecomunicacions de Catalunya (CTTC/CERCA) www.cttc.es
#
# Licensed under the Apache License, Version 2.0 (the "License");
# you may not use this file except in compliance with the License.
# You may obtain a copy of the License at
#
#     http://www.apache.org/licenses/LICENSE-2.0
#
# Unless required by applicable law or agreed to in writing, software
# distributed under the License is distributed on an "AS IS" BASIS,
# WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
# See the License for the specific language governing permissions and
# limitations under the License.

# Author: Luca Vettori
import csv
import hashlib
import ipaddress
import json

import connexion
import configparser

from nbi.swagger_server import encoder
from os import path, getenv
from log.log import configure_log
from sqlalchemy import create_engine
from sqlalchemy.orm import scoped_session, sessionmaker
from flask import render_template, send_from_directory, request, session, flash, redirect, url_for, abort
from db.db_models import *
from sqlalchemy.exc import IntegrityError, InvalidRequestError, StatementError
from requests.exceptions import ConnectionError
from nbi import webserver_utils as gui_utils
# from pa.pa_client import PaClient
from ra.ra_client import RaClient

from nbi.login_module.server_login import login_passed

# load configuration parameters for RL from configuration file
rl_config_file = path.realpath(path.join(path.dirname(path.realpath(__file__)), '../conf/rl.cfg'))
rl_config = configparser.ConfigParser()
rl_config.read(rl_config_file)
rl_parameters = rl_config["rl"]
RL_PORT = rl_parameters.getint('RL_PORT')
LOG_FILE = rl_parameters.get('LOG_FILE')
LOG_LEVEL = rl_parameters.get('LOG_LEVEL')
# get the env variable OPTION_PA_ALGORITHM
# OPTION_PA_ALGORITHM = getenv("OPTION_PA_ALGORITHM", "OPTION_B")
OPTION_PA_ALGORITHM = rl_parameters.get('OPTION_PA_ALGORITHM')
OPTION_A_BOOT_QUOTES = rl_parameters.getboolean('OPTION_A_BOOT_QUOTES')
# allowed values are OPTION_A and OPTION_B (default value is set to OPTION_B)
# TODO #3 control if the value is OPTION_A or OPTION_B

config_ra_file = path.realpath(path.join(path.dirname(path.realpath(__file__)), '../conf/ra.cfg'))
ra_client = RaClient(filename=config_ra_file)
name_db_models = path.realpath(path.join(path.dirname(path.realpath(__file__)), '../db/rl_db.db'))
engine = create_engine('sqlite:///' + name_db_models, convert_unicode=True, connect_args={'check_same_thread': False})
db_session = scoped_session(sessionmaker(autocommit=False, autoflush=False, bind=engine))

local_swagger_ui_path = path.join(path.dirname(path.realpath(__file__)), 'swagger_server/swagger-ui-cttc')
options = {'swagger_path': local_swagger_ui_path}
app = connexion.App(__name__, specification_dir='./swagger_server/swagger/', options=options)
# app.app.config.from_object('config')
from sbi.openstack_connector import get_status_os_network
from orchestrator import absLogic, ro, orch


@app.route('/login', methods=['GET', 'POST'])
def do_admin_login():
    """
    Check if the entered user and password are in the db.
    Only allowed the POST method
    :return: redirect to main function of the main module
    """
    # enter in the page by the GET method, only render the login.html page
    if request.method == 'GET':
        return render_template('login.html')
    else:
        # enter in the page by a POST method (filled the register page)
        username = str(request.form['username'])
        password = str(request.form['password'])
        # password has been hashed with md5 in the db, to avoid collecting in clear password
        hashed_password = hashlib.md5(password.encode())
        query = db_session.query(Dbuser).filter_by(username=username, password=hashed_password.hexdigest())
        result = query.first()
        if result:
            session['logged_in'] = True
            session['user'] = username
            session['role'] = result.role
            # session.permanent = True  # uncomment/comment to activate/deactivate expiring session
            logger.info('User "{}" logged correctly'.format(username))
        else:
            flash('Username and/or Password are incorrect', 'warning')
            logger.debug('Username and/or Password are incorrect')
        return redirect(url_for('home'))


@app.route('/register', methods=['GET', 'POST'])
def register():
    """
    Register a new user for the RL platform.
    Allowed GET and POST methods.
    :return: rendering of the corresponding html page
    """
    # enter in the page by the GET method, only render the register.html page
    if request.method == 'GET':
        return render_template('register.html')
    # enter in the page by a POST method (filled the register page)
    else:
        username = request.form['username']
        password = request.form['password']
        password_confirmed = request.form['password_confirmed']
        if password != password_confirmed:
            logger.debug("Confirmed password is not the same!")
            flash('Confirmed password is not the same!', 'danger')
            return render_template("register.html")
        # hashing the password with md5
        user = db_session.query(Dbuser).filter_by(username=username).first()
        # check if the entered 'user' is already in the db
        if user:
            logger.debug("Username already in database".format(username))
            flash('Username already registered', 'danger')
            return render_template("register.html")
        else:
            hash_object = hashlib.md5(password.encode())
            # insert the new user (and hashed password) in the db
            db_session.add(Dbuser(username=username, password=hash_object.hexdigest(), role='Member'))
            db_session.commit()
            logger.info('User "{}" successfully registered'.format(username))
            flash('User "{}" successfully registered'.format(username), 'success')
            return render_template("login.html")


@app.route("/logout")
def logout():
    """
    Perform the logout from platform
    :return: rendering of the login html page
    """
    if 'user' in session:
        logger.info('User "{}" logged out'.format(session['user']))
        flash('User "{}" logged out'.format(session['user']), 'success')
        session['logged_in'] = False
        session.pop('user', None)
    return render_template("login.html")


@app.route('/favicon.ico')
def favicon():
    """
    To upload the favicon
    :return: send a file
    """
    return send_from_directory(path.join(path.dirname(path.realpath(__file__)), 'static', 'images'),
                               'favicon_cttc_blue.ico', mimetype='image/vnd.microsoft.icon')


@app.route('/')
@app.route('/ui')
@login_passed
def home():
    """
    This function just responds to the browser Url
    :return:        the rendered template 'home.html'
    """
    return render_template('home.html', html_title='Dashboard')


@app.route('/databases/<string:table_name>', methods=['GET'])
@login_passed
def databases(table_name):
    """
    This function just responds to the browser Url
    :param table_name: string
    :return: the rendered template 'tables.html'
    """
    if table_name not in ['domain', 'nfvipops', 'res_attr', 'loglinks', 'serviceid', 'stitch', 'users', 'virlinks']:
        abort(404)
    # Convert the DB of Users in List
    users = [u.__dict__ for u in db_session.query(Dbuser).all()]
    # Convert the DB of Domain VIM/WIM in List
    list_domain = [u.__dict__ for u in db_session.query(Dbdomainlist).all()]
    # Convert the DB of NfviPops in List
    list_nfvipops = [u.__dict__ for u in db_session.query(Dbnfvipops).all()]
    # Convert the DB of LLs in List
    list_lls = [u.__dict__ for u in db_session.query(Dbllinternfvipops).all()]
    # Convert the DB of InterNfviPopConnectivity in List
    list_connectivity = [u.__dict__ for u in db_session.query(Dbinternfvipopconnectivity).all()]
    # Convert the DB of Resource Attributes in List
    list_res = [u.__dict__ for u in db_session.query(Dbresourceattributes).all()]
    # Convert the DB of Stitching in List
    list_stitching = [u.__dict__ for u in db_session.query(Dbstitching).all()]
    # Convert the DB of Virtual Links in List
    list_vls = [u.__dict__ for u in db_session.query(Dbvirtuallinks).all()]
    return render_template('tables.html', html_title='Databases',
                           list_users=users,
                           list_domain=list_domain,
                           list_nfvipops=list_nfvipops,
                           list_lls=list_lls,
                           list_connectivity=list_connectivity,
                           list_resources=list_res,
                           list_stitching=list_stitching,
                           list_vls=list_vls,
                           table_name=table_name)


@app.route('/delete_element', methods=['POST'])
@login_passed
def delete_element():
    if request.method == 'POST':
        table_to_choose = request.form['table_to_choose']
        id_to_be_deleted = request.form['id']
        # DELETION OF VL FROM GUI: JUST TO CHECK FUNCTIONALITY
        if table_to_choose == 'virtuallinks':
            vl_to_be_deleted = db_session.query(Dbvirtuallinks).filter_by(id=id_to_be_deleted).first()
            info = {"vimId": vl_to_be_deleted.vimId, "netId": vl_to_be_deleted.networkId}
            status_os_net = get_status_os_network(info)
            if status_os_net[0]:
                try:
                    ro.delete_intrapop_net(vl_to_be_deleted)
                except KeyError as e:
                    logger.error(e)
                    flash(str(e), 'danger')
                    return redirect(request.referrer)
            else:
                message = "Network {}: {}".format(vl_to_be_deleted.vlName, status_os_net[1]['type'])
                logger.info(message)
                flash(message, 'info')
        # TODO #4 better investigate security issue
        # because I'm putting in the HTML code the name of dbs
        try:
            db_session.query(get_class_by_tablename(table_to_choose)).filter_by(id=id_to_be_deleted).delete()
            db_session.commit()
            message = {'Success': 'Deleted "{}" from "{}" table'.format(id_to_be_deleted, table_to_choose)}
            logger.info(message['Success'])
            flash(message['Success'], 'success')
        except IntegrityError:
            db_session.rollback()
            message = {'Error': 'Error in database operation'}
            logger.error(message['Error'])
            flash(message['Error'], 'danger')
        return redirect(request.referrer)


@app.route('/modify_element', methods=['POST'])
@login_passed
def modify_element():
    """
    This function just responds to the browser Url
    :return: redirect to the previous page
    """
    if request.method == 'POST':
        try:
            table_to_choose = request.form['table_to_choose']
            id_to_be_modified = request.form['id']
            result = db_session.query(get_class_by_tablename(table_to_choose)).filter_by(id=id_to_be_modified).first()
            for key, value in request.form.to_dict().items():
                if key == 'table_to_update' or key == 'id':
                    # keys not to be udpated in the table
                    continue
                elif key == 'networkCE':
                    if all(ipaddress.ip_address(a) for a in value.split()):
                        setattr(result, key, value.split())
                elif key == 'floatingIp':
                    setattr(result, key, eval(value))
                    # setattr(result, key, True) if value == "True" else setattr(result, key, False)
                elif key == 'federated':
                    setattr(result, key, eval(value))
                else:
                    setattr(result, key, value)
            db_session.commit()
        except (IntegrityError, InvalidRequestError, StatementError, ValueError) as e:
            db_session.rollback()
            message = {'Error': 'Error in database operation'}
            logger.error(e)
            flash(message['Error'], 'danger')
        return redirect(request.referrer)


@app.route('/create_element', methods=['POST'])
@login_passed
def create_element():
    """
    This function just responds to the browser Url
    :return: redirect to the previous page
    """
    if request.method == 'POST':
        try:
            table_to_choose = request.form['table_to_choose']
            element_to_add = request.form.to_dict()
            if table_to_choose == 'domainlist':
                # check if the chosen Domain_id is already present in the db
                if db_session.query(Dbdomainlist).filter_by(domain_id=element_to_add['domain_id']).first():
                    message = {'Error': 'Domain Id "{}" already in the DB'.format(element_to_add['domain_id'])}
                    flash(message['Error'], 'danger')
                    return redirect(request.referrer)
            if 'networkCE' in element_to_add:
                if all(ipaddress.ip_address(a) for a in element_to_add['networkCE'].split()):
                    element_to_add['networkCE'] = element_to_add['networkCE'].split()
            if 'floatingIp' in element_to_add:
                element_to_add['floatingIp'] = eval(element_to_add['floatingIp'])
            if 'federated' in element_to_add:
                element_to_add['federated'] = eval(element_to_add['federated'])
            element_to_add.pop('table_to_choose')
            db_session.add(get_class_by_tablename(table_to_choose)(**element_to_add))
            db_session.commit()
        except (IntegrityError, StatementError, ValueError) as e:
            db_session.rollback()
            message = {'Error': 'Error in database operation'}
            logger.error(e)
            flash(message['Error'], 'danger')
        return redirect(request.referrer)


@app.route('/add_elements', methods=['POST'])
@login_passed
def add_element_from_file():
    """
    This function just responds to the browser Url
    :return: redirect to the previous page
    """
    if request.method == 'POST':
        try:
            table_to_choose = request.form['table_to_choose']
            f = request.files['file_to_add']
            if table_to_choose == "stitching" or table_to_choose == "llinternfvipops":
                csvfile = f.read().decode('utf-8').splitlines()
                reader = csv.DictReader(csvfile)
                for row in reader:
                    db_session.add(get_class_by_tablename(table_to_choose)(**row))
                db_session.commit()
            else:
                message = {'Error': 'Database not accessible'}
                logger.error('Tried to add file to not alllowed table')
                flash(message['Error'], 'danger')
        except (IntegrityError, StatementError, ValueError) as e:
            db_session.rollback()
            message = {'Error': 'Error in database operation'}
            logger.error(e)
            flash(message['Error'], 'danger')
        return redirect(request.referrer)


@app.route('/remove_all_elements', methods=['POST'])
@login_passed
def remove_all_elements():
    """
    This function just responds to the browser Url
    :return: redirect to the previous page
    """
    if request.method == 'POST':

        table_to_choose = request.form['table_to_choose']
        try:
            if table_to_choose == "stitching" or table_to_choose == "llinternfvipops" or\
                    table_to_choose == "internfvipopconnectivity":
                rows_deleted = db_session.query(get_class_by_tablename(table_to_choose)).delete()
                logger.info("Deleted {} rows in {} table".format(rows_deleted, table_to_choose))
                db_session.commit()
        except (IntegrityError, StatementError, ValueError) as e:
            db_session.rollback()
            message = {'Error': 'Error in database operation'}
            logger.error(e)
            flash(message['Error'], 'danger')
        return redirect(request.referrer)


@app.route('/instantiate_ll', methods=['POST'])
@login_passed
def instantiate_ll():
    """
    This function just responds to the browser Url
    :return: redirect to the previous page
    """
    body = {
        "logicalLinkPathList": [
            {
                "logicalLinkAttributes": {
                    "logicalLinkId": request.form['logicalLinkId'],
                    "srcGwIpAddress": request.form['srcGwIpAddress'],
                    "localLinkId": int(request.form['localLinkId']),
                    "dstGwIpAddress": request.form['dstGwIpAddress'],
                    "remoteLinkId": int(request.form['remoteLinkId'])
                },
                "reqBandwidth": int(request.form['reqBandwidth']),
                "reqLatency": float(request.form['reqLatency']),
                "metaData": eval(request.form['metaData'])
            }
        ],
        "metaData": [
            {
                "key": "ServiceId",
                "value": request.form['metadata_serviceId']
            }
        ],
        "networkLayer": request.form['networkLayer'],
        "interNfviPopNetworkType": request.form['interNfviPopNetworkType']
    }
    try:
        connectivity_list = orch.create_connectivity(body=body, request_from_rest_api=False)
        flash("Created InterNfviPopConnnectivity with Id: '{}'".format(
            connectivity_list[0].inter_nfvi_pop_connnectivity_id), "success")
        return redirect(url_for('databases', table_name="serviceid"))
    except(KeyError, IndexError, TypeError) as e:
        logger.error(str(e))
        flash(str(e), "danger")
        return redirect(request.referrer)


@app.route('/res_view')
@login_passed
def res_view():
    """
    This function just responds to the browser Url
    :return: the rendered template 'res_view.html'
    """
    resource_d3network = {}
    try:
        resource_d3network = gui_utils.resource_view()
        logger.info("Retrieved all the global view of resources")
    except(TypeError, AttributeError, KeyError) as e:
        logger.error(e)
        flash(e, "danger")
    return render_template('res_view.html',
                           html_title="Resources View",
                           d3_network=resource_d3network)


@app.route('/call_view/<string:call_id>')
@login_passed
def call_view(call_id):
    """
    This function just responds to the browser Url
    :return: the rendered template 'call_view.html'
    """
    # try to add call with the following format
    call_path = None
    resource_d3network = {}
    call_response = db_session.query(Dbinternfvipopconnectivity).filter_by(interNfviPopConnectivityId=call_id).first()
    if call_response is not None and call_response.pathCall is not None:
        call_path = eval(call_response.pathCall)
    # call = [{"source": "00.00.00.00.00.2a", "destination": "00.00.00.00.00.3c", "inter_link_type": "interWanLink"},
    #         {"source": "00.00.00.00.00.2c", "destination": "00.00.00.00.00.1b", "inter_link_type": "interWanLink"},
    #         {"source": "00.00.00.00.00.1a", "destination": "00.00.00.00.00.2a", "inter_link_type": "WanLink"},
    #         {"source": "00.00.00.00.00.3c", "destination": "00.00.00.00.00.2c", "inter_link_type": "WanLink"},
    #         {"source": "00.00.00.00.00.1b", "destination": "00.00.00.00.00.2b", "inter_link_type": "WanLink"}]
    else:
        logger.debug("Something wrong in DB for call path")
        flash("The path of call '{}' could not be displayed".format(call_id), "danger")
    try:
        resource_d3network = gui_utils.resource_view(call_path)
    except(TypeError, KeyError) as e:
        logger.error(e)
        flash(e, "danger")
    return render_template('call_view.html',
                           html_title="View of Call: {}".format(call_id),
                           d3_network=resource_d3network)


@app.route('/wim_view/<string:wim_id>')
@login_passed
def wim_view(wim_id):
    """
    This function just responds to the browser Url
    :return: the rendered template 'wim_view.html'
    """
    wim_resource_d3network = {}
    try:
        wim_resource_d3network = gui_utils.wim_resource_view(wim_id)
    except(TypeError, KeyError, ConnectionError) as e:
        logger.debug(e)
        flash(e, 'danger')
    return render_template('wim_view.html',
                           html_title="WIM '{}' Resources View".format(wim_id),
                           d3_network=wim_resource_d3network)


@app.route('/abs_view')
@login_passed
def abs_view():
    """
    This function just responds to the browser Url
    :return: the rendered template 'abs_view.html'
    """
    abs_view_graph = {}
    # attacking the inner functions
    try:
        nfvi_pops, lls_list = absLogic.get_abstraction(request_from_rest_api=False)
        fed_nfvi_pops, fed_lls_list = absLogic.get_fed_abstraction()
        lls_list.extend(fed_lls_list)
        abs_view_graph = gui_utils.abstracted_view(nfvi_pops, fed_nfvi_pops, lls_list)
    except ConnectionError as e:
        logger.error(e)
        flash("Problem with Abs Viewer: {}".format(e), 'danger')
        abort(408)
    return render_template('abs_view.html',
                           html_title="Abstracted View",
                           d3_network=abs_view_graph)


# @app.route('/display_yaml/<json_file>')
# @login_passed
# def display_yaml_file(json_file):
#     """
#     Display the principal features of the YAML file
#     :param json_file: json descriptor to be represented in yaml format
#     :return: rendering a html page
#     """
#     # to load a string as json inside the string should be " instead of '
#     json_dict = json.loads(json_file.replace("\'", "\""))
#     yaml_descriptor = yaml.safe_dump(json_dict, default_flow_style=False)
#     return render_template('text_editor.html',
#                            test_editor=yaml_descriptor)


@app.route('/config')
@login_passed
def display_config_files():
    """
    This function just responds to the browser Url
    :return: the rendered template 'config_file_page.html'
    """
    with open(config_ra_file, 'r') as ra_file:
        lines = ra_file.read().splitlines()
    data_config_ra = "\n".join(lines)
    with open(rl_config_file, 'r') as conf_file:
        lines_rl = conf_file.read().splitlines()
    data_rl = "\n".join(lines_rl)
    return render_template('config_file_page.html',
                           html_title="Config Files",
                           data_config_ra=data_config_ra,
                           config_rl=data_rl)


@app.route('/in_out')
@login_passed
def in_out():
    """
    This function just responds to the browser Url
    :return: the rendered template 'in_out.html'
    """
    with open("body_input.dat", 'r') as file_in:
        lines = file_in.read().splitlines()
        if len(lines) > 50:
            lines = lines[:]
    data_in = "\n".join(lines)
    with open("body_output.dat", 'r') as file_out:
        lines = file_out.read().splitlines()
        if len(lines) > 50:
            lines = lines[:]
    data_out = "\n".join(lines)
    return render_template('in_out.html',
                           html_title="Input/Output RL",
                           input=data_in,
                           output=data_out)


@app.route('/logs_popup')
@login_passed
def display_logs_popup():
    """
    This function just responds to the browser Url
    :return: the rendered template 'log_page.html'
    """
    with open(LOG_FILE, 'r') as log_file:
        lines = log_file.read().splitlines()
        last_lines = lines[-50:]
    last_lines.reverse()
    data = "\n".join(last_lines)
    return render_template('log_page.html',
                           logs=data)


# error handlers
# @app.app.errorhandler(400)
# @login_passed
# def bad_request(self):
#     """
#     Handles the 400 HTTP method
#     :return: render the corresponding html page
#     """
#     logger.error('Bad request: {}'.format(request.path))
#     message = {'Bad request': 'The server cannot process the request'}
#     return render_template('data.html', response=message, html_title="Error Page"), 400
#
#
# @app.app.errorhandler(404)
# @login_passed
# def page_not_found(self):
#     """
#     Handles the 404 HTTP method
#     :return: render the corresponding html page
#     """
#     logger.error('Page not found: {}'.format(request.path))
#     message = {'Page Not Found': 'Sorry, but the page you were trying to view does not exist.'}
#     return render_template('data.html', response=message, html_title="Error Page"), 404
#
#
# @app.app.errorhandler(405)
# @login_passed
# def method_not_allowed(self):
#     """
#     Handles the 405 HTTP method
#     :return: render the corresponding html page
#     """
#     logger.error('Method not allowed: {}'.format(request.path))
#     message = {'Method not allowed': 'Sorry, but the request is not available till now.'}
#     return render_template('data.html', response=message, html_title="Error Page"), 405
#
#
# @app.app.errorhandler(408)
# @login_passed
# def request_timeout(self):
#     """
#     Handles the 408 HTTP method
#     :return: render the corresponding html page
#     """
#     logger.error('Request timeout: {}'.format(request.path))
#     message = {'Request Timeout': 'Sorry, but the request is timeout.'}
#     return render_template('data.html', response=message, html_title="Error Page"), 408
#
#
# @app.app.errorhandler(500)
# @login_passed
# def internal_error(self):
#     """
#     Handles the 500 HTTP method
#     :return: render the corresponding html page
#     """
#     logger.error('Internal Server Error')
#     message = {'Internal Server Error': 'Sorry, check in the log.'}
#     return render_template('data.html', response=message, html_title="Error Page"), 500
#
#
# @app.app.errorhandler(503)
# @login_passed
# def service_unavailable(self):
#     """
#     Handles the 503 HTTP method
#     :return: render the corresponding html page
#     """
#     logger.error("Service Unavailable")
#     message = {'Service unavailable': 'Required service not available. Please check log.'}
#     return render_template('data.html', response=message, html_title="Error Page"), 503

def calculate_paths_at_boot():
    """
    Calculation of the underlying paths at RL boot
    """
    r_zone_attr_list = db_session.query(Dbresourceattributes).all()
    nfvi_pops = absLogic.calc_abs_compute(r_zone_attr_list)
    # define a list of GW that belongs to "local" domain of RL
    list_gw_not_federated = [net_gw.net_gw_ip_address for pop in nfvi_pops for net_gw in pop.nfvi_pop_attributes.network_connectivity_endpoint]
    absLogic.pre_calculate_paths(list_gw_not_federated, ra_client.k_paths_ina)


if __name__ == '__main__':
    logger = configure_log(LOG_FILE, LOG_LEVEL)
    logger.info("RL using '{}' Architecture!".format(OPTION_PA_ALGORITHM))
    logger.info("Starting RL NBI server.")
    if OPTION_PA_ALGORITHM == "OPTION_A" and OPTION_A_BOOT_QUOTES:
        calculate_paths_at_boot()
    app.app.json_encoder = encoder.JSONEncoder
    app.add_api('swagger.yaml', arguments={'title': '5GT-MTP NBI'}, strict_validation=True)
    app.app.secret_key = "secret"
    # app.app.permanent_session_lifetime = timedelta(minutes=10) # to activate expiring session(uncomment also in login)
    app.run(port=RL_PORT)
