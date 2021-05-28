/*
 * Copyright 2014-present Open Networking Foundation
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.onosproject.net.intent;

import com.google.common.annotations.Beta;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import org.onosproject.core.ApplicationId;
import org.onosproject.net.Link;
import org.onosproject.net.NetworkResource;
import org.onosproject.net.ResourceGroup;
import org.onosproject.net.flow.DefaultTrafficSelector;
import org.onosproject.net.flow.DefaultTrafficTreatment;
import org.onosproject.net.flow.TrafficSelector;
import org.onosproject.net.flow.TrafficTreatment;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import static com.google.common.base.MoreObjects.firstNonNull;
import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Abstraction of connectivity intent for traffic matching some criteria.
 */
@Beta
public abstract class ConnectivityIntent extends Intent {

    // TODO: other forms of intents should be considered for this family:
    //   point-to-point with constraints (waypoints/obstacles)
    //   multi-to-single point with constraints (waypoints/obstacles)
    //   single-to-multi point with constraints (waypoints/obstacles)
    //   concrete path (with alternate)
    //   ...

    private final TrafficSelector selector;
    private final TrafficTreatment treatment;
    private final List<Constraint> constraints;

    /**
     * Creates a connectivity intent that matches on the specified selector
     * and applies the specified treatment.
     * <p>
     * Path will be optimized based on the first constraint if one is given.
     * </p>
     *
     * @param appId       application identifier
     * @param key         explicit key to use for intent
     * @param resources   required network resources (optional)
     * @param selector    traffic selector
     * @param treatment   treatment
     * @param constraints optional prioritized list of constraints
     * @param priority    priority to use for flows generated by this intent
     * @param resourceGroup resource group for this intent
     * @throws NullPointerException if the selector or treatment is null
     */
    protected ConnectivityIntent(ApplicationId appId,
                                 Key key,
                                 Collection<NetworkResource> resources,
                                 TrafficSelector selector,
                                 TrafficTreatment treatment,
                                 List<Constraint> constraints,
                                 int priority,
                                 ResourceGroup resourceGroup) {
        super(appId, key, resources, priority, resourceGroup);
        this.selector = checkNotNull(selector);
        this.treatment = checkNotNull(treatment);
        this.constraints = checkNotNull(constraints);
    }

    /**
     * Constructor for serializer.
     */
    protected ConnectivityIntent() {
        super();
        this.selector = null;
        this.treatment = null;
        this.constraints = Collections.emptyList();
    }

    /**
     * Abstract builder for connectivity intents.
     */
    public abstract static class Builder extends Intent.Builder {
        protected TrafficSelector selector = DefaultTrafficSelector.emptySelector();
        protected TrafficTreatment treatment = DefaultTrafficTreatment.emptyTreatment();
        protected List<Constraint> constraints = ImmutableList.of();

        /**
         * Creates a new empty builder.
         */
        protected Builder() {
        }

        /**
         * Creates a new builder pre-populated with the information in the given
         * intent.
         *
         * @param intent initial intent
         */
        protected Builder(ConnectivityIntent intent) {
            super(intent);

            this.selector(intent.selector())
                    .treatment(intent.treatment())
                    .constraints(intent.constraints());
        }

        @Override
        public Builder appId(ApplicationId appId) {
            return (Builder) super.appId(appId);
        }

        @Override
        public Builder key(Key key) {
            return (Builder) super.key(key);
        }

        @Override
        public Builder priority(int priority) {
            return (Builder) super.priority(priority);
        }

        /**
         * Sets the traffic selector for the intent that will be built.
         *
         * @param selector selector to use for built intent
         * @return this builder
         */
        public Builder selector(TrafficSelector selector) {
            this.selector = selector;
            return this;
        }

        /**
         * Sets the traffic treatment for the intent that will be built.
         *
         * @param treatment treatment to use for built intent
         * @return this builder
         */
        public Builder treatment(TrafficTreatment treatment) {
            this.treatment = treatment;
            return this;
        }

        /**
         * Sets the constraints for the intent that will be built.
         *
         * @param constraints constraints to use for built intent
         * @return this builder
         */
        public Builder constraints(List<Constraint> constraints) {
            this.constraints = ImmutableList.copyOf(constraints);
            return this;
        }
    }

    /**
     * Returns the match specifying the type of traffic.
     *
     * @return traffic match
     */
    public TrafficSelector selector() {
        return selector;
    }

    /**
     * Returns the action applied to the traffic.
     *
     * @return applied action
     */
    public TrafficTreatment treatment() {
        return treatment;
    }

    /**
     * Returns the set of connectivity constraints.
     *
     * @return list of intent constraints
     */
    public List<Constraint> constraints() {
        return constraints;
    }

    /**
     * Produces a collection of network resources from the given links.
     *
     * @param resources base resources
     * @param links collection of links
     * @return collection of resources
     */
    protected static Collection<NetworkResource> resources(Collection<NetworkResource> resources,
                                                           Collection<Link> links) {
        return ImmutableSet.<NetworkResource>builder()
                .addAll(firstNonNull(resources, ImmutableList.of()))
                .addAll(links)
                .build();
    }

    /**
     * Produces a collection of network resources from the given links.
     *
     * @param links collection of links
     * @return collection of link resources
     */
    protected static Collection<NetworkResource> resources(Collection<Link> links) {
        return ImmutableSet.copyOf(links);
    }

}