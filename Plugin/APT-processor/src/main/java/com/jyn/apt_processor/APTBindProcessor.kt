package com.jyn.apt_processor

import com.jyn.apt_annotation.APTBindView
import javax.annotation.processing.*
import javax.lang.model.element.Element
import javax.lang.model.element.TypeElement
import javax.lang.model.element.VariableElement
import javax.lang.model.util.Elements
import javax.lang.model.util.Types
import javax.lang.model.element.ElementKind

/*
 * 注解处理器
 *
 * Java中的插件自动发现机制
 * https://blog.csdn.net/luoyu0817/article/details/84726145
 *
 * （译）JavaPoet 官方教程
 * https://juejin.cn/post/6844904022600597517
 *
 * Android进阶——Java注解实战之APT构建模块化的第一步
 * https://juejin.cn/post/6844903841595572232
 *
 * ./gradlew :app:kaptDebugKotlin
 */
class APTBindProcessor : AbstractProcessor() {
    private lateinit var filer: Filer           //文件管理工具类，可以用于生成java源文件。
    private lateinit var elementUtils: Elements // Element处理工具类，获取Element的信息
    private lateinit var messager: Messager     //用于报告错误，警告和其他通知的消息
    private lateinit var typeUtils: Types       // 类型处理工具类，
    private lateinit var options: Map<String, String>   // 选项参数，即在gradle文件中配置选项参数值 annotationProcessorOptions，

    override fun init(processingEnv: ProcessingEnvironment) {
        super.init(processingEnv)
        filer = processingEnv.filer
        elementUtils = processingEnv.elementUtils
        messager = processingEnv.messager
        typeUtils = processingEnv.typeUtils
        options = processingEnv.options
    }

    override fun process(
        annotations: MutableSet<out TypeElement>,
        roundEnv: RoundEnvironment
    ): Boolean {
        println("注解处理器开始工作")

        //返回使用给定注释类型注释的元素
        val elements: Set<Element> = roundEnv.getElementsAnnotatedWith(APTBindView::class.java)
        /*
         * 编译时注解学习一之 Element元素
         * https://blog.csdn.net/u010126792/article/details/95614328
         *
         * 编译时注解学习三之 注解处理器AbstractProcessor工具和Element属性简述
         * https://blog.csdn.net/u010126792/article/details/96713820
         *
         * ExecutableElement:表示类或者接口中的方法，构造函数或者初始化器。
         *  PackageElement :表示包程序元素
         *  TypeELement:表示一个类或者接口元素
         *  TypeParameterElement:表示类，接口，方法的泛型类型例如T。
         *  VariableElement：表示字段，枚举常量，方法或者构造函数参数，局部变量，资源变量或者异常参数。
         */
        elements.forEach { element ->
            /**
             * 读取build.gradle配置的常量
             *  javaCompileOptions {
             *      annotationProcessorOptions {
             *          arguments = ["xxxx":"xxxxx", "CLASSNAME":"lidxclassname"]
             *      }
             *  }
             */
            println("options ——>$options")

            /**
             * 元素的类型
             * [ElementKind]
             */
            println("ElementKind ——> ${element.kind} ")


            //1.获取包名
            val packageElement = elementUtils.getPackageOf(element)
            val pkName = packageElement.qualifiedName.toString()
            println("pkName ——> $pkName") //com.jyn.masterroad.utils.aop

            //2.获取包装类类型，及当前注解处在那个类中
            val enclosingElement = element.enclosingElement as TypeElement
            val enclosingName = enclosingElement.qualifiedName.toString()
            println("enclosingName ——> $enclosingName") //com.jyn.masterroad.utils.aop.AOPActivity

            //因为BindView只作用于filed，所以这里可直接进行强转
            val bindViewElement = element as VariableElement
            //3.获取注解的成员变量名
            val bindViewFiledName = bindViewElement.simpleName.toString()
            println("bindViewFiledName ——> $bindViewFiledName") //btnApt
            //3.获取注解的成员变量类型
            val bindViewFiledClassType = bindViewElement.asType().toString()
            println("bindViewFiledClassType ——> $bindViewFiledClassType") //android.widget.Button

            //4.获取注解元数据
            val bindView: APTBindView = element.getAnnotation(APTBindView::class.java)
            val bindViewValue: Int = bindView.value
            println("bindViewValue ——> $bindViewValue") // R.id.aop_btn_apt 对应的值

            //最后一步 生成文件
            createBindFile()
        }
        return false
    }

    /**
     * 添加限制：限制只有那些注解才能被使用
     */
    override fun getSupportedAnnotationTypes(): Set<String> {
        return setOf(APTBindView::class.java.canonicalName)
    }

    private fun createBindFile() {

    }
}