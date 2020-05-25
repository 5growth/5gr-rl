package io.swagger.api.intent.model;


public class IntentModel {
    private String appId = null;
    private ingressPoint ingressPoint = null;
    private egressPoint egressPoint = null;

    private Signal signal = null;

    //signal & suggestedPath will be added

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public ingressPoint getIngressPoint() {
        return ingressPoint;
    }

    public void setIngressPoint(ingressPoint ingressPoint) {
        this.ingressPoint = ingressPoint;
    }

    public egressPoint getEgressPoint() {
        return egressPoint;
    }

    public void setEgressPoint(egressPoint egressPoint) {
        this.egressPoint = egressPoint;
    }

    public Signal getSignal() {
        return signal;
    }

    public void setSignal(Signal signal) {
        this.signal = signal;
    }
}
