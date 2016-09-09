package org.licket.spring.surface.element.html;

import org.licket.spring.resource.ResourcesStorage;
import org.licket.surface.element.BaseElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import static org.licket.spring.surface.element.html.HtmlElementFactory.NAMESPACE;

/**
 * @author activey
 */
public class HeadElement extends BaseElement {

    private static final Logger LOGGER = LoggerFactory.getLogger(HeadElement.class);

    @Autowired
    private ResourcesStorage resourcesStorage;

    public HeadElement() {
        super("head", NAMESPACE);
    }

    @Override
    protected void onFinish() {
        resourcesStorage.getJavascriptResources().forEach(resource -> {
            LOGGER.debug("Using head JS resource: {}", resource.getName());

            ScriptElement scriptElement = new ScriptElement();
            scriptElement.setSrc(resourcesStorage.getResourceUrl(resource));
            addChildElement(scriptElement);
        });
    }

    @Override
    public BaseElement provideElement() {
        return this;
    }
}
