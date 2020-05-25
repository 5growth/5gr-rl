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

import com.rl.extinterface.nbi.swagger.model.AllocateNetworkRequest;
import com.rl.extinterface.nbi.swagger.model.AllocateNetworkResult;
import com.rl.extinterface.nbi.swagger.model.CapacityInformation;
import com.rl.extinterface.nbi.swagger.model.Filter;
import com.rl.extinterface.nbi.swagger.model.NfviPop;
import com.rl.extinterface.nbi.swagger.model.QueryNetworkCapacityRequest;
import com.rl.extinterface.nbi.swagger.model.VirtualNetwork;
import io.swagger.client.ApiException;
import java.math.BigDecimal;

import org.junit.Test;
import org.junit.Ignore;

import java.util.List;

/**
 * API tests for VimNetworkResourcesApi
 */
@Ignore
public class VimNetworkResourcesApiTest {

    private final VimNetworkResourcesApi api = new VimNetworkResourcesApi();

    
    /**
     * Retrieve free vlan tag from VIM domain
     *
     * Retrieve free vlan tag from VIM domain
     *
     * @throws ApiException
     *          if the Api call fails
     */
    @Test
    public void freeVlanTest() throws ApiException {
        List<BigDecimal> response = api.freeVlan();

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
    public void queryNFVIPoPNetworkInformationTest() throws ApiException {
        Filter nfviPopNetworkInformationRequest = null;
        List<NfviPop> response = api.queryNFVIPoPNetworkInformation(nfviPopNetworkInformationRequest);

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
    public void queryNetworkCapacityTest() throws ApiException {
        QueryNetworkCapacityRequest queryNetworkCapacityRequest = null;
        CapacityInformation response = api.queryNetworkCapacity(queryNetworkCapacityRequest);

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