package com.jyn.apt_processor

import javax.annotation.processing.AbstractProcessor
import javax.annotation.processing.RoundEnvironment
import javax.lang.model.element.TypeElement
/**
 * 注解处理器
 *
 * Java中的插件自动发现机制
 * https://blog.csdn.net/luoyu0817/article/details/84726145
 */
class APTBindProcessor : AbstractProcessor() {
    override fun process(
        annotations: MutableSet<out TypeElement>?,
        roundEnv: RoundEnvironment?
    ): Boolean {
        return true
    }

    /**
     * 添加限制：限制只有那些注解才能被使用
     */
    override fun getSupportedAnnotationTypes(): MutableSet<String> {
        return super.getSupportedAnnotationTypes()
    }
}