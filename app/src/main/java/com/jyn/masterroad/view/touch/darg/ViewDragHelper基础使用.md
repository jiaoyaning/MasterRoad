> 本文由 [简悦 SimpRead](http://ksria.com/simpread/) 转码， 原文地址 [blog.csdn.net](https://blog.csdn.net/xingzhong128/article/details/79388085)

前言
--

Android 中拖动问题很常见但是也很复杂，往往令开发者不知所措，为此系统在 Support 包中专门封装了一个 ViewDragHelper 工具类方便开发者实现各种滑动效果。在使用这个开发类之前先简单的看一下完全手动的实现拖动该怎么做，通过手工实现拖动更好的理解 ViewDragHelper 工具类的使用。

手工拖动实现
------

如何移动一个视图对象呢，可以为这个视图对象设置 onTouchListener 方法，记录下最开始的 ACTION_DOWN 位置，在 ACTION_MOVE 事件里判断是否已经超出滑动最小范围 TOUCH_SLOP，超出就开始更新当前视图的位置，就好像视图跟随着用户的手指移动，用户拖动了当前的视图对象。实现的代码如下：

```
// 子视图自己监视用户触摸事件
text.setOnTouchListener(new View.OnTouchListener() {
    // 记录下上一次移动到的位置
    private int mLastX;
    private int mLastY;
    // 记录下最开始ACTION_DOWN的位置
    private int mDownX;
    private int mDownY;
    // 记录系统默认的滑动阈值
    private int mTouchSlop = ViewConfiguration.get(SingleActivity.this).getScaledTouchSlop();
    // 是否正在进行拖动操作
    private boolean mIsDragging = false;

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        int x = (int) event.getX(), y = (int) event.getY();
        switch (event.getActionMasked()) {
            case MotionEvent.ACTION_DOWN:
                // 记录初始按下位置
                mLastX = mDownX = (int) event.getX();
                mLastY = mDownY = (int) event.getY();
                Log.d(TAG, "ACTION_DOWN: x = " + mLastX + ", y = " + mLastY);
                break;
            case MotionEvent.ACTION_MOVE:
                // 如果用户当前没有在拖动而且用户在x轴或者y轴方向的移动距离超出了区别拖动与点击的最小距离，那么判断用户目前正在做拖动操作
                if (!mIsDragging && (Math.abs(mDownX - x) > mTouchSlop || Math.abs(mDownY - y) > mTouchSlop)) {
                    Log.d(TAG, "ACTION_MOVE: mIsDragging = " + mIsDragging + ", x = " + x + ", y = " + y);
                    mIsDragging = true;
                }

                Log.d(TAG, "ACTION_MOVE: mIsDragging = " + mIsDragging + ", x = " + x + ", y = " + y);

                // 如果用户正在做拖动操作，那么更新当前视图的位置
                if (mIsDragging) {
                    // 移动当前视图的位置
                    text.offsetLeftAndRight(x - mLastX);
                    text.offsetTopAndBottom(y - mLastY);
                }

                mLastX = x;
                mLastY = y;
                break;
            case MotionEvent.ACTION_CANCEL:
            case MotionEvent.ACTION_UP:
                Log.d(TAG, "ACTION_UP: x = " + mLastX + ", y = " + mLastY);
                mIsDragging = false;
                break;
        }
        status.setText(getString(R.string.location, (int) text.getX(), (int) text.getY()));
        return true;
    }
});
```

这里使用三种改变当前视图位置的方法：

```
// text.setX(text.getX() + x - mLastX);
// text.setY(text.getY() + y - mLastY);

text.offsetLeftAndRight(x - mLastX);
text.offsetTopAndBottom(y - mLastY);

// ViewGroup.MarginLayoutParams layoutParams = (ViewGroup.MarginLayoutParams) text.getLayoutParams();
// layoutParams.topMargin += y - mLastY;
// layoutParams.leftMargin +=  x - mLastX;
// text.requestLayout();
```

但是实际操作发现这三种方式实现的拖动效果并不好，问题是手指已经移动了很远，视图虽然移动了但是离用户手指还是比较远，个人猜测这可能跟视图不停的改变位置同时不停的接收新的触摸事件不同步导致的，因而可以把拖动操作的用户触摸事件交给父控件监视，由父控件来根据用户动作更新视图的位置。但是用户拖动父控件的其他部分会发现子视图依然跟着移动，这种情况不应该发生，所以需要定位最开始用户按下位置是否是子视图的位置：

```
// 这里使用父控件监视用户的触摸事件
rootView.setOnTouchListener(new View.OnTouchListener() {
    private int mLastX;
    private int mLastY;
    private int mDownX;
    private int mDownY;
    private int mTouchSlop = ViewConfiguration.get(MultiActivity.this).getScaledTouchSlop();
    private boolean mIsDragging = false;

    // 记录子视图的当前位置矩阵
    private Rect mTmpRect = new Rect();
    // 当前正在拖动的子视图
    private View mTargetView;

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        int x = (int) event.getX(), y = (int) event.getY();
        switch (event.getActionMasked()) {
            case MotionEvent.ACTION_DOWN:
                mLastX = mDownX = (int) event.getX();
                mLastY = mDownY = (int) event.getY();
                Log.d(TAG, "ACTION_DOWN: x = " + mLastX + ", y = " + mLastY);

                // 判断当前是按下在子视图的位置
                decideTargetView(x, y);
                break;
            case MotionEvent.ACTION_MOVE:
                if (!mIsDragging && (Math.abs(mDownX - x) > mTouchSlop || Math.abs(mDownY - y) > mTouchSlop)) {
                    Log.d(TAG, "ACTION_MOVE: mIsDragging = " + mIsDragging + ", x = " + x + ", y = " + y);
                    mIsDragging = true;
                }

                Log.d(TAG, "ACTION_MOVE: mIsDragging = " + mIsDragging + ", x = " + x + ", y = " + y);

                if (mIsDragging && mTargetView != null) {
                    int dx = x - mLastX, dy = y - mLastY;
                    mTargetView.offsetLeftAndRight(dx);
                    mTargetView.offsetTopAndBottom(dy);
                }

                mLastX = x;
                mLastY = y;
                break;
            case MotionEvent.ACTION_CANCEL:
            case MotionEvent.ACTION_UP:
                Log.d(TAG, "ACTION_UP: x = " + mLastX + ", y = " + mLastY);
                mIsDragging = false;
                mTargetView = null;
                break;
        }
        status.setText(getString(R.string.location, (int) text.getX(), (int) text.getY()));
        return true;
    }

    private void decideTargetView(int x, int y) {
        int count = rootView.getChildCount();
        for (int i = 0; i < count; i++) {
            View view = rootView.getChildAt(i);
            mTmpRect.set(view.getLeft(), view.getTop(), view.getRight(), view.getBottom());

            // 如果子视图的位置包含按下点，那么用户就在拖动这个子视图
            if (mTmpRect.contains(x, y)) {
                mTargetView = view;
                break;
            }
        }
    }
});
```

再为父视图多添加几个子视图，这时发现这些视图都能够被拖动，而且和用户手指同步效果非常好。

ViewDragHelper 简介
-----------------

ViewDragHelper 是 v4 包中添加的辅助自定义 ViewGroup 实现拖动操作的工具类，它目前有两个创建方法：

```
// 实际生成的方法，需要传递要监视用户触摸事件的ViewGroup和回调对象，回调对象里定义了用户的各种操作
public static ViewDragHelper create(ViewGroup forParent, Callback cb) {
    return new ViewDragHelper(forParent.getContext(), forParent, cb);
}

// 这个其实调用的上一个
public static ViewDragHelper create(ViewGroup forParent, float sensitivity, Callback cb) {
    final ViewDragHelper helper = create(forParent, cb);
    helper.mTouchSlop = (int) (helper.mTouchSlop * (1 / sensitivity));
    return helper;
}
```

ViewDragHelper.Callback 回调里的方法提供了用户实现自己逻辑的接口，这里介绍每个方法的意义：

```
public abstract static class Callback {
    /**
     *当拖动状态改变的时候回调，主要的回调状态包含：
     * STATE_IDLE: 静止状态
     * STATE_DRAGGING：用户正在拖动状态
     * STATE_SETTLING: 用户拖动放手之后将子视图安置到最终位置的中间状态
     */
    public void onViewDragStateChanged(int state) {}

    /**
     * 当子视图在拖动或者安置状态位置发生变化时候的回调
     */
    public void onViewPositionChanged(View changedView, int left, int top, int dx, int dy) {}

    /**
     * 用户当前正在拖动的子视图回调，相当于前面的decideTargetView过程
     */
    public void onViewCaptured(View capturedChild, int activePointerId) {}

    /**
    * 当View不再被拖动的时候回调，这个接口提供了fling抛动时候的速度值。
    * 实现的代码可以决定继续抛动还是让View进行安置操作。
    * 如果要进行安置操作可以调用settleCapturedViewAt(int, int)或者flingCapturedView(int, int, int, int)，
    * 调用这两个方法之后ViewDragHelper就会进入STATE_SETTLING状态，
    * 直到整个安置操作完全结束才会到达STATE_IDLE状态。
    * 如果以上方法一个也没有调用那么会直接进入STATE_IDLE状态。
    **/
    public void onViewReleased(View releasedChild, float xvel, float yvel) {}

    /**
     * 当用户没有拖动子视图时触碰到ViewGroup某一个边沿就会回调。触碰的边沿常量包括：
     * EDGE_LEFT：左边沿
     * EDGE_TOP：上边沿
     * EDGE_RIGHT：右边沿
     * EDGE_BOTTOM：下边沿
     */
    public void onEdgeTouched(int edgeFlags, int pointerId) {}

    /**
     * 当给定的边沿被锁定的时候回调。这个方法返回true的时候代表锁定边沿，
     * 否则边沿处于未锁定状态。默认返回false不锁定指定边沿。
     */
    public boolean onEdgeLock(int edgeFlags) {
        return false;
    }

    /**
    * 当用户没有拖动子视图而触摸到某个边沿并且开始拖动时触发回调，
    * 边沿常量这里不再赘述。
     */
    public void onEdgeDragStarted(int edgeFlags, int pointerId) {}

    /**
     * 当决定子视图的z-order顺序的时候回调，z-order越大表明子视图越靠近用户，
     * 也就能够覆盖在其他比它小的子视图上，默认根据添加到ViewGroup的位置决定z-order
     */
    public int getOrderedChildIndex(int index) {
        return index;
    }

    /**
    * 返回可拖动子视图在水平方向的移动量级，如果子视图横向无法拖动就返回0。
    * 这个方法的量级只是针对向Button这样的clickable是true的子视图，
    * 因为点击和拖动两者判断可能冲突，需要返回非零值表明clickable对象是可以拖动的
     */
    public int getViewHorizontalDragRange(View child) {
        return 0;
    }

    /**
     * 返回可拖动子视图在竖直方向的移动量级，如果子视图竖向无法拖动就返回0。
     * 这个方法的量级只是针对向Button这样的clickable是true的子视图，
     * 因为点击和拖动两者判断可能冲突，需要返回非零值表明clickable对象是可以拖动的
     */
    public int getViewVerticalDragRange(View child) {
        return 0;
    }

    /**
     * 当用户表明想要移动指定的子视图时回调，如果允许用户移动子视图回调需要返回true，
     * 这个回调方法会被多次调用来确定其他手指需要拖动的已经被当前手指拖动子视图，
     * 如果这个方法返回true，那么onViewCaptured(android.view.View, int)方法会被调用
     */
    public abstract boolean tryCaptureView(View child, int pointerId);

    /**
     * 限制被拖动的子视图在水平轴（x轴）方向的位置
     * 默认实现返回0代表不允许横轴方向移动，实现该回调的子类需要重写这个方法默认实现
     */
    public int clampViewPositionHorizontal(View child, int left, int dx) {
        return 0;
    }

    /**
    * 限制被拖动的子视图在竖直轴（y轴）方向的位置
    * 默认实现返回0代表不允许移动，实现该回调的子类需要重写这个方法默认实现
    */
    public int clampViewPositionVertical(View child, int top, int dy) {
        return 0;
    }
}
```

ViewDragHelper 实现抽屉菜单
---------------------

明白了 Callback 中每个回调接口的含义，现在先实现自定义的 ViewGroup 里的拖动多个对象操作。只需要在 tryCaptureView 方法里返回 true，那么对应的 child 就能够被拖动，同时对那些普通的子视图 clamp 方法返回的就是它们将要到达的 top 和 left 值。对于抽屉菜单需要明白它属于边沿触发的拖动对象，所以为 ViewDragHelper.setEdgeTrackingEnabled(ViewDragHelper.EDGE_LEFT); 设置左边缘触发，在 onEdgeDragStarted 方法里直接 helper.captureChildView(menu, pointerId); 设置为拖动对象，最后需要注意抽屉菜单只能左右滚动而且 left 最大值只能到 0，top 始终为 0，实现代码如下：

```
public class DraggerLayout extends FrameLayout {
    private static final String TAG = "DraggerLayout";

    private ViewDragHelper helper;

    public DraggerLayout(@NonNull Context context) {
        this(context, null);
    }

    public DraggerLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DraggerLayout(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        helper = ViewDragHelper.create(this, new ViewDragHelper.Callback() {
            @Override
            public boolean tryCaptureView(View child, int pointerId) {
                // 只有包含移动的TextView
                if (child instanceof TextView) {
                    if (((TextView) child).getText().toString().contains("移动")) {
                        return true;
                    }
                }

                // 抽屉菜单布局可以被拖动
                if (child instanceof LinearLayout) {
                    return true;
                }
                return false;
            }

            @Override
            public int clampViewPositionHorizontal(View child, int left, int dx) {
                Log.d(TAG, "left = " + left + ", dx = " + dx);
                // 限制抽屉菜单的横向位置最大只能是0
                if (child instanceof LinearLayout && left > 0) {
                    return 0;
                }
                return left;
            }

            @Override
            public int clampViewPositionVertical(View child, int top, int dy) {
                Log.d(TAG, "top = " + top + ", dy = " + dy);
                // 限制抽屉菜单的竖向位置只能是0
                if (child instanceof LinearLayout) {
                    return 0;
                }
                return top;
            }

            @Override
            public void onEdgeDragStarted(int edgeFlags, int pointerId) {
                super.onEdgeDragStarted(edgeFlags, pointerId);

              // 由于边沿拖动并不会调用tryCaptureView，需要用户直接调用captureChildView，
              // 也就相当于直接指定targetView为抽屉菜单布局
              helper.captureChildView(getChildAt(getChildCount() - 1), pointerId);
            }

            @Override
            public void onViewReleased(View releasedChild, float xvel, float yvel) {
                super.onViewReleased(releasedChild, xvel, yvel);

                // 在用户释放拖动时，如果拖动超出抽屉菜单的一半那么抽屉菜单全部展示，否则隐藏抽屉菜单
                if (releasedChild instanceof LinearLayout) {
                    int left = Math.abs(releasedChild.getLeft());
                    if (left < CommonUtils.dp2px(125)) {
                        helper.smoothSlideViewTo(releasedChild, 0, 0);
                    } else {
                        helper.smoothSlideViewTo(releasedChild, -CommonUtils.dp2px(250), 0);
                    }
                    invalidate();
                }
            }
        });

        // 允许左边沿的拖动
        helper.setEdgeTrackingEnabled(ViewDragHelper.EDGE_LEFT);
    }

    @Override
    public void computeScroll() {
        super.computeScroll();
        if (helper != null && helper.continueSettling(true)) {
            invalidate();
        }
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        // 是否截获触摸事件发送给ViewDragHelper来实现
        return helper.shouldInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        // 处理用户的触摸时间，记得一定要返回true，否则后续的触摸事件不会被发送过来
        helper.processTouchEvent(event);
        return true;
    }
}
```

上面代码里的 smoothSlideViewTo 查看源码发现它会调用 forceSettleCapturedViewAt 方法，也就安置子视图方法，这个方法内部使用 Scroller 实现平滑滑动效果，所以在 computeScroll 方法里还要调用 helper.continueSettling 方法实现后续的放置效果。查看所有的实现代码请点击[查看代码](https://github.com/744719042/scroll/tree/master/app/src/main/java/com/example/scroll)，实现的最终效果如下：  
![](https://img-blog.csdn.net/20180227143719919?watermark/2/text/aHR0cDovL2Jsb2cuY3Nkbi5uZXQveGluZ3pob25nMTI4/font/5a6L5L2T/fontsize/400/fill/I0JBQkFCMA==/dissolve/70)