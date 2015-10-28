package com.guoguo.logic.json;

/**
 * Created by Administrator on 2015/9/29.
 */
public class SimpleJsonDataBean implements Cloneable{
    private MemClean memClean = new MemClean();

    public SimpleJsonDataBean() {

    }

    public SimpleJsonDataBean clone() {
        SimpleJsonDataBean bean = new SimpleJsonDataBean();
        bean.memClean = this.memClean.clone();
        return bean;
    }

    public MemClean getMemClean() {
        return this.memClean;
    }

    public void setMemClean(MemClean memClean) {
        this.memClean = memClean;
    }

    public static class MemClean implements Cloneable{
        private int open = 0;
        private int notifyBottom = 75;
        private int popcnt = 1;

        public MemClean () {

        }

        public MemClean clone() {
            MemClean bean = new MemClean();
            bean.open = this.open;
            bean.notifyBottom = this.notifyBottom;
            bean.popcnt = this.popcnt;
            return bean;
        }

        public int getOpen() {
            return open;
        }

        public void setOpen(int open) {
            this.open = open;
        }

        public int getNotifyBottom() {
            return this.notifyBottom;
        }

        public void setNotifyBottom(int notifyBottom) {
            this.notifyBottom = notifyBottom;
        }

        public int getPopcnt() {
            return this.popcnt;
        }

        public void setPopcnt(int popcnt) {
            this.popcnt = popcnt;
        }
    }
}
