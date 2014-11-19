package com.base.utils.common;

import com.google.common.collect.Lists;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.log4j.Logger;
import org.reflections.Reflections;
import org.reflections.scanners.MethodAnnotationsScanner;
import org.reflections.scanners.Scanner;
import org.reflections.scanners.SubTypesScanner;
import org.reflections.scanners.TypeAnnotationsScanner;

import java.io.Serializable;
import java.lang.reflect.Modifier;
import java.util.List;
import java.util.Set;

/**
 * 只寻找包com.appcenter下的相关类或资源信息
 *
 * @author flym
 */
public class AppcenterClassFinder implements Serializable {
    static Logger logger = Logger.getLogger(AppcenterClassFinder.class);
	private static final String defaultPackageName = "com.base.utils.scheduleabout";
	private Reflections reflections;

	public AppcenterClassFinder() {
		Scanner[] scanners = {new TypeAnnotationsScanner(), new SubTypesScanner(), new MethodAnnotationsScanner()};
		reflections = new Reflections(defaultPackageName, scanners);
	}
    public AppcenterClassFinder(String packageName) {
        Scanner[] scanners = {new TypeAnnotationsScanner(), new SubTypesScanner(), new MethodAnnotationsScanner()};
        reflections = new Reflections(packageName, scanners);
    }

	public static AppcenterClassFinder getInstance() {
		return Internal.appcenterClassFinder;
	}
    public static AppcenterClassFinder getInstance(String packageName) {
        AppcenterClassFinder appcenterClassFinder = new AppcenterClassFinder(packageName);
        return appcenterClassFinder;
    }

	/** 用于懒加载appcenterClassFinder */
	private static class Internal {
		private static AppcenterClassFinder appcenterClassFinder = new AppcenterClassFinder();
	}

	public Reflections getClassFinder() {
		return reflections;
	}

	/** 找到所有的子类,不包括接口 */
	@SuppressWarnings("unchecked")
	public <T> List<Class<? extends T>> findSubClass(final Class<T> classInterface) {
		Set<Class<? extends T>> classSet = reflections.getSubTypesOf(classInterface);
		List<Class<? extends T>> classList = Lists.newArrayList();
		for(Class<? extends T> clazz : classSet) {
			int modifier = clazz.getModifiers();
			if(classInterface.isAssignableFrom(clazz) && !Modifier.isInterface(modifier) && !Modifier
					.isAbstract(modifier) && Modifier.isPublic(modifier)) {
				classList.add(clazz);
			}
		}
		return classList;
	}

	/** 找到所有的子接口,不包括当前接口 */
	@SuppressWarnings("unchecked")
	public <T> List<Class<? extends T>> findSubInterface(final Class<T> classInterface) {
		Set<Class<? extends T>> classSet = reflections.getSubTypesOf(classInterface);
		List<Class<? extends T>> classList = Lists.newArrayList();
		for(Class<? extends T> clazz : classSet) {
			int modifier = clazz.getModifiers();
			if(classInterface.isAssignableFrom(clazz) && Modifier.isInterface(modifier) && Modifier.isPublic(modifier)
					&& !clazz.equals(classInterface)) {
				classList.add(clazz);
			}
			;
		}
		return classList;
	}
}
