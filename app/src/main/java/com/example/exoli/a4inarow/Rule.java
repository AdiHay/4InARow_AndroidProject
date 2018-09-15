package com.example.exoli.a4inarow;

abstract class Rule {
    private final int[] ruleIds;
    private int selectId =0;

    Rule(int[] ids) {
        ruleIds = ids;
        selectId = ids[0];
    }

    public int[] getRuleIds() {
        return ruleIds;
    }

    public int getSelectId() {
        return selectId;
    }

    public void setSelectId(int selectId) {
        this.selectId = selectId;
    }
}
