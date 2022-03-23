package com.jyn.leetcode;

public class 快速排序 {

    public static void main(String[] args) {

    }

    /**
     * 快排核心是：找一个标杆，使其左边的元素都比其小，右边的元素都比其大。
     *
     *
     */
    public static void quickSort(int[] num, int left, int right) {
        //如果left等于right，即数组只有一个元素，直接返回
        if (left >= right) {
            return;
        }
        //设置最左边的元素为基准值
        int key = num[left];
        //数组中比key小的放在左边，比key大的放在右边，key值下标为i
        int i = left;
        int j = right;
        while (i < j) {
            /*
             * while 循环中的 >= 和 <= 表示为 true 时会执行内部代码
             * 也就是说，会略过当前的标杆数
             */

            //j向左移，直到遇到比key小的值
            while (num[j] >= key && i < j) {
                j--;
            }
            //i向右移，直到遇到比key大的值
            while (num[i] <= key && i < j) {
                i++;
            }
            //i和j指向的元素交换
            if (i < j) {
                int temp = num[i];
                num[i] = num[j];
                num[j] = temp;
            }
        }

        /*
         * 由于标杆数并未参与排序，所以要把自身放到左右区分的位置，也就是left所在的位置
         *
         * 为什么会是left呢？
         *   因为是right先-- ，然后才是left--，所以left才是最后一个小于标杆数的数字
         *   其实此时的 left 和 right 相同
         *
         * 把标杆数放到其该有的中间点位置，就可以开始左右子树的排序了。
         */
        num[left] = num[i];
        num[i] = key;
        quickSort(num, left, i - 1);
        quickSort(num, i + 1, right);
    }
}
