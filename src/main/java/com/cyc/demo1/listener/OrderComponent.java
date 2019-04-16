package com.cyc.demo1.listener;

import java.util.*;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.AnnotationAwareOrderComparator;
import org.springframework.core.annotation.AnnotationUtils;

/**
 * @author chenyuchuan
 */
public class OrderComponent<S> {
    private List<S> unordered = new ArrayList<>();
    private List<S> ordered = new ArrayList<>();
    private Comparator<? super S> comparator = new AnnotationAwareOrderComparator();
    private List<S> list = new ArrayList<>();

    OrderComponent() {}

    public void setComponents(List<? extends S> components) {
        this.unordered.clear();
        this.ordered.clear();
        Iterator<? extends S> listenerIterator = components.iterator();

        while (listenerIterator.hasNext()) {
            S s = listenerIterator.next();
            this.add(s);
        }

    }

    public void add(S item) {
        if (item instanceof Ordered) {
            if (!this.ordered.contains(item)) {
                this.ordered.add(item);
            }
        } else if (AnnotationUtils.isAnnotationDeclaredLocally(org.springframework.core.annotation.Order.class,
            item.getClass())) {
            if (!this.ordered.contains(item)) {
                this.ordered.add(item);
            }
        } else if (!this.unordered.contains(item)) {
            this.unordered.add(item);
        }

        Collections.sort(this.ordered, this.comparator);
        this.list.clear();
        this.list.addAll(this.ordered);
        this.list.addAll(this.unordered);
    }

    public Iterator<S> iterator() {
        return (new ArrayList<>(this.list)).iterator();
    }

    public Iterator<S> reverse() {
        ArrayList<S> result = new ArrayList<>(this.list);
        Collections.reverse(result);
        return result.iterator();
    }
}
