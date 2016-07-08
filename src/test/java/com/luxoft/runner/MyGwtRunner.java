package com.luxoft.runner;


import com.googlecode.gwt.test.GwtRunner;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.List;

public class MyGwtRunner extends GwtRunner {
    public MyGwtRunner(Class<?> clazz) throws Throwable {
        super(clazz);
    }

    static {
        URLClassLoader classLoader = (URLClassLoader) MyGwtRunner.class.getClassLoader();

        try {
            URL[] urls = getClassPath();
            ClassLoader cl = URLClassLoader.newInstance(urls, classLoader);
            Thread.currentThread().setContextClassLoader(cl);
        } catch (MalformedURLException e) {
            throw new IllegalStateException(e);
        }

    }

    private static URL[] getClassPath() throws MalformedURLException {
        String classPath = System.getProperty("java.class.path");
        String pathSeparator = System.getProperty("path.separator");
        String[] array = classPath.split(pathSeparator);

        List<URL> files = new ArrayList<URL>();
        for (String a : array) {
            files.add(new File(a).toURI().toURL());
        }
        return files.toArray(new URL[files.size()]);
    }
}
