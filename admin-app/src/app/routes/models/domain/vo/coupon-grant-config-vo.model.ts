
export class CouponGrantConfigVo {
    /**
       * 发放配置ID
     */
    id:number;
    /**
       * GUID
     */
    guid:string;
    /**
       * 优惠券（coupon表ID）
     */
    couponId:number;
    /**
       * 发放策略（1一次性发放，2分批发放）
     */
    grantStrategy:number;
    /**
       * 一次性发放节点（1购买，2收货，3评论，4超过15天）
     */
    grantNode:number;
    /**
       * 状态（0启用1禁用）
     */
    state:number;
    /**
       * 备注
     */
    remark:string;
    /**
       * 创建时间
     */
    createTime:string;
    /**
       * 删除（0否1是）
     */
    deleted:number;
    /**
       * 删除时间
     */
    delTime:string;
}
