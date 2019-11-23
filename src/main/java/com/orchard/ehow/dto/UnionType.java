package com.orchard.ehow.dto;

/**
 * @author Orchard Chang
 * @date 2019/11/23 0023 22:11
 * @Description
 */
public enum UnionType {
    INDEPENDENCE("独立申报", "0"),
    AS_LEADER("牵头公司", "1"),
    AS_FOLLOWER("联合申报公司", "2"),
    ;
    private String unionTypename;
    private String unionTypeCode;

    UnionType(String unionTypename, String unionTypeCode) {
        this.unionTypeCode = unionTypeCode;
        this.unionTypename = unionTypename;
    }

    public String getUnionTypeCode() {
        return unionTypeCode;
    }

    public void setUnionTypeCode(String unionTypeCode) {
        this.unionTypeCode = unionTypeCode;
    }

    public String getUnionTypename() {
        return unionTypename;
    }

    public void setUnionTypename(String unionTypename) {
        this.unionTypename = unionTypename;
    }
}
