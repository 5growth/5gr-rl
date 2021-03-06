/*
 * Prometheus Manager API
 * The API of the Prometheus Manager.
 *
 * OpenAPI spec version: 0.1
 * Contact: m.capitani@nextworks.it
 *
 * NOTE: This class is auto generated by the swagger code generator program.
 * https://github.com/swagger-api/swagger-codegen.git
 * Do not edit the class manually.
 */


package io.swagger.client.api;

import io.swagger.client.ApiException;
import com.rl.extinterface.mon.swagger.client.model.Dashboard;
import com.rl.extinterface.mon.swagger.client.model.DashboardDescription;
import com.rl.extinterface.mon.swagger.client.model.DashboardQueryResult;
import com.rl.extinterface.mon.swagger.client.model.InlineResponse2002;
import org.junit.Test;
import org.junit.Ignore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * API tests for DashboardApi
 */
@Ignore
public class DashboardApiTest {

    private final DashboardApi api = new DashboardApi();

    
    /**
     * Delete dashboard
     *
     * 
     *
     * @throws ApiException
     *          if the Api call fails
     */
    @Test
    public void deleteDashboardTest() throws ApiException {
        String dashboardId = null;
        InlineResponse2002 response = api.deleteDashboard(dashboardId);

        // TODO: test validations
    }
    
    /**
     * Get all dashboards
     *
     * 
     *
     * @throws ApiException
     *          if the Api call fails
     */
    @Test
    public void getAllDashboardsTest() throws ApiException {
        DashboardQueryResult response = api.getAllDashboards();

        // TODO: test validations
    }
    
    /**
     * Get dashboard details
     *
     * 
     *
     * @throws ApiException
     *          if the Api call fails
     */
    @Test
    public void getDashboardTest() throws ApiException {
        String dashboardId = null;
        Dashboard response = api.getDashboard(dashboardId);

        // TODO: test validations
    }
    
    /**
     * Create new dashboard
     *
     * 
     *
     * @throws ApiException
     *          if the Api call fails
     */
    @Test
    public void postDashboardTest() throws ApiException {
        DashboardDescription dashboard = null;
        Dashboard response = api.postDashboard(dashboard);

        // TODO: test validations
    }
    
    /**
     * Update dashboard
     *
     * 
     *
     * @throws ApiException
     *          if the Api call fails
     */
    @Test
    public void putDashboardTest() throws ApiException {
        String dashboardId = null;
        Dashboard dashboard = null;
        Dashboard response = api.putDashboard(dashboardId, dashboard);

        // TODO: test validations
    }
    
}
