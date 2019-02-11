
export class CouponProduct {

    /**
       * ID
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
       * 报考（product表ID）
     */
    productId:number;

    /**
       * 报考编码
     */
    productNo:string;

    /**
       * 报考名称
     */
    productName:string;

    /**
       * 删除（0否1是）
     */
    deleted:boolean;

    /**
       * 删除时间
     */
    delTime:string;
}
