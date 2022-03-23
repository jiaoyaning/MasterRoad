package com.jyn.masterroad

import org.hamcrest.CustomMatcher
import org.hamcrest.Matchers.*
import org.junit.Assert.assertThat
import org.junit.Test
import kotlin.collections.ArrayList
import kotlin.collections.HashMap


/*
 * JUnit 4.11 and later 自动集成 core
 *
 * Android 单元测试第六篇(Hamcrest 匹配器)
 * https://www.jianshu.com/p/6f44a5e2bf5b
 */
class HamCrestTest {
    @Test
    fun hamCrestTest() {
        //1. 字符串相关
        assertThat("myValue", startsWith("my"))
        assertThat("myValue", containsString("Val"))
        assertThat("myValue", endsWith("e"))
        assertThat("myValue", equalTo("myValue"))
        assertThat("myValue", anything()) //怎样都是通过
        assertThat("myValue", anything("怎样都是通过"))


        //2. 定义相关，比如某位是什么，不是什么，等于什么,不等于什么
        assertThat(1, equalTo(1))
        assertThat("myValue", instanceOf(String::class.java))
        assertThat(1, not(2))
        assertThat(null, nullValue())
        assertThat("myValue", notNullValue())
        assertThat("myValue", sameInstance("myValue")) //和 theInstance(T)一样


        //3. 集合相关 Iterable
        //3.1 everyItem 每个 item 都要符合条件
        assertThat(listOf("bar", "baz"), everyItem(startsWith("ba")))
        //3.2 hasItem 至少有一个 item 都符合条件，或者集合中有这个 item，参数可以是 T 也可以是匹配器
        assertThat(listOf("foo", "bar"), hasItem("bar"))
        assertThat(listOf("foo", "bar"), hasItem(startsWith("fo")))
        //3.3 hasItems 是hasItem复数版本，支持多 T 类型参数和多匹配器参数
        assertThat(listOf("foo", "bar", "baz"), hasItems("baz", "foo"))
        assertThat(listOf("foo", "bar", "baz"), hasItems(endsWith("z"), endsWith("o")))

        //组合匹配器，一般都支持多个参数，虽然下面的提供的是使用两个参数的例子
        //1. allOf 全部条件都需要满足
        assertThat("myValue", allOf(startsWith("my"), containsString("Val")))
        //2. anyOf 满足其中一个条件即可
        assertThat("myValue", anyOf(startsWith("foo"), containsString("Val")))
        //3. both().and() 满足两个条件，为 allOf 的真子集
        assertThat("fab", both(containsString("a")).and(containsString("b")))
        //4. either().or() 满足一个条件，为 anyOf 的真子集
        assertThat("fab", either(containsString("a")).or(containsString("b")))


        //辅助断言，对于机器来说没什么用，只是让语句读起来更加像自然语言
        //1. describedAs 增加断言辅助描述，增强可读性，一旦断言不通过，将直接打印描述内容到控制台。
        //比如下面这个例子，看完之后我们就知道这个断言辅助描述是想告诉我们为什么期待的值是2.
        //等同于 assertThat(2, equalTo(2));
        assertThat(2, describedAs("1 + 1 must equal 2", equalTo(2)))

        //2. is 又是一个语法糖，增加可读性而已
        assertThat("foo", `is`(equalTo("foo")))
        //2.0 如果里面的匹配器是 equalTo，则可以简写
        assertThat("foo", `is`("foo"))
        //3. isA 又是一个语法糖,不过参数只能是 Class<T>
        //其实就是assertThat("foo", is(instanceOf(String.class)))的简写
        assertThat("foo", isA(String::class.java))


        //自定义匹配器, 继承自CustomMatcher，实现 matches 方法即可
        val aNonEmptyString: CustomMatcher<String?> = object : CustomMatcher<String?>("a non empty string") {
                override fun matches(item: Any?): Boolean {
                    return item is String && item.isNotEmpty()
                }
            }
        assertThat("foo", aNonEmptyString)
    }

    /**
     * 以上是core部分，意思就是使用Junit 4.12自带依赖的Hamcrest即可使用，不过需要手动导包，而且是很多包
     * ，下面补充一下其它常用的匹配器，属于core之外的了。
     */
    @Test
    fun hamcrestAll() {
        //array,针对数组每一项进行测试 each matcher[i] is satisfied by array[i]，条件成立仅当匹配器个数等于数组元素个数，且每个匹配器都通过
        //数组类型
        assertThat(arrayOf(1, 2, 3), `is`(array(equalTo(1), equalTo(2), equalTo(3))))
        //包含所有内容，不需要按照顺序，和array不一样
        assertThat(arrayOf(1, 2, 3), arrayContainingInAnyOrder(3, 2, 1))
        assertThat(arrayOf("foo", "bar"), hasItemInArray(startsWith("ba")))
        assertThat(arrayOf(1, 2, 3), arrayWithSize(3)) //对应 Collection 类型的 hasSize()
        assertThat(arrayOfNulls<String>(0), emptyArray()) //对应 Collection 的 empty()

        //Iterable类型也有上面相应的 API，下面举两个例子
        assertThat(listOf("foo", "bar"), hasSize(2))
        assertThat(ArrayList<String>(), `is`(empty()))

        //map类型
        val map: HashMap<String, String> = HashMap()
        map["bar"] = "foo"
        map["name"] = "Mango"
        //是否包含特定键值对
        assertThat(map, hasEntry("bar", "foo"))
        assertThat(map, hasEntry(equalTo("bar"), equalTo("foo")))
        assertThat(map, hasKey(equalTo("bar")))
        assertThat(map, hasValue(equalTo("foo")))

        //期望的值是否属于某个集合
        assertThat("foo", isIn(listOf("bar", "foo")))

        //double 类型
        //误差在正负0.04内算通过
        assertThat(1.03, `is`(closeTo(1.0, 0.04)))
        assertThat(2, greaterThan(1))
        assertThat(1, greaterThanOrEqualTo(1))
        assertThat(1, lessThan(2))
        assertThat(1, lessThanOrEqualTo(1))

        //text 类型
        assertThat("Foo", equalToIgnoringCase("FOO"))
        assertThat("   my\tfoo  bar ", equalToIgnoringWhiteSpace(" my  foo bar"))
        assertThat("", isEmptyString())
        assertThat(null, isEmptyOrNullString())
    }
}