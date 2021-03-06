package org.licket.core.module.platform;

import static org.licket.framework.hippo.NameBuilder.name;
import static org.licket.framework.hippo.PropertyNameBuilder.property;
import org.licket.core.view.hippo.ngmodule.AngularModule;
import org.licket.framework.hippo.PropertyNameBuilder;

/**
 * @author activey
 */
public class BrowserModule implements AngularModule {

    @Override
    public PropertyNameBuilder angularName() {
        return property(property(name("ng"), name("platformBrowser")), name("BrowserModule"));
    }
}
