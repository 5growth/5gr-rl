package rest;

import org.onlab.rest.AbstractWebApplication;

import java.util.Set;

public class P4PipeconfWebApplication extends AbstractWebApplication{
    @Override
    public Set<Class<?>> getClasses() {
        return getClasses(P4PipelineWebResource.class);
    }
}
