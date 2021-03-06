package org.licket.framework.hippo;

import org.mozilla.javascript.ast.PropertyGet;

/**
 * @author activey
 */
public class PropertyNameBuilder extends AbstractAstNodeBuilder<PropertyGet> {

    private final AbstractAstNodeBuilder left;
    private final NameBuilder right;

    private PropertyNameBuilder(AbstractAstNodeBuilder left, NameBuilder right) {
        this.left = left;
        this.right = right;
    }

    public static PropertyNameBuilder property(KeywordLiteralBuilder keywordLiteral, NameBuilder right) {
        return new PropertyNameBuilder(keywordLiteral, right);
    }

    public static PropertyNameBuilder property(FunctionCallBuilder functionCall, NameBuilder right) {
        return new PropertyNameBuilder(functionCall, right);
    }

    public static PropertyNameBuilder property(PropertyNameBuilder left, NameBuilder right) {
        return new PropertyNameBuilder(left, right);
    }

    public static PropertyNameBuilder property(NameBuilder left, NameBuilder right) {
        return new PropertyNameBuilder(left, right);
    }

    public PropertyGet build() {
        PropertyGet propertyGet = new PropertyGet();
        propertyGet.setTarget(left.build());
        propertyGet.setProperty(right.build());
        return propertyGet;
    }
}
