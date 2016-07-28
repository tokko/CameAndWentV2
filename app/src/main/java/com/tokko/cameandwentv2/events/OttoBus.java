package com.tokko.cameandwentv2.events;

import com.squareup.otto.Bus;

import org.androidannotations.annotations.EBean;

/**
 * Created by Andreas on 28/07/2016.
 */

// Declare the bus as an enhanced bean
@EBean(scope = EBean.Scope.Singleton)
public class OttoBus extends Bus {

}