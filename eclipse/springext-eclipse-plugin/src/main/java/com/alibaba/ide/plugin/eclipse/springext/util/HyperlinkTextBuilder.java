package com.alibaba.ide.plugin.eclipse.springext.util;

import static com.alibaba.citrus.util.BasicConstant.*;
import static com.alibaba.citrus.util.CollectionUtil.*;
import static com.alibaba.citrus.util.ObjectUtil.*;

import java.util.Collections;
import java.util.LinkedList;
import java.util.Map;
import java.util.WeakHashMap;

import org.eclipse.jface.text.IRegion;
import org.eclipse.jface.text.hyperlink.IHyperlink;
import org.eclipse.ui.forms.events.HyperlinkAdapter;
import org.eclipse.ui.forms.events.HyperlinkEvent;
import org.eclipse.ui.forms.events.IHyperlinkListener;
import org.eclipse.ui.forms.widgets.FormText;
import org.eclipse.ui.forms.widgets.FormToolkit;

/**
 * 方便生成form text。
 * 
 * @author Michael Zhou
 */
public class HyperlinkTextBuilder {
    private final static Map<FormText, LinkedList<IHyperlinkListener>> listenersMapping = Collections
            .synchronizedMap(new WeakHashMap<FormText, LinkedList<IHyperlinkListener>>());

    private final Map<String, IHyperlinkListener> linksMapping = createHashMap();
    private final StringBuilder buf = new StringBuilder();
    private final FormToolkit toolkit;

    public HyperlinkTextBuilder(FormToolkit toolkit) {
        this.toolkit = toolkit;
    }

    public HyperlinkTextBuilder append(String s) {
        buf.append(s);
        return this;
    }

    public HyperlinkTextBuilder appendNewLine(String s) {
        buf.append("\n");
        return this;
    }

    public HyperlinkTextBuilder appendLink(String text, IHyperlink link) {
        return appendLink(text, link, null);
    }

    public HyperlinkTextBuilder appendLink(String text, final IHyperlink link, String linkStyle) {
        IHyperlinkListener listener = new HyperlinkAdapter() {
            @Override
            public void linkActivated(HyperlinkEvent e) {
                link.open();
            }
        };

        String key = identityHashCode(listener) + "";
        linksMapping.put(key, listener);
        buf.append(String.format("<a href=\"%s\"%s>%s</a>", key, linkStyle == null ? EMPTY_STRING : " " + linkStyle,
                text));

        return this;
    }

    public HyperlinkTextBuilder format(String format, String... args) {
        buf.append(String.format(format, (Object[]) args));
        return this;
    }

    public void setText(FormText control) {
        control.setText("<form>" + buf.toString() + "</form>", true, false);
        control.setHyperlinkSettings(toolkit.getHyperlinkGroup());
        addHyperlinkListener(control, new ListenerDelegator(linksMapping));
    }

    private void addHyperlinkListener(FormText control, IHyperlinkListener listener) {
        LinkedList<IHyperlinkListener> listeners = listenersMapping.get(control);

        if (listeners == null) {
            listeners = createLinkedList();
            listenersMapping.put(control, listeners);
        }

        for (IHyperlinkListener previousListener : listeners) {
            control.removeHyperlinkListener(previousListener);
        }

        listeners.add(listener);
        control.addHyperlinkListener(listener);
    }

    @Override
    public String toString() {
        return buf.toString();
    }

    public static abstract class AbstractHyperlink implements IHyperlink {
        public IRegion getHyperlinkRegion() {
            return null;
        }

        public String getTypeLabel() {
            return null;
        }

        public String getHyperlinkText() {
            return null;
        }
    }

    private static class ListenerDelegator extends HyperlinkAdapter {
        private final Map<String, IHyperlinkListener> linksMapping;

        public ListenerDelegator(Map<String, IHyperlinkListener> linksMapping) {
            this.linksMapping = linksMapping;
        }

        @Override
        public void linkActivated(HyperlinkEvent e) {
            IHyperlinkListener listener = linksMapping.get(e.getHref());

            if (listener != null) {
                listener.linkActivated(e);
            }
        }
    }
}
