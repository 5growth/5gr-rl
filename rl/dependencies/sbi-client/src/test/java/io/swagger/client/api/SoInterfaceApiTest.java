/*
 * MTP Manager API
 * MTP Manager API
 *
 * OpenAPI spec version: 2.0
 * 
 *
 * NOTE: This class is auto generated by the swagger code generator program.
 * https://github.com/swagger-api/swagger-codegen.git
 * Do not edit the class manually.
 */


package io.swagger.client.api;

import com.rl.extinterface.nbi.swagger.model.AllocateComputeRequest;
import com.rl.extinterface.nbi.swagger.model.AllocateNetworkRequest;
import com.rl.extinterface.nbi.swagger.model.AllocateNetworkResult;
import com.rl.extinterface.nbi.swagger.model.AppD;
import com.rl.extinterface.nbi.swagger.model.DeleteInterNfviPopConnectivityRequest;
import com.rl.extinterface.nbi.swagger.model.Filter;
import com.rl.extinterface.nbi.swagger.model.InlineResponse2005;
import com.rl.extinterface.nbi.swagger.model.InlineResponse201;
import com.rl.extinterface.nbi.swagger.model.InterNfviPopConnectivityRequest;
import com.rl.extinterface.nbi.swagger.model.OperateComputeRequest;
import com.rl.extinterface.nbi.swagger.model.PNFReply;
import com.rl.extinterface.nbi.swagger.model.PNFRequest;
import com.rl.extinterface.nbi.swagger.model.VirtualCompute;
import com.rl.extinterface.nbi.swagger.model.VirtualNetwork;
import io.swagger.client.ApiException;

import org.junit.Test;
import org.junit.Ignore;

import java.util.List;

/**
 * API tests for SoInterfaceApi
 */
@Ignore
public class SoInterfaceApiTest {

    private final SoInterfaceApi api = new SoInterfaceApi();

    
    /**
     * 
     *
     * 
     *
     * @throws ApiException
     *          if the Api call fails
     */
    @Test
    public void abstractOperateComputeTest() throws ApiException {
        OperateComputeRequest body = null;
        OperateComputeRequest response = api.abstractOperateCompute(body);

        // TODO: test validations
    }
    
    /**
     * Allocate abstract compute resources
     *
     * 
     *
     * @throws ApiException
     *          if the Api call fails
     */
    @Test
    public void allocateAbstractComputeTest() throws ApiException {
        AllocateComputeRequest body = null;
        VirtualCompute response = api.allocateAbstractCompute(body);

        // TODO: test validations
    }
    
    /**
     * Retrieve aggregated Cloud NFVI-PoP and Inter-NFVI-PoP Connectivity
     *
     * Retrieve aggregated Cloud NFVI-PoP and Inter-NFVI-PoP Connectivity
     *
     * @throws ApiException
     *          if the Api call fails
     */
    @Test
    public void collectMtpCloudNetworkResourceInformationTest() throws ApiException {
        InlineResponse2005 response = api.collectMtpCloudNetworkResourceInformation();

        // TODO: test validations
    }
    
    /**
     * Retrieve aggregated Cloud NFVI-PoP and Inter-NFVI-PoP Connectivity for Federation
     *
     * Retrieve aggregated Cloud NFVI-PoP and Inter-NFVI-PoP Connectivity for Federation
     *
     * @throws ApiException
     *          if the Api call fails
     */
    @Test
    public void collectMtpFederatedCloudNetworkResourceInformationTest() throws ApiException {
        InlineResponse2005 response = api.collectMtpFederatedCloudNetworkResourceInformation();

        // TODO: test validations
    }
    
    /**
     * Create inter-NFVI-PoP connectivity
     *
     * 
     *
     * @throws ApiException
     *          if the Api call fails
     */
    @Test
    public void createInterNfviPoPConnectivityTest() throws ApiException {
        InterNfviPopConnectivityRequest body = null;
        List<InlineResponse201> response = api.createInterNfviPoPConnectivity(body);

        // TODO: test validations
    }
    
    /**
     * Delete pnf in radio/compute domain
     *
     * Delete PNF of Radio/NFVI PoP
     *
     * @throws ApiException
     *          if the Api call fails
     */
    @Test
    public void deleteAbstractPnfTest() throws ApiException {
        PNFReply body = null;
        PNFReply response = api.deleteAbstractPnf(body);

        // TODO: test validations
    }
    
    /**
     * Delete inter-NFVI-PoP connectivity
     *
     * 
     *
     * @throws ApiException
     *          if the Api call fails
     */
    @Test
    public void deleteInterNfviPoPConnectivityTest() throws ApiException {
        DeleteInterNfviPopConnectivityRequest body = null;
        api.deleteInterNfviPoPConnectivity(body);

        // TODO: test validations
    }
    
    /**
     * Retrieve information about a specific application package.
     *
     * Retrieve information about a specific application package.
     *
     * @throws ApiException
     *          if the Api call fails
     */
    @Test
    public void mecappOnboardAppDIdGetTest() throws ApiException {
        String appDId = null;
        AppD response = api.mecappOnboardAppDIdGet(appDId);

        // TODO: test validations
    }
    
    /**
     * Retrieve a list of onboarded application packages.
     *
     * Retrieve a list of onboarded application packages.
     *
     * @throws ApiException
     *          if the Api call fails
     */
    @Test
    public void mecappOnboardGetTest() throws ApiException {
        List<AppD> response = api.mecappOnboardGet();

        // TODO: test validations
    }
    
    /**
     * Set pnf status supported by radio/compute domain
     *
     * Set PNF of Radio/NFVI PoP to start or stop
     *
     * @throws ApiException
     *          if the Api call fails
     */
    @Test
    public void setAbstractPnflistTest() throws ApiException {
        PNFRequest body = null;
        PNFReply response = api.setAbstractPnflist(body);

        // TODO: test validations
    }
    
    /**
     * Terminate abstract compute resources
     *
     * 
     *
     * @throws ApiException
     *          if the Api call fails
     */
    @Test
    public void terminateResourcesTest() throws ApiException {
        List<String> computeId = null;
        List<String> response = api.terminateResources(computeId);

        // TODO: test validations
    }
    
    /**
     * 
     *
     * 
     *
     * @throws ApiException
     *          if the Api call fails
     */
    @Test
    public void vIMallocateNetworkTest() throws ApiException {
        AllocateNetworkRequest params = null;
        AllocateNetworkResult response = api.vIMallocateNetwork(params);

        // TODO: test validations
    }
    
    /**
     * 
     *
     * 
     *
     * @throws ApiException
     *          if the Api call fails
     */
    @Test
    public void vIMqueryNetworksTest() throws ApiException {
        Filter networkQueryFilter = null;
        List<VirtualNetwork> response = api.vIMqueryNetworks(networkQueryFilter);

        // TODO: test validations
    }
    
    /**
     * 
     *
     * 
     *
     * @throws ApiException
     *          if the Api call fails
     */
    @Test
    public void vIMterminateNetworksTest() throws ApiException {
        List<String> networkResourceId = null;
        List<String> response = api.vIMterminateNetworks(networkResourceId);

        // TODO: test validations
    }
    
}
