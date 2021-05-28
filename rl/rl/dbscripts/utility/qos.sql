CREATE TABLE mtpservdb.networkqos (
netQosId INT UNSIGNED NOT NULL AUTO_INCREMENT,
qosName VARCHAR (100),
/*qosValue VARCHAR (100),*/
/*Primary and Foreign Keys*/
netServId INT UNSIGNED,
FOREIGN KEY (netServId) REFERENCES mtpservdb.virtualnetworkdata (netServId),
netQosPolicyId INT UNSIGNED,
FOREIGN KEY (netQosPolicyId) REFERENCES mtpservdb.networkQosPolicy (netQosPolicyId),
PRIMARY KEY (netQosId)
);

CREATE TABLE mtpservdb.networkQosPolicy (
  netQosPolicyId INT UNSIGNED NOT NULL AUTO_INCREMENT,
  maxBandwidth VARCHAR (100),
  burstSize VARCHAR (100),
  /*Primary and Foreign Keys*/
  netQosId INT UNSIGNED NOT NULL,
  FOREIGN KEY (netQosId) REFERENCES mtpservdb.networkqos (netQosId),
  PRIMARY KEY (netQosPolicyId)
);
