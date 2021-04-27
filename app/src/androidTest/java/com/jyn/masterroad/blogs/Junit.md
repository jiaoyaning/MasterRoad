# @Rule和@ClassRule
* @Rule注解是方法级别的，每个测试方法执行时都会执行被@Rule注解的成员变量的方法（类似于@Before）。
* @ClassRule注解是类级别的，测试类执行时仅会执行一次被@ClassRule注解的静态变量的方法（类似于@BeforeClass）。

>@BeforeClass – 在类中的任何测试方法开始之前只执行一次。public static void   
 @AfterClass – 所有测试用例执行完才执行一次。public static void  
 @Before – 在@Test之前执行，对于每一个测试方法都要执行一次。public void
 @After – 在@Test之后执行，释放资源，对于每一个测试方法都要执行一次。public void
 @Test – This is the test method to run, public void
>一个JUnit4的单元测试用例执行顺序为：   
 `@BeforeClass -> @Before -> @Test -> @After -> @AfterClass `
 每一个测试方法的调用顺序为：   
 `@Before -> @Test -> @After`  

## 捕获Exception = try catch
```java
public class ListTest {
  private final List<Object> list = new ArrayList<>();

  @Test(expected = IndexOutOfBoundsException.class)
  public void testIndexOutOfBoundsException() {
    list.get(0);
  }
}
```


