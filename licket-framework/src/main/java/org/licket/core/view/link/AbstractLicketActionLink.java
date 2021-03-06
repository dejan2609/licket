package org.licket.core.view.link;

import static com.google.common.base.Preconditions.checkNotNull;
import static org.licket.core.model.LicketModel.emptyModel;
import static org.licket.core.view.ComponentView.fromComponentContainerClass;
import static org.licket.framework.hippo.ArrayElementGetBuilder.arrayElementGet;
import static org.licket.framework.hippo.ExpressionStatementBuilder.expressionStatement;
import static org.licket.framework.hippo.FunctionCallBuilder.functionCall;
import static org.licket.framework.hippo.KeywordLiteralBuilder.thisLiteral;
import static org.licket.framework.hippo.NameBuilder.name;
import static org.licket.framework.hippo.PropertyNameBuilder.property;
import static org.licket.framework.hippo.StringLiteralBuilder.stringLiteral;

import org.licket.core.module.application.LicketComponentModelReloader;
import org.licket.core.module.application.LicketRemoteCommunication;
import org.licket.core.view.AbstractLicketComponent;
import org.licket.core.view.hippo.annotation.AngularClassFunction;
import org.licket.core.view.hippo.annotation.Name;
import org.licket.core.view.render.ComponentRenderingContext;
import org.licket.framework.hippo.BlockBuilder;
import org.licket.framework.hippo.NameBuilder;

/**
 * @author activey
 */
public abstract class AbstractLicketActionLink extends AbstractLicketComponent<Void> {

    @Name("licketRemote")
    private LicketRemoteCommunication communicationService;

    @Name("modelReloader")
    private final LicketComponentModelReloader modelReloader;

    public AbstractLicketActionLink(String id, LicketRemoteCommunication communicationService, LicketComponentModelReloader modelReloader) {
        super(id, Void.class, emptyModel(), fromComponentContainerClass(AbstractLicketActionLink.class));
        this.communicationService = checkNotNull(communicationService, "Communication service reference must not be null!");
        this.modelReloader = checkNotNull(modelReloader, "Model reloader service reference must not be null!");
    }

    @AngularClassFunction
    public void afterClick(@Name("response") NameBuilder response, BlockBuilder functionBody) {
        ComponentActionCallback componentActionCallback = new ComponentActionCallback();

        // invoking post action callback
        onAfterClick(componentActionCallback);

        // sending reload request for gathered components
        componentActionCallback.forEachToBeReloaded(component ->  {
            functionBody.appendStatement(
                    functionCall()
                            .target(property(name("modelReloader"), name("notifyModelChanged")))
                            .argument(stringLiteral(component.getCompositeId().getValue()))
                            .argument(arrayElementGet()
                                    .target(property(functionCall().target(property(name("response"), name("json"))), name("model")))
                                    .element(stringLiteral(component.getCompositeId().getValue())))
            );
        });
    }

    @AngularClassFunction
    public void handleClick(BlockBuilder functionBlock) {
        functionBlock.appendStatement(
                expressionStatement(
                        functionCall()
                                .target(property(name("licketRemote"), name("handleActionLinkClick")))
                                .argument(stringLiteral(getCompositeId().getValue()))
                                .argument(property(thisLiteral(), name("afterClick")))
                )
        );
    }

    protected void onAfterClick(ComponentActionCallback componentActionCallback) {}

    @Override
    protected void onRender(ComponentRenderingContext renderingContext) {
        // basically invokeAction() should handle all the stuff, the rest is done on javascript level
        renderingContext
            .onSurfaceElement(surfaceElement -> surfaceElement.setAttribute("(click)", "handleClick()"));
    }

    public final void invokeAction(ComponentActionCallback componentActionCallback) {
        onInvokeAction();
        onAfterClick(componentActionCallback);
    }

    protected abstract void onInvokeAction();

}
