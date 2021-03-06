package org.licket.framework.hippo;

import org.mozilla.javascript.ast.ElementGet;

/**
 * @author activey
 */
public class ArrayElementGetBuilder extends AbstractAstNodeBuilder<ElementGet> {

    private AbstractAstNodeBuilder<?> element;
    private AbstractAstNodeBuilder<?> target;

    private ArrayElementGetBuilder() {}

    public static ArrayElementGetBuilder arrayElementGet() {
        return new ArrayElementGetBuilder();
    }

    public ArrayElementGetBuilder element(StringLiteralBuilder stringLiteral) {
        this.element = stringLiteral;
        return this;
    }

    public ArrayElementGetBuilder target(PropertyNameBuilder propertyName) {
        this.target = propertyName;
        return this;
    }

    @Override
    public ElementGet build() {
        ElementGet elementGet = new ElementGet();
        elementGet.setElement(element.build());
        elementGet.setTarget(target.build());
        return elementGet;
    }
}
