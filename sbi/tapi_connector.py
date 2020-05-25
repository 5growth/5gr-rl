from sbi import wim_connector


class Connector(wim_connector.WimConnector):
    def __init__(self, parameters):
        super().__init__()
        self.description = "T-API Connector!"
        # TODO implement the rest of parameters

    def get_access(self):
        """
        Get the ip address to connect with WIM
        """
        raise wim_connector.ConnectorNotImplemented("Should have implemented this")

    def get_context(self):
        """
        Query Context to a WIM
        """
        raise wim_connector.ConnectorNotImplemented("Should have implemented this")

    def create_call(self, callId, internal_path, inter_wan_path, edge_paths, src_ip, dst_ip, metadata_call, vlan, bw):
        """
        Create a call in WIM server
        """
        raise wim_connector.ConnectorNotImplemented("Should have implemented this")

    def delete_call(self, callIds):
        """
        Delete a call in WIM server
        """
        raise wim_connector.ConnectorNotImplemented("Should have implemented this")