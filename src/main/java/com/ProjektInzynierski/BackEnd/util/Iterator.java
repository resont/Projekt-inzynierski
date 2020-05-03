package com.ProjektInzynierski.BackEnd.util;

import org.springframework.stereotype.Component;

@Component
public interface Iterator {
    public boolean hasNext();
    public Object next();
}
